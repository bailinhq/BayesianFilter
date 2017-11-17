public class WordValue {
    public String word;
    public int frequency;
    public double probabiliy;

    public WordValue(String w, int f, double p){
        word = w;
        frequency = f;
        probabiliy = p;

    }

    public String getWord() {
        return word;
    }

    public int getFrequency() {
        return frequency;
    }

    public double getProbabiliy() {
        return probabiliy;
    }

}
