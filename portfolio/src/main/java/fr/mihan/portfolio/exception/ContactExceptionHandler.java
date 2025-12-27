package fr.mihan.portfolio.exception;

import fr.mihan.portfolio.dto.ErrorResponseDTO;
import fr.mihan.portfolio.service.ContactMailService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = ContactMailService.class)
public class ContactExceptionHandler {

    private static final
    Logger log = LoggerFactory.getLogger(ContactExceptionHandler.class);

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleMessagingExceptionException(MessagingException exception) {

        log.error(exception.getMessage(), exception);

        ErrorResponseDTO error = new ErrorResponseDTO(
                "Une erreur technique est survenue lors de la préparation de l'email.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
