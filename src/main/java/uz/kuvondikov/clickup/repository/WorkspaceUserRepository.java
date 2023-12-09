package uz.kuvondikov.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kuvondikov.clickup.entity.WorkspaceUser;

import java.util.Optional;

@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser,Long> {
    Optional<WorkspaceUser> findWorkspaceUserByUserIdAndWorkspaceId(Long userId,Long workspaceId);
}
