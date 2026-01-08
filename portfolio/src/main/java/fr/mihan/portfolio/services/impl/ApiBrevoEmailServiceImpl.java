package fr.mihan.portfolio.services.impl;

import fr.mihan.portfolio.dto.ContactDTO;
import fr.mihan.portfolio.providers.ContactMail;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiBrevoEmailServiceImpl implements ContactMail{
    @Qualifier("apiBrevoEmailProviderImpl")
    private final ContactMail contactMail;

    @Override
    public void sendEmail(ContactDTO dto) throws MessagingException {
        contactMail.sendEmail(dto);
    }
}
