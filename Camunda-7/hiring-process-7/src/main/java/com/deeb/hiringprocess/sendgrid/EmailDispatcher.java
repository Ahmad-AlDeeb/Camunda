package com.deeb.hiringprocess.sendgrid;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class EmailDispatcher {
    private static final String EMAIL_ENDPOINT = "mail/send";
    private final SendGrid sendGrid;
    private final Email fromEmail;

    public void dispatchEmail(String emailId, String subject, String body) throws IOException {
        Email toEmail = new Email(emailId);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint(EMAIL_ENDPOINT);
        request.setBody(mail.build());

        sendGrid.api(request);
    }
}
