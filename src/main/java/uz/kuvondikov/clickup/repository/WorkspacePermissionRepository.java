package uz.kuvondikov.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kuvondikov.clickup.entity.WorkspacePermission;

@Repository
public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, Long> {
}
