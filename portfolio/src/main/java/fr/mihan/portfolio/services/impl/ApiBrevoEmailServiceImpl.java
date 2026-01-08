package fr.mihan.portfolio.services.impl;

import fr.mihan.portfolio.dto.ContactDTO;
import fr.mihan.portfolio.providers.ContactMailProvider;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApiBrevoEmailServiceImpl {
    //@Qualifier("apiBrevoEmailProviderImpl")
    private final Map<String, ContactMailProvider> contactMail;

    public void sendEmail(ContactDTO dto) throws MessagingException {
        contactMail.get("apiBrevoEmailProviderImpl").sendEmail(dto);
    }
}
