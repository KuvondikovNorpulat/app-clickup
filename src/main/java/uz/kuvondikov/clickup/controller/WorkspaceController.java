package uz.kuvondikov.clickup.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kuvondikov.clickup.anotation.CurrentUser;
import uz.kuvondikov.clickup.controller.base.AbstractController;
import uz.kuvondikov.clickup.dto.MemberDTO;
import uz.kuvondikov.clickup.dto.MemberEditDTO;
import uz.kuvondikov.clickup.dto.PaginationDTO;
import uz.kuvondikov.clickup.dto.response.DataDTO;
import uz.kuvondikov.clickup.dto.workspace.TeamDTO;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceCreateDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceUpdateDto;
import uz.kuvondikov.clickup.entity.AuthUser;

import java.util.List;

/**
 * The WorkspaceController interface defines the API endpoints for managing workspaces.
 */
@RequestMapping(AbstractController.PATH + "/workspace")
public interface WorkspaceController {

    /**
     * Creates a new workspace.
     *
     * @param createDto   the {@link WorkspaceCreateDto} object containing the workspace details
     * @param currentUser the {@link AuthUser} object representing the current user
     * @return a {@link ResponseEntity} object containing a {@link DataDTO} object with the ID of the created workspace
     */
    @PostMapping("/create")
    ResponseEntity<DataDTO<Long>> create(@Valid @RequestBody WorkspaceCreateDto createDto, @CurrentUser AuthUser currentUser);

    /**
     * Retrieves all workspaces.
     *
     * @return a ResponseEntity object containing a DataDTO object with a list of WorkspaceDto objects
     */
    @GetMapping("all")
    ResponseEntity<DataDTO<List<WorkspaceDto>>> getAll();

    /**
     * Retrieves the workspace with the given ID.
     *
     * @param id the ID of the workspace
     * @return a ResponseEntity object containing a DataDTO object with the workspace details
     */
    @GetMapping("/{id}")
    ResponseEntity<DataDTO<WorkspaceDto>> getById(@PathVariable Long id);

    /**
     * Retrieves a paginated list of workspaces.
     *
     * @param page     the page number (optional, default value is 0)
     * @param size     the number of workspaces per page (optional, default value is 10)
     * @param authUser the current authenticated user
     * @return a ResponseEntity object containing a DataDTO object with the paginated list of workspaces
     */
    @GetMapping("/page")
    ResponseEntity<DataDTO<PaginationDTO<List<WorkspaceDto>>>> getPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @CurrentUser AuthUser authUser);

    /**
     * Updates a workspace.
     *
     * @param updateDto   the {@link WorkspaceUpdateDto} object containing the updated workspace details
     * @param currentUser the {@link AuthUser} object representing the current user
     * @return a {@link ResponseEntity} object containing a {@link DataDTO} object with the ID of the updated workspace
     */
    @PutMapping("/update")
    ResponseEntity<DataDTO<Long>> update(@Valid @RequestBody WorkspaceUpdateDto updateDto, @CurrentUser AuthUser currentUser);

    /**
     * Deletes a workspace with the specified ID.
     *
     * @param id          the ID of the workspace to delete
     * @param currentUser the authenticated user performing the deletion
     * @return a ResponseEntity object containing a DataDTO object with the ID of the deleted workspace
     */
    @DeleteMapping("/delete/{id}")
    ResponseEntity<DataDTO<Long>> delete(@PathVariable Long id, @CurrentUser AuthUser currentUser);

    /**
     * Changes the owner of a workspace to a new member.
     *
     * @param memberEditDTO the {@link MemberEditDTO} object containing the details of the member to be assigned as owner
     * @param currentUser   the {@link AuthUser} object representing the current user
     * @return a {@link ResponseEntity} object containing a {@link DataDTO} object with the ID of the changed owner
     */
    @PutMapping("/change-role")
    ResponseEntity<DataDTO<Long>> changeOwner(@Valid @RequestBody MemberEditDTO memberEditDTO, @CurrentUser AuthUser currentUser);

    /**
     * Endpoint for inviting a new member to a workspace.
     *
     * @param memberDTO the {@link MemberDTO} object containing the details of the new member
     * @return a {@link ResponseEntity} object containing a {@link DataDTO} object with the ID of the invited member
     */
    @PostMapping("/invite-new-member")
    ResponseEntity<DataDTO<Long>> inviteNewMember(@Valid @RequestBody MemberDTO memberDTO);

    /**
     * Joins a workspace with the specified ID.
     *
     * @param workspaceId the ID of the workspace to join
     * @param currentUser the currently authenticated user
     * @return a ResponseEntity object containing a DataDTO object with the ID of the joined workspace
     */
    @PutMapping("/join/{workspace-id}")
    ResponseEntity<DataDTO<Long>> joinWorkspace(@PathVariable("workspace-id") Long workspaceId, @CurrentUser AuthUser currentUser);

    /**
     * Retrieves teams associated with a specific workspace.
     *
     * @param id the ID of the workspace
     * @return ResponseEntity object containing DataDTO that holds a list of TeamDTO objects
     */
    @GetMapping("/team/{id}")
    ResponseEntity<DataDTO<List<TeamDTO>>> teamWorkspace(@PathVariable Long id);

    /**
     * Retrieves workspaces associated with the authenticated user.
     *
     * @param authUser the authenticated user
     * @return a ResponseEntity object containing a DataDTO object with a list of WorkspaceDto objects
     */
    @GetMapping("/my-workspaces")
    ResponseEntity<DataDTO<List<WorkspaceDto>>> myWorkspace(@CurrentUser AuthUser authUser);
}
