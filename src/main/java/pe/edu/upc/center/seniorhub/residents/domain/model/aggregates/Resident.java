<<<<<<< HEAD
=======

>>>>>>> develop
package pe.edu.upc.center.seniorhub.residents.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.edu.upc.center.seniorhub.residents.domain.model.commands.CreateResidentCommand;
<<<<<<< HEAD
import pe.edu.upc.center.seniorhub.residents.domain.model.entities.MedicalHistory;
import pe.edu.upc.center.seniorhub.residents.domain.model.entities.Medication;
import pe.edu.upc.center.seniorhub.residents.domain.model.entities.MentalHealthRecord;
=======
>>>>>>> develop
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.Address;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.FullName;
import pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects.ReceiptId;
import pe.edu.upc.center.seniorhub.residents.shared.domain.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "residents")
@Getter
@NoArgsConstructor
public class Resident extends AuditableAbstractAggregateRoot<Resident> {

    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true, length = 20)
    private String dni;

    @Embedded
    private FullName fullName;

    @Embedded
    private Address address;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @NotNull
    @Column(nullable = false)
    private String gender;

    @Embedded
    private ReceiptId receipt;

<<<<<<< HEAD
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "resident_id")
    private List<Medication> medication = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "resident_id")
    private List<MedicalHistory> medicalHistories = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "resident_id")
    private List<MentalHealthRecord> mentalHealthRecords = new ArrayList<>();

    // Constructor completo
    public Resident(String dni, FullName fullName, Address address, Date birthDate,
                    String gender, ReceiptId receipt, List<Medication> medication,
                    List<MedicalHistory> medicalHistories, List<MentalHealthRecord> mentalHealthRecords) {
        this.dni = dni;
        this.fullName = fullName;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
        this.receipt = receipt;
        this.medication = medication;
        this.medicalHistories = medicalHistories;
        this.mentalHealthRecords = mentalHealthRecords;
    }

    // Constructor desde CreateResidentCommand
    public Resident(CreateResidentCommand command) {
        this(
                command.dni(),
                command.fullName(),
                command.address(),
                command.birthDate(),
                command.gender(),
                command.receipt(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    // Método para actualizar información básica
    public void updateInformation(String dni, FullName fullName, Address address,
                                  Date birthDate, String gender, ReceiptId receipt) {
        this.dni = dni;
        this.fullName = fullName;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
        this.receipt = receipt;
    }

    // Métodos de dominio
    public void addMedication(Medication med) {
        this.medication.add(med);
    }

    public void removeMedication(Medication med) {
        this.medication.remove(med);
    }

    public void addMedicalHistory(MedicalHistory history) {
        this.medicalHistories.add(history);
    }

    public void addMentalHealthRecord(MentalHealthRecord record) {
        this.mentalHealthRecords.add(record);
    }

    public void removeMedicationById(Long medicationId) {
        this.medication.removeIf(med -> med.getId().equals(medicationId));
    }

    public void removeMedicalHistoryById(Long medicalHistoryId) {
        this.medicalHistories.removeIf(med -> med.getId().equals(medicalHistoryId));
    }

    public void removeMentalHealthRecordById(Long mentalHealthRecordId) {
        this.mentalHealthRecords.removeIf(med -> med.getId().equals(mentalHealthRecordId));
    }
=======
>>>>>>> develop

    public String getFullNameAsString() {
        return fullName.firstName() + " " + fullName.lastName();
    }
<<<<<<< HEAD
=======
        public Resident(String dni, FullName fullName, Address address, Date birthDate, String gender, ReceiptId receipt) {
        this.dni = dni;
        this.fullName = fullName;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
        this.receipt = receipt;
    }

    public void updateInformation(String dni, FullName fullName, Address address, Date birthDate, String gender, ReceiptId receipt) {
        this.dni = dni;
        this.fullName = fullName;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
        this.receipt = receipt;
    }
>>>>>>> develop
}
