package personal.GesundKlinik.modules.appointment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import personal.GesundKlinik.modules.appointment.entity.Appointment;
import personal.GesundKlinik.modules.appointment.entity.CancellationReason;
import personal.GesundKlinik.modules.appointment.query.IAppointmentQueryService;
import personal.GesundKlinik.modules.appointment.repository.IAppointmentRepository;
import personal.GesundKlinik.modules.appointment.validation.IAppointmentValidator;
import personal.GesundKlinik.modules.doctor.entity.Doctor;
import personal.GesundKlinik.modules.doctor.entity.Speciality;
import personal.GesundKlinik.modules.doctor.query.IDoctorQueryService;
import personal.GesundKlinik.modules.patient.entity.Patient;
import personal.GesundKlinik.modules.patient.query.IPatientQueryService;
import personal.GesundKlinik.shared.exception.AppointmentAlreadyCancelledException;
import personal.GesundKlinik.shared.exception.InvalidPatientIdException;
import personal.GesundKlinik.shared.exception.NoSpecialityChosenException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private IAppointmentRepository repository;
    @Mock
    private IDoctorQueryService doctorQueryService;
    @Mock
    private IPatientQueryService patientQueryService;
    @Mock
    private IAppointmentQueryService appointmentQueryService;
    @Mock
    private IAppointmentValidator validator;

    @InjectMocks
    private AppointmentService service;

    @Captor
    private ArgumentCaptor<Appointment> appointmentCaptor;

    private Appointment appointment;
    private Doctor doctor;
    private Patient patient;

    @BeforeEach
    void setup() {
        doctor = new Doctor();
        doctor.setId(1L);

        patient = new Patient();
        patient.setId(2L);

        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(LocalDateTime.now().plusDays(1));
        appointment.setSpeciality(Speciality.CARDIOLOGY);


        service = new AppointmentService(
                repository,
                doctorQueryService,
                patientQueryService,
                appointmentQueryService,
                List.of(validator)
        );

    }

    @Nested
    class ScheduleTests {

        @Test
        @DisplayName("Should call dependencies in order when doctor and patient are valid")
        void shouldCallDependenciesInOrder_whenDoctorAndPatientAreValid() {
            // ARRANGE
            Appointment spyAppointment = spy(appointment);

            doNothing().when(appointmentQueryService).verifyDoctorExists(1L);
            doNothing().when(appointmentQueryService).verifyPatientExists(2L);

            when(doctorQueryService.getReferenceById(1L)).thenReturn(doctor);
            when(patientQueryService.getReferenceById(2L)).thenReturn(patient);
            when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

            // ACT
            service.schedule(spyAppointment);

            // ASSERT
            InOrder inOrder = inOrder(
                    appointmentQueryService,
                    patientQueryService,
                    doctorQueryService,
                    validator,
                    repository
            );

            inOrder.verify(appointmentQueryService).verifyDoctorExists(1L);
            inOrder.verify(appointmentQueryService).verifyPatientExists(2L);
            inOrder.verify(patientQueryService).getReferenceById(2L);
            inOrder.verify(doctorQueryService).getReferenceById(1L);
            inOrder.verify(validator).validate(spyAppointment);
            inOrder.verify(repository).save(spyAppointment);
        }

        @Test
        @DisplayName("Should persist correct appointment when doctor and patient are valid")
        void shouldPersistCorrectAppointment_whenDoctorAndPatientAreValid() {
        }

        @Test
        @DisplayName("Should throw InvalidPatientIdException when patient is null or ID is null")
        void shouldThrowInvalidPatientIdException_whenPatientIsNullOrIdIsNull() { }

        @Test
        @DisplayName("Should throw NoSpecialityChosenException when no doctor and no speciality provided")
        void shouldThrowNoSpecialityChosenException_whenNoDoctorAndNoSpecialityProvided() { }

        @Test
        @DisplayName("Should throw NoDoctorAvailableOnThisDateException when no doctor found for speciality and date")
        void shouldThrowNoDoctorAvailableOnThisDateException_whenNoDoctorFoundForSpecialityAndDate() { }

        @Test
        @DisplayName("Should throw DoctorAlreadyAssignedException when assigning doctor twice")
        void shouldThrowDoctorAlreadyAssignedException_whenAssigningDoctorTwice() { }

        @Test
        @DisplayName("Should throw PatientAlreadyAssignedException when assigning patient twice")
        void shouldThrowPatientAlreadyAssignedException_whenAssigningPatientTwice() { }

        @Test
        @DisplayName("Should run all validators in order when scheduling appointment")
        void shouldRunAllValidatorsInOrder_whenSchedulingAppointment() { }

        @Test
        @DisplayName("Should save appointment with resolved entities when valid input is given")
        void shouldSaveAppointmentWithResolvedEntities_whenValidInputIsGiven() { }

    }

    @Nested
    class RescheduleTests {

        @Test
        @DisplayName("Should call update and save in order when rescheduling with date and doctor")
        void shouldCallUpdateAndSaveInOrder_whenReschedulingWithDateAndDoctor() {
        }

        @Test
        @DisplayName("Should persist updated appointment when rescheduling with date and doctor")
        void shouldPersistUpdatedAppointment_whenReschedulingWithDateAndDoctor() {
        }

        @Test
        @DisplayName("Should skip doctor update when only date is provided")
        void shouldSkipDoctorUpdate_whenOnlyDateProvided() {
        }

        @Test
        @DisplayName("Should update only date when only date is provided")
        void shouldUpdateOnlyDate_whenOnlyDateProvided() {
        }

        @Test
        @DisplayName("Should call doctor update only when only doctor is provided")
        void shouldCallDoctorUpdateOnly_whenOnlyDoctorProvided() {
        }

        @Test
        @DisplayName("Should update only doctor when only doctor is provided")
        void shouldUpdateOnlyDoctor_whenOnlyDoctorProvided() {
        }

        @Test
        @DisplayName("Should throw InvalidPatientIdException when changing patient ID")
        void shouldThrowInvalidPatientIdException_whenChangingPatientId() { }

        @Test
        @DisplayName("Should throw NoDoctorAvailableException when doctor ID is invalid")
        void shouldThrowNoDoctorAvailableException_whenDoctorIdIsInvalid() { }

        @Test
        @DisplayName("Should run all validators in order when rescheduling")
        void shouldRunAllValidatorsInOrder_whenRescheduling() { }

        @Test
        @DisplayName("Should persist changes correctly after successful reschedule")
        void shouldPersistChangesCorrectly_afterSuccessfulReschedule() { }

    }

    @Nested
    class CancelTests {

        @Test
        @DisplayName("Should call cancelWithReason when valid cancellation reason is provided")
        void shouldCallCancelWithReason_whenValidCancellationReasonProvided() {
        }

        @Test
        @DisplayName("Should persist cancelled appointment when valid cancellation reason is provided")
        void shouldPersistCancelledAppointment_whenValidCancellationReasonProvided() {
        }

        @Test
        @DisplayName("Should throw AppointmentAlreadyCancelledException when already cancelled")
        void shouldThrowAppointmentAlreadyCancelledException_whenAlreadyCancelled() { }

        @Test
        @DisplayName("Should throw IllegalArgumentException when cancellation reason is null")
        void shouldThrowIllegalArgumentException_whenCancellationReasonIsNull() { }

        @Test
        @DisplayName("Should save appointment with cancellation reason when cancellation is successful")
        void shouldSaveAppointmentWithCancellationReason_whenCancellationIsSuccessful() { }

    }

}