package com.app.service.email;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MailJetEmailServiceImpl")
public class MailJetEmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailJetEmailServiceImpl.class);

    private static final String API_KEY = "98d89634af88a325ffcca93604084372";
    private static final String API_SECRET = "4dd3d94862c4bab0e14ba50dc3d8de47";
    private static final String MAILJET_EMAIL_ADDRESS = "jbytttzvrq@effobe.com";

    @Override
    public boolean sendEmail(List<String> to, String subject, String text) {
        try {
            MailjetClient client;
            MailjetRequest request;
            MailjetResponse response;
            client = new MailjetClient(API_KEY, API_SECRET, new ClientOptions("v3.1"));
            request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", MAILJET_EMAIL_ADDRESS)
                                            .put("Name", "no-reply"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", to)))
                                    .put(Emailv31.Message.SUBJECT, subject)
                                    .put(Emailv31.Message.TEXTPART, text)));
//                                    .put(Emailv31.Message.HTMLPART, "<h3>Dear passenger 1, welcome to <a href='https://www.mailjet.com/'>Mailjet</a>!</h3><br />May the delivery force be with you!");
//                                    .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")));
            response = client.post(request);
            if (response.getStatus() != 200) {
                LOGGER.error("Error sending email to " + to + ", subject: " + subject + ", text: " + text + "\n" + response.getStatus() + "\n" + response.getData());
                return false;
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("Exception while sending email to "  + to + ", subject: " + subject + ", text: " + text, e);
            return false;
        }
    }
}
