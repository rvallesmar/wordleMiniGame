import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordBank {
  /*  This first method implementation is completed for you already. 
      Do not modify the method signature 
   */
  public static String getAnswerForPuzzleNumber(int puzzleNumber) {
    try {
      /* Create a file scanner to read answers.txt */
      Scanner scanner = new Scanner(new File("answers.txt"));

      /* Skip the first puzzleNumber number of words in the file */
      for (int i = 0; i < puzzleNumber-1; i++) {
        scanner.next();
      }

      /* Return the very next word */ 
      return scanner.next();

    } catch (Exception e) {
      /* Handle exception */
      System.out.println("Input/File not found!");
    }
    
    return null;
  }

  /* Do not modify the method signature. */
  public static boolean checkInDictionary(String userTry) {
    try {
      File dictionary = new File("dictionary.txt");
      Scanner fileReader = new Scanner(dictionary);

      //Compare the user word to each word in the dictionary, and return true if there is an exact match
      while(fileReader.hasNextLine()){
        String temp = fileReader.nextLine().trim();
        if(userTry.equals(temp)){
          fileReader.close();
          return true;
        }
      }

      fileReader.close();
      return false;

    } catch (Exception e) {
      System.out.println("File not found " + e);
      return false;
    }
  }

  //Method to return the total number of words in the dictionary
  public static int numWordsInDictionary(){
    try {
      File dictionary = new File("answers.txt");
      Scanner fileReader = new Scanner(dictionary);
      int total = 0;

      //Compare the user word to each word in the dictionary, and return true if there is an exact match
      while(fileReader.hasNextLine()){
        total++;
        fileReader.nextLine().trim();
      }

      fileReader.close();
      return total;

    } catch (Exception e) {
      System.out.println("File not found " + e);
      return -1;
    }
  }
}
