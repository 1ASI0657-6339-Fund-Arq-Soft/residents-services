
package pe.edu.upc.center.seniorhub.residents.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.edu.upc.center.seniorhub.residents.domain.model.commands.CreateResidentCommand;
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


    public String getFullNameAsString() {
        return fullName.firstName() + " " + fullName.lastName();
    }
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
}
