package uz.kuvondikov.clickup.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.kuvondikov.clickup.dto.MemberDTO;
import uz.kuvondikov.clickup.dto.MemberEditDTO;
import uz.kuvondikov.clickup.dto.PaginationDTO;
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

    @Override
    public Long create(WorkspaceCreateDto createDto, AuthUser owner) {
        if (repository.existsByNameAndOwnerAndDeletedFalse(createDto.getName(), owner))
            throw new BadRequestException(owner.getFirstname() + WORKSPACE_ALREADY_EXISTS + createDto.getName());
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

        WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();
        ArrayList<WorkspacePermission> ownerPermissions = new ArrayList<>();
        ArrayList<WorkspacePermission> adminPermissions = new ArrayList<>();
        ArrayList<WorkspacePermission> memberPermissions = new ArrayList<>();
        ArrayList<WorkspacePermission> guestPermissions = new ArrayList<>();

        for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
            if (workspacePermissionName.workspaceRoleNames.contains(WorkspaceRoleName.WORKSPACE_ROLE_OWNER)) {
                ownerPermissions.add(WorkspacePermission.builder().workspaceRole(workspaceOwner).workspacePermissionName(workspacePermissionName).build());
            }

            if (workspacePermissionName.workspaceRoleNames.contains(WorkspaceRoleName.WORKSPACE_ROLE_ADMIN)) {
                adminPermissions.add(WorkspacePermission.builder().workspaceRole(workspaceAdmin).workspacePermissionName(workspacePermissionName).build());
            }

            if (workspacePermissionName.workspaceRoleNames.contains(WorkspaceRoleName.WORKSPACE_ROLE_MEMBER)) {
                memberPermissions.add(WorkspacePermission.builder().workspaceRole(workspaceMember).workspacePermissionName(workspacePermissionName).build());
            }
            if (workspacePermissionName.workspaceRoleNames.contains(WorkspaceRoleName.WORKSPACE_ROLE_GUEST)) {
                guestPermissions.add(WorkspacePermission.builder().workspaceRole(workspaceGust).workspacePermissionName(workspacePermissionName).build());
            }
        }
        workspacePermissionRepository.saveAll(ownerPermissions);
        workspacePermissionRepository.saveAll(adminPermissions);
        workspacePermissionRepository.saveAll(memberPermissions);
        workspacePermissionRepository.saveAll(guestPermissions);

        WorkspaceUser user = WorkspaceUser.builder().workspace(workspace).user(owner).workspaceRole(workspaceOwner).invitedAt(new Timestamp(System.currentTimeMillis())).joinedAt(new Timestamp(System.currentTimeMillis())).build();
        workspaceUserRepository.save(user);
        return repository.save(workspace).getId();
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
    public Long update(WorkspaceUpdateDto updateDto, AuthUser currentUser) {
        Workspace workspace = repository.findById(updateDto.getId()).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + updateDto.getId()));

//        if (!currentUser.getId().equals(workspace.getOwner().getId()))
//            throw new BadRequestException(OPERATION_CAN_NOT_PERFORMED);

        if (repository.existsByNameAndOwnerAndDeletedFalse(updateDto.getName(), currentUser))
            throw new BadRequestException(currentUser.getFirstname() + WORKSPACE_ALREADY_EXISTS + updateDto.getName());
        Workspace workspaceUpdated = mapper.fromUpdateDTO(updateDto, workspace);
        workspace.setUpdatedBy(currentUser);
        return repository.save(workspaceUpdated).getId();
    }

    @Override
    public Long delete(Long id, AuthUser currentUser) {
        Workspace workspace = repository.findById(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + id));
//        if (!currentUser.getId().equals(workspace.getOwner().getId()))
//            throw new BadRequestException(OPERATION_CAN_NOT_PERFORMED);
        workspace.setDeleted(true);
        repository.save(workspace);
        return id;
    }

    @Override
    public Long changeRole(MemberEditDTO memberEditDTO, AuthUser currentUser) {
        Long workspaceId = memberEditDTO.getWorkspaceId();
        Long userId = memberEditDTO.getUserId();
//        if (!currentUser.getId().equals(workspace.getOwner().getId()))
//            throw new BadRequestException(OPERATION_CAN_NOT_PERFORMED);
        WorkspaceUser workspaceUser = workspaceUserRepository.findWorkspaceUserByUserIdAndWorkspaceId(userId, workspaceId).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + workspaceId));
        WorkspaceRole workspaceRole = workspaceRoleRepository.findById(memberEditDTO.getWorkspaceRoleId()).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + memberEditDTO.getWorkspaceId()));
        workspaceUser.setWorkspaceRole(workspaceRole);
        return workspaceUserRepository.save(workspaceUser).getId();
    }

    @Override
    public Long inviteMember(MemberDTO memberDTO) {
        Optional<AuthUser> optionalAuthUser = authUserRepository.findByEmailAndDeletedFalseAndEnabledTrue(memberDTO.getNewMemberEmail());
        if (optionalAuthUser.isEmpty())
            throw new NotFoundException(USER_NOT_FOUND + memberDTO.getNewMemberEmail());
        AuthUser authUser = optionalAuthUser.get();
        if (workspaceUserRepository.findWorkspaceUserByUserIdAndWorkspaceId(authUser.getId(), memberDTO.getWorkspaceId()).isPresent())
            throw new BadRequestException(USER_ALREADY_EXISTS + authUser.getId());

        Workspace workspace = repository.findById(memberDTO.getWorkspaceId()).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + memberDTO.getWorkspaceId()));
        WorkspaceRole workspaceRole = workspaceRoleRepository.findById(memberDTO.getWorkspaceRoleId()).orElseThrow(() -> new NotFoundException("Role not found"));
        workspaceRole = workspaceRole.getName().equals("WORKSPACE_ROLE_GUEST") ? workspaceRole : workspaceRoleRepository.findWorkspaceRoleByWorkspaceIdAndName(memberDTO.getWorkspaceId(), "WORKSPACE_ROLE_MEMBER").orElseThrow();
        WorkspaceUser workspaceUser = WorkspaceUser.builder()
                .workspace(workspace)
                .user(authUser)
                .workspaceRole(workspaceRole)
                .invitedAt(new Timestamp(System.currentTimeMillis()))
                .joinedAt(null)
                .build();
        return workspaceUserRepository.save(workspaceUser).getId();
    }
}
