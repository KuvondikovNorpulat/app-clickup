package uz.kuvondikov.clickup.service;

import uz.kuvondikov.clickup.dto.MemberDTO;
import uz.kuvondikov.clickup.dto.MemberEditDTO;
import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceCreateDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceUpdateDto;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.service.base.BaseService;

import java.util.List;

public interface WorkspaceService extends BaseService {
    Long create(WorkspaceCreateDto createDto, AuthUser owner);

    List<WorkspaceDto> getAll();

    WorkspaceDto getById(Long id);

    PaginationDTO<List<WorkspaceDto>> getPage(int page, int size, AuthUser authUser);

    Long update(WorkspaceUpdateDto updateDto, AuthUser currentUser);

    Long delete(Long id, AuthUser currentUser);
    Long inviteMember(MemberDTO memberDTO);

    Long changeRole(MemberEditDTO memberEditDTO, AuthUser currentUser);
}
