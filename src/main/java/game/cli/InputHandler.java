package game.cli;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class InputHandler {

    private static Scanner scan = new Scanner(System.in);

    private InputHandler() {}

    public static String takeInput(String prompt) {
        System.out.println(prompt);
        System.out.print("Answer: ");
        return scan.nextLine().trim();
    }

    public static String getUserInputAllowedCharacters(HashSet<String> allowedCharacters) {

        while(true) {
            String userInput = InputHandler.takeInput("");
            if(allowedCharacters.contains(userInput.toLowerCase())) {
                return userInput;
            }
            System.out.println("Error: Please enter " + allowedCharacters);
        }

    }

}
