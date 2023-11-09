package uz.kuvondikov.clickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.repository.base.BaseRepository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, BaseRepository {
    Optional<AuthUser> findAuthUsersByEmailAndDeletedFalse(String email);

    boolean existsByEmailAndDeletedFalse(String email);

    Optional<AuthUser> findByEmailAndDeletedFalse(String email);
}
