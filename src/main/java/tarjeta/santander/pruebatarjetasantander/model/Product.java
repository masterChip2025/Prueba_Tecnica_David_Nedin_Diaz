package tarjeta.santander.pruebatarjetasantander.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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
        initialValue = 1000000
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @Min(value=1000000, message= "El Id debe contener minimo 6 digitos")
    @Max(value=9999999, message = "El Id no puede tener mas de 6 digitos")
    private Integer productId;

    private String name;
    private typeTransaction type;
}
