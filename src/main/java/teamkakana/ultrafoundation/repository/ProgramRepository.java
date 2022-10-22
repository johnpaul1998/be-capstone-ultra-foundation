package teamkakana.ultrafoundation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamkakana.ultrafoundation.entity.ProgramEntity;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.UUID;

@Repository
public interface ProgramRepository extends JpaRepository<ProgramEntity , BigInteger>{
    ProgramEntity findByProgramId(UUID programId);

    @Transactional
    void deleteByProgramId(UUID programId);
}
