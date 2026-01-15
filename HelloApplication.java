package com.example.demo5;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HelloApplication extends Application {

    private TextArea textArea;

    @Override
    public void start(Stage primaryStage) {
        // Initialize the text area
        textArea = new TextArea();

        // Set up the menu bar
        MenuBar menuBar = createMenuBar(primaryStage);

        // Main layout
        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(textArea);

        // Scene and stage setup
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setTitle("Simple Kotlin Code Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open...");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem exitItem = new MenuItem("Exit");

        openItem.setOnAction(e -> openFile(stage));
        saveItem.setOnAction(e -> saveFile(stage));
        exitItem.setOnAction(e -> stage.close());

        fileMenu.getItems().addAll(openItem, saveItem, exitItem);

        // Edit menu
        Menu editMenu = new Menu("Edit");
        MenuItem undoItem = new MenuItem("Undo");
        MenuItem redoItem = new MenuItem("Redo");
        MenuItem cutItem = new MenuItem("Cut");
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");

        undoItem.setOnAction(e -> textArea.undo());
        redoItem.setOnAction(e -> textArea.redo());
        cutItem.setOnAction(e -> textArea.cut());
        copyItem.setOnAction(e -> textArea.copy());
        pasteItem.setOnAction(e -> textArea.paste());

        editMenu.getItems().addAll(undoItem, redoItem, cutItem, copyItem, pasteItem);

        menuBar.getMenus().addAll(fileMenu, editMenu);
        return menuBar;
    }

    private void openFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Kotlin Files (*.kt)", "*.kt"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                String content = Files.readString(file.toPath());
                textArea.setText(content);
            } catch (IOException e) {
                showError("Error Opening File", "An error occurred while opening the file.");
                e.printStackTrace();
            }
        }
    }

    private void saveFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Kotlin Files (*.kt)", "*.kt"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                Files.writeString(file.toPath(), textArea.getText());
            } catch (IOException e) {
                showError("Error Saving File", "An error occurred while saving the file.");
                e.printStackTrace();
            }
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
