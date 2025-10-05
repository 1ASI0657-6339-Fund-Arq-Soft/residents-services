package pe.edu.upc.center.seniorhub.residents.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.edu.upc.center.seniorhub.residents.shared.domain.entities.AuditableModel;

@Entity
@Table(name = "medications")
@Getter
@NoArgsConstructor
public class Medication extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String frequency;

    public Medication(String name, String frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public void updateMedication(String name, String frequency) {
        this.name = name;
        this.frequency = frequency;
    }
}
