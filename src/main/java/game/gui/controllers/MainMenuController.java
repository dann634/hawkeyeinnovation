package game.gui.controllers;

import game.gui.Main;
import game.gui.scenes.InfoPage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainMenuController extends Scene {



    public MainMenuController() {

        super(new VBox());

        VBox root = (VBox) getRoot();


        Main.applyWindowSize(root);

        addContent(root);


        root.setId("root");
        getStylesheets().add("file:src/main/resources/stylesheets/menu.css");

    }

    private void addContent(VBox root) {
        Label title = new Label("Main Menu");


        var higherLowerButton = new Button("Higher or Lower");
        String playInstructions = "1. A card is shown" +
                "\n2. Do you think the next random card will be a higher or lower value (Cards values are 1-15)" +
                "\n3. If you are right you play again, if wrong it's game over" +
                "\n4. Try get as many points as possible";
        higherLowerButton.setOnAction(e -> Main.setScene(new InfoPage(playInstructions, new HigherLowerController())));

        var blackjackButton = new Button("Blackjack");
        blackjackButton.setOnAction(e -> Main.setScene(new InfoPage("", new BlackjackController())));

        root.getChildren().add(title);
        root.getChildren().add(higherLowerButton);
        root.getChildren().add(blackjackButton);
    }
}
