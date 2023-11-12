package uz.kuvondikov.clickup.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceCreateDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceUpdateDto;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.entity.Workspace;
import uz.kuvondikov.clickup.exception.BadRequestException;
import uz.kuvondikov.clickup.exception.NotFoundException;
import uz.kuvondikov.clickup.mapper.WorkspaceMapper;
import uz.kuvondikov.clickup.repository.WorkspaceRepository;
import uz.kuvondikov.clickup.service.WorkspaceService;
import uz.kuvondikov.clickup.service.base.AbstractService;
import uz.kuvondikov.clickup.service.base.BaseService;

import java.util.List;

import static uz.kuvondikov.clickup.constant.ErrorMessages.*;

@Service
public class WorkspaceServiceImpl extends AbstractService<WorkspaceRepository, WorkspaceMapper> implements BaseService, WorkspaceService {
    public WorkspaceServiceImpl(WorkspaceRepository repository, WorkspaceMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public Long create(WorkspaceCreateDto createDto, AuthUser owner) {
        if (repository.existsByNameAndOwnerAndDeletedFalse(createDto.getName(), owner))
            throw new BadRequestException(owner.getFirstname() + WORKSPACE_ALREADY_EXISTS + createDto.getName());
        Workspace workspace = mapper.fromDTO(createDto);
        workspace.setOwner(owner);
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

        if (!currentUser.getEmail().equals(workspace.getOwner().getEmail()))
            throw new BadRequestException(OPERATION_CAN_NOT_PERFORMED);

        if (repository.existsByNameAndOwnerAndDeletedFalse(updateDto.getName(), currentUser))
            throw new BadRequestException(currentUser.getFirstname() + WORKSPACE_ALREADY_EXISTS + updateDto.getName());

        if (updateDto.getName().trim().isEmpty())
            updateDto.setName(null);

        Workspace workspaceUpdated = mapper.fromUpdateDTO(updateDto, workspace);
        return repository.save(workspaceUpdated).getId();
    }

    @Override
    public Long delete(Long id, AuthUser currentUser) {
        Workspace workspace = repository.findById(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND_WITH_THIS_ID + id));
        if (!currentUser.equals(workspace.getOwner()))
            throw new BadRequestException(OPERATION_CAN_NOT_PERFORMED);
        workspace.setDeleted(true);
        repository.save(workspace);
        return id;
    }
}
