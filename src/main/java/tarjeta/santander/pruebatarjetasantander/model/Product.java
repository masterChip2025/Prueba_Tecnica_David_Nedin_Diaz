package tarjeta.santander.pruebatarjetasantander.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(
        name= "product_id_seq",
        sequenceName="seq_product_id",
        allocationSize = 1,
        initialValue = 100000
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @Min(value=100000, message= "El Id debe contener minimo 6 digitos")
    @Max(value=999999, message = "El Id no puede tener mas de 6 digitos")
    @Column(name = "product_id")
    private Integer productId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeTransaction type;
}
