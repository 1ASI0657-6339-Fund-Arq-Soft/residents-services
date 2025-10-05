package pe.edu.upc.center.seniorhub.residents.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ReceiptId(Long receiptId) {

    public ReceiptId() {
        this(null);
    }

    public ReceiptId {
        if (receiptId == null || receiptId <= 0) {
            throw new IllegalArgumentException("ReceiptId must be positive and not null");
        }
    }
}
