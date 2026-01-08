package fr.mihan.portfolio.exceptions;

import fr.mihan.portfolio.dto.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final
    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * L'email n'est pas renseigné ou est invalide
     * @param exception l'exception
     * @return response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleValidationExceptions(Exception exception) {

        log.error(exception.getMessage(), exception);

        ErrorResponseDTO error = new ErrorResponseDTO(
                "Données de formulaire invalides",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Erreur spécifique si l'envoi de mail échoue
     * @param exception l'exception
     * @return response
     */
    @ExceptionHandler(MailException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleMailException(Exception exception) {

        log.error(exception.getMessage(), exception);

        ErrorResponseDTO error = new ErrorResponseDTO(
                "Désolé, le service d'envoi de mail est temporairement indisponible.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Gestion générique pour toutes les autres erreurs
     * @param exception l'exception
     * @return response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO>
    handleAllExceptions(Exception exception) {

        log.error(exception.getMessage(), exception);

        ErrorResponseDTO error = new ErrorResponseDTO(
                "Une erreur interne est survenue.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
