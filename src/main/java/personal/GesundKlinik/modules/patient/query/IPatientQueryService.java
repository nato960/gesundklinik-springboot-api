package personal.GesundKlinik.modules.patient.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import personal.GesundKlinik.modules.patient.entity.Patient;

import java.util.List;

public interface IPatientQueryService {

    Patient findById(final Long id);

    Patient getReferenceById(final Long id);

    List<Patient> list();

    Page<Patient> listPaged(Pageable pageable);

    void verifyPhone(final String phone);

    void verifyPhone(final Long id,final String phone);

    void verifyEmail(final String email);

    void verifyEmail(final Long id,final String email);

}
