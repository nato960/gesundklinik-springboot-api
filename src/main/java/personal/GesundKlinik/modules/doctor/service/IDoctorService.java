package personal.GesundKlinik.modules.doctor.service;

import personal.GesundKlinik.modules.doctor.entity.Doctor;

public interface IDoctorService {

    Doctor save(final Doctor entity);

    Doctor update(final Doctor entity);

    void softDelete(final Long id);

}
