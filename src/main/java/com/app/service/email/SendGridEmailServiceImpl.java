package com.app.service.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

// https://cloud.google.com/compute/docs/tutorials/sending-mail/using-sendgrid
// free trial with 12,000 transactional emails free each month

@Service("SendGridEmailServiceImpl")
public class SendGridEmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendGridEmailServiceImpl.class);

    private static final String API_KEY = "APIKEY";
    private static final String EMAIL_FROM = "admin@vaniascamping.com";

    @Override
    public boolean sendEmail(List<String> to, String subject, String text) {
        try {
            Mail mail = createMail(to, subject, text);
            SendGrid sg = new SendGrid(API_KEY);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return true;
        } catch (Exception e) {
            LOGGER.error("Exception while sending email to "  + to + ", subject: " + subject + ", text: " + text, e);
            return false;
        }
    }

    private Mail createMail(List<String> to, String subject, String text) {
        Mail mail = new Mail();
        mail.setFrom(new Email(EMAIL_FROM));
        mail.setSubject(subject);
        mail.addContent(new Content("text/plain", text));
        Personalization personalization = new Personalization();
        for (String toEmail : to) {
            personalization.addTo(new Email(toEmail));
        }
        mail.addPersonalization(personalization);
        return mail;
    }
}
