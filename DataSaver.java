import java.io.*;
import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class DataSaver {

    public FileWriter spam;
    public FileWriter notSpam;
    public PrintWriter spamWriter;
    public PrintWriter writer;

    /**
     *
     */
    public DataSaver() {
        try {
            File file = new File(
                    System.getProperty("user.home") + "\\spam.txt");
            spam = new FileWriter(file);
            File file2 = new File(
                    System.getProperty("user.home") + "\\notSpam.txt");
            notSpam = new FileWriter(file2);
            spamWriter = new PrintWriter(spam);
            writer = new PrintWriter(notSpam);
        } catch (IOException e) {

        }
    }

    /**
     * Saves the WordValues of spam words into a txt file.
     *
     * @param spam
     */
    public void saveSpam(HashMap<String, WordValue> spam) {
        Set<String> keys = spam.keySet();
        for (String key : keys) {
            spamWriter.println(spam.get(key).toString());
        }
        spamWriter.close();
    }

    /**
     * Saves the WordValues of not-spam words into a txt file.
     *
     * @param notSpam
     */
    public void saveNotSpam(HashMap<String, WordValue> notSpam) {
        Set<String> keys = notSpam.keySet();
        for (String key : keys) {
            writer.println(notSpam.get(key).toString());
        }
        writer.close();
    }

    /**
     * Loads a txt file with spam wordvalues.
     * @return HashMap of spam words with their respective WordValue
     */
    public HashMap<String, WordValue> loadSpam() {
        String spam = "";
        String nombre;
        double freq;
        double prob;
        HashMap<String, WordValue> mapa = new HashMap<>();
        try {
            FileReader lector = new FileReader("spam.txt");
            BufferedReader contenido = new BufferedReader(lector);
            while ((spam = contenido.readLine()) != null) {
                String parts[] = spam.split(" ");
                nombre = parts[0];
                //System.out.println(nombre);
                freq = Double.parseDouble(parts[1]);
                //System.out.println(freq);
                prob = Double.parseDouble(parts[2]);
                //System.out.println(prob);
                WordValue wordValue = new WordValue(nombre, freq, prob);
                mapa.put(nombre, wordValue);
            }
        } catch (Exception e) {
            System.out.println("Error al leer");
        }
        return mapa;
    }

    /**
     * Loads a txt file with not-spam wordvalues.
     * @return HashMap of not-spam words with their respective WordValue
     */
    public HashMap<String, WordValue> loadNotSpam() {
        String notSpam = "";
        String nombre;
        double freq;
        double prob;
        HashMap<String, WordValue> mapa = new HashMap<>();
        try {
            FileReader lector = new FileReader("notSpam.txt");
            BufferedReader contenido = new BufferedReader(lector);
            while ((notSpam = contenido.readLine()) != null) {
                String parts[] = notSpam.split(" ");
                nombre = parts[0];
                //System.out.println(nombre);
                freq = Double.parseDouble(parts[1]);
                //System.out.println(freq);
                prob = Double.parseDouble(parts[2]);
                //System.out.println(prob);
                WordValue wordValue = new WordValue(nombre, freq, prob);
                mapa.put(nombre, wordValue);
            }
        } catch (Exception e) {
            System.out.println("Error al leer");
        }
        return mapa;
    }

    /**
     * Deletes the txt file with the spam WordValues.
     */
    public void clearSpam() {
        File data = new File(System.getProperty("user.home") + "\\spam.txt");
        data.delete();
    }

    /**
     * Deletes the txt file with the not-spam WordValues.
     */
    public void clearNotSpam() {
        File data = new File(System.getProperty("user.home") + "\\notSpam.txt");
        data.delete();
    }
}
