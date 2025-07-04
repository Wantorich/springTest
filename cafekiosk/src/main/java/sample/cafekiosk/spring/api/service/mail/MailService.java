package sample.cafekiosk.spring.api.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

@Service
@RequiredArgsConstructor
public class MailService {

  private final MailSendHistoryRepository mailSendHistoryRepository;
  private final MailSendClient mailSendClient;

  public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {

    boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);
    if (result) {
      mailSendHistoryRepository.save(
          MailSendHistory.builder()
              .fromEmail(fromEmail)
              .toEmail(toEmail)
              .subject(subject)
              .content(content)
              .build()
      );
      return true;
    }
    return false;
  }
}
