package pe.edu.upc.center.seniorhub.residents.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.edu.upc.center.seniorhub.residents.shared.domain.entities.AuditableModel;

import java.util.Date;

@Entity
@Table(name = "medical_histories")
@Getter
@NoArgsConstructor
public class MedicalHistory extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "record_date", nullable = false)
    private Date recordDate;

    @NotBlank
    @Column(nullable = false)
    private String diagnosis;

    @NotBlank
    @Column(nullable = false)
    private String treatment;

    public MedicalHistory(Date recordDate, String diagnosis, String treatment) {
        this.recordDate = recordDate;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public void update(String diagnosis, String treatment) {
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }
}
