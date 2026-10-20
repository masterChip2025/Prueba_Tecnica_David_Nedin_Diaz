package tarjeta.santander.pruebatarjetasantander.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Persistent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Random;

@Entity
@Table(name="Cards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_id")
    private Integer cardId;

    @Column(name="card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(name="holder_name", nullable = false, length = 100)
    private String holderName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Column(name = "issued_At", nullable = false, updatable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expires_At", nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CardStatus status = CardStatus.ISSUED;

    @Column(name = "blocked At")
    private LocalDateTime blockedAt;

    @Column(name = "blocked_Reason", length = 255)
    private String blockedReason;

    @Column(name = "balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency="USD" ;

    @PrePersist
    protected void onCreate(){
        if(this.product == null || this.product.getProductId() == null){
            throw new IllegalStateException("no es posible generar un numero de tarjeta sin producto asignado.");
        }

        String prefix = String.valueOf(this.product.getProductId());
        if(prefix.length() != 6){
            prefix = String.format("%06d", this.product.getProductId());
        }

        String randonDigits = generateRamdonDigits(10);

        this.cardNumber = prefix + randonDigits;

    }

    private String generateRamdonDigits( int length){
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i< length; i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }




}
