package game.gui.scenes;

import game.gui.Main;
import game.gui.controllers.MainMenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameOverScene extends Scene {

    public GameOverScene(int points, Scene nextScene) {

        super(new VBox());
        setupScene(nextScene);

        VBox root = (VBox) getRoot();
        addPoints(root, points);
    }

    public GameOverScene(Scene nextScene) {
        super(new VBox());
        setupScene(nextScene);
    }

    private void setupScene(Scene nextScene) {
        VBox innerRoot = (VBox) getRoot();
        innerRoot.setId("innerRoot");
        Main.applyWindowSize(innerRoot);

        var gameOver = new Label("Game Over");
        gameOver.setId("heading");


        HBox buttonBar = new HBox(20);
        buttonBar.setAlignment(Pos.CENTER);

        var playAgainButton = new Button("Play Again");
        playAgainButton.setOnAction(e -> Main.setScene(nextScene));

        var exitToMenuButton = new Button("Back to Menu");
        exitToMenuButton.setOnAction(e -> Main.setScene(new MainMenuController()));

        buttonBar.getChildren().add(playAgainButton);
        buttonBar.getChildren().add(exitToMenuButton);

        innerRoot.getChildren().add(gameOver);
        innerRoot.getChildren().add(buttonBar);

        getStylesheets().add("file:src/main/resources/stylesheets/gameOver.css");
    }

    private void addPoints(VBox vbox, int points) {
        var pointsLabel = new Label("Points: " + points);
        pointsLabel.setId("points");
        vbox.getChildren().add(1, pointsLabel);
    }

    public void addReason(String messageStr) {
        var message = new Label(messageStr);
        message.setId("points");

        VBox root = (VBox) getRoot();
        root.getChildren().add(1, message);
    }

}