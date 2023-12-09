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
import uz.kuvondikov.clickup.dto.workspace.WorkspaceCreateDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceDto;
import uz.kuvondikov.clickup.dto.workspace.WorkspaceUpdateDto;
import uz.kuvondikov.clickup.entity.AuthUser;

import java.util.List;

@RequestMapping(AbstractController.PATH + "/workspace")
public interface WorkspaceController {

    @PostMapping("/create")
    ResponseEntity<DataDTO<Long>> create(@Valid @RequestBody WorkspaceCreateDto createDto, @CurrentUser AuthUser currentUser);

    @GetMapping("all")
    ResponseEntity<DataDTO<List<WorkspaceDto>>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<DataDTO<WorkspaceDto>> getById(@PathVariable Long id);

    @GetMapping("/page")
    ResponseEntity<DataDTO<PaginationDTO<List<WorkspaceDto>>>> getPage(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       @CurrentUser AuthUser authUser);

    @PutMapping("/update")
    ResponseEntity<DataDTO<Long>> update(@Valid @RequestBody WorkspaceUpdateDto updateDto, @CurrentUser AuthUser currentUser);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<DataDTO<Long>> delete(@PathVariable Long id, @CurrentUser AuthUser currentUser);

    @PutMapping("/change-role")
    ResponseEntity<DataDTO<Long>> changeOwner(@Valid @RequestBody MemberEditDTO memberEditDTO, @CurrentUser AuthUser currentUser);

    @PostMapping("/invite-new-member")
    ResponseEntity<DataDTO<Long>> inviteNewMember(@Valid @RequestBody MemberDTO memberDTO);
}
