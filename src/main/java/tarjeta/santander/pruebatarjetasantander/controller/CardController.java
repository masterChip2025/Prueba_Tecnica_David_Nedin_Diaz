package tarjeta.santander.pruebatarjetasantander.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tarjeta.santander.pruebatarjetasantander.dto.BlockRequestDTO;
import tarjeta.santander.pruebatarjetasantander.dto.CardRequestDTO;
import tarjeta.santander.pruebatarjetasantander.dto.CardResponseDTO;
import tarjeta.santander.pruebatarjetasantander.dto.GetCardResponseDTO;
import tarjeta.santander.pruebatarjetasantander.service.CardService;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponseDTO> emitCard(@Valid @RequestBody CardRequestDTO request) {
        //System.out.println("Inicio Controller Crear Card ");
        CardResponseDTO response = cardService.emitCard(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{cardId}/activate")
    public ResponseEntity<Void> activateCard(@PathVariable Integer cardId) {
        cardService.activateCard(cardId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cardId}/block")
    public ResponseEntity<Void> blockCard(
            @PathVariable Integer cardId,
            @Valid @RequestBody BlockRequestDTO request) {
        cardService.blockCard(cardId, request.blockedReason());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<GetCardResponseDTO> getCard(@PathVariable Integer cardId) {
        GetCardResponseDTO response = cardService.getCardById(cardId);

        return ResponseEntity.ok(response);
    }
}
