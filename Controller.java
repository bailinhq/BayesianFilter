import com.google.api.client.auth.oauth2.Credential;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Controller {
    private Menu menu;
    private GmailConnector connector;
    private BayesianFilter filter;
    private Scanner sc = new Scanner(System.in);

    public Controller ()
    {
        try {
            menu = new Menu(this);
            connector = new GmailConnector();
            filter = new BayesianFilter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void configure()
    {
        System.out.println("Ingrese la probabilidad del SpamThreshold: ");
        double d = sc.nextDouble();
        filter.setSpamThresold(d);
        System.out.println("Ingrese la probabilidad del Spam: ");
        d = sc.nextDouble();
        filter.setSpamProb(d);
        System.out.println("Ingrese el tamaño de entrenamiento ");
        int i = sc.nextInt();
        filter.setTrainingSize(i);
    }
    public void showData()
    {
        System.out.print("Spam\n\n");
        Set<String> keys = filter.getListaSpam().keySet();
        for (String key : keys) {
            System.out.print(key);
            System.out.print("    Frecuencia:    ");
            System.out.print(filter.getListaSpam().get(key).frequency);
            System.out.print("    Probabilidad:   ");
            System.out.print(filter.getListaSpam().get(key).probabiliy);
        }

        System.out.print("Not Spam\n\n");
        Set<String> keys2 = filter.getListaNotSpam().keySet();
        for (String key2 : keys2) {
            System.out.print(key2);
            System.out.print("    Frecuencia:    ");
            System.out.print(filter.getListaNotSpam().get(key2).frequency);
            System.out.print("    Probabilidad:   ");
            System.out.print(filter.getListaNotSpam().get(key2).probabiliy);

        }
    }
    public void getNewMail()
    {
        try {
            List<Email> newEmails = connector.getNewMail();
            filter.isSpam(newEmails);
            for (Email email : newEmails){
                if(email.isSpam){
                    System.out.println("IS SPAM");
                } else{
                    System.out.println("NOT SPAM");
                }
                System.out.println(email.getBody());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void train()
    {
        try {
            int cantidad = filter.getTrainingSize();
            filter.training(connector.getSpam(cantidad),connector.getNotSpam(cantidad));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logout()
    {
        try {
            GmailConnector.deleteCredentials();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void login()
    {
        try {
            connector.logIn();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void exit()
    {
System.exit(1);
    }
}
