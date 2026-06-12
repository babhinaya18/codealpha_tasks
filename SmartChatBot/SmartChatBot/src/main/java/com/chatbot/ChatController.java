package com.chatbot;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.util.List;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class ChatController {
    @FXML private TextArea inputArea;
    @FXML private VBox chatDisplay;
    @FXML private ScrollPane scrollPane;
    @FXML private ToggleButton modeToggle;
    @FXML private Label statusLabel;

    private String currentMode = "Technical";

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(chatDisplay.heightProperty());
    }

    @FXML
    protected void handleSend() {
        String userText = inputArea.getText().trim();
        if (userText.isEmpty()) return;



        HBox userBox = new HBox();

        userBox.setAlignment(Pos.CENTER_RIGHT);

        Label userLabel =
                new Label(userText);

        userLabel.getStyleClass()
                .add("user-label");

        userLabel.setWrapText(true);

        userLabel.setMaxWidth(300);

        userBox.getChildren()
                .add(userLabel);

        chatDisplay.getChildren()
                .add(userBox);

        // Phase 5 Shortcut Command Hook interceptor processing
        if (userText.equalsIgnoreCase("/showhistory")) {
            inputArea.clear();
            List<String> logs = DatabaseManager.getChatHistory();
            Label historyHeader = new Label("📋 --- Displaying Recent Chat History Node Transactions ---");
            historyHeader.setStyle("-fx-text-fill: #e1b12c; -fx-font-weight: bold;");
            chatDisplay.getChildren().add(historyHeader);
            for (String logLine : logs) {
                Label logLabel = new Label(logLine);
                logLabel.setWrapText(true);
                logLabel.setStyle("-fx-text-fill: #a0a0a5;");
                chatDisplay.getChildren().add(logLabel);
            }
            return;
        }

        // Phase 3 Transition Implementation Layout: Append Simulation Typing Marker Node
        Label typingLabel = new Label("⚡ Assistant is thinking & compiling analytics...");
        typingLabel.setStyle("-fx-text-fill: #03dac6; -fx-font-style: italic; -fx-padding: 5;");
        chatDisplay.getChildren().add(typingLabel);

        inputArea.clear();

        // Phase 3 Processing Time Pipeline Execution Model
        PauseTransition pause = new PauseTransition(Duration.seconds(1.2));
        pause.setOnFinished(event -> {
            // Remove typing bubble node layout safely
            chatDisplay.getChildren().remove(typingLabel);

            // Fetch computational calculations from logic layer
            String botReply = NLPProcessor.processInput(userText, currentMode);

            // Phase 5 Action: Commit chat parameters onto system database tables persistently
            DatabaseManager.saveChat(userText, botReply);

            // 2. Render Assistant Response Bubble Component
            HBox botBox = new HBox();

            botBox.setAlignment(Pos.CENTER_LEFT);

            Label botLabel =
                    new Label(botReply);

            botLabel.getStyleClass().add("bot-label");

            botLabel.setWrapText(true);

            botLabel.setMaxWidth(300);

            botBox.getChildren().add(botLabel);

            chatDisplay.getChildren().add(botBox);
        });

        pause.play();
    }

    @FXML
    protected void handleModeToggle() {
        if (modeToggle.isSelected()) {
            currentMode = "Creative";
            modeToggle.setText("Switch to Tech Mode");
            statusLabel.setText("Active Mode: Creative & Design Core");
        } else {
            currentMode = "Technical";
            modeToggle.setText("Switch to Creative Mode");
            statusLabel.setText("Active Mode: Technical Workspace");
        }
    }
    @FXML
    private void openKnowledgeBase() {

        Stage stage = new Stage();

        TextArea area = new TextArea();

        area.setEditable(false);

        area.setText(
                DatabaseManager.getAllLearnedData()
        );

        Scene scene = new Scene(area,600,400);

        stage.setTitle("Knowledge Base");

        stage.setScene(scene);

        stage.show();
    }
    @FXML
    private void openAnalytics() {

        Stage stage = new Stage();

        VBox root = new VBox(15);

        root.setStyle(
                "-fx-padding:20;"
        );

        Label chats =
                new Label(
                        "Total Chats : "
                                +
                                DatabaseManager.getTotalChats()
                );

        Label learned =
                new Label(
                        "Learned Questions : "
                                +
                                DatabaseManager
                                        .getLearnedQuestionCount()
                );

        root.getChildren()
                .addAll(
                        chats,
                        learned
                );

        Scene scene =
                new Scene(
                        root,
                        400,
                        250
                );

        stage.setTitle(
                "Analytics Dashboard"
        );

        stage.setScene(scene);

        stage.show();
    }
}