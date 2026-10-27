package tarjeta.santander.pruebatarjetasantander.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tarjeta.santander.pruebatarjetasantander.dto.CardRequestDTO;
import tarjeta.santander.pruebatarjetasantander.dto.CardResponseDTO;
import tarjeta.santander.pruebatarjetasantander.dto.CardStatusResponseDTO;
import tarjeta.santander.pruebatarjetasantander.dto.GetCardResponseDTO;
import tarjeta.santander.pruebatarjetasantander.exception.CardNotFoundException;
import tarjeta.santander.pruebatarjetasantander.exception.InvalidCardStatusException;
import tarjeta.santander.pruebatarjetasantander.model.Card;
import tarjeta.santander.pruebatarjetasantander.model.CardStatus;
import tarjeta.santander.pruebatarjetasantander.model.Product;
import tarjeta.santander.pruebatarjetasantander.repository.CardRepository;
import tarjeta.santander.pruebatarjetasantander.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardService implements ICardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Transactional
    public CardResponseDTO emitCard(CardRequestDTO request){
        System.out.println("Inicio Service Crear Card ");
        Product product = productRepository.findById(request.productId())
                .orElseThrow(()-> new tarjeta.santander.pruebatarjetasantander.exception.ResourceNotFoundException(
                        "Producto no encontrado con el Id especificado."));

        Card card = new Card();
        card.setProduct(product);
        card.setHolderName(request.holderName());
        card.setBalance(new java.math.BigDecimal("0.00"));
        card.setCurrency("USD");
        card.setStatus(CardStatus.ISSUED);
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        card.setIssuedAt(now);
        card.setExpiresAt(now.toLocalDate().plusYears(3));

        Card savedCard = cardRepository.save(card);
        return mapToResponseDTO(savedCard);
    }

    @Transactional
    @Override
    public CardStatusResponseDTO activateCard(Integer cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));

        if (card.getStatus() == CardStatus.ACTIVE) {
            return modelMapper.map(card, CardStatusResponseDTO.class);
        }
        if (card.getStatus() == CardStatus.BLOCKED) {
            throw new InvalidCardStatusException(
                    "No es posible activar una tarjeta BLOCKED");
        }

        if (card.getStatus() != CardStatus.ISSUED) {
            throw new InvalidCardStatusException(
                    "Estado inválido para activar la tarjeta");
        }

        card.setStatus(CardStatus.ACTIVE);
        Card savedCard = cardRepository.save(card);
        return modelMapper.map(savedCard, CardStatusResponseDTO.class);
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

    @Transactional
    public GetCardResponseDTO getCardById(Integer cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada; Id: "+cardId));
       return modelMapper.map(card, GetCardResponseDTO.class);

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
        String expiresAtFormatted = card.getExpiresAt() != null
                ? card.getExpiresAt().toString()
                : "";

        return new CardResponseDTO(
                String.valueOf(card.getCardId()),
                card.getCardNumber(),
                card.getStatus().name(),
                expiresAtFormatted,
                card.getBalance(),
                card.getCurrency()
        );
    }
}
