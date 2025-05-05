package personal.GesundKlinik.modules.doctor.service;

import jakarta.transaction.Transactional;
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
        queryService.verifyEmail(entity.getId(), entity.getEmail());
        queryService.verifyPhone(entity.getId(), entity.getPhone());

        var stored = queryService.findById(entity.getId());
        if (entity.getName() != null)
            stored.setName(entity.getName());

        if (entity.getEmail() != null)
            stored.setEmail(entity.getEmail());

        if (entity.getPhone() != null)
            stored.setPhone(entity.getPhone());

        if (entity.getAddress() != null)
            stored.setAddress(entity.getAddress());

        return repository.save(stored);
    }

    @Transactional
    @Override
    public void softDelete(Long id) {
        Doctor doctor = queryService.findById(id);
        doctor.deactivate();
    }

}
