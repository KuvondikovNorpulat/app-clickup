package uz.kuvondikov.clickup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.entity.Workspace;
import uz.kuvondikov.clickup.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long>, BaseRepository {

    boolean existsByNameAndOwnerAndDeletedFalse(String name, AuthUser owner);

    List<Workspace> findAllByDeletedFalse();

    Optional<Workspace> findByIdAndDeletedFalse(Long id);

    Page<Workspace> findByOwnerAndDeletedFalse(Pageable pageable, AuthUser owner);
}
