package personal.GesundKlinik.modules.doctor.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.doctor.entity.Speciality;
import personal.GesundKlinik.modules.doctor.repository.IDoctorRepository;
import personal.GesundKlinik.shared.exception.EmailInUseException;
import personal.GesundKlinik.shared.exception.NotFoundException;
import personal.GesundKlinik.shared.exception.PhoneInUseException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DoctorQueryService implements IDoctorQueryService{

    private final IDoctorRepository repository;

    @Override
    public Doctor findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Doctor not found"));
    }

    @Override
    public Doctor getReferenceById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Doctor not found");
        }
        return repository.getReferenceById(id);
    }

    @Override
    public Page<Doctor> listPaged(Pageable pageable) {
        return repository.findAllByActiveTrue(pageable);
    }

    @Override
    public List<Doctor> list() {
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

    @Override
    public Doctor chooseRandomDoctorFreeOnDate(Speciality speciality, LocalDateTime date) {
        return repository.chooseRandomDoctorFreeOnDate(speciality, date);
    }


}
