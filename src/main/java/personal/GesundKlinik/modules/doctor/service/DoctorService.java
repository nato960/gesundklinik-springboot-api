package personal.GesundKlinik.modules.doctor.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.doctor.query.IDoctorQueryService;
import personal.GesundKlinik.modules.doctor.repository.IDoctorRepository;

@RequiredArgsConstructor
@Service
public class DoctorService implements IDoctorService{

    final private IDoctorRepository repository;
    final private IDoctorQueryService queryService;


    @Transactional
    @Override
    public Doctor save(Doctor entity) {
        queryService.verifyEmail(entity.getEmail());
        queryService.verifyPhone(entity.getPhone());

        return repository.save(entity);
    }

    @Transactional
    @Override
    public Doctor update(Doctor entity) {
        var stored = queryService.findById(entity.getId());

        if (entity.getName() != null)
            stored.setName(entity.getName());

        if (entity.getEmail() != null && !entity.getEmail().equals(stored.getEmail())) {
            queryService.verifyEmail(entity.getId(), entity.getEmail());
            stored.setEmail(entity.getEmail());
        }

        if (entity.getPhone() != null && !entity.getPhone().equals(stored.getPhone())) {
            queryService.verifyPhone(entity.getId(), entity.getPhone());
            stored.setPhone(entity.getPhone());
        }

        if (entity.getAddress() != null)
            stored.setAddress(entity.getAddress());

        return repository.save(stored);
    }

    @Transactional
    @Override
    public void softDelete(Long id) {
        Doctor doctor = queryService.findById(id);
        doctor.deactivate();
        repository.save(doctor);
    }

}
