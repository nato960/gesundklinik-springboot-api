package personal.GesundKlinik.modules.pacient.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import personal.GesundKlinik.modules.pacient.entity.Pacient;
import personal.GesundKlinik.modules.pacient.query.IPacientQueryService;
import personal.GesundKlinik.modules.pacient.repository.IPacientRepository;

@Service
@RequiredArgsConstructor
public class PacientService implements IPacientService{

    private final IPacientRepository repository;
    private final IPacientQueryService queryService;


    @Transactional
    @Override
    public Pacient save(Pacient entity) {
        queryService.verifyEmail(entity.getEmail());
        queryService.verifyPhone(entity.getPhone());

        return repository.save(entity);
    }

    @Transactional
    @Override
    public Pacient update(Pacient entity) {
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
        Pacient pacient = queryService.findById(id);
        pacient.deactivate();
        repository.save(pacient);
    }

}


