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
    
    private WordFrequency(String input)
    {
        inputString = input;
        words = new HashMap<String, Integer>();
    }
    
    public static WordFrequency makeInstance(String filename){
        String line = null;
        String input = null;
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
        return new WordFrequency(input);
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
        Integer testing = 0;
        words.put(test, 0);
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
