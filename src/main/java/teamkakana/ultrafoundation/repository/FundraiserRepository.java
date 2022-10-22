package teamkakana.ultrafoundation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamkakana.ultrafoundation.entity.FundraiserEntity;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.UUID;

@Repository
public interface FundraiserRepository extends JpaRepository<FundraiserEntity, BigInteger> {
    FundraiserEntity findByFundraiserId(UUID fundraiserId);

    @Transactional
    void deleteByFundraiserId(UUID fundraiserId);
}
