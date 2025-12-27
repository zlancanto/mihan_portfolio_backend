package fr.mihan.portfolio.controller;

import fr.mihan.portfolio.dto.ContactDTO;
import fr.mihan.portfolio.dto.SuccessResponseDTO;
import fr.mihan.portfolio.service.ContactMailService;
import fr.mihan.portfolio.service.RateLimitService;
import io.github.bucket4j.Bucket;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
    private final ContactMailService contactMailService;
    private final RateLimitService rateLimitService;

    public ContactController(
            ContactMailService contactMailService,
            RateLimitService rateLimitService
    ) {
        this.contactMailService = contactMailService;
        this.rateLimitService = rateLimitService;
    }

    /**
     * Envoi de message
     * @param dto
     * @param request
     * @return response
     * @throws MessagingException
     */
    @PostMapping
    public ResponseEntity<SuccessResponseDTO> sendMessage(
            @Valid @RequestBody ContactDTO dto,
            HttpServletRequest request
    ) throws MessagingException {

        final String ip = request.getRemoteAddr();
        Bucket bucket = rateLimitService.resolveBucket(ip);

        if (bucket.tryConsume(1)) {
            contactMailService.sendMail(dto);
            return ResponseEntity.ok(new SuccessResponseDTO("Message envoyé avec succès."));
        }
        else {
            return new ResponseEntity<>(
                    new SuccessResponseDTO("Trop de messages envoyés. Veuillez réessayer plus tard."),
                    HttpStatus.TOO_MANY_REQUESTS
            );
        }
    }
}
