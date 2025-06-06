package personal.GesundKlinik.modules.patient.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import personal.GesundKlinik.modules.patient.entity.Patient;
import personal.GesundKlinik.modules.patient.query.IPatientQueryService;
import personal.GesundKlinik.modules.patient.repository.IPatientRepository;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final IPatientRepository repository;
    private final IPatientQueryService queryService;


    @Transactional
    @Override
    public Patient save(Patient entity) {
        queryService.verifyEmail(entity.getEmail());
        queryService.verifyPhone(entity.getPhone());

        return repository.save(entity);
    }

    @Transactional
    @Override
    public Patient update(Patient entity) {
        var stored = queryService.findById(entity.getId());

        if (entity.getEmail() != null && !entity.getEmail().equals(stored.getEmail())) {
            queryService.verifyEmail(entity.getId(), entity.getEmail());
        }

        if (entity.getPhone() != null && !entity.getPhone().equals(stored.getPhone())) {
            queryService.verifyPhone(entity.getId(), entity.getPhone());
        }

        stored.updateWith(entity);

        return repository.save(stored);
    }

    @Transactional
    @Override
    public void softDelete(Long id) {
        Patient patient = queryService.findById(id);
        patient.deactivate();
        repository.save(patient);
    }

}


