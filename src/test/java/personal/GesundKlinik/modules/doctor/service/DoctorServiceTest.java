package personal.GesundKlinik.modules.doctor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.doctor.query.IDoctorQueryService;
import personal.GesundKlinik.modules.doctor.repository.IDoctorRepository;
import personal.GesundKlinik.shared.model.Address;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private IDoctorRepository repository;

    @Mock
    private IDoctorQueryService queryService;

    @InjectMocks
    private DoctorService service;

    private Doctor doctor;

    @BeforeEach
    void setup() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Renato");
        doctor.setEmail("renato@email.com");
        doctor.setCrm("8888888888");
        doctor.setPhone("99999999");
        doctor.setAddress(new Address("Rua A", "123", "Cidade", "UF", "12345678"));
    }

    @Nested
    class SaveTests {

        @Test
        @DisplayName("")
        void save() {
        }
    }

    @Nested
    class UpdateTests {

        @Test
        void update() {
        }
    }

    @Nested
    class SoftDeleteTests {

        @Test
        void softDelete() {
        }
    }
}