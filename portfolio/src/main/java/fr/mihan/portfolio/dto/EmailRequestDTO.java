package fr.mihan.portfolio.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record EmailRequestDTO(
        Sender sender,
        List<To> to,
        String subject,
        String htmlContent
) {
    public record Sender(String name, String email) {}
    public record To(String email) {}
}
