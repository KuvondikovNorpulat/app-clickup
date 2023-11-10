package uz.kuvondikov.clickup.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kuvondikov.clickup.entity.AuthUser;
import uz.kuvondikov.clickup.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, BaseRepository {


    Optional<AuthUser> findByEmailAndDeletedFalse(String email);

    List<AuthUser> findAllByDeletedFalseAndEnabledTrue();

    Page<AuthUser> findByDeletedFalseAndEnabledTrue(PageRequest pageRequest, Pageable pageable);

    /***
     * email paramateridagi deleted qilinmagan va active bolgan user borligini aniqlaydi
     * @return boolean
     */
    boolean existsByEmailAndDeletedFalseAndEnabledTrue(String email);

    /***
     * email paramateridagi deleted qilinmagan va active bolmagan user borligini aniqlaydi
     * @return boolean
     */
    boolean existsByEmailAndDeletedFalseAndEnabledFalse(String email);

    /***
     * email paramateridagi deleted qilinmagan va active bo'lmagan userni qaytaradi
     * @return boolean
     */
    Optional<AuthUser> findByEmailAndDeletedFalseAndEnabledFalse(String email);

    /***
     * email paramateridagi deleted qilinmagan va active bolgan userni qaytaradi
     * @return boolean
     */
    Optional<AuthUser> findByEmailAndDeletedFalseAndEnabledTrue(String email);

    Optional<Object> findByEmail(String username);

    Optional<AuthUser> findByIdAndDeletedFalse(Long id);
}
