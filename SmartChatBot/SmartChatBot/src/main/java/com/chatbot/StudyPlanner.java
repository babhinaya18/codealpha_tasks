package com.chatbot;

public class StudyPlanner {

    public static String generatePlan(int days) {

        String[] subjects = {
                "Java",
                "DSA",
                "DBMS",
                "Operating Systems",
                "Computer Networks"
        };

        StringBuilder plan = new StringBuilder();

        for(int i=1;i<=days;i++) {

            plan.append("Day ")
                    .append(i)
                    .append(" : ")
                    .append(subjects[(i-1)%subjects.length])
                    .append("\n");

        }

        return plan.toString();
    }
}