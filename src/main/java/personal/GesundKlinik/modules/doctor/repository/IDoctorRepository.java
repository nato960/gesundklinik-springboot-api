package personal.GesundKlinik.modules.doctor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.doctor.entity.Speciality;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByEmail(final String email);

    boolean existsByPhone(final String phone);

    Optional<Doctor> findByEmail(final String email);

    Optional<Doctor> findByPhone(final String phone);

    Page<Doctor> findAllByActiveTrue(Pageable page);

    @Query("""
            select d from Doctor d
            where
            d.active = true
            and
            d.speciality = :speciality
            and
            d.id not in(
                select a.doctor.id from Appointment a
                where
                a.date = :date
                and
                a.cancellationReason is null
            )
            order by rand()
            limit 1
            """)
    Optional<Doctor> chooseRandomDoctorFreeOnDate(final Speciality speciality, final LocalDateTime date);


    @Query("""
            select d.active from Doctor d
            where
            d.id = :id
            """)
    Boolean findActiveById(final Long id);

}
