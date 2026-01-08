package fr.mihan.portfolio.controllers;

import fr.mihan.portfolio.dto.ContactDTO;
import fr.mihan.portfolio.dto.SuccessResponseDTO;
import fr.mihan.portfolio.services.impl.ApiBrevoEmailServiceImpl;
import fr.mihan.portfolio.services.impl.RateLimitServiceImpl;
import io.github.bucket4j.Bucket;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contact")
public class ContactController {
    private final ApiBrevoEmailServiceImpl apiBrevoEmailService;
    private final RateLimitServiceImpl rateLimitService;

    /**
     * Envoi de message
     * @param dto les infos du user qui envoie le msg
     * @param request la requette http
     * @return response
     * @throws MessagingException en cas d'exception
     */
    @PostMapping
    public ResponseEntity<SuccessResponseDTO> sendMessage(
            @Valid @RequestBody ContactDTO dto,
            HttpServletRequest request
    ) throws MessagingException {

        final String ip = request.getRemoteAddr();
        Bucket bucket = rateLimitService.resolveBucket(ip);

        if (bucket.tryConsume(1)) {
            apiBrevoEmailService.sendEmail(dto);
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
