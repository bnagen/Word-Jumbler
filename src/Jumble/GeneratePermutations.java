package Jumble;
import java.util.ArrayList;

/**
 * File: GeneratePermutations.java
 * Description: This class will generate the permutations for the word 
 * scrambler via a recursive method and return the final result permutation.
 * Date: 5/2/2016
 * Platform: Windows 8, jdk 1.8.0_66, NetBeans 8.1
 * @see java.util.ArrayList
 * @author Branavan Nagendiram and Jason Bowen
 */
public class GeneratePermutations 
{  
    /**
     * Description: Default constructor- assigns a randomly generated word
     * to the string word.
     * @param word - scrambled word
     */
    public GeneratePermutations(String word)
    {
        this.word = word;
    }
    /**
     * Description: This method uses an String ArrayList to recursively call
     * itself to generate a permutations from the scrambled word.
     * @return results- all possible permutations for the scrambled word
     */
    public ArrayList<String> Permutations()
    {
        ArrayList<String> results = new ArrayList();
        if(word.length() == 0)
        {
            results.add(word);
            return results;
        }
        for(int i = 0; i < word.length(); i++)
        {
            String shortWord = word.substring(0, i) + word.substring(i + 1);
            
            GeneratePermutations getPermutations = 
                    new GeneratePermutations(shortWord);
            ArrayList<String> wordPermutations = 
                    getPermutations.Permutations();
            for(String s : wordPermutations )
            {
                results.add(word.charAt(i) + s);
            }
        }
        return results;
    }
    
    String word = "";
    
}