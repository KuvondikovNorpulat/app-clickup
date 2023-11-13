package uz.kuvondikov.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kuvondikov.clickup.entity.WorkspaceRole;

@Repository
public interface WorkspaceRoleRepository extends JpaRepository<WorkspaceRole, Long> {
}
