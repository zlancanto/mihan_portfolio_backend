package fr.mihan.portfolio.exceptions.handler;

import fr.mihan.portfolio.dto.ErrorResponseDTO;
import fr.mihan.portfolio.exceptions.ContactMailException;
import fr.mihan.portfolio.services.impl.ApiBrevoEmailServiceImpl;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = ApiBrevoEmailServiceImpl.class)
public class ContactExceptionHandler {

    @ExceptionHandler(ContactMailException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleContactMailException(ContactMailException ex) {
        log.warn("Erreur métier d'envoi de mail : {}", ex.getMessage());

        ErrorResponseDTO error = new ErrorResponseDTO(
                "Serveur d'envoi de mail momentanément indisponible",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler({MessagingException.class, RuntimeException.class})
    public ResponseEntity<ErrorResponseDTO>
    handleMessagingExceptionException(MessagingException exception) {

        log.error(exception.getMessage(), exception);

        ErrorResponseDTO error = new ErrorResponseDTO(
                "Le service est momentanément indisponible. Veuillez réessayer plus tard",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
