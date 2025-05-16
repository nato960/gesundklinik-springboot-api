package personal.GesundKlinik.modules.pacient.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import personal.GesundKlinik.modules.pacient.entity.Pacient;

import java.util.Optional;

@Repository
public interface IPacientRepository extends JpaRepository<Pacient, Long> {

    boolean existsByEmail(final String email);

    boolean existsByPhone(final String phone);

    Optional<Pacient> findByEmail(final String email);

    Optional<Pacient> findByPhone(final String phone);

    Page<Pacient> findAllByActiveTrue(Pageable page);

    @Query("""
            select p.active
            from Pacient p
            where
            p.id = :id
            """)
    Boolean findActiveById(final Long id);

}
