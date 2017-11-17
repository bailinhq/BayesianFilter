import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Ds {
    private String fileName;
    private String filePath;

    public void Save (HashMap spam, HashMap notSpam)
    {

        Set<String> keys = spam.keySet();
        for (String key: keys){
            String palabra = spam.get(key).toString();
            String saludo;
            saludo = (String) palabra;
            try {
                File archivo = new File("text.txt");
                FileWriter escribir = new FileWriter(archivo, true);
                escribir.write(saludo+"\n");
                escribir.close();
            } catch (Exception e) {
                System.out.println("Error al escribir");
            }
        }
    }
    public void load ()
    {
        String texto="";
        try
        {
            FileReader lector=new FileReader("texto.txt");
            BufferedReader contenido=new BufferedReader(lector);
            while((texto=contenido.readLine())!=null)
            {
                System.out.println(texto);
            }
        }
        catch(Exception e)
        {
            System.out.println("Error al leer");
        }
    }
    public void delete ()
    {

    }
}
