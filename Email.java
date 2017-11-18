import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DocumentType;

import javax.swing.text.Document;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Email {
    String body;
    String header;
    String id;
    String from;
    Boolean isSpam;
    public Email(Message mensaje, Boolean spam) throws IOException
    {
        //Pone el ID
        id = mensaje.getId();
        //Pone el Body
        String type = mensaje.getPayload().getMimeType();
        if(type.equals("multipart/alternative")) {
            byte[] bodyBytes = Base64.decodeBase64(mensaje.getPayload().getParts().get(0).getBody().getData().trim().toString()); // get body
            body = new String(bodyBytes, "UTF-8");
        } else if(type.equals("text/plain")){
            byte[] bodyBytes = Base64.decodeBase64(mensaje.getPayload().getBody().getData()); // get body
            body = new String(bodyBytes, "UTF-8");
        } else if(type.equals("text/html")){
            byte[] bodyBytes = Base64.decodeBase64(mensaje.getPayload().getBody().getData()); // get body
            body = new String(bodyBytes);
            org.jsoup.nodes.Document doc = Jsoup.parse(body);
            body = doc.body().text(); 
        }
        // Pone el Header
        header = mensaje.getPayload().getHeaders().toString();
        //Pregunta si es spam
        isSpam = spam;
    }

    public String getBody() {
        return body;
    }

    public String getHeader() {
        return header;
    }

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public Boolean getSpam() {
        return isSpam;
    }
}
