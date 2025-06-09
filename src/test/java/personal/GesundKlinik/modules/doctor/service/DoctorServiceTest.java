package personal.GesundKlinik.modules.doctor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.doctor.query.IDoctorQueryService;
import personal.GesundKlinik.modules.doctor.repository.IDoctorRepository;
import personal.GesundKlinik.shared.exception.EmailInUseException;
import personal.GesundKlinik.shared.exception.NotFoundException;
import personal.GesundKlinik.shared.exception.PhoneInUseException;
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

    @Captor
    private ArgumentCaptor<Doctor> doctorCaptor;

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
        @DisplayName("Should save a new doctor when email and phone are valid")
        void shouldSaveDoctor_whenEmailAndPhoneAreValid() {
            // ARRANGE
            when(repository.save(doctor)).thenReturn(doctor);

            // ACT
            Doctor saved = service.save(doctor);

            // ASSERT
            assertThat(saved).usingRecursiveComparison().isEqualTo(doctor);

            InOrder inOrder = inOrder(queryService, repository);
            inOrder.verify(queryService).verifyEmail(doctor.getEmail());
            inOrder.verify(queryService).verifyPhone(doctor.getPhone());
            inOrder.verify(repository).save(doctor);
        }

        @Test
        @DisplayName("Should throw EmailInUseException when email is already in use")
        void shouldThrowEmailInUseException_whenEmailAlreadyUsed() {
            // ARRANGE
            doThrow(new EmailInUseException("This email is already in use"))
                    .when(queryService).verifyEmail(doctor.getEmail());

            // ACT & ASSERT
            EmailInUseException ex = assertThrows(
                    EmailInUseException.class,
                    () -> service.save(doctor)
            );

            assertEquals("This email is already in use", ex.getMessage());

            verify(queryService).verifyEmail(doctor.getEmail());
            verify(queryService, never()).verifyPhone(any());
            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw PhoneInUseException when phone is already in use")
        void shouldThrowPhoneInUseException_whenPhoneAlreadyUsed() {
            // ARRANGE
            doNothing().when(queryService).verifyEmail(doctor.getEmail());
            doThrow(new PhoneInUseException("This phone is already in use"))
                    .when(queryService).verifyPhone(doctor.getPhone());

            // ACT & ASSERT
            PhoneInUseException ex = assertThrows(
                    PhoneInUseException.class,
                    () -> service.save(doctor)
            );

            assertEquals("This phone is already in use", ex.getMessage());

            verify(queryService).verifyEmail(doctor.getEmail());
            verify(queryService).verifyPhone(doctor.getPhone());
            verify(repository, never()).save(any());
        }
    }


    @Nested
    class UpdateTests {

        @Test
        @DisplayName("Should call updateWith() and save in correct order when name and address are updated")
        void shouldCallUpdateWithAndSaveInOrder_whenUpdatingNameAndAddress() {
            // ARRANGE
            Doctor updates = new Doctor();
            updates.setId(1L);
            updates.setName("New Name");
            updates.setAddress(new Address("New Street", "456", "New City", "TX", "22222222"));

            Doctor spyDoctor = spy(doctor);
            when(queryService.findById(1L)).thenReturn(spyDoctor);
            when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            // ACT
            service.update(updates);

            // ASSERT – behavior
            InOrder inOrder = inOrder(queryService, spyDoctor, repository);
            inOrder.verify(queryService).findById(1L);
            verify(queryService, never()).verifyEmail(any(), any());
            verify(queryService, never()).verifyPhone(any(), any());
            inOrder.verify(spyDoctor).updateWith(updates);
            inOrder.verify(repository).save(spyDoctor);
        }

        @Test
        @DisplayName("Should persist doctor with updated name and address")
        void shouldPersistUpdatedFields_whenUpdatingNameAndAddress() {
            // ARRANGE
            Doctor updates = new Doctor();
            updates.setId(1L);
            updates.setName("New Name");
            updates.setAddress(new Address("New Street", "456", "New City", "TX", "22222222"));

            when(queryService.findById(1L)).thenReturn(doctor);
            when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            // ACT
            Doctor updated = service.update(updates);

            // ASSERT – final state
            verify(repository).save(doctorCaptor.capture());
            Doctor captured = doctorCaptor.getValue();

            assertEquals("New Name", captured.getName());
            assertThat(captured.getAddress()).usingRecursiveComparison().isEqualTo(updates.getAddress());
            assertSame(updated, captured);
        }

        @Test
        @DisplayName("Should call updateWith() and save in correct order when email and phone are updated")
        void shouldCallUpdateWithAndSaveInOrder_whenUpdatingEmailAndPhone() {
            // ARRANGE
            Doctor updates = new Doctor();
            updates.setId(1L);
            updates.setEmail("newemail@email.com");
            updates.setPhone("111111111");

            Doctor spyDoctor = spy(doctor) ;
            when(queryService.findById(1L)).thenReturn(spyDoctor);
            when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            // ACT
            service.update(updates);

            // ASSERT
            InOrder inOrder = inOrder(queryService, spyDoctor, repository);
            inOrder.verify(queryService).findById(1L);
            inOrder.verify(queryService).verifyEmail(1L, updates.getEmail());
            inOrder.verify(queryService).verifyPhone(1L, updates.getPhone());
            inOrder.verify(spyDoctor).updateWith(updates);
            inOrder.verify(repository).save(spyDoctor);
        }

        @Test
        @DisplayName("Should persist doctor with updated email and phone")
        void shouldPersistUpdatedFields_whenUpdatingEmailAndPhone() {
            // ARRANGE
            Doctor updates = new Doctor();
            updates.setId(1L);
            updates.setEmail("newemail@email.com");
            updates.setPhone("111111111");

            when(queryService.findById(1L)).thenReturn(doctor);
            when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            // ACT
            Doctor updated = service.update(updates);

            // ASSERT
            verify(repository).save(doctorCaptor.capture());
            Doctor captured = doctorCaptor.getValue();

            assertEquals("newemail@email.com", captured.getEmail());
            assertEquals("111111111", captured.getPhone());
            assertSame(captured, updated);
        }

        @Test
        @DisplayName("Should not update any field when all input fields are null or unchanged")
        void shouldNotUpdateFields_whenAllFieldsAreNullOrUnchanged() {
            // ARRANGE
            Doctor updates = new Doctor();
            updates.setId(1L);

            when(queryService.findById(1L)).thenReturn(doctor);
            when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            // ACT
            Doctor updated = service.update(updates);

            // ASSERT
            assertSame(doctor, updated);

            verify(queryService).findById(1L);
            verify(queryService, never()).verifyEmail(anyLong(), anyString());
            verify(queryService, never()).verifyPhone(anyLong(), anyString());
            verify(repository).save(doctor);
        }

        @Test
        @DisplayName("Should throw EmailInUseException when trying to update with an email already in use")
        void shouldThrowEmailInUseException_whenUpdatingWithEmailAlreadyUsed() {
            // ARRANGE
            Doctor updates = new Doctor();
            updates.setId(1L);
            updates.setEmail("already@used.com");

            when(queryService.findById(1L)).thenReturn(doctor);
            doThrow(new EmailInUseException("This email is already in use"))
                    .when(queryService).verifyEmail(1L, "already@used.com");

            // ACT & ASSERT
            EmailInUseException ex = assertThrows(EmailInUseException.class,
                    () -> service.update(updates)
            );

            assertEquals("This email is already in use", ex.getMessage());

            verify(queryService).findById(1L);
            verify(queryService).verifyEmail(1L, "already@used.com");
            verify(queryService, never()).verifyPhone(anyLong(), anyString());
            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw PhoneInUseException when trying to update with a phone already in use")
        void shouldThrowPhoneInUseException_whenUpdatingWithPhoneAlreadyUsed() {
            // ARRANGE
            Doctor updates = new Doctor();
            updates.setId(1L);
            updates.setPhone("111111111");

            when(queryService.findById(1L)).thenReturn(doctor);
            doThrow(new PhoneInUseException("This phone is already in use"))
                    .when(queryService).verifyPhone(1L, "111111111");

            // ACT & ASSERT
            PhoneInUseException ex = assertThrows(PhoneInUseException.class,
                    () -> service.update(updates));

            verify(queryService).findById(1L);
            verify(queryService).verifyPhone(1L, "111111111");
            verify(queryService, never()).verifyEmail(anyLong(), anyString());
            verify(repository, never()).save(any());
        }
    }

    @Nested
    class SoftDeleteTests {

        @Test
        @DisplayName("Should deactivate doctor when valid ID is provided")
        void shouldDeactivateDoctor_whenValidIdProvided() {
            // ARRANGE
            Doctor spyDoctor = spy(doctor);
            when(queryService.findById(1L)).thenReturn(spyDoctor);
            when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            // ACT
            service.softDelete(1L);

            // ASSERT
            assertFalse(spyDoctor.getActive());

            InOrder inOrder = inOrder(queryService, spyDoctor, repository);
            inOrder.verify(queryService).findById(1L);
            inOrder.verify(spyDoctor).deactivate();
            inOrder.verify(repository).save(spyDoctor);
        }

        @Test
        @DisplayName("Should throw exception if doctor not found during softDelete")
        void shouldThrowNotFoundException_whenSoftDeleteAndDoctorDoesNotExist() {
            // ARRANGE
            when(queryService.findById(1L)).thenThrow(new NotFoundException("Doctor not found"));

            // ACT & ASSERT
            NotFoundException ex = assertThrows(NotFoundException.class,
                    () -> service.softDelete(1L)
            );

            assertEquals("Doctor not found", ex.getMessage());

            verify(queryService).findById(1L);
            verify(repository, never()).save(any());
        }
    }
}