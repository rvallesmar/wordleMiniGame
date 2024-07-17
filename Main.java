//import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static WordleGame startGame(Scanner scanner)  {
        System.out.println("Please input the number of the word to guess, max is " + WordBank.numWordsInDictionary());
        
        boolean isInt = false;
        while(!isInt){
            try{
                int playerPick = scanner.nextInt();
                if(WordBank.numWordsInDictionary() >= playerPick && playerPick > 0){
                    WordleGame game = new WordleGame(playerPick);
                    return game;
                }else{
                    System.out.println("Not a valid input, please try again");
                }
            }catch(Exception a){
                System.out.println("Not a valid input");
            }
        }
        return null;
    }
    
    //Method for randomized game
    public static WordleGame startRandomGame()  {
        return new WordleGame((int) (Math.random()*WordBank.numWordsInDictionary() + 1));
    }
 
    public static void playGame(Scanner scanner, WordleGame game)  {
        scanner.nextLine();
        while(!game.isGameOver()){
            System.out.println("Type your guess");
            String playerGuess = scanner.nextLine().trim().toLowerCase();

            if(playerGuess.length() == 5){
                if(WordBank.checkInDictionary(playerGuess)){
                    game.guess(playerGuess);
                    System.out.println("\n");
                    System.out.println(game);
                }else{
                    System.out.println("Not a real word");
                }
            }else{
                System.out.println("Not a valid input, please try again");
            }

            if(game.isGameWin()){
                reportGameOutcome(game);
            }
        }
        if(!game.isGameWin()){
            reportGameOutcome(game); //report the losing outcome only when the game was lost
        }
    }

    public static void reportGameOutcome(WordleGame game) {
        if(game.isGameWin()){
            System.out.println("Congratulations, you won!");
        }else{
            System.out.println("Game over, the answer was: " + game.getAnswer());
        }
    }

    //Method to print the game's menu
    public static void printGameMenu(){
        System.out.println(
            "Please select the game mode: \n" +
            "1. I want to choose the word\n" +
            "2. Give me a random word\n" +
            "3. Exit"
        );
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exitProgram = false;
        
        while(!exitProgram){
            printGameMenu();
            try{
                int playerInput = scanner.nextInt();
                switch (playerInput) {
                    case 1:
                        scanner.nextLine();
                        WordleGame newGame = startGame(scanner);
                        playGame(scanner, newGame);
                        break;
                    case 2:
                        WordleGame newGameRandom = startRandomGame();
                        playGame(scanner, newGameRandom);
                        break;
                    case 3:
                        exitProgram = true;
                        break;
                    default:
                        System.out.println("Not a valid number");
                        break;
                }
            }catch(Exception a){
                System.out.println("Not a number" + a);
                scanner.nextLine();
            }
        }

        System.out.println("Thank you for playing!");
    }
}
