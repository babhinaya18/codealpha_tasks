package com.chatbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/chatbot_db";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    // Baseline FAQ lookups
    public static String getBotResponse(String userInput) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT response, keywords FROM faq";
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String response = rs.getString("response");
                    String[] keywords = rs.getString("keywords").split(",");
                    for (String keyword : keywords) {
                        if (userInput.toLowerCase().contains(keyword.trim().toLowerCase())) {
                            return response;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Phase 1: Save dynamically learned answer data definitions
    public static void saveLearnedAnswer(String question, String answer) {
        String sql = "INSERT INTO learned_data (question, answer) VALUES (?, ?) ON DUPLICATE KEY UPDATE answer=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, question.trim().toLowerCase());
            stmt.setString(2, answer);
            stmt.setString(3, answer);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phase 1: Retrieve dynamically learned solution models
    public static String getLearnedAnswer(String question) {
        String sql = "SELECT answer FROM learned_data WHERE question = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, question.trim().toLowerCase());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("answer");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Phase 5: Append conversational history entries securely
    public static void saveChat(String question, String answer) {
        String sql = "INSERT INTO chat_history (question, answer) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, question);
            stmt.setString(2, answer);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phase 5: Fetch chat logs straight from server system records
    public static List<String> getChatHistory() {
        List<String> historyList = new ArrayList<>();
        String sql = "SELECT question, answer FROM chat_history ORDER BY id DESC LIMIT 10";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                historyList.add("🔴 You: " + rs.getString("question"));
                historyList.add("🤖 Bot: " + rs.getString("answer"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historyList;
    }
    public static String getAllLearnedData() {

        StringBuilder data = new StringBuilder();

        String sql = "SELECT question, answer FROM learned_data";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while(rs.next()) {

                data.append("Question: ")
                        .append(rs.getString("question"))
                        .append("\n");

                data.append("Answer: ")
                        .append(rs.getString("answer"))
                        .append("\n");

                data.append("---------------------------------\n");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return data.toString();
    }
    public static int getTotalChats() {

        String sql =
                "SELECT COUNT(*) FROM chat_history";

        try (
                Connection conn =
                        DriverManager.getConnection(
                                URL,
                                USER,
                                PASSWORD
                        );

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            if(rs.next()) {
                return rs.getInt(1);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    public static int getLearnedQuestionCount() {

        String sql =
                "SELECT COUNT(*) FROM learned_data";

        try (
                Connection conn =
                        DriverManager.getConnection(
                                URL,
                                USER,
                                PASSWORD
                        );

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            if(rs.next()) {
                return rs.getInt(1);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}