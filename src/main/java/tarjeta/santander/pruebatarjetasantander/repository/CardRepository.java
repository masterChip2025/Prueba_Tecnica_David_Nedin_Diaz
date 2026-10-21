package tarjeta.santander.pruebatarjetasantander.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tarjeta.santander.pruebatarjetasantander.model.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {

    boolean existsByCardNumber(String cardNumber);

}
