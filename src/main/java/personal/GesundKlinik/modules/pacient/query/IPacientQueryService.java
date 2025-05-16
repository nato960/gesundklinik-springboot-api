package personal.GesundKlinik.modules.pacient.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.pacient.entity.Pacient;

import java.util.List;

public interface IPacientQueryService {

    Pacient findById(final Long id);

    Pacient getReferenceById(final Long id);

    List<Pacient> list();

    Page<Pacient> listPaged(Pageable pageable);

    void verifyPhone(final String phone);

    void verifyPhone(final Long id,final String phone);

    void verifyEmail(final String email);

    void verifyEmail(final Long id,final String email);

}
