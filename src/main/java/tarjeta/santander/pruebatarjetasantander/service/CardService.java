package tarjeta.santander.pruebatarjetasantander.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import tarjeta.santander.pruebatarjetasantander.dto.CardRequestDTO;
import tarjeta.santander.pruebatarjetasantander.dto.CardResponseDTO;
import tarjeta.santander.pruebatarjetasantander.model.Card;
import tarjeta.santander.pruebatarjetasantander.model.CardStatus;
import tarjeta.santander.pruebatarjetasantander.model.Product;
import tarjeta.santander.pruebatarjetasantander.repository.CardRepository;
import tarjeta.santander.pruebatarjetasantander.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public CardResponseDTO emitCard(CardRequestDTO request){
        Product product = productRepository.findById(request.productId())
                .orElseThrow(()-> new RuntimeException("Producto no encontrado con el Id especificado."));

        Card card = new Card();
        card.setProduct(product);
        card.setHolderName(request.holderName());
        card.setCurrency(request.currency());
        card.setBalance(new BigDecimal("0.00"));
        card.setStatus(CardStatus.ISSUED);

        LocalDateTime now = LocalDateTime.now();

        card.setIssuedAt(now);
        card.setExpiresAt(now.toLocalDate().plusYears(3));

        String generatedNumber;
        do {
            generatedNumber = generateCardNumber(product.getProductId());
        } while (cardRepository.existsByCardNumber(generatedNumber));

        card.setCardNumber(generatedNumber);

        Card savedCard = cardRepository.save(card);
        return mapToResponseDTO(savedCard);
    }

    @Transactional
    public void activateCard(Integer cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));

        if ("ACTIVE".equals(card.getStatus())) {
            throw new IllegalStateException("La tarjeta ya se encuentra activa.");
        }
        if ("BLOCKED".equals(card.getStatus())) {
            throw new IllegalStateException("No se puede activar una tarjeta bloqueada.");
        }

        card.setStatus(CardStatus.ISSUED);
        cardRepository.save(card);
    }

    @Transactional
    public void blockCard(Integer cardId, String reason) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));

        card.setStatus(CardStatus.BLOCKED);
        card.setBlockedAt(LocalDateTime.now());
        card.setBlockedReason(reason);
        cardRepository.save(card);
    }

    @Transactional()
    public CardResponseDTO getCardById(Integer cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));
        return mapToResponseDTO(card);
    }

    private String generateCardNumber(Integer productId) {
        String prefix = String.format("%06d", productId);
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return prefix + sb.toString();
    }

    private CardResponseDTO mapToResponseDTO(Card card) {
        return new CardResponseDTO(
                card.getCardId(),
                card.getCardNumber(),
                card.getHolderName(),
                card.getStatus(),
                card.getBlockedAt(),
                card.getBlockedReason(),
                card.getExpiresAt(),
                card.getBalance(),
                card.getCurrency()
        );
    }
}
