package personal.GesundKlinik.modules.patient.service;

import personal.GesundKlinik.modules.patient.entity.Patient;

public interface IPatientService {

    Patient save(final Patient entity);

    Patient update(final Patient entity);

    void softDelete(final Long id);

}
