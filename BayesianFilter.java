import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class BayesianFilter {
    private double spamThresold = 0.9;
    private double spamProb = 0.3;
    private int trainingSize = 50;
    private HashMap<String, WordValue> listaNotSpam;
    private HashMap<String, WordValue> listaSpam;

    /**
     *
     */
    public BayesianFilter() {
        listaNotSpam = new HashMap<String, WordValue>();
        listaSpam = new HashMap<String, WordValue>();
    }

    /**
     * Trains the BayesianFilter by finding the probability of each sordl
     * @param spam
     * @param notSpam
     */
    public void training(List<Email> spam, List<Email> notSpam) {
        setFrequency(spam, notSpam);
    }

    /**
     * Assigns boolean spam to each email in the list.
     * @param newMessages
     */
    public void isSpam(List<Email> newMessages) {
        for (int i = 0; i < newMessages.size(); i++){
            double probability = this.calculateBayProb(newMessages.get(i));
            if(probability > spamThresold){
                newMessages.get(i).isSpam = true;
                System.out.println("es spam");
            } else{
                System.out.println("not spam");
                System.out.println(newMessages.get(i).getBody());
            }
        }
    }

    /**
     * Calculates the Spam Bayesian Probability of an Email.
     * @param email
     * @return probability
     */
    private double calculateBayProb(Email email) {
        Pattern pattern = Pattern.compile("(?<!\\S)[a-z]+(?!\\S)");
        Matcher matcher = pattern.matcher(email.getBody());
        HashMap<String, String> words = new HashMap<String, String>();
        while (matcher.find()) {
            String word = email.getBody().substring(matcher.start(), matcher.end());
            if (!words.containsKey(word)) {
                words.put(word, word);
            }
        }
        Set<String> keys = words.keySet();
        double numerator = 1.0;
        double denominator1 = 1.0;
        for (String key : keys) {
            if (listaSpam.containsKey(key)) {
                numerator = numerator * listaSpam.get(key).getProbabiliy();
            }
            if (listaNotSpam.containsKey(key)) {
                denominator1 = denominator1 * listaNotSpam.get(key).getProbabiliy();
            }
        }
        numerator = numerator * spamProb;
        denominator1 = denominator1 * (1.0 - spamProb);
        denominator1 += numerator;
        double probability = numerator / denominator1;
        return probability;
    }

    /**
     * Loads the HashMap with WordValue objects.
     * @param spam
     * @param notSpam
     */
    private void setFrequency(List<Email> spam, List<Email> notSpam) {
        double word_counter = 0.0;
        for (int i = 0; i < spam.size(); i++) {
            String body = spam.get(i).body;
            Pattern pattern = Pattern.compile("(?<!\\S)[a-z]+(?!\\S)");
            Matcher matcher = pattern.matcher(body);
            while (matcher.find()) {
                String word = body.substring(matcher.start(), matcher.end());
                word_counter++;
                if (!listaSpam.containsKey(word)) {
                    listaSpam.put(word, new WordValue(word, 0, 0));
                    listaSpam.get(word).increaseCount();
                } else {
                    listaSpam.get(word).increaseCount();
                }
            }
        }
        Set<String> keys = listaSpam.keySet();
        for (String key : keys) {
            int count = listaSpam.get(key).count;
            double f = count * 1.0 / spam.size() * 1.0;
            listaSpam.get(key).setFrequency(f);
            double p = count * 1.0 / word_counter;
            listaSpam.get(key).setProbabiliy(p);
        }
        word_counter = 0;
        for (int i = 0; i < notSpam.size(); i++) {
            String body = notSpam.get(i).body;
            Pattern pattern = Pattern.compile("(?<!\\S)[a-z]+(?!\\S)");
            Matcher matcher = pattern.matcher(body);
                while (matcher.find()) {
                    String word = body.substring(matcher.start(), matcher.end());
                    word_counter++;
                    if (!listaNotSpam.containsKey(word)) {
                        listaNotSpam.put(word, new WordValue(word, 0, 0));
                        listaNotSpam.get(word).increaseCount();
                    } else {
                        listaNotSpam.get(word).increaseCount();
                    }
            }
        }
        keys = listaNotSpam.keySet();
        for (String key : keys) {
            int count = listaNotSpam.get(key).count;
            double f = count * 1.0 / spam.size() * 1.0;
            listaNotSpam.get(key).setFrequency(f);
            double p = count * 1.0 / word_counter;
            listaNotSpam.get(key).setProbabiliy(p);
        }
    }

    /**
     * Returns the spam threshold.
     * @return spamThreshold
     */
    public double getSpamThresold() {
        return spamThresold;
    }

    /**
     * Returns the probability of spam.
     * @return spamProb
     */
    public double getSpamProb() {
        return spamProb;
    }

    /**
     * Returns the training size.
     * @return trainingSize
     */
    public int getTrainingSize() {
        return trainingSize;
    }

    /**
     * Returns a HashMap with WordValues from not-spam words.
     * @return listaNotSpam
     */
    public HashMap<String, WordValue> getListaNotSpam() {
        return listaNotSpam;
    }

    /**
     * Returns a HashMap with WordValues from nspam words.
     * @return listaSpam
     */
    public HashMap<String, WordValue> getListaSpam() {
        return listaSpam;
    }

    /**
     * Sets the value for spam threshold.
     * @param spamThresold
     */
    public void setSpamThresold(double spamThresold) {
        this.spamThresold = spamThresold;
    }

    /**
     * Sets the value for spam probability.
     * @param spamProb
     */
    public void setSpamProb(double spamProb) {
        this.spamProb = spamProb;
    }

    /**
     * Sets the value for training size.
     * @param trainingSize
     */
    public void setTrainingSize(int trainingSize) {
        this.trainingSize = trainingSize;
    }
}
