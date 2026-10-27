package tarjeta.santander.pruebatarjetasantander.service;

import tarjeta.santander.pruebatarjetasantander.dto.CardStatusResponseDTO;

public interface ICardService {
    CardStatusResponseDTO activateCard(Integer cardId);
}
