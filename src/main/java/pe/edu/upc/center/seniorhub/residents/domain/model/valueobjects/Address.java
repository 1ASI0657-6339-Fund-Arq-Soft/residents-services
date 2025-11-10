package pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Address(String city, String state, String country, String street, String zipCode) {

    public Address() {
        this(null, null, null, null, null);
    }

    public Address {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or blank");
        }
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("State cannot be null or blank");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be null or blank");
        }
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be null or blank");
        }
        if (zipCode == null || zipCode.isBlank()) {
            throw new IllegalArgumentException("ZipCode cannot be null or blank");
        }
    }
}
