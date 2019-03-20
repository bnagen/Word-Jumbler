package Jumble;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * File Name: Dictionary.java
 * Description: This class will read the dictionary file and put it into
 * an ArrayList which will be inputted into an array there will be a swap and
 * scramble method which will swap/scramble the jumbled word.
 * @author Branavan Nagendiram
 */
public class Dictionary 
{
    
    public static final int DECREMENT = 1;
    public static final int MAX_SCRAMBLE = 15;
    public static final int MIN_SCRAMBLE = 5;
    public static final int DEFAULT_ARRAY_SIZE = 1;
    public static final int NOT_THERE = -1;
    public static final int FIRST_INDEX_ARRAY = 0;
    public Dictionary()
    {
        myRand = new Random();
        
    }
    /**
     * Description: Default Constructor - assigns variable file to the
     * dictionary file.
     * @param file - dictionary file as a file
     */
    public Dictionary(File file)
    {
        this();
        getFile(file);
    }
    /**
     * Description: this methods assigns the file variable to file.
     * @param file - dictionary file as a file
     */
    public void getFile(File file)
    {
        this.file = file;
    }
    /**
     * Description: This method readFile will read all the words from the file
     * and assign it to an array.
     */
    public void readFile() 
    {
        wordsArrayList = new ArrayList();
        arrayOfWords = new String[DEFAULT_ARRAY_SIZE];
        if(!file.exists() || !file.canRead())
        {
            return;
        }
        try
        {
        inputFile = new Scanner(file);
        while(inputFile.hasNextLine())
        {
           line = inputFile.nextLine();
           wordsArrayList.add(line);
        }
        inputFile.close();
        wordSize = wordsArrayList.size();
        arrayOfWords = wordsArrayList.toArray(arrayOfWords);
        }
        catch(FileNotFoundException exp)
        {
            JOptionPane.showMessageDialog(GUI, exp, "File Not Found", 
                    JOptionPane.ERROR_MESSAGE);
        }
        
    }
    /**
     * Description: This method will create the original word that will be
     * swapped and scrambled.
     * @return word that will be scrambled
     */
    public String createJumble()
    {
        scrambledWord = arrayOfWords[myRand.nextInt(wordSize)];
        int amount = myRand.nextInt(MAX_SCRAMBLE) + MIN_SCRAMBLE;
        scrambledWord = doScramble(scrambledWord, amount);
        return scrambledWord;
    }
    /**
     * Description: This method scrambles the word by swapping the letters
     * recursively x amounts where x is a random number between 5 - 15 times.
     * @param word - String of the original word
     * @param amount - amount of times to swap/scramble.
     * @return doScramble - calls itself to scramble until amount is less
     * than 1
     */
    public String doScramble(String word, int amount)
    {
        word = doSwap(word, myRand.nextInt(word.length())
                , myRand.nextInt(word.length()));
        if(amount < DECREMENT)
        {
            return word;
        }
        return doScramble(word, amount - DECREMENT);
    }
    /**
     * Description: This method will swap the specific letters from the word.
     * @param word - String of the original word
     * @param low - low value index of the array
     * @param high - high value index of the array
     * @return word - which is the swapped word
     */
    public String doSwap(String word, int low, int high)
    {
        if(low == high)
        {
            return word;
        }
        char[] letterArray = word.toCharArray();
        char temp = letterArray[low];
        letterArray[low] = letterArray[high];
        letterArray[high] = temp;
        word = new String(letterArray);
        return word;
        
    }
    /**
     * Description: This method uses a String array list to add in the
     * permutations of a scrambled word and by using a binary search it
     * searches through the dictionary file to match the permutations
     * with the dictionary word and returns the words from the file.
     * @return scrambledWordAnswer - an array list with the solutions
     * of the jumbled word.
     */
    public ArrayList<String> jumbleAnswer()
    {
        
        allPermutations = new ArrayList();
        scrambledWordAnswer = new ArrayList();
        GeneratePermutations permutations = 
                new GeneratePermutations(scrambledWord);
        allPermutations = permutations.Permutations();
        for(String answer : allPermutations)
        {
            position = BinarySearch.BinarySearch(getArrayOfWords()
                , answer, FIRST_INDEX_ARRAY, 
                getArrayOfWords().length);
            if(position != -1 & !scrambledWordAnswer.contains(answer))
            {
                scrambledWordAnswer.add(answer);
            }
        }
        
       return scrambledWordAnswer;
    }
    /**
     * Description: This method returns an integer with the amount of 
     * answers of the scrambled word.
     * @return scrambledWordAnswer - total amount of solutions to the word
     */
    public int getAmountOfAnswers()
    {
        return scrambledWordAnswer.size();
    }
    /**
     * Description: This will return the arrayOfWords array
     * @return array - with the scrambled word
     */
    public String[] getArrayOfWords()
    {
        return arrayOfWords;
    }
    private File file;
    private Random myRand;
    private ArrayList<String> wordsArrayList;
    String line = "";
    private Scanner inputFile;
    private String[] arrayOfWords;
    JumbleGUI GUI;
    public int wordSize = 0;
    int position = 1;
    String originalWord = "";
    String scrambledWord = "";
    ArrayList<String> allPermutations;
    ArrayList<String> scrambledWordAnswer;
   
}
