import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private void setFrequency(List<Email> spam, List<Email> notSpam) {
        for(int i = 0; i < spam.size(); i++){
            String body = spam.get(i).body;
            Pattern pattern = Pattern.compile("[\\w']+");
            Matcher matcher = pattern.matcher(body);
            while (matcher.find()){
                System.out.println(body.substring(matcher.start(), matcher.end()));
            }
        }
    }

    public double getSpamThresold() {
        return spamThresold;
    }

    public double getSpamProb() {
        return spamProb;
    }

    public int getTrainingSize() {
        return trainingSize;
    }

    public Hashtable<String, WordValue> getListaNotSpam() {
        return listaNotSpam;
    }

    public Hashtable<String, WordValue> getListaSpam() {
        return listaSpam;
    }

    public void setSpamThresold(double spamThresold) {
        this.spamThresold = spamThresold;
    }

    public void setSpamProb(double spamProb) {
        this.spamProb = spamProb;
    }

    public void setTrainingSize(int trainingSize) {
        this.trainingSize = trainingSize;
    }
}
