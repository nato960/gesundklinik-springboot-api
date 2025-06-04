package personal.GesundKlinik.modules.patient.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import personal.GesundKlinik.modules.patient.entity.Patient;

import java.util.Optional;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByEmail(final String email);

    boolean existsByPhone(final String phone);

    Optional<Patient> findByEmail(final String email);

    Optional<Patient> findByPhone(final String phone);

    Page<Patient> findAllByActiveTrue(Pageable page);

    @Query("""
            select p.active
            from Patient p
            where
            p.id = :id
            """)
    Boolean findActiveById(final Long id);

}
