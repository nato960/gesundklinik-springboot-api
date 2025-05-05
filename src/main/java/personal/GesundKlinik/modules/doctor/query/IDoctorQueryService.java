package personal.GesundKlinik.modules.doctor.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.doctor.entity.Speciality;

import java.time.LocalDateTime;
import java.util.List;

public interface IDoctorQueryService {

    Doctor findById(final Long id);

    Doctor getReferenceById(Long id);

    List<Doctor> list();

    Page<Doctor> listPaged(Pageable pageable);

    void verifyPhone(final String phone);

    void verifyPhone(final Long id,final String phone);

    void verifyEmail(final String email);

    void verifyEmail(final Long id,final String email);

    Doctor chooseRandomDoctorFreeOnDate(Speciality speciality, LocalDateTime date);

}
