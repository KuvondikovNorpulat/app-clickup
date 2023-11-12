package uz.kuvondikov.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceCreateDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceUpdateDto;
import uz.kuvondikov.clickup.entity.Workspace;
import uz.kuvondikov.clickup.mapper.base.BaseMapper;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorkspaceMapper extends BaseMapper {
    List<WorkspaceDto> toDTO(List<Workspace> workspaceList);

    WorkspaceDto toDTO(Workspace workspace);

    Workspace fromDTO(WorkspaceCreateDto workspaceCreateDto);

    Workspace fromUpdateDTO(WorkspaceUpdateDto updateDTO, @MappingTarget Workspace target);
}
