package uz.kuvondikov.clickup.service;

import uz.kuvondikov.clickup.dto.MemberDTO;
import uz.kuvondikov.clickup.dto.MemberEditDTO;
import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.workspace.TeamDTO;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceCreateDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceUpdateDto;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.service.base.BaseService;

import java.util.List;

/**
 * The WorkspaceService interface defines the operations that can be performed on workspaces.
 * It extends the BaseService interface for common operations.
 */
public interface WorkspaceService extends BaseService {
    /**
     * Creates a new workspace with the given workspace create DTO and owner.
     *
     * @param createDto the workspace create DTO
     * @param owner the owner of the workspace
     * @return the ID of the created workspace
     */
    Long create(WorkspaceCreateDto createDto, AuthUser owner);

    /**
     * Retrieves all workspaces.
     *
     * @return a list of WorkspaceDto objects representing the workspaces
     */
    List<WorkspaceDto> getAll();

    /**
     * Retrieves a {@link WorkspaceDto} by its ID.
     *
     * @param id the ID of the workspace to retrieve
     * @return the {@link WorkspaceDto} with the specified ID
     */
    WorkspaceDto getById(Long id);

    /**
     * Retrieves a page of workspaces with pagination information.
     *
     * @param page      the page number
     * @param size      the number of elements per page
     * @param authUser  the authenticated user
     * @return a PaginationDTO object containing a list of WorkspaceDto objects,
     *         the page number, the number of elements per page, the total number of elements,
     *         and the total number of pages
     */
    PaginationDTO<List<WorkspaceDto>> getPage(int page, int size, AuthUser authUser);

    /**
     * Updates a workspace with the given update DTO and current user.
     *
     * @param updateDto    the update DTO containing the new data for the workspace
     * @param currentUser the current user performing the update
     * @return the ID of the updated workspace
     */
    Long update(WorkspaceUpdateDto updateDto, AuthUser currentUser);

    /**
     * Deletes a workspace with the specified ID.
     *
     * @param id          the ID of the workspace to delete
     * @param currentUser the authenticated user performing the delete operation
     * @return the ID of the deleted workspace
     */
    Long delete(Long id, AuthUser currentUser);

    /**
     * Invites a member to the workspace.
     *
     * @param memberDTO - The member details including email, workspaceId, and workspaceRoleId
     * @return The id of the invited member.
     */
    Long inviteMember(MemberDTO memberDTO);

    /**
     * Changes the role of a member in a workspace.
     *
     * @param memberEditDTO The MemberEditDTO object containing the necessary data for changing the role.
     * @param currentUser The currently authenticated user.
     * @return The ID of the member whose role was changed.
     */
    Long changeRole(MemberEditDTO memberEditDTO, AuthUser currentUser);

    /**
     * Join a workspace with the given workspace ID and user.
     *
     * @param workspaceId the ID of the workspace to join
     * @param currentUser the authenticated user joining the workspace
     * @return the ID of the workspace joined
     */
    Long joinWorkspace(Long workspaceId, AuthUser currentUser);

    /**
     * Retrieves a list of TeamDTO objects associated with the specified id.
     *
     * @param id The id of the workspace.
     * @return A list of TeamDTO objects.
     */
    List<TeamDTO> teamWorkspace(Long id);

    /**
     * Returns a list of WorkspaceDto objects based on the given user ID.
     *
     * @param id the ID of the user
     * @return a list of WorkspaceDto objects
     */
    List<WorkspaceDto> myWorkspace(Long id);
}
