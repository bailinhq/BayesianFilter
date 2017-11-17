public class WordValue {
    public String word;
    public double frequency;
    public double probabiliy;
    public int count = 0;

    public WordValue(String w, double f, double p){
        word = w;
        frequency = f;
        probabiliy = p;

    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public void setProbabiliy(double probabiliy) {
        this.probabiliy = probabiliy;
    }

    public String getWord() {
        return word;
    }

    public double getFrequency() {
        return frequency;
    }

    public double getProbabiliy() {
        return probabiliy;
    }

    public void increaseCount(){
        count++;
    }

    public String toString(){
        String string = "" + word + " " + frequency + " " + probabiliy; 
        return string;
    }
}
