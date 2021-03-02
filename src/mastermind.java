import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
/**
 * @author Lewis Shemery
 *
 * This is my attempt to recreate the classic game mastermind. The main class
 * first generates an answer_array() then gets the users input as a guess_array().
 * It then displays the accuracy of the guess array using a number of black pegs 
 * and white pegs. The number of black pegs represents characters in the guess array 
 * that are correct and at the right index. The number of white pegs represents 
 * characters in the guess array that are correct but at the wrong index. If 
 * right_answer_count increments to the length of the answer array, you win!
 *
 * features I plan to add: 
 *  - text box formatting around the output 
 *  - Easy and Hard mode (hard mode would be another class with more numbers)
 */
public class mastermind {

    public static void main(String[] args) {
        welcome_message(); //at the very bottom
        String[] answer = answer_array();

        for (int turn_counter = 1; turn_counter < 11; turn_counter++) { 
            String guess = user_guess(answer, turn_counter);
            if (guess == null) {
                continue;
            }
            System.out.printf("your guess was: %s\n", guess);
            process_guess(answer, guess);
        }
        System.out.printf("No more turns! You are no mastermind.\n");
        print_answer("The code was: ", answer);
    }

    public static String[] answer_array() {
        //generate an array of 4 random numbers between 1 and 6
        Random ran = new Random();
        String[] answer = new String[4];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = Integer.toString(ran.nextInt(6) + 1);
        }
        return answer;
    }

    public static String user_guess(String[] answer, int turn) { 
        //creates an array of 4 guesses from the user
        Scanner input = new Scanner(System.in);
        System.out.printf("Turn %d.\nGuess the 4 digit code: ", turn);

        String guess = input.next();
        if (guess.equals("show")) {
            print_answer("The code is: ", answer);
            return null;
        }
        if (guess.equals("q")) {
            System.exit(0);
        }
        if (validate_guess(guess, answer) == false) {
            System.out.printf("\n\nERROR: You can only enter 4 consecutive integers\n"
                    + "ranging from 1-6.\n\n");
            return null;
        }

        return guess;
    }

    public static boolean validate_guess(String guess, String[] answer) {
        if (guess.length() != answer.length) {
            return false;
        }
        for (int i = 0; i < guess.length(); i++) {
            char c = guess.charAt(i);
            if ("123456".indexOf(c) < 0) {
                return false;
            }
        }

        return true;
    }

    public static void process_guess(String[] answer, String guess) {
        String[] guess_array = string_to_array(guess);
        compute_feedback(answer, guess_array);
    }

    public static String[] string_to_array(String guess) {
        String[] result = new String[guess.length()];
        for (int i = 0; i < guess.length(); i++) {
            result[i] = guess.substring(i, i + 1);
        }
        return result;
    }

    public static void compute_feedback(String[] answer, String[] guess) {
        int black_pegs = count_black(answer, guess);
        int white_pegs = count_white(answer, guess);
        System.out.printf("Black pegs: %d\n", black_pegs);
        System.out.printf("White pegs: %d\n\n", white_pegs);
        if(black_pegs ==4){
            System.out.printf("Congratulations! You are a mastermind.\n");
            System.exit(0);
        }
        
    }

    public static int count_black(String[] answer, String[] guess) {
        int result = 0;
        for (int i = 0; i < guess.length; i++) {
            if (answer[i].equals(guess[i])) { 
                result++;
            }
        }
        return result;
    }

    public static int count_white(String[] answer, String[] guess) {
        int result = 0;
        String[] answer2 = Arrays.copyOf(answer, answer.length);
        answer = answer2;

        for (int i = 0; i < guess.length; i++) {
            if (answer[i].equals(guess[i])) { 
                guess[i] = "o";
                answer[i] = "x";
            }
        }

        for (int i = 0; i < guess.length; i++) {
            for (int j = 0; j < answer.length; j++) {
                if (guess[i].equals(answer[j])) {
                    result++;
                    guess[i] = "o";
                    answer[j] = "x";
                }
            }
        }
        return result;
    }

    public static void print_answer(String name, String[] a) { 
        //prints the answer, used for testing and as a cheat if you enter "show" as your guess
        System.out.printf("%s", name);
        for (String a_i : a) {
            System.out.printf("%3s", a_i);
        }
        System.out.printf("\n");
    }

    static void welcome_message() {
        System.out.printf("********************************************************************************\n"
                + "*  Welcome to Mastermind! You have 10 turns to crack the secret 4-digit code.  *\n"
                + "*  Each digit is a randomly generated number from 1 to 6. Every black peg you  *\n"
                + "*  get shows you guessed a correct number and in the correct spot. Every white *\n"
                + "*  peg you get shows that you guessed a correct number but in the wrong spot.  *\n"
                + "*  Channel your inner savant and become a true mastermind!                     *\n"
                + "*                                                                              *\n"
                + "*  Enter \"q\" to quit at any time.                                            *\n"
                + "********************************************************************************\n");
    }
}