package uz.kuvondikov.clickup.service.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.kuvondikov.clickup.dto.MemberDTO;
import uz.kuvondikov.clickup.dto.MemberEditDTO;
import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.workspace.TeamDTO;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceCreateDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceUpdateDto;
import uz.kuvondikov.clickup.entity.*;
import uz.kuvondikov.clickup.enums.WorkspacePermissionName;
import uz.kuvondikov.clickup.enums.WorkspaceRoleName;
import uz.kuvondikov.clickup.exception.BadRequestException;
import uz.kuvondikov.clickup.exception.NotFoundException;
import uz.kuvondikov.clickup.mapper.WorkspaceMapper;
import uz.kuvondikov.clickup.repository.*;
import uz.kuvondikov.clickup.service.EmailService;
import uz.kuvondikov.clickup.service.WorkspaceService;
import uz.kuvondikov.clickup.service.base.AbstractService;
import uz.kuvondikov.clickup.service.base.BaseService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.kuvondikov.clickup.constant.ErrorMessages.*;

@Service
public class WorkspaceServiceImpl extends AbstractService<WorkspaceRepository, WorkspaceMapper> implements BaseService, WorkspaceService {

    private final WorkspaceUserRepository workspaceUserRepository;
    private final WorkspaceRoleRepository workspaceRoleRepository;
    private final WorkspacePermissionRepository workspacePermissionRepository;
    private final AuthUserRepository authUserRepository;
    private final EmailService emailService;

    public WorkspaceServiceImpl(WorkspaceRepository repository, WorkspaceMapper mapper, WorkspaceUserRepository workspaceUserRepository1, WorkspaceRoleRepository workspaceRoleRepository, WorkspacePermissionRepository workspacePermissionRepository, AuthUserRepository authUserRepository, EmailService emailService) {
        super(repository, mapper);
        this.workspaceUserRepository = workspaceUserRepository1;
        this.workspaceRoleRepository = workspaceRoleRepository;
        this.workspacePermissionRepository = workspacePermissionRepository;
        this.authUserRepository = authUserRepository;
        this.emailService = emailService;
    }

    private static void buildWorkspacePermissions(WorkspacePermissionName[] workspacePermissionNames, ArrayList<WorkspacePermission> ownerPermissions, Result result, ArrayList<WorkspacePermission> adminPermissions, ArrayList<WorkspacePermission> memberPermissions, ArrayList<WorkspacePermission> guestPermissions) {
        for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
            if (workspacePermissionName.workspaceRoleNames.contains(WorkspaceRoleName.WORKSPACE_ROLE_OWNER)) {
                ownerPermissions.add(WorkspacePermission.builder().workspaceRole(result.workspaceOwner()).workspacePermissionName(workspacePermissionName).build());
            }
            if (workspacePermissionName.workspaceRoleNames.contains(WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)) {
                adminPermissions.add(WorkspacePermission.builder().workspaceRole(result.workspaceAdmin()).workspacePermissionName(workspacePermissionName).build());
            }
            if (workspacePermissionName.workspaceRoleNames.contains(WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)) {
                memberPermissions.add(WorkspacePermission.builder().workspaceRole(result.workspaceMember()).workspacePermissionName(workspacePermissionName).build());
            }
            if (workspacePermissionName.workspaceRoleNames.contains(WorkspaceRoleName.WORKSPACE_ROLE_GUEST)) {
                guestPermissions.add(WorkspacePermission.builder().workspaceRole(result.workspaceGust()).workspacePermissionName(workspacePermissionName).build());
            }
        }
    }

    @Override
    public Long create(WorkspaceCreateDto createDto, AuthUser owner) {
        if (repository.existsByNameAndOwnerAndDeletedFalse(createDto.getName(), owner))
            throw new BadRequestException(owner.getFirstname() + WORKSPACE_ALREADY_EXISTS + createDto.getName());
        Result result = createWorkspace(createDto, owner);
        WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();
        saveWorkspacePermissions(workspacePermissionNames, result);
        WorkspaceUser user = WorkspaceUser.builder().workspace(result.workspace()).user(owner).workspaceRole(result.workspaceOwner()).invitedAt(new Timestamp(System.currentTimeMillis())).joinedAt(new Timestamp(System.currentTimeMillis())).build();
        workspaceUserRepository.save(user);
        return repository.save(result.workspace()).getId();
    }

    private void saveWorkspacePermissions(WorkspacePermissionName[] workspacePermissionNames, Result result) {
        ArrayList<WorkspacePermission> ownerPermissions = new ArrayList<>();
        ArrayList<WorkspacePermission> adminPermissions = new ArrayList<>();
        ArrayList<WorkspacePermission> memberPermissions = new ArrayList<>();
        ArrayList<WorkspacePermission> guestPermissions = new ArrayList<>();
        buildWorkspacePermissions(workspacePermissionNames, ownerPermissions, result, adminPermissions, memberPermissions, guestPermissions);
        workspacePermissionRepository.saveAll(ownerPermissions);
        workspacePermissionRepository.saveAll(adminPermissions);
        workspacePermissionRepository.saveAll(memberPermissions);
        workspacePermissionRepository.saveAll(guestPermissions);
    }

    @NotNull
    private Result createWorkspace(WorkspaceCreateDto createDto, AuthUser owner) {
        Workspace workspace = mapper.fromDTO(createDto);
        workspace.setOwner(owner);
        workspace.setCreatedBy(owner);
        workspace = repository.save(workspace);
        WorkspaceRole workspaceOwner = WorkspaceRole.builder().workspace(workspace).name(WorkspaceRoleName.WORKSPACE_ROLE_OWNER.name()).extendsWorkspaceRoleName(null).build();
        WorkspaceRole workspaceAdmin = WorkspaceRole.builder().workspace(workspace).name(WorkspaceRoleName.WORKSPACE_ROLE_ADMIN.name()).extendsWorkspaceRoleName(null).build();
        WorkspaceRole workspaceMember = WorkspaceRole.builder().workspace(workspace).name(WorkspaceRoleName.WORKSPACE_ROLE_MEMBER.name()).extendsWorkspaceRoleName(null).build();
        WorkspaceRole workspaceGust = WorkspaceRole.builder().workspace(workspace).name(WorkspaceRoleName.WORKSPACE_ROLE_GUEST.name()).extendsWorkspaceRoleName(null).build();
        workspaceRoleRepository.save(workspaceOwner);
        workspaceRoleRepository.save(workspaceGust);
        workspaceRoleRepository.save(workspaceMember);
        workspaceRoleRepository.save(workspaceAdmin);
        return new Result(workspace, workspaceOwner, workspaceAdmin, workspaceMember, workspaceGust);
    }

    @Override
    public List<WorkspaceDto> getAll() {
        List<Workspace> allWorkspace = repository.findAllByDeletedFalse();
        return mapper.toDTO(allWorkspace);
    }

    @Override
    public WorkspaceDto getById(Long id) {
        Workspace workspace = repository.findById(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + id));
        return mapper.toDTO(workspace);
    }

    @Override
    public PaginationDTO<List<WorkspaceDto>> getPage(int page, int size, AuthUser authUser) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
        Page<Workspace> workspacePage = repository.findByOwnerAndDeletedFalse(pageRequest, authUser);
        int totalPages = workspacePage.getTotalPages();
        List<Workspace> content = workspacePage.getContent();
        long totalElements = workspacePage.getTotalElements();
        List<WorkspaceDto> workspaceDtoList = mapper.toDTO(content);
        return new PaginationDTO<>(workspaceDtoList, page, size, totalElements, totalPages);
    }

    @Override
    @Transactional
    public Long update(WorkspaceUpdateDto updateDto, AuthUser currentUser) {
        Workspace workspace = repository.findById(updateDto.getId()).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + updateDto.getId()));

        if (!currentUser.getId().equals(workspace.getOwner().getId()))
            throw new BadRequestException(OPERATION_CAN_NOT_PERFORMED);

        if (repository.existsByNameAndOwnerAndDeletedFalse(updateDto.getName(), currentUser))
            throw new BadRequestException(currentUser.getFirstname() + WORKSPACE_ALREADY_EXISTS + updateDto.getName());
        Workspace workspaceUpdated = mapper.fromUpdateDTO(updateDto, workspace);
        workspace.setUpdatedBy(currentUser);
        return repository.save(workspaceUpdated).getId();
    }

    @Override
    public Long delete(Long id, AuthUser currentUser) {
        Workspace workspace = repository.findById(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + id));
        if (!currentUser.getId().equals(workspace.getOwner().getId()))
            throw new BadRequestException(OPERATION_CAN_NOT_PERFORMED);
        workspace.setDeleted(true);
        repository.save(workspace);
        return id;
    }

    @Override
    @Transactional
    public Long changeRole(MemberEditDTO memberEditDTO, AuthUser currentUser) {
        Long workspaceId = memberEditDTO.getWorkspaceId();
        Long userId = memberEditDTO.getUserId();
        Workspace workspace = repository.findById(workspaceId).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + workspaceId));
        if (!currentUser.getId().equals(workspace.getOwner().getId()))
            throw new BadRequestException(OPERATION_CAN_NOT_PERFORMED);
        WorkspaceUser workspaceUser = workspaceUserRepository.findWorkspaceUserByUserIdAndWorkspaceId(userId, workspaceId).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + workspaceId));
        WorkspaceRole workspaceRole = workspaceRoleRepository.findById(memberEditDTO.getWorkspaceRoleId()).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + memberEditDTO.getWorkspaceId()));
        workspaceUser.setWorkspaceRole(workspaceRole);
        return workspaceUserRepository.save(workspaceUser).getId();
    }

    @Override
    public Long joinWorkspace(Long workspaceId, AuthUser currentUser) {
        WorkspaceUser workspaceUser = workspaceUserRepository.findWorkspaceUserByUserIdAndWorkspaceId(currentUser.getId(), workspaceId).orElseThrow(() -> new BadRequestException("You are not invited to this workspace!!!"));
        workspaceUser.setJoinedAt(new Timestamp(System.currentTimeMillis()));
        return workspaceUserRepository.save(workspaceUser).getId();
    }

    @Override
    public Long inviteMember(MemberDTO memberDTO) {
        Optional<AuthUser> optionalAuthUser = authUserRepository.findByEmailAndDeletedFalseAndEnabledTrue(memberDTO.getNewMemberEmail());
        if (optionalAuthUser.isEmpty()) throw new NotFoundException(USER_NOT_FOUND + memberDTO.getNewMemberEmail());
        AuthUser authUser = optionalAuthUser.get();
        if (workspaceUserRepository.findWorkspaceUserByUserIdAndWorkspaceId(authUser.getId(), memberDTO.getWorkspaceId()).isPresent())
            throw new BadRequestException(USER_ALREADY_EXISTS + authUser.getId());
        Workspace workspace = repository.findById(memberDTO.getWorkspaceId()).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + memberDTO.getWorkspaceId()));
        WorkspaceRole workspaceRole = workspaceRoleRepository.findById(memberDTO.getWorkspaceRoleId()).orElseThrow(() -> new NotFoundException("Role not found"));
        workspaceRole = workspaceRole.getName().equals("WORKSPACE_ROLE_GUEST") ? workspaceRole : workspaceRoleRepository.findWorkspaceRoleByWorkspaceIdAndName(memberDTO.getWorkspaceId(), "WORKSPACE_ROLE_MEMBER").orElseThrow();
        WorkspaceUser workspaceUser = WorkspaceUser.builder().workspace(workspace).user(authUser).workspaceRole(workspaceRole).invitedAt(new Timestamp(System.currentTimeMillis())).joinedAt(null).build();
        return workspaceUserRepository.save(workspaceUser).getId();
    }

    @Override
    public List<TeamDTO> teamWorkspace(Long id) {
        return workspaceUserRepository.findByWorkspaceId(id).stream().map(TeamDTO::from).collect(Collectors.toList());
    }

    @Override
    public List<WorkspaceDto> myWorkspace(Long id) {
        List<WorkspaceUser> workspaceUsers = workspaceUserRepository.findByUserId(id);
        return workspaceUsers.stream().map(this::toWorkspaceDto).collect(Collectors.toList());
    }

    private WorkspaceDto toWorkspaceDto(WorkspaceUser workspaceUser) {
        Workspace workspace = workspaceUser.getWorkspace();
        return WorkspaceDto.builder().id(workspace.getId()).name(workspace.getName()).avatar(workspace.getAvatar()).color(workspace.getColor()).build();
    }

    private record Result(Workspace workspace, WorkspaceRole workspaceOwner, WorkspaceRole workspaceAdmin,
                          WorkspaceRole workspaceMember, WorkspaceRole workspaceGust) {
    }
}