package application;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ServerApp extends Application {

    private Server server = new Server();
    private TextArea consoleArea;
    private TextField userInputField;
    private Label questionLabel;
    private Stage serverStage;
    private State currentState = State.LOGIN;
    private BufferedWriter fileWriter;

    private enum State {
        LOGIN,
        CONNECT_TO_SERVER,
        CHANGE_TEXT,
        ENTER_TEXT
    }

    @Override
    public void start(Stage primaryStage) {
        serverStage = primaryStage;
        serverStage.setTitle("Login");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");
        Label loginStatusLabel = new Label();
        
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // Replace this with your actual authentication logic
            if (isValidCredentials(username, password)) {
                showServerScene();
                
            } else {
                loginStatusLabel.setText("Invalid username or password");
            }
        });

        VBox loginRoot = new VBox(10);
        loginRoot.setPadding(new Insets(10));
        loginRoot.getChildren().addAll(usernameField, passwordField, loginButton, loginStatusLabel);

        Scene loginScene = new Scene(loginRoot, 400, 200);
        serverStage.setScene(loginScene);
        serverStage.show();
        try {
            fileWriter = new BufferedWriter(new FileWriter("user_logs.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
    
    private void logToFile(String logMessage, boolean append) {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("user_logs.txt", append));
            fileWriter.write(logMessage);
            fileWriter.newLine();
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	    private boolean isValidCredentials(String username, String password) {
	        // Replace this with your authentication logic (e.g., checking against a database)
	        return (username.equals("aran") && password.equals("0000"))|| (username.equals("rashmit") && password.equals("0000"));
	    }

    private void showServerScene() {
        questionLabel = new Label("Would you like to connect to a server?: (y/n)");
        userInputField = new TextField();
        Button submitButton = new Button("Submit");
        consoleArea = new TextArea();
        consoleArea.setEditable(false);
        consoleArea.setWrapText(true);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(consoleArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefSize(400, 200);

        submitButton.setOnAction(e -> handleSubmission());

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(questionLabel, userInputField, submitButton, scrollPane);

        Scene scene = new Scene(root, 400, 400);
        serverStage.setScene(scene);
        currentState = State.CONNECT_TO_SERVER;
        
        currentState = State.CONNECT_TO_SERVER;
        logToFile("Application started", false);
        logToFile("Would you like to connect to a server?: (y/n)", true);
    }

    private void handleSubmission() {
        String input = userInputField.getText().trim();

        logToFile("User Input: " + input, true);
        
        switch (currentState) {
            case CONNECT_TO_SERVER:
                if (input.equalsIgnoreCase("y")) {
                    consoleArea.appendText("Requesting to connect to a server\n");
                    consoleArea.appendText("Waiting for a connection on port: " + server.getLocalPort() + "\n");
                    
                    logToFile("Requesting to connect to a server", true);
                    logToFile("Waiting for a connection on port: " + server.getLocalPort(), true);
                    
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(event -> {
                        try {
                            // Simulating server connection
                            consoleArea.appendText("Connected to port(" + server.getLocalPort() + ")\n");
                            logToFile("Connected to port(" + server.getLocalPort() + ")\n", true);
                        } catch (Exception e) {
                            consoleArea.appendText("Error: Unable to establish server connection.\n");
                        }
                    });
                    delay.play();

                 // Second transition for the delay before displaying the next message
                    PauseTransition messageDelay = new PauseTransition(Duration.seconds(2)); // Adjust duration as needed
                    messageDelay.setOnFinished(event -> {
                        currentState = State.CHANGE_TEXT;
                        questionLabel.setText("Would you like to change the text to uppercase? (y/n)");
                        logToFile("Would you like to change the text to uppercase? (y/n)", true);
                    });
                    messageDelay.play();
                } else if (input.equalsIgnoreCase("n")) {
                    consoleArea.appendText("No server request was made.\n");
                    logToFile("No server request was made.", true);
                    Platform.exit();
                } else {
                    consoleArea.appendText("Incorrect Input! Please enter 'y' or 'n'.\n");
                    logToFile("Incorrect Input! Please enter 'y' or 'n'.", true);
                }
                break;
            case CHANGE_TEXT:
                if (input.equalsIgnoreCase("y")) {
                    consoleArea.appendText("Please enter the text to be converted to uppercase:\n");
                    currentState = State.ENTER_TEXT;
                    questionLabel.setText("Please enter the text to be converted to uppercase:");
                    logToFile("Please enter the text to be converted to uppercase:", true);
                } else if (input.equalsIgnoreCase("n")) {
                    consoleArea.appendText("Exiting the Server\n");
                    currentState = State.CONNECT_TO_SERVER;
                    questionLabel.setText("Would you like to connect to a server?: (y/n)");
                    logToFile("Would you like to connect to a server?: (y/n)", true);
                    // Close the window when 'n' is entered
                    Platform.exit();
                } else {
                    consoleArea.appendText("Incorrect Input! Please enter 'y' or 'n'.\n");
                }
                break;
            case ENTER_TEXT:
                questionLabel.setText("Please enter the text to be converted to uppercase:");
                logToFile("Please enter the text to be converted to uppercase:", true);
                String[] words = input.split("\\s+");
                StringBuilder processedText = new StringBuilder();
                int count = 1;

                for (String word : words) {
                    try {
                        consoleArea.appendText("Sending packet (" + count + "): " + word + "\n");
                        String processedWord = server.process(word);
                        consoleArea.appendText("Received packet (" + count + "): " + processedWord + "\n\n");
                        processedText.append(processedWord).append(" ");
                        count++;
                        logToFile("Sending packet (" + count + "): " + word + "\n", true);
                        logToFile("Received packet (" + count + "): " + processedWord + "\n\n", true);
                    } catch (Exception e) {
                        consoleArea.appendText("Error while processing word: " + word + "\n");
                        // Additional error handling or logging can be done here
                    }
                }

                consoleArea.appendText("Processed message in uppercase:\n" + processedText.toString().trim() + "\n\n");
                currentState = State.CONNECT_TO_SERVER;
                questionLabel.setText("Would you like to connect to a server?: (y/n)");
                // Log processed message
                logToFile("Processed message in uppercase:", true);
                logToFile(processedText.toString().trim(), true);
                logToFile("", true);
                logToFile("Would you like to connect to a server?: (y/n)", true);
                break;
        }

        userInputField.clear(); // Clear input field after submission
    }

    @Override
    public void stop() {
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}