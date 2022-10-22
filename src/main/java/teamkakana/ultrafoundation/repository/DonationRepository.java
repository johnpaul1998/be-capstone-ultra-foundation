package teamkakana.ultrafoundation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamkakana.ultrafoundation.entity.DonationEntity;

import javax.transaction.Transactional;
import java.math.BigInteger;



public interface DonationRepository extends JpaRepository<DonationEntity, BigInteger> {
    DonationEntity findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
