import com.google.api.client.auth.oauth2.Credential;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class Controller {
    private Menu menu;
    private GmailConnector connector;
    private BayesianFilter filter;
    private Scanner sc = new Scanner(System.in);

    public Controller ()
    {
        menu = new Menu(this);
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
            connector.getNewMail();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void train()
    {
        try {
            int cantidad= filter.getTrainingSize();
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
            GmailConnector.authorize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void exit()
    {
System.exit(1);
    }
}
