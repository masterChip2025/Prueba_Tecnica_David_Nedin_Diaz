package tarjeta.santander.pruebatarjetasantander.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(Integer cardId) {
        super("Card no encontrada con ID: " + cardId);
    }
}
