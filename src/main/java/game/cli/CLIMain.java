package game.cli;

import game.Game;

public class CLIMain implements Game {

    public static void main(String[] args) {
        new CLIMain().startGame();
    }

    @Override
    public void startGame() {

        //Games List allows for additional games to be added easily
        String[] gameList = new String[]{"Higher or Lower", "Blackjack"};

        //Generates Prompt from Game List
        String prompt = "What would you like to play? (1-" + (gameList.length) + ")";
        for (int i = 0; i < gameList.length; i++) {
            prompt += String.format("%n%d: %s", (i + 1), gameList[i]);
        }


        int answer = -1;
        do {
            String input = InputHandler.takeInput(prompt);
            try {
                answer = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid choice");
            }
        } while(answer < 1 || answer > gameList.length);


        GameInterface game;
        switch (answer) {
            case 1:
                game = new HigherLowerGame();
                break;
            default:
                game = new BlackJackGame();
        }

        game.startGame();

    }
}
