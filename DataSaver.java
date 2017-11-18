import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class DataSaver {

    public FileWriter spam;
    public FileWriter notSpam;
    public PrintWriter spamWriter;
    public PrintWriter writer;

    public DataSaver(){
        try {
            File file = new File(
                    System.getProperty("user.home")+ "\\spam.txt");
            spam = new FileWriter(file);
            notSpam = new FileWriter("notSpam.txt");
            spamWriter = new PrintWriter(spam);
            writer = new PrintWriter(notSpam);
        } catch (IOException e){

        }
    }

    public void saveSpam(HashMap<String, WordValue> spam){
        Set<String> keys = spam.keySet();
        for (String key : keys){
            spamWriter.println(spam.get(key).toString());
        }
        spamWriter.close();
    }

    public void saveNotSpam(HashMap<String, WordValue> notSpam){
        Set<String> keys = notSpam.keySet();
        for (String key : keys){
            writer.println(notSpam.get(key).toString());
        }
        writer.close();
    }

    public void clearSpam(){
        File data = new File(System.getProperty("user.home") + "\\spam.txt");
        data.delete();
    }

    public void clearNotSpam(){
        File data = new File(System.getProperty("user.home") + "\\notSpam.txt");
        data.delete();
    }
}
