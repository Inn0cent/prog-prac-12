import java.util.HashMap;
import java.util.Set;
import java.io.*;
/**
 * Write a description of class WordFrequency here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WordFrequency
{

    private String inputString;
    private HashMap<String, Double> words;
    private String documentType;
    private static HashMap<String, WordFrequency> typeFrequency = new HashMap<String, WordFrequency>();
    private int combines;
    private static final String[] trainingSet = {"viva_la_vida.txt", "Coldplay", "paradise.txt", "Coldplay", "everglow.txt", "Coldplay", "heathens.txt", "Twenty One Pilots", "lane boy.txt", "Twenty One Pilots", "car radio.txt", "Twenty One Pilots"};

    public WordFrequency(String input)
    {
        inputString = input.toLowerCase().replaceAll("(?:--|[\\[\\]{}()+/\\\\]).,'!", "");
        words = new HashMap<String, Double>();
        combines = 0;
    }

    public static WordFrequency makeInstance(String filename){
        return new WordFrequency(getFromFile(filename));
    }

    public static void applyTrainingSet(){
        for(int i = 0; i < trainingSet.length-1; i += 2){
            train(trainingSet[i], trainingSet[i+1]);
        }
    }
    
    public static void train(String filename, String docType){
        classifyAs(docType, getFromFile(filename));
    }

    public static void classifyAs(String docType, String text){
        boolean newDocType = true;
        String[] included = text.toLowerCase().split(" ");
        for(String type : typeFrequency.keySet()){
            if(type.equals(docType)){
                newDocType = false;
            }
        }

        if(newDocType){
            WordFrequency newType = new WordFrequency(text);
            typeFrequency.put(docType, newType);
        } else {
            typeFrequency.get(docType).addMoreText(text);
        }
        typeFrequency.get(docType).allRelFrequencies();
    }

    public static String checkType(String filename){
        double minDistance = 100000000;
        String likelyType = "none";
        WordFrequency test = new WordFrequency(getFromFile(filename));
        for(String type : typeFrequency.keySet()){
            if(minDistance > (minDistance = typeFrequency.get(type).distanceBetween(test))){
                likelyType = type;
            }
        }
        return likelyType;
    }

    public static String getFromFile(String filename){
        String line = null;
        String input = "";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                input += line + " ";
            }   
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + filename + "' instansiating with curent string");                  
        }
        return input;
    }

    public double distanceBetween(WordFrequency compare){
        HashMap<String, Double> distances = new HashMap<String, Double>();
        double distance = 0;
        this.allRelFrequencies();
        compare.allRelFrequencies();
        HashMap<String, Double> wordsThis = new HashMap<String, Double>(this.returnWords());
        HashMap<String, Double> wordsThat = new HashMap<String, Double>(compare.returnWords());
        for(String wordThis : wordsThis.keySet()){
            distances.put(wordThis, 0.0);
        }
        for(String wordThat : wordsThat.keySet()){
            distances.put(wordThat, 0.0);
        }
        for(String word : distances.keySet()){
            if(wordsThis.get(word) == null){
                distance += wordsThat.get(word);
            } else if (wordsThat.get(word) == null){
                distance += wordsThis.get(word);
            } else {
                distance += Math.abs(wordsThis.get(word) - wordsThat.get(word));
            }
        }
        return distance;
    }

    public Set<String> wordsIncluded(){
        return words.keySet();
    }

    public HashMap<String, Double> returnWords(){
        return words;
    }

    public void addMoreText(String text){
        inputString += " " + text.toLowerCase().replaceAll("(?:--|[\\[\\]{}()+/\\\\]).,'!", "");
    }
    
    public void allRelFrequencies(){
        String[] included = inputString.split("\\s");
        for(String word : included){
            relativeFrequency(word);
        }
    }

    public double wordFrequency(String test){
        String[] included = inputString.split("\\s");
        test = test.toLowerCase();
        words.put(test, 0.0);
        for(String word : included){
            if(word.equals(test)){
                words.put(test, words.get(test) + 1);
            }
        }
        return words.get(test);
    }

    public double relativeFrequency(String test){
        words.put(test, wordFrequency(test.toLowerCase())*1.0/inputString.split("\\s").length);
        return words.get(test);
    }
}
