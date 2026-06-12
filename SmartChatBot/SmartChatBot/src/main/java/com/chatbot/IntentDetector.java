package com.chatbot;

public class IntentDetector {

    public static String detectIntent(String input) {

        input = input.toLowerCase();

        // Java Intent
        if (input.contains("java")
                || input.contains("tell me about java")
                || input.contains("teach java")
                || input.contains("explain java")) {

            return "JAVA_INTENT";
        }

        // DSA Intent
        if (input.contains("dsa")
                || input.contains("data structure")
                || input.contains("algorithm")) {

            return "DSA_INTENT";
        }

        // Internship Intent
        if (input.contains("internship")
                || input.contains("training")
                || input.contains("industrial training")) {

            return "INTERNSHIP_INTENT";
        }

        // Resume Intent
        if (input.contains("resume")
                || input.contains("cv")) {

            return "RESUME_INTENT";
        }

        // Study Plan Intent
        if (input.contains("study plan")
                || input.contains("exam preparation")
                || input.contains("schedule")) {

            return "STUDY_PLAN_INTENT";
        }
        // Attendance Intent
        if(input.contains("attendance")) {
            return "ATTENDANCE_INTENT";
        }

// CGPA Intent
        if(input.contains("cgpa")
                || input.contains("grade")) {
            return "CGPA_INTENT";
        }

// Placement Intent
        if(input.contains("placement")
                || input.contains("job")) {
            return "PLACEMENT_INTENT";
        }

// Interview Intent
        if(input.contains("interview")) {
            return "INTERVIEW_INTENT";
        }

// Project Intent
        if(input.contains("project idea")
                || input.contains("project")) {
            return "PROJECT_INTENT";
        }

        return "UNKNOWN";
    }
}