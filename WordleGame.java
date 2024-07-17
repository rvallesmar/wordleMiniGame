import java.io.FileNotFoundException;

public class WordleGame {
  /* allGuesses represents the wordle game */
  private WordleLetter[][] allGuesses = new WordleLetter[6][5];
  private String wordInPlay = "";

  /*------TODO - implement according to spec ------*/
  //Constructor for a Wordle Game
  public WordleGame(int puzzleNumber){
    wordInPlay = WordBank.getAnswerForPuzzleNumber(puzzleNumber);
  }

  //Getters
  public String getAnswer(){
    return this.wordInPlay;
  }

  //General
  public void guess(String guessWord){
    for(int i = 0; i<allGuesses.length; i++){
      if(allGuesses[i][0] == null){ //skip the spots that have already been filled with wordle letters
        for(int j=0; j<guessWord.length(); j++){
          allGuesses[i][j] = new WordleLetter(guessWord.charAt(j));

          if(checkInGuess(allGuesses[i][j])){
            if(answerLetterPosition(allGuesses[i][j]) == j){
              allGuesses[i][j].setColor("green");
            }else{
              allGuesses[i][j].setColor("yellow");
            }
          }else{
            allGuesses[i][j].setColor("red");
          }
        }

        //After all the letters have a color set, check for duplicates to handle those cases
        for(int j=0; j<allGuesses[i].length; j++){
          if((timesInAnswer(allGuesses[i][j]) > 1) && !allGuesses[i][j].isGreen()){
            WordleLetter temp = allGuesses[i][j];
            int indexOfGreenRepeated = -1;
            int indexOfYellowRepeated = -1;
            //Traverse the wordle letter array and get the possible position of a green repeated instance (in order to avoid it)
            for(int k=0; k<allGuesses[i].length; k++){
              if(allGuesses[i][k].isGreen() && (allGuesses[i][k].getLetter() == temp.getLetter()) && (k!=j)){
                indexOfGreenRepeated = k;
              }
            }

            //Traverse the wordle letter array and get the possible position of a yellow repeated instance (in order to avoid it)
            for(int n=0; n<allGuesses[i].length; n++){
              if(!allGuesses[i][n].isGreen() && (allGuesses[i][n].getLetter() == temp.getLetter()) && (n!=j)){
                indexOfYellowRepeated = n;
              }
            }

            for(int m=0;m<allGuesses[i].length;m++){
              if(indexOfGreenRepeated != -1){
                if((m!=indexOfGreenRepeated) && (m!=j)){
                  if(allGuesses[i][j].getLetter() == guessWord.charAt(m)){
                    if(answerLetterPosition(allGuesses[i][j]) == m){
                      allGuesses[i][j].setColor("green");
                    }else{
                      allGuesses[i][j].setColor("yellow");
                    }
                  }
                }
              }

              if(indexOfYellowRepeated != -1){
                if((m!=indexOfYellowRepeated) && (m!=j)){
                  if(allGuesses[i][j].getLetter() == guessWord.charAt(m)){
                    if(answerLetterPosition(allGuesses[i][j]) == m){
                      allGuesses[i][j].setColor("green");
                    }else{
                      allGuesses[i][j].setColor("yellow");
                    }
                  }
                }
              }
            }
          }
        }

        //Handle the case where there is only supposed to be 1 instance of a wordle letter, so as to not color a repeated letter in the guess
        //Traverse the array of the player's guess to change the color of the dupp
        for(int j=0; j<allGuesses[i].length; j++){
          WordleLetter tempEvaluation = allGuesses[i][j];
          if(timesInAnswer(tempEvaluation) == 1){
            if(tempEvaluation.isGreen()){ //traverse the array in question, and check if a letter is green already first
              for(int k=0;k<allGuesses[i].length;k++){
                //if it is green, traverse the array again but avoid itself, and check for other character matches in the rest of the array
                if((k!=j) && (allGuesses[i][k].getLetter() == tempEvaluation.getLetter())){
                  allGuesses[i][k].setColor("red");
                }
              }
            }else{
              //if the repeated word is instead found to be yellow, then we do the below
              if(tempEvaluation.isYellow()){
                for(int k=0; k<allGuesses[i].length;k++){
                  //traverse the array avoiding itself again, but if it founds that another is already green, then change itself to red
                  if((k!=j) && (allGuesses[i][k].isGreen()) && (allGuesses[i][k].getLetter() == allGuesses[i][j].getLetter())){
                    allGuesses[i][j].setColor("red");
                    //otherwise, if it founds another one that is not green, change that one to
                  } else if((k!=j) && !allGuesses[i][k].isGreen() && (allGuesses[i][k].getLetter() == allGuesses[i][j].getLetter())){
                    allGuesses[i][k].setColor("red");
                  }
                }
              }
            }
          }
        }

        break;
      }
    }
  }

  //Method to check the number of times a certain wordle letter is repeated in the wordInPlay
  public int timesInAnswer(WordleLetter x){
    int counter = 0;
    for(int i=0; i<wordInPlay.length();i++){
      if(x.getLetter() == wordInPlay.charAt(i)){
        counter++;
      }
    }

    return counter;
  }


  //Method to check the number of guesses so far
  public int getNumberGuessesSoFar(){
    int counter = 0;
    
    for(int i=0; i<allGuesses.length; i++){
      if(allGuesses[i][0] != null){
        counter++;
      }
    }

    return counter;
  }

  //Method that returns the array of Wordle Letters at a specific posistion
  public WordleLetter[] getGuess(int guessNumber){
    return allGuesses[guessNumber];
  }

  //Method to turn a Wordle Letter array into a string
  public static String wordleArrToString(WordleLetter[] arr){
    String output = "";

    for(int i=0; i<arr.length; i++){
      output += arr[i].getLetter();
    }

    return output;
  }



  //Method to check if a corresponding wordle letter object is in the String being guessed
  public boolean checkInGuess(WordleLetter guessLetter){
    for(int i=0; i<wordInPlay.length(); i++){
      if(guessLetter.getLetter() == wordInPlay.charAt(i)){
        return true;
      }
    }

    return false;
  }

  //Method to return the index position of a specific character in a string if it exists
  public int answerLetterPosition(WordleLetter x){
    for(int i=0; i<wordInPlay.length(); i++){
      if(x.getLetter() == wordInPlay.charAt(i)){
        return i;
      }
    }

    return -1;
  }

  //Method to check if the game is over or not
  public boolean isGameOver(){
    if(getNumberGuessesSoFar() == 0){
      return false;
    }else{
      if(isGameWin()){
        return true;
      } 
      
      if(getNumberGuessesSoFar() >= allGuesses.length){
        return true;
      }else{
        return false;
      }
    }

  }
  
  //Method to determine if the player won the game
  public boolean isGameWin(){
    if(getNumberGuessesSoFar() == 0){
      return false;
    }else{
      //get the guess of the latest one made (has to be -1 because the counter of numOfguesses would have already moved)
      if(wordleArrToString(getGuess(getNumberGuessesSoFar()-1)).equals(wordInPlay)){ 
        return true;
      }else{
        return false;
      }
    }
  }

  /*------- TODO - include the remainder of the below code back in once rest of class is implemented.
            Do not modify this method implementation ------*/

  public String toString() {
    /* result will be used to build the full answer String */ 
    String result = ""; 
       /* for each word guessed so far */ 
    for (int i = 0; i < getNumberGuessesSoFar(); i++) {
         /* get each letter of each word */
      for (int j = 0; j < 5; j++) {
           /* concatenate it to the result */ 
           /* WordleLetter's toString() is automatically invoked here. */
        result += getGuess(i)[j];
      }
         /* new line separator between each word */ 
      result += "\n";
    }
    return result;
  }
}
