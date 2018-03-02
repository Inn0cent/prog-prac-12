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
    private HashMap<String, Integer> words;
    private String documentType;
    private static HashMap<String, WordFrequency> typeFrequency;
    private int combines = 0;
    
    public WordFrequency(String input, String docType)
    {
        inputString = input;
        words = new HashMap<String, Integer>();
        typeFrequency = new HashMap<String, WordFrequency>();
    }
    
    public static WordFrequency makeInstance(String filename, String docType){
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
        return new WordFrequency(input, docType);
    }
    
    public static void classifyAs(String docType, String text){
        //combines += 1;
        typeFrequency.get(docType).addMoreText(text);
        //for(String incl : 
    }
    
    public Set wordsIncluded(){
        String[] included = inputString.split("\\s");
        for(String incl : included){
            words.put(incl, 0);
        }
        return words.keySet();
    }
    
    public void addMoreText(String text){
        inputString += text;
    }
   
    public int wordFrequency(String test){
        String[] included = inputString.split("\\s");
        if(!words.containsKey(test)){
            words.put(test, 0);
        }
        for(String word : included){
           if(word.equals(test)){
               words.put(test, words.get(test) + 1);
            }
        }
        return words.get(test);
    }
    
    public double relativeFrequency(String test){
        return wordFrequency(test)*1.0/inputString.split("\\s").length;
    }
}
