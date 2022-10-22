package teamkakana.ultrafoundation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamkakana.ultrafoundation.entity.UserEntity;

import javax.transaction.Transactional;
import java.math.BigInteger;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, BigInteger> {
    UserEntity findByEmail(String email);
    @Transactional
    void deleteByEmail(String email);
}
