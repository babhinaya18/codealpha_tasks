package com.chatbot;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class NLPProcessor {

    // Phase 1 Learning State Keepers
    private static boolean waitingForAnswer = false;
    private static boolean waitingForStudyDays = false;
    private static boolean waitingForCompany = false;
    private static String lastUnknownQuestion = "";

    // Phase 2 Runtime Key-Value Memory Store Container
    private static final HashMap<String, String> memory = new HashMap<>();

    public static String processInput(String input, String currentMode) {
        if (input == null || input.trim().isEmpty()) {
            return "Please type something so I can help you!";
        }

        String lowerInput = input.trim().toLowerCase();
        if(lowerInput.equals("/help")) {

            return """
           🤖 Smart Student AI Assistant

           Available Features:

           📚 create study plan
           📄 /analyze_resume
           💾 /save note
           📜 /showhistory
           📊 Analytics Dashboard
           📚 Knowledge Base

           Student Services:

           🎯 placement help
           🎓 cgpa help
           📝 project ideas
           📖 internship guidance

           AI Features:

           • Self Learning
           • Memory System
           • Intent Detection
           • Sentiment Analysis
           """;
        }
        if(lowerInput.equals("placement help")) {

            waitingForCompany = true;

            return "Which company are you preparing for? (TCS / Infosys / Wipro)";
        }
        if(waitingForCompany) {

            waitingForCompany = false;

            if(lowerInput.contains("tcs")) {

                return """
               TCS Preparation Roadmap:

               • Aptitude Practice
               • Java Basics
               • SQL Queries
               • Communication Skills
               • Mock Interviews
               """;
            }

            if(lowerInput.contains("infosys")) {

                return """
               Infosys Preparation Roadmap:

               • DSA Basics
               • Java
               • DBMS
               • Puzzle Questions
               • HR Interview Practice
               """;
            }

            if(lowerInput.contains("wipro")) {

                return """
               Wipro Preparation Roadmap:

               • Aptitude
               • Coding Round
               • SQL
               • Communication Skills
               """;
            }

            return "Company not available yet.";
        }

        if(lowerInput.equals("create study plan")) {

            waitingForStudyDays = true;

            return "How many days are left for your exam?";
        }
        if(waitingForStudyDays) {

            try {

                int days = Integer.parseInt(input);

                waitingForStudyDays = false;

                return StudyPlanner.generatePlan(days);

            } catch(Exception e) {

                return "Please enter a valid number of days.";
            }
        }

        // Phase 1: Core Target Interactive AI Learning Intercept Logic
        if (waitingForAnswer) {
            DatabaseManager.saveLearnedAnswer(lastUnknownQuestion, input);
            waitingForAnswer = false;
            return "✨ Process complete! Thank you, I have permanently learned that answer logic.";
        }

        // Phase 7: Resume Analyzer Framework Integration Parsing
        if (lowerInput.startsWith("/analyze_resume")) {
            return analyzeResumeText(input);
        }

        // Standard Utility System Action
        if (lowerInput.startsWith("/save ")) {
            String noteContent = input.substring(6);
            return exportToTextFile(noteContent);
        }

        // Phase 2: Context Memory Key Processing Actions
        if (lowerInput.contains("my name is")) {
            String name = input.substring(input.toLowerCase().indexOf("my name is") + 10).trim();
            memory.put("name", name);
            return "Nice to meet you, " + name + "! I've committed your profile parameters to runtime memory blocks.";
        }
        if (lowerInput.contains("what is my name")) {
            if (memory.containsKey("name")) {
                return "Your registered user profile identity parameter is: " + memory.get("name") + ".";
            } else {
                return "I don't have a record of your name yet. Try saying: 'My name is [your name]'.";
            }
        }

        // Phase 4: Sentiment Analytics Emotional Target Detector Block
        String detectedEmotionMessage = detectEmotion(lowerInput);
        if (detectedEmotionMessage != null) {
            return detectedEmotionMessage;
        }

        String intent =
                IntentDetector.detectIntent(input);
        switch(intent){

            case "JAVA_INTENT":
                return "Java is an object-oriented programming language used for desktop, web, mobile and enterprise applications.";

            case "DSA_INTENT":
                return "DSA stands for Data Structures and Algorithms. It is essential for coding interviews.";

            case "INTERNSHIP_INTENT":
                return "Internships help students gain real-world industry experience.";

            case "RESUME_INTENT":
                return "A strong resume should contain skills, projects, education and contact details.";

            case "STUDY_PLAN_INTENT":
                return "Tell me how many days are left for your exam and I will create a study plan.";
            case "ATTENDANCE_INTENT":
                return """
           Attendance Tips:

           • Attend every class possible
           • Maintain above 75%
           • Track attendance weekly
           • Avoid unnecessary absences
           """;

            case "CGPA_INTENT":
                return """
           CGPA Improvement Tips:

           • Study daily
           • Focus on weak subjects
           • Practice previous papers
           • Improve internal marks
           """;

            case "PLACEMENT_INTENT":
                return """
           Placement Preparation:

           • Learn DSA
           • Build Projects
           • Practice Aptitude
           • Improve Communication Skills
           """;

            case "INTERVIEW_INTENT":
                return """
           Common Interview Questions:

           • Tell me about yourself
           • Explain OOP
           • What is Java?
           • Explain your projects
           """;

            case "PROJECT_INTENT":
                return """
           Project Ideas:

           • AI Chatbot
           • Stock Trading Simulator
           • Student Management System
           • Face Attendance System
           • Smart Resume Analyzer
           """;
        }


        // Baseline Level 1 Processing Check: MySQL Reference Index matching lookup
        String dbResponse = DatabaseManager.getBotResponse(input);
        if (dbResponse != null) {
            return dbResponse;
        }

        // Phase 1 Processing: Fallback Match Target Search to AI Memory Database
        String learnedAnswer = DatabaseManager.getLearnedAnswer(input);
        if (learnedAnswer != null) {
            return learnedAnswer;
        }

        // Phase 1 Processing: Set State parameters if no answer matches exist anywhere
        waitingForAnswer = true;
        lastUnknownQuestion = input;
        return "I don't know the answer to that specific concept yet... 💡 Can you please type and teach me what the correct response should be?";
    }

    // Phase 4 Engine Model Strategy Core
    private static String detectEmotion(String input) {
        if (input.contains("sad")) {
            return "I notice you might be feeling sad. 💡 Take a deep breath, split your workflow into smaller tasks, and keep moving forward step-by-step.";
        }
        if (input.contains("happy") || input.contains("excited")) {
            return "That's fantastic! 🚀 Capitalize on this positive momentum to power through your creative and development tasks.";
        }
        if (input.contains("angry")) {
            return "I understand you might be feeling frustrated or angry. Let's take a brief step back or clear this workspace module to reset.";
        }
        if (input.contains("stressed") || input.contains("anxious")) {
            return "Work stress can be tough. 🧘 Try pausing for a short break, clear your screen workspace, and reset your active module goals.";
        }
        if (input.contains("tired") || input.contains("sleepy")) {
            return "Your focus metrics look depleted. ☕ Consider taking a short break or grabbing a cup of coffee before tackling more coding routines.";
        }
        return null; // Passes verification safely on to underlying matching rules
    }

    // Phase 7 Resume Metrics Verification Logic Engine
    private static String analyzeResumeText(String text) {

        String lower = text.toLowerCase();

        int score = 0;

        StringBuilder report =
                new StringBuilder();

        report.append(
                "📄 Resume Analysis Report\n\n"
        );

        if(lower.contains("skills")) {
            report.append("✅ Skills Section Found\n");
            score += 15;
        } else {
            report.append("❌ Skills Section Missing\n");
        }

        if(lower.contains("projects")) {
            report.append("✅ Projects Section Found\n");
            score += 20;
        } else {
            report.append("❌ Projects Section Missing\n");
        }

        if(lower.contains("education")) {
            report.append("✅ Education Section Found\n");
            score += 15;
        } else {
            report.append("❌ Education Section Missing\n");
        }

        if(lower.contains("experience")) {
            report.append("✅ Experience Section Found\n");
            score += 15;
        } else {
            report.append("❌ Experience Section Missing\n");
        }

        if(text.contains("@")) {
            report.append("✅ Email Found\n");
            score += 10;
        } else {
            report.append("❌ Email Missing\n");
        }

        if(lower.contains("github")) {
            report.append("✅ GitHub Found\n");
            score += 10;
        } else {
            report.append("❌ GitHub Missing\n");
        }

        if(lower.contains("linkedin")) {
            report.append("✅ LinkedIn Found\n");
            score += 10;
        } else {
            report.append("❌ LinkedIn Missing\n");
        }

        if(lower.contains("certification")) {
            report.append("✅ Certifications Found\n");
            score += 5;
        } else {
            report.append("❌ Certifications Missing\n");
        }

        report.append("\n");

        report.append(
                "⭐ Resume Score : "
                        + score
                        + "/100\n\n"
        );

        if(score >= 80) {
            report.append(
                    "Excellent Resume. Ready for internships.\n"
            );
        }
        else if(score >= 60) {
            report.append(
                    "Good Resume. Some improvements recommended.\n"
            );
        }
        else {
            report.append(
                    "Resume needs major improvements.\n"
            );
        }

        return report.toString();
    }

    private static String exportToTextFile(String content) {
        try (FileWriter writer = new FileWriter("Workspace_Notes.txt", true)) {
            writer.write(content + "\n---\n");
            return "✨ System Action: Note successfully saved to local file 'Workspace_Notes.txt'!";
        } catch (IOException e) {
            return "❌ Failed to save the note locally.";
        }
    }
}