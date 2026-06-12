module com.chatbot {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // This fixes the 'java.sql' module error!

    opens com.chatbot to javafx.fxml;
    exports com.chatbot;
}