package uz.kuvondikov.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kuvondikov.clickup.entity.WorkspaceUser;

@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser,Long> {
}
