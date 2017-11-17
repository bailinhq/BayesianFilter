import java.util.Hashtable;
import java.util.List;

public class BayesianFilter {
    private double spamThresold = 0.9;
    private double spamProb = 0.3;
    private int trainingSize;
    private Hashtable<String, WordValue> listaNotSpam;
    private Hashtable<String, WordValue> listaSpam;
    
    public BayesianFilter(){
        listaNotSpam = new Hashtable<String, WordValue>();
        listaSpam = new Hashtable<String, WordValue>();
    }
    
    public void training(List<Email> spam, List<Email> notSpam) {

    }

    public void isSpam(List<Email> newMessage) {

    }

    private double calculateBayProb() {

        return 0.0;
    }

    private double calculateProb(double prob, double thresold) {

        return 0.0;
    }

    private void getFrequency(List<Email> spam, List<Email> notSpam) {

    }

}
