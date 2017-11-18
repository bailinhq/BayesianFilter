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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.nio.file.*;

public class GmailConnector {

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME =
            "Gmail API Java Quickstart";

    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/gmail-java-quickstart");

    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     * <p>
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(GmailScopes.GMAIL_READONLY);

    private Gmail service;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public GmailConnector() throws IOException {
        service = getGmailService();
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                Quickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Gmail client service.
     *
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

    }

    public List<Email> getSpam(int cantidad) throws IOException {
        String user = "me";
        String Query = "in:Spam";
        ListMessagesResponse response = service.users().messages().list(user).setQ(Query).execute();
        List<Email> emails = new ArrayList<Email>();
        List<Message> messages = new ArrayList<Message>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(user).setQ(Query)
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        int contador = 0;
        for (Message message : messages) {
            Message mensaje = service.users().messages().get(user, message.getId()).setFormat("full").execute();
            if (mensaje.getPayload().getMimeType().equals("multipart/alternative")) {
                emails.add(new Email(mensaje, true));
                contador++;
            }
            if (contador == cantidad) {
                break;
            }
        }
        return emails;
    }

    public List<Email> getNewMail() throws IOException {
        String user = "me";
        String Query = "in:UNREAD";
        ListMessagesResponse response = service.users().messages().list(user).setQ(Query).execute();
        List<Email> emails = new ArrayList<Email>();
        List<Message> messages = new ArrayList<Message>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(user).setQ(Query)
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        for (Message message : messages) {
            Message mensaje = service.users().messages().get(user, message.getId()).setFormat("full").execute();
            if (mensaje.getPayload().getMimeType().equals("multipart/alternative")) {
                emails.add(new Email(mensaje, true));
            }
        }
        return emails;
    }

    public List<Email> getNotSpam(int cantidad) throws IOException {
        String user = "me";
        String Query = "in:INBOX";
        ListMessagesResponse response = service.users().messages().list(user).setQ(Query).execute();
        List<Email> emails = new ArrayList<Email>();
        List<Message> messages = new ArrayList<Message>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(user).setQ(Query)
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        int contador = 0;
        for (Message message : messages) {
            Message mensaje = service.users().messages().get(user, message.getId()).setFormat("full").execute();
            String type = mensaje.getPayload().getMimeType();
            if(type == "text/plain" || type == "text/html" || type == "multipart/alternative") {
                if (contador < cantidad) {
                    emails.add(new Email(mensaje, true));
                    contador++;
                }
                if (contador == cantidad) {
                    break;
                }
            }
        }
        return emails;
    }

    public static void deleteCredentials() throws IOException{
        String path = System.getProperty("user.home") + "//.credentials/gmail-java-quickstart//StoredCredential";
        File data = new File(path);
        data.delete();
        //Files.deleteIfExists(DATA_STORE_DIR.toPath());
        //boolean delete = DATA_STORE_DIR.delete();
    }
    public static void main(String[] args) throws IOException {
        GmailConnector conector = new GmailConnector();
        BayesianFilter filter = new BayesianFilter();
        filter.training(conector.getSpam(20), conector.getNotSpam(20));
        filter.isSpam(conector.getSpam(10));
    }

}


