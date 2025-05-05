package personal.GesundKlinik.modules.pacient.service;

import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.pacient.entity.Pacient;

public interface IPacientService {

    Pacient save(final Pacient entity);

    Pacient update(final Pacient entity);

    void softDelete(final Long id);

}
