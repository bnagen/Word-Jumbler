package Jumble;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;


/**
 * File Name: JumbleGUI.java
 * Description: The program will display a scrambled word from dictionary 
 * text file and allow the user to guess what the unscrambled word is,
 * it will display the letters in a JComboBox 
 * and it will notify the user if they  are correct, 
 * it will also allow the user to see the answer and pick 
 * another scrambled word.
 * @see    java.awt.BorderLayout
 * @see    java.awt.Color
 * @see    java.awt.Font
 * @see    java.awt.event.ActionEvent
 * @see    java.awt.event.ActionListener
 * @see    java.io.File
 * @see    javax.swing.JButton
 * @see    javax.swing.JComboBox
 * @see    javax.swing.JFileChooser
 * @see    javax.swing.JFrame
 * @see    javax.swing.JLabel
 * @see    javax.swing.JMenu
 * @see    javax.swing.JMenuBar
 * @see    javax.swing.JMenuItem
 * @see    javax.swing.filechooser.FileFilter
 * @see    javax.swing.JPanel
 * 
 * @author Branavan Nagendiram
 */
public class JumbleGUI extends JFrame 
{
    public static final int MAX_LETTERS = 7;
    public static final int FONT_SIZE = 20;
    public static final String FILE_NAME = "Jumble-en-US.dic";
    public static final Color BLUE = new Color(0,200,240);
    public static final int NOT_THERE = -1;
    
    /**
     * Description: Main constructor - sets frame to visible,
     * centers form, makes default close button exit the program.
     * @param args - takes in string arguments
     */
    public static void main(String[] args) 
    {
        JFrame frame = new JumbleGUI();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(3);
        
    }
    public JumbleGUI()
    {
        setSize(600,300);
        setTitle("Jumble Puzzle");
        setLayout(new BorderLayout());
        loadDictionary();
        setNorthPanel();
        setCenterPanel();
        setSouthPanel();
        setFileMenu();
        jumbleButton.addActionListener(listener);
        seeAnswerButton.addActionListener(listener);
        testButton.addActionListener(listener);
        jumbleAnswer = new ArrayList();
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }
    /**
     * Description: This method will set the northern part of the GUI
     * and display the dictionary name + the number of solution for the
     * scrambled word.
     */
    public void setNorthPanel()
    {
        northPanel = new JPanel();
        dictLabel = new JLabel("Dictionary Name: " + dictionaryFileName);
        numOfSolutions = new JLabel("Number Of Solutions: ");
        northPanel.add(dictLabel);
        northPanel.add(numOfSolutions);
    }
    /**
     * Description: This method will set the center part of the GUI
     * a label for the scrambled word, the combo-boxes with each letter
     * from the word, and the solution for the scrambled word.
     */
    public void setCenterPanel()
    {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(BLUE);
        letterComboBox = new JComboBox[MAX_LETTERS];
        letterCombo = new JPanel();
        scrambledWordText = new JLabel();
        scrambleSolution = new JLabel("");
        
        scrambledWordText.setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
        scrambledWordText.setForeground(Color.magenta);
        for(int i = 0; i < MAX_LETTERS; i++)
        {
            letterComboBox[i] = new JComboBox();
            letterCombo.add(letterComboBox[i]);
            letterComboBox[i].setVisible(false);
        }
        centerPanel.add(scrambledWordText, BorderLayout.NORTH);
        centerPanel.add(letterCombo , BorderLayout.CENTER);
        centerPanel.add(scrambleSolution , BorderLayout.SOUTH);
    }
    /**
     * Description: This method will set the southern part of the GUI
     * and display 3 buttons, the getJumble button, testAnswer button, and the
     * see answer button, which will respectively get the jumble, check to see
     * if the answer is right and display the correct answer if the player is
     * stuck.
     */
    public void setSouthPanel()
    {
        southPanel = new JPanel();
        southPanel.setBackground(Color.gray);
        jumbleButton = new JButton("get JUMBLE");
        testButton  = new JButton("Test Answer");
        seeAnswerButton = new JButton("See Answer");
        southPanel.add(jumbleButton);
        southPanel.add(testButton);
        southPanel.add(seeAnswerButton);
        if(!dictionaryFile.canRead())
        {
            dictionaryFileName = "Cannot be found/read";
            jumbleButton.setEnabled(false);
            testButton.setEnabled(false);
            seeAnswerButton.setEnabled(false);
        }
    }
    /**
     * Description: This method will create the File Menu for the user
     * to select the dictionary file if it is not already loaded.
     */
    public void setFileMenu()
    {
        menuSelector = new JMenuBar();
        selectDictionary = new JMenuItem("Load Dictionary");
        fileMenu = new JMenu("File");
        fileMenu.add(selectDictionary);
        menuSelector.add(fileMenu);
        selectDictionary.addActionListener(listener);
        setJMenuBar(menuSelector);
    }
    /**
     * Description: This method loadDictionary will check to see if the 
     * dictionary file is in the same folder as the program, if so it will
     * automatically load the file into the program.
     */
    public void loadDictionary()
    {
        chooseFile = new JFileChooser();
        dictionaryFileName = FILE_NAME;
        dictionaryFile = new File(dictionaryFileName);
        chooseFile.setCurrentDirectory
            (dictionaryFile.getAbsoluteFile().getParentFile());
        FileFilter filter;
        filter = new ExtensionFileFilter(".dic", new String[] {"dic"});
        chooseFile.setFileFilter(filter);
        if(dictionaryFile.canRead() & dictionaryFile.exists())
        {
            dictionaryFileName = "Jumble-en-US.dic";
            dictionary = new Dictionary(dictionaryFile);
           
        }
        else
        {
            dictionaryFileName = "Cannot be found/read";
            
        }
    }
    ActionListener listener = new ActionListener() 
    {
    /**
     * Description: Overrides an actionPerformed method in order to create
     * an event for when the selectDictionary file item is selected it will
     * open a JFileChooser for the user to select the dictionary file.
     * @param event - check to see if action occurred
     * 
     */
    @Override
    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource() == selectDictionary)
        {
            int result = chooseFile.showOpenDialog(selectDictionary);
            if(result == JFileChooser.APPROVE_OPTION)
            {
            dictionaryFile = chooseFile.getSelectedFile();
                if(dictionaryFile.canRead() & dictionaryFile.exists())
                {
                dictionaryFileName = dictionaryFile.getName();
                dictLabel.setText("Dictionary Name: " + dictionaryFileName);
                dictionary = new Dictionary(dictionaryFile);
                jumbleButton.setEnabled(true);
                testButton.setEnabled(true);
                seeAnswerButton.setEnabled(true);
                }
            }
        }
        if(event.getSource() == jumbleButton)
        {
           
           dictionary.readFile();
           scrambledWord = dictionary.createJumble();
           int sizeOfWord = scrambledWord.length();
           lettersFromScrambledWord = new String[sizeOfWord];
           for(int i = 0; i < sizeOfWord; i++)
           {
               lettersFromScrambledWord[i] = 
                       scrambledWord.substring(i, i + 1);
           }
           scrambledWordText.setText("       " + scrambledWord);
           jumbleAnswer = dictionary.jumbleAnswer();
           numOfSolutions.setText("Number Of Solutions: " +
                    Integer.toString(dictionary.getAmountOfAnswers()));
           for(int i = 0; i < MAX_LETTERS; i++ )
           {
               letterComboBox[i].removeAllItems();
               letterComboBox[i].setVisible(false);
           }
           for(int i = 0; i < sizeOfWord; i++)
           {
               letterComboBox[i].setVisible(true);
               letterComboBox[i].addItem("");
               for(int j = 0; j < sizeOfWord; j++)
               {
                   letterComboBox[i].addItem(lettersFromScrambledWord[j]);
               }
           }
          
           scrambleSolution.setText("");
           
        }
        else if(event.getSource() == seeAnswerButton)
        {
            scrambleSolution.setText(jumbleAnswer.toString());
        }
        if(event.getSource() == testButton)
        {
            String collectedLetters = "";
            
            
            for(JComboBox letters : letterComboBox )
            {
             
              if(letters.getSelectedIndex() != NOT_THERE)
              {
               collectedLetters = collectedLetters 
                       + letters.getSelectedItem();
               if(letters.getSelectedItem().equals(""))
               {
                  scrambleSolution.setText
                  ("Incomplete- Fill every Combobox!");
                  return;
               }
              }
             
            }
              
            for(String word: jumbleAnswer)
            {
                if(word.equals(collectedLetters))
                {
                    scrambleSolution.setText("CORRECT!");
                    break;
                }
                else
                {
                  scrambleSolution.setText("INCORRECT TRY AGAIN!");  
                }
                
            }
        }
        
    }
    };
    JPanel northPanel;
    JPanel centerPanel;
    JPanel southPanel;
    JLabel dictLabel; 
    JLabel numOfSolutions;
    JComboBox[] letterComboBox;
    JPanel letterCombo;
    JLabel scrambledWordText;
    JLabel scrambleSolution;
    JButton jumbleButton;
    JButton testButton;
    JButton seeAnswerButton;
    String dictionaryFileName;
    JFileChooser chooseFile;
    JMenuBar menuSelector;
    JMenu fileMenu;
    JMenuItem selectDictionary;
    Dictionary dictionary;
    File dictionaryFile;
    String scrambledWord = "";
    ArrayList<String> jumbleAnswer;
    String[] lettersFromScrambledWord;
    
}
