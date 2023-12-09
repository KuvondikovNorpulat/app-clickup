package uz.kuvondikov.clickup.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.kuvondikov.clickup.anotation.CurrentUser;
import uz.kuvondikov.clickup.controller.WorkspaceController;
import uz.kuvondikov.clickup.controller.base.AbstractController;
import uz.kuvondikov.clickup.dto.MemberDTO;
import uz.kuvondikov.clickup.dto.MemberEditDTO;
import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.response.DataDTO;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceCreateDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceUpdateDto;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.service.WorkspaceService;

import java.util.List;

@RestController
public class WorkspaceControllerImpl extends AbstractController<WorkspaceService> implements WorkspaceController {

    public WorkspaceControllerImpl(WorkspaceService service) {
        super(service);
    }

    @Override
    public ResponseEntity<DataDTO<Long>> create(WorkspaceCreateDto createDto, AuthUser owner) {
        Long id = service.create(createDto, owner);
        return ResponseEntity.ok(new DataDTO<>(id));
    }

    @Override
    public ResponseEntity<DataDTO<List<WorkspaceDto>>> getAll() {
        List<WorkspaceDto> workspaceDtoList = service.getAll();
        return ResponseEntity.ok(new DataDTO<>(workspaceDtoList));
    }

    @Override
    public ResponseEntity<DataDTO<WorkspaceDto>> getById(Long id) {
        WorkspaceDto workspaceDto = service.getById(id);
        return ResponseEntity.ok(new DataDTO<>(workspaceDto));
    }

    @Override
    public ResponseEntity<DataDTO<PaginationDTO<List<WorkspaceDto>>>> getPage(int page, int size, AuthUser authUser) {
        PaginationDTO<List<WorkspaceDto>> paginationDTO = service.getPage(page, size, authUser);
        return ResponseEntity.ok(new DataDTO<>(paginationDTO));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> update(WorkspaceUpdateDto updateDto, @CurrentUser AuthUser currentUser) {
        Long id = service.update(updateDto, currentUser);
        return ResponseEntity.ok(new DataDTO<>(id));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> delete(Long id, AuthUser currentUser) {
        Long deletedId = service.delete(id, currentUser);
        return ResponseEntity.ok(new DataDTO<>(deletedId));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> changeOwner(MemberEditDTO memberEditDTO, AuthUser currentUser) {
        Long changedOwnerId = service.changeRole(memberEditDTO, currentUser);
        return ResponseEntity.ok(new DataDTO<>(changedOwnerId));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> inviteNewMember(MemberDTO memberDTO) {
        Long id = service.inviteMember(memberDTO);
        return ResponseEntity.ok(new DataDTO<>(id));
    }
}
