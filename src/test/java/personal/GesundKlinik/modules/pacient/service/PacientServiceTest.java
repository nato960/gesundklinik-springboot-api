package personal.GesundKlinik.modules.pacient.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import personal.GesundKlinik.modules.pacient.entity.Pacient;
import personal.GesundKlinik.modules.pacient.query.IPacientQueryService;
import personal.GesundKlinik.modules.pacient.repository.IPacientRepository;
import personal.GesundKlinik.shared.exception.EmailInUseException;
import personal.GesundKlinik.shared.exception.NotFoundException;
import personal.GesundKlinik.shared.exception.PhoneInUseException;
import personal.GesundKlinik.shared.model.Address;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacientServiceTest {

    @Mock
    private IPacientRepository repository;

    @Mock
    private IPacientQueryService queryService;

    @InjectMocks
    private PacientService service;

    private Pacient pacient;

    @BeforeEach
    void setup() {
        pacient = new Pacient();
        pacient.setId(1L);
        pacient.setName("Renato");
        pacient.setEmail("renato@email.com");
        pacient.setCpf("8888888888");
        pacient.setPhone("99999999");
        pacient.setAddress(new Address("Rua A", "123", "Cidade", "UF", "12345678"));
    }

    @Nested
    class SaveTests {

        @Test
        @DisplayName("Should successfully save patient when provided with valid data")
        void shouldSavePacientSuccessfully(){
            // ARRANGE
            when(repository.save(pacient)).thenReturn(pacient);

            // ACT
            Pacient saved = service.save(pacient);

            // ASSERT
            assertThat(saved).usingRecursiveComparison().isEqualTo(pacient);

            InOrder inOrder = inOrder(queryService, repository);
            inOrder.verify(queryService).verifyEmail(pacient.getEmail());
            inOrder.verify(queryService).verifyPhone(pacient.getPhone());
            inOrder.verify(repository).save(pacient);
        }

        @Test
        @DisplayName("Should throw EmailInUseException when email is already in use")
        void shouldThrowEmailInUseExceptionWhenEmailIsAlreadyInUse(){
            // ARRANGE
            doThrow(new EmailInUseException("This email is already in use"))
                    .when(queryService).verifyEmail(pacient.getEmail());

            // ACT and ASSERT
            EmailInUseException ex = assertThrows(
                    EmailInUseException.class,
                    ()-> service.save(pacient));

            assertEquals("This email is already in use", ex.getMessage());

            verify(queryService).verifyEmail(pacient.getEmail());
            verify(queryService, never()).verifyPhone(any());
            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw PhoneInUseException when phone is already in use")
        void shouldThrowPhoneInUseExceptionWhenPhoneIsAlreadyInUse(){
            // ARRANGE
            doNothing().when(queryService).verifyEmail(pacient.getEmail());
            doThrow(new PhoneInUseException("This phone is already in use"))
                    .when(queryService).verifyPhone(pacient.getPhone());

            // ACT and ASSERT
            PhoneInUseException ex = assertThrows(
                    PhoneInUseException.class,
                    ()-> service.save(pacient));

            assertEquals("This phone is already in use", ex.getMessage());

            verify(queryService).verifyEmail(pacient.getEmail());
            verify(queryService).verifyPhone(pacient.getPhone());
            verify(repository, never()).save(any());
        }
    }

    @Nested
    class UpdateTests {

        @Test
        @DisplayName("Should update patient name and address when fields are provided")
        void shouldUpdateNameAndAddress() {
            // ARRANGE
            Pacient updates = new Pacient();
            updates.setId(1L);
            updates.setName("New Name");
            updates.setAddress(new Address("New Street", "456", "New City", "TX", "22222222"));

            when(queryService.findById(1L)).thenReturn(pacient);
            when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            // ACT
            Pacient updated = service.update(updates);

            // ASSERT
            assertEquals("New Name", updated.getName());
            assertThat(updated.getAddress()).usingRecursiveComparison().isEqualTo(updates.getAddress());

            verify(queryService).findById(1L);
            verify(queryService, never()).verifyEmail(any(), any());
            verify(queryService, never()).verifyPhone(any(), any());
            verify(repository).save(pacient);
        }

        @Test
        @DisplayName("Should update email and phone when both are changed and available")
        void shouldUpdateEmailAndPhoneWhenBothAreChangedAndAvailable() {
            // ARRANGE
            Pacient updates = new Pacient();
            updates.setId(1L);
            updates.setEmail("newemail@email.com");
            updates.setPhone("111111111");

            when(queryService.findById(1L)).thenReturn(pacient);
            when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            // ACT
            Pacient updated = service.update(updates);

            // ASSERT
            assertEquals("newemail@email.com", updated.getEmail());
            assertEquals("111111111", updated.getPhone());

            InOrder inOrder = inOrder(queryService, repository);

            inOrder.verify(queryService).findById(1L);
            inOrder.verify(queryService).verifyEmail(1L, updates.getEmail());
            inOrder.verify(queryService).verifyPhone(1L, updates.getPhone());
            inOrder.verify(repository).save(pacient);
        }

        @Test
        @DisplayName("Should not update any field when all inputs are null or equal to existing values")
        void shouldNotUpdateAnythingWhenFieldsAreNullOrEqual() {
            // ARRANGE
            Pacient updates = new Pacient();
            updates.setId(1L);

            when(queryService.findById(1L)).thenReturn(pacient);
            when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            // ACT
            Pacient updated = service.update(updates);

            // ASSERT
            assertSame(pacient, updated);

            verify(queryService).findById(1L);
            verify(queryService, never()).verifyEmail(anyLong(), anyString());
            verify(queryService, never()).verifyPhone(anyLong(), anyString());
            verify(repository).save(pacient);
        }

        @Test
        @DisplayName("Should throw EmailInUseException when trying to update with an email already in use")
        void shouldThrowEmailInUseExceptionWhenEmailIsAlreadyUsed() {
            // ARRANGE
            Pacient updates = new Pacient();
            updates.setId(1L);
            updates.setEmail("already@used.com");

            when(queryService.findById(1L)).thenReturn(pacient);
            doThrow(new EmailInUseException("This email is already in use"))
                    .when(queryService).verifyEmail(1L, "already@used.com");

            // ACT & ASSERT
            EmailInUseException ex = assertThrows(
                    EmailInUseException.class,
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
        void shouldThrowPhoneInUseExceptionWhenPhoneIsAlreadyUsed() {
            // ARRANGE
            Pacient updates = new Pacient();
            updates.setId(1L);
            updates.setPhone("111111111");

            when(queryService.findById(1L)).thenReturn(pacient);
            doThrow(new PhoneInUseException("This phone is already in use"))
                    .when(queryService).verifyPhone(1L, "111111111");

            // ACT & ASSERT
            PhoneInUseException ex = assertThrows(
                    PhoneInUseException.class,
                    () -> service.update(updates)
            );

            assertEquals("This phone is already in use", ex.getMessage());

            verify(queryService).findById(1L);
            verify(queryService).verifyPhone(1L, "111111111");
            verify(queryService, never()).verifyEmail(anyLong(), anyString());
            verify(repository, never()).save(any());
        }
    }

    @Nested
    class SoftDeleteTests {

        @Test
        @DisplayName("Should deactivate and save patient when valid ID is provided")
        void shouldSoftDeletePatientSuccessfully() {
            // ARRANGE
            Pacient toDeactivate = new Pacient();
            toDeactivate.setId(1L);
            toDeactivate.setActive(true);

            when(queryService.findById(1L)).thenReturn(toDeactivate);
            when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

            // ACT
            service.softDelete(1L);

            // ASSERT
            assertFalse(toDeactivate.getActive());

            InOrder inOrder = inOrder(queryService, repository);
            inOrder.verify(queryService).findById(1L);
            inOrder.verify(repository).save(toDeactivate);
        }

        @Test
        @DisplayName("Should throw exception if patient not found during softDelete")
        void shouldThrowExceptionIfPatientNotFound() {
            // ARRANGE
            when(queryService.findById(1L)).thenThrow(new NotFoundException("Pacient not found"));

            // ACT & ASSERT
            assertThrows(NotFoundException.class, () -> service.softDelete(1L));

            verify(queryService).findById(1L);
            verify(repository, never()).save(any());
        }
    }
}