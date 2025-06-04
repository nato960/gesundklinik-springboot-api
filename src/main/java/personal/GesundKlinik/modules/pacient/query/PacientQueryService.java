package personal.GesundKlinik.modules.pacient.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import personal.GesundKlinik.modules.pacient.entity.Pacient;
import personal.GesundKlinik.modules.pacient.repository.IPacientRepository;
import personal.GesundKlinik.shared.exception.EmailInUseException;
import personal.GesundKlinik.shared.exception.NotFoundException;
import personal.GesundKlinik.shared.exception.PhoneInUseException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PacientQueryService implements IPacientQueryService{

    private final IPacientRepository repository;

    @Override
    public Pacient findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Pacient not found"));
    }

    @Override
    public Pacient getReferenceById(Long id) {
        if (!repository.existsById(id)){
            throw new NotFoundException("Pacient not found");
        }
        return repository.getReferenceById(id);
    }

    @Override
    public Page<Pacient> listPaged(Pageable pageable) {
        return repository.findAllByActiveTrue(pageable);
    }

    @Override
    public List<Pacient> list() {
        return repository.findAll();
    }

    @Override
    public void verifyPhone(String phone) {
        if (repository.existsByPhone(phone)) {
            throw new PhoneInUseException("This phone is already in use");
        }
    }

    @Override
    public void verifyPhone(Long id, String phone) {
        var optional = repository.findByPhone(phone);
        if (optional.isPresent() && optional.get().getId() != id) {
            throw new PhoneInUseException("This phone is already in use");
        }
    }

    @Override
    public void verifyEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new EmailInUseException("This email is already in use");
        }
    }

    @Override
    public void verifyEmail(Long id, String email) {
        var optional = repository.findByEmail(email);
        if (optional.isPresent() && !Objects.equals(optional.get().getId(), id)) {
            throw new EmailInUseException("This email is already in use");
        }
    }
}
