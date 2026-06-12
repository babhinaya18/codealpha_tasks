import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class StudentGradeTracker extends JFrame {

    // Form inputs
    private JTextField idField;
    private JTextField nameField;
    private JTextField subjectField;
    private JTextField marksField;
    private JTextField searchField;

    // Table data elements
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    // Stat cards labels
    private JLabel averageCardLabel, averageCardValue;
    private JLabel highestCardLabel, highestCardValue;
    private JLabel lowestCardLabel, lowestCardValue;
    private JLabel totalCardLabel, totalCardValue;
    private JLabel topperCardLabel, topperCardValue;

    // Visual spectrum data labels
    private JPanel distributionPanel;
    private Map<String, JLabel> gradeCountLabels = new HashMap<>();

    // Theming Containers
    private JPanel headerPanel;
    private JPanel inputPanel;
    private JPanel dashboardPanel;
    private JPanel tableContainerPanel;
    private JLabel titleLabel;
    private JComboBox<String> themeComboBox;

    private boolean isDarkMode = false;
    private int nextIdSequence = 1;

    // Modern color themes
    private static final Color LIGHT_BG = new Color(242, 245, 249);
    private static final Color LIGHT_PANEL = Color.WHITE;
    private static final Color LIGHT_TEXT = new Color(45, 45, 48);
    private static final Color LIGHT_TEXT_MUTED = new Color(115, 115, 120);
    private static final Color LIGHT_BORDER = new Color(215, 222, 230);

    private static final Color DARK_BG = new Color(24, 24, 28);
    private static final Color DARK_PANEL = new Color(36, 36, 40);
    private static final Color DARK_TEXT = new Color(225, 225, 230);
    private static final Color DARK_TEXT_MUTED = new Color(155, 155, 165);
    private static final Color DARK_BORDER = new Color(55, 55, 65);

    private static final Color PRIMARY_ACCENT = new Color(30, 144, 255);
    private static final Color GREEN_ACCENT = new Color(46, 139, 87);
    private static final Color RED_ACCENT = new Color(220, 20, 60);

    public StudentGradeTracker() {
        // Core framework initialization
        setTitle("VS System | Student Grade Tracker");
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 800));
        setLocationRelativeTo(null);

        // Root container setting using formal layout manager to eliminate overlapping
        JPanel mainContentPanel = new JPanel(new BorderLayout(15, 15));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(mainContentPanel);

        // --- TOP MODULE: HEADER BAR ---
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        titleLabel = new JLabel("VS System Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel themeTogglePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        themeTogglePanel.setOpaque(false);
        JLabel themeLabel = new JLabel("Theme: ");
        themeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        themeComboBox = new JComboBox<>(new String[]{"Light Mode", "Dark Mode"});
        themeComboBox.setFont(new Font("Arial", Font.PLAIN, 13));
        themeComboBox.addActionListener(e -> {
            isDarkMode = themeComboBox.getSelectedIndex() == 1;
            applyTheme();
        });
        themeTogglePanel.add(themeLabel);
        themeTogglePanel.add(themeComboBox);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(themeTogglePanel, BorderLayout.EAST);
        mainContentPanel.add(headerPanel, BorderLayout.NORTH);

        // --- CENTRAL SPLIT WINDOW FRAMEWAY ---
        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setOpaque(false);
        mainContentPanel.add(centerPanel, BorderLayout.CENTER);

        // LEFT WING: Inputs and Analytics
        JPanel leftColumnPanel = new JPanel(new BorderLayout(15, 15));
        leftColumnPanel.setOpaque(false);
        leftColumnPanel.setPreferredSize(new Dimension(380, 0));

        // Registry Form Setup
        inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel idLabel = new JLabel("Student ID:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        idField = new JTextField();
        idField.setEditable(false);
        inputPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        nameField = new JTextField();
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        JLabel subjLabel = new JLabel("Subject:");
        subjLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(subjLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        subjectField = new JTextField();
        inputPanel.add(subjectField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        JLabel marksLabel = new JLabel("Marks:");
        marksLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(marksLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        marksField = new JTextField();
        inputPanel.add(marksField, gbc);

        // Button Action Grid Control
        JPanel actionButtonPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        actionButtonPanel.setOpaque(false);

        JButton addButton = new JButton("Add Student");
        addButton.setBackground(GREEN_ACCENT);
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 13));

        JButton updateButton = new JButton("Update Entry");
        updateButton.setBackground(PRIMARY_ACCENT);
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 13));

        JButton deleteButton = new JButton("Delete Entry");
        deleteButton.setBackground(RED_ACCENT);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 13));

        JButton clearButton = new JButton("Clear All");
        clearButton.setBackground(Color.GRAY);
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Arial", Font.BOLD, 13));

        JButton saveButton = new JButton("Save Data");
        JButton loadButton = new JButton("Load Data");

        actionButtonPanel.add(addButton);
        actionButtonPanel.add(updateButton);
        actionButtonPanel.add(deleteButton);
        actionButtonPanel.add(clearButton);
        actionButtonPanel.add(saveButton);
        actionButtonPanel.add(loadButton);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 5, 10);
        inputPanel.add(actionButtonPanel, gbc);

        // Grade spectrum analytics labels panel
        distributionPanel = new JPanel(new GridLayout(5, 1, 5, 5));

        String[] grades = {"A", "B", "C", "D", "F"};
        for (String g : grades) {
            JLabel gLabel = new JLabel(" Grade " + g + ": 0 records found (0.0%)");
            gLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            gLabel.setOpaque(true);
            gLabel.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
            gradeCountLabels.put(g, gLabel);
            distributionPanel.add(gLabel);
        }

        leftColumnPanel.add(inputPanel, BorderLayout.CENTER);
        leftColumnPanel.add(distributionPanel, BorderLayout.SOUTH);
        centerPanel.add(leftColumnPanel, BorderLayout.WEST);

        // RIGHT WING: Stats Summary Rows & Central JTable Records Grid
        JPanel rightColumnPanel = new JPanel(new BorderLayout(15, 15));
        rightColumnPanel.setOpaque(false);
        centerPanel.add(rightColumnPanel, BorderLayout.CENTER);

        dashboardPanel = new JPanel(new GridLayout(1, 5, 12, 12));
        dashboardPanel.setOpaque(false);

        JPanel avgCard = createStatCard("AVERAGE MARKS", averageCardLabel = new JLabel("0.00"), averageCardValue = new JLabel("—"));
        JPanel highCard = createStatCard("HIGHEST MARKS", highestCardLabel = new JLabel("0"), highestCardValue = new JLabel("—"));
        JPanel lowCard = createStatCard("LOWEST MARKS", lowestCardLabel = new JLabel("0"), lowestCardValue = new JLabel("—"));
        JPanel topCard = createStatCard("CLASS TOPPER", topperCardLabel = new JLabel("None"), topperCardValue = new JLabel("—"));
        JPanel totalCard = createStatCard("TOTAL STUDENTS", totalCardLabel = new JLabel("0"), totalCardValue = new JLabel("Registered"));

        dashboardPanel.add(avgCard);
        dashboardPanel.add(highCard);
        dashboardPanel.add(lowCard);
        dashboardPanel.add(topCard);
        dashboardPanel.add(totalCard);
        rightColumnPanel.add(dashboardPanel, BorderLayout.NORTH);

        // Main table panel grid system setup
        tableContainerPanel = new JPanel(new BorderLayout(10, 10));

        // Filtering query bar tools panel
        JPanel filterBar = new JPanel(new BorderLayout(10, 10));
        filterBar.setOpaque(false);
        filterBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        JLabel searchIconLabel = new JLabel("Search File: ");
        searchIconLabel.setFont(new Font("Arial", Font.BOLD, 13));
        searchField = new JTextField();

        JButton searchBtn = new JButton("Search Box Popup");
        JButton reportBtn = new JButton("Export Report Text");
        reportBtn.setFont(new Font("Arial", Font.BOLD, 12));
        reportBtn.setBackground(PRIMARY_ACCENT);
        reportBtn.setForeground(Color.WHITE);

        JPanel searchControlPanel = new JPanel(new BorderLayout(5, 5));
        searchControlPanel.setOpaque(false);
        searchControlPanel.add(searchField, BorderLayout.CENTER);
        searchControlPanel.add(searchBtn, BorderLayout.EAST);

        filterBar.add(searchIconLabel, BorderLayout.WEST);
        filterBar.add(searchControlPanel, BorderLayout.CENTER);
        filterBar.add(reportBtn, BorderLayout.EAST);
        tableContainerPanel.add(filterBar, BorderLayout.NORTH);

        // Instantiating main structured dataset model mapping columns
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) return Integer.class;
                return super.getColumnClass(columnIndex);
            }
        };

        model.addColumn("Student ID");
        model.addColumn("Student Name");
        model.addColumn("Subject");
        model.addColumn("Grade");
        model.addColumn("Marks");

        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        table.setRowHeight(34);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setupCustomTableRenderers();

        JScrollPane scrollPane = new JScrollPane(table);
        tableContainerPanel.add(scrollPane, BorderLayout.CENTER);
        rightColumnPanel.add(tableContainerPanel, BorderLayout.CENTER);

        // --- BOTTOM MODULE: FOOTER INFORMATION DISPLAY ---
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);
        JLabel footerLabel = new JLabel("VS System Framework v2.0 | Clean Stable Engine");
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        JLabel designerLabel = new JLabel("Developed by Abhinaya ");
        designerLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        footerPanel.add(footerLabel, BorderLayout.WEST);
        footerPanel.add(designerLabel, BorderLayout.EAST);
        mainContentPanel.add(footerPanel, BorderLayout.SOUTH);

        // --- EVENT WIREWAYS AND CONTROL ROUTING ACTIONS ---

        // Dynamic key filtering lookup engine text listener
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { fireFilter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { fireFilter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { fireFilter(); }
            private void fireFilter() {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // Controller actions routing
        addButton.addActionListener(e -> handleAddStudent());
        nameField.addActionListener(e -> handleAddStudent());
        subjectField.addActionListener(e -> handleAddStudent());
        marksField.addActionListener(e -> handleAddStudent());

        updateButton.addActionListener(e -> handleUpdateStudent());
        deleteButton.addActionListener(e -> handleDeleteRow());

        clearButton.addActionListener(e -> {
            if (model.getRowCount() == 0) return;
            int selection = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete all entries from the list?",
                    "Confirm Clear", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (selection == JOptionPane.YES_OPTION) {
                model.setRowCount(0);
                resetStatistics();
                clearInputFields();
            }
        });

        saveButton.addActionListener(e -> handleSaveData());
        loadButton.addActionListener(e -> handleLoadData(false));
        reportBtn.addActionListener(e -> handleExportReport());

        searchBtn.addActionListener(e -> {
            String term = searchField.getText().trim();
            if(term.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please type a search query first.");
                return;
            }
            boolean found = false;
            for(int i = 0; i < model.getRowCount(); i++) {
                if(model.getValueAt(i, 1).toString().equalsIgnoreCase(term) || model.getValueAt(i, 0).toString().equals(term)) {
                    int viewIndex = table.convertRowIndexToView(i);
                    table.setRowSelectionInterval(viewIndex, viewIndex);
                    table.scrollRectToVisible(table.getCellRect(viewIndex, 0, true));

                    JOptionPane.showMessageDialog(this, "Student Found!\n" +
                            "ID: " + model.getValueAt(i, 0) + "\n" +
                            "Name: " + model.getValueAt(i, 1) + "\n" +
                            "Subject: " + model.getValueAt(i, 2) + "\n" +
                            "Marks: " + model.getValueAt(i, 4) + "\n" +
                            "Grade: " + model.getValueAt(i, 3));
                    found = true;
                    break;
                }
            }
            if(!found) {
                JOptionPane.showMessageDialog(this, "No student record found with that matching name or ID.");
            }
        });

        // SAFE ROW DOUBLE CLICK SELECTION ACTION
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedViewRow = table.getSelectedRow();
                // SAFE CHECK: Verify valid selection parameters before processing indices mapping values
                if (e.getClickCount() == 2 && selectedViewRow != -1) {
                    int modelRow = table.convertRowIndexToModel(selectedViewRow);
                    if (modelRow >= 0 && modelRow < model.getRowCount()) {
                        idField.setText(model.getValueAt(modelRow, 0).toString());
                        nameField.setText(model.getValueAt(modelRow, 1).toString());
                        subjectField.setText(model.getValueAt(modelRow, 2).toString());
                        marksField.setText(model.getValueAt(modelRow, 4).toString());
                    }
                }
            }
        });

        // Build structural configurations
        applyTheme();
        handleLoadData(true);

        setVisible(true);
    }

    private JPanel createStatCard(String headerText, JLabel metricLabel, JLabel contextLabel) {
        JPanel card = new JPanel(new BorderLayout(2, 2));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JLabel title = new JLabel(headerText);
        title.setFont(new Font("Arial", Font.BOLD, 11));
        title.setForeground(LIGHT_TEXT_MUTED);

        metricLabel.setFont(new Font("Arial", Font.BOLD, 22));
        contextLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        contextLabel.setForeground(LIGHT_TEXT_MUTED);

        card.add(title, BorderLayout.NORTH);
        card.add(metricLabel, BorderLayout.CENTER);
        card.add(contextLabel, BorderLayout.SOUTH);
        return card;
    }

    private String generateGrade(int marks) {
        if (marks >= 90) return "A";
        if (marks >= 80) return "B";
        if (marks >= 70) return "C";
        if (marks >= 60) return "D";
        return "F";
    }

    private void handleAddStudent() {
        String name = nameField.getText().trim();
        String subject = subjectField.getText().trim();
        String marksText = marksField.getText().trim();

        if (name.isEmpty() || subject.isEmpty() || marksText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all empty input boxes.", "Input Missing", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int marks = Integer.parseInt(marksText);
            if (marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100.", "Invalid Score Range", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 1).toString().equalsIgnoreCase(name) &&
                        model.getValueAt(i, 2).toString().equalsIgnoreCase(subject)) {
                    JOptionPane.showMessageDialog(this, "This student and subject combined entry already exists.", "Duplicate Found", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            String generatedID = "STU-" + String.format("%04d", nextIdSequence++);
            String assignedGrade = generateGrade(marks);

            model.addRow(new Object[]{generatedID, name, subject, assignedGrade, marks});
            calculateStatistics();
            clearInputFields();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a clean number in the marks box.", "Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // FIXED: Patched view-to-model coordinate calculation matrix to avoid Sorter Warnings
    private void handleUpdateStudent() {
        int selectedViewRow = table.getSelectedRow();
        if (selectedViewRow == -1) {
            JOptionPane.showMessageDialog(this, "Click and highlight a row in the table first before hitting update.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Dynamic view index translation validation step logic
        int modelRow = table.convertRowIndexToModel(selectedViewRow);
        if (modelRow < 0 || modelRow >= model.getRowCount()) {
            JOptionPane.showMessageDialog(this, "Table tracking state changed. Please clear search parameters filters and retry row selection.", "Synchronization Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = nameField.getText().trim();
        String subject = subjectField.getText().trim();
        String marksText = marksField.getText().trim();

        if (name.isEmpty() || subject.isEmpty() || marksText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cannot update with empty input forms.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int marks = Integer.parseInt(marksText);
            if (marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(this, "Marks must be a value from 0 to 100.", "Invalid Range", JOptionPane.ERROR_MESSAGE);
                return;
            }

            model.setValueAt(name, modelRow, 1);
            model.setValueAt(subject, modelRow, 2);
            model.setValueAt(generateGrade(marks), modelRow, 3);
            model.setValueAt(marks, modelRow, 4);

            calculateStatistics();
            clearInputFields();
            table.clearSelection();
            JOptionPane.showMessageDialog(this, "Student record successfully modified and updated!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Marks must be numeric.", "Format Exception", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteRow() {
        int selectedViewRow = table.getSelectedRow();
        if (selectedViewRow == -1) {
            JOptionPane.showMessageDialog(this, "Please click a row in the data list to delete first.", "Select Row", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student's data?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            int modelRow = table.convertRowIndexToModel(selectedViewRow);
            if (modelRow >= 0 && modelRow < model.getRowCount()) {
                model.removeRow(modelRow);
                clearInputFields();
                if (model.getRowCount() > 0) calculateStatistics(); else resetStatistics();
            }
        }
    }

    private void clearInputFields() {
        idField.setText("");
        nameField.setText("");
        subjectField.setText("");
        marksField.setText("");
        nameField.requestFocus();
    }

    private void calculateStatistics() {
        int rowCount = model.getRowCount();
        if (rowCount == 0) {
            resetStatistics();
            return;
        }

        int total = 0;
        int highest = Integer.MIN_VALUE;
        int lowest = Integer.MAX_VALUE;
        String topper = "N/A";

        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;

        for (int i = 0; i < rowCount; i++) {
            int marks = Integer.parseInt(model.getValueAt(i, 4).toString());
            String grade = model.getValueAt(i, 3).toString();
            total += marks;

            if (marks > highest) {
                highest = marks;
                topper = model.getValueAt(i, 1).toString();
            }
            if (marks < lowest) {
                lowest = marks;
            }

            switch (grade) {
                case "A": countA++; break;
                case "B": countB++; break;
                case "C": countC++; break;
                case "D": countD++; break;
                case "F": countF++; break;
            }
        }

        double average = (double) total / rowCount;

        averageCardLabel.setText(String.format("%.2f", average));
        highestCardLabel.setText(String.valueOf(highest));
        lowestCardLabel.setText(String.valueOf(lowest));
        topperCardLabel.setText(topper);
        totalCardLabel.setText(String.valueOf(rowCount));

        averageCardValue.setText("Class Mean");
        highestCardValue.setText("Top Score");
        lowestCardValue.setText("Lowest Score");
        topperCardValue.setText("Highest Student");

        updateDistributionRow("A", countA, rowCount, new Color(46, 139, 87, 40));
        updateDistributionRow("B", countB, rowCount, new Color(139, 195, 74, 40));
        updateDistributionRow("C", countC, rowCount, new Color(255, 193, 7, 40));
        updateDistributionRow("D", countD, rowCount, new Color(255, 152, 0, 40));
        updateDistributionRow("F", countF, rowCount, new Color(220, 20, 60, 40));
    }

    private void updateDistributionRow(String grade, int count, int total, Color baseAlertBg) {
        double percentage = (total == 0) ? 0.0 : ((double) count / total) * 100;
        JLabel lbl = gradeCountLabels.get(grade);
        if (lbl != null) {
            lbl.setText(String.format(" Grade %s: %d records found (%.1f%%)", grade, count, percentage));
            lbl.setBackground(count > 0 ? baseAlertBg : new Color(0,0,0,0));
        }
    }

    private void resetStatistics() {
        averageCardLabel.setText("0.00");
        highestCardLabel.setText("0");
        lowestCardLabel.setText("0");
        topperCardLabel.setText("None");
        totalCardLabel.setText("0");

        averageCardValue.setText("—");
        highestCardValue.setText("—");
        lowestCardValue.setText("—");
        topperCardValue.setText("—");

        for (String g : gradeCountLabels.keySet()) {
            JLabel lbl = gradeCountLabels.get(g);
            lbl.setText(" Grade " + g + ": 0 records found (0.0%)");
            lbl.setBackground(new Color(0,0,0,0));
        }
        nextIdSequence = 1;
    }

    private void handleSaveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("StudentData.txt"))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.write(
                        model.getValueAt(i, 0) + "," +
                                model.getValueAt(i, 1) + "," +
                                model.getValueAt(i, 2) + "," +
                                model.getValueAt(i, 3) + "," +
                                model.getValueAt(i, 4)
                );
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Data list saved successfully!", "Saved File", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving data onto the file tracker.", "Save Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLoadData(boolean isAutoRun) {
        try (BufferedReader reader = new BufferedReader(new FileReader("StudentData.txt"))) {
            model.setRowCount(0);
            String traceLine;
            int maxIdTracked = 0;

            while ((traceLine = reader.readLine()) != null) {
                String[] tokens = traceLine.split(",");
                if (tokens.length == 5) {
                    model.addRow(new Object[]{
                            tokens[0],
                            tokens[1],
                            tokens[2],
                            tokens[3],
                            Integer.parseInt(tokens[4])
                    });

                    try {
                        int currentNumId = Integer.parseInt(tokens[0].replace("STU-", ""));
                        if (currentNumId > maxIdTracked) maxIdTracked = currentNumId;
                    } catch (Exception ignored) {}
                }
            }
            nextIdSequence = maxIdTracked + 1;
            calculateStatistics();
            if (!isAutoRun) {
                JOptionPane.showMessageDialog(this, "Data successfully loaded from storage!", "Data Loaded", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            if (!isAutoRun) {
                JOptionPane.showMessageDialog(this, "No previous grading records found.", "Load Complete", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void handleExportReport() {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "The data list is empty. There is nothing to export.", "Export Aborted", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Report.txt"))) {
            writer.write("================================================="); writer.newLine();
            writer.write("               VS SYSTEM STUDENT REPORT          "); writer.newLine();
            writer.write("================================================="); writer.newLine();
            writer.newLine();
            writer.write("CLASS SUMMARY STATISTICS:"); writer.newLine();
            writer.write("----------------------------------"); writer.newLine();
            writer.write("Total Registered Students: " + totalCardLabel.getText()); writer.newLine();
            writer.write("Class Performance Average: " + averageCardLabel.getText() + " / 100.00"); writer.newLine();
            writer.write("Highest Grade in Class   : " + highestCardLabel.getText() + " Points"); writer.newLine();
            writer.write("Lowest Grade in Class    : " + lowestCardLabel.getText() + " Points"); writer.newLine();
            writer.write("Class Topper Student Name: " + topperCardLabel.getText()); writer.newLine();
            writer.newLine();
            writer.write("GRADE SPECTRUM DISTRIBUTION SPREAD:"); writer.newLine();
            for (String g : new String[]{"A", "B", "C", "D", "F"}) {
                writer.write("  " + gradeCountLabels.get(g).getText().trim()); writer.newLine();
            }
            writer.newLine();
            writer.write("COMPREHENSIVE STUDENT RECORD ENTRIES LIST:"); writer.newLine();
            writer.write("================================================="); writer.newLine();
            writer.write(String.format("%-10s %-20s %-15s %-6s %-5s", "ID", "NAME", "SUBJECT", "GRADE", "SCORE")); writer.newLine();
            writer.write("-------------------------------------------------"); writer.newLine();

            for (int i = 0; i < model.getRowCount(); i++) {
                writer.write(String.format("%-10s %-20s %-15s %-6s %-5s",
                        model.getValueAt(i, 0).toString(),
                        model.getValueAt(i, 1).toString(),
                        model.getValueAt(i, 2).toString(),
                        model.getValueAt(i, 3).toString(),
                        model.getValueAt(i, 4).toString()
                ));
                writer.newLine();
            }
            writer.write("================================================="); writer.newLine();
            writer.write("                [END OF VS SYSTEM REPORT]        "); writer.newLine();

            JOptionPane.showMessageDialog(this, "Simple report data successfully generated and saved into 'Report.txt'.", "Report Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while creating your report text file.", "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupCustomTableRenderers() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object val, boolean isSel, boolean hasFocus, int row, int col) {
                Component cell = super.getTableCellRendererComponent(table, val, isSel, hasFocus, row, col);
                setHorizontalAlignment(JLabel.CENTER);
                setFont(new Font("Arial", Font.BOLD, 14));

                if (val != null) {
                    String g = val.toString();
                    if (isSel) {
                        cell.setForeground(Color.WHITE);
                    } else {
                        switch (g) {
                            case "A": cell.setForeground(GREEN_ACCENT); break;
                            case "B": cell.setForeground(new Color(100, 175, 70)); break;
                            case "C": cell.setForeground(new Color(220, 150, 0)); break;
                            case "D": cell.setForeground(Color.ORANGE); break;
                            default: cell.setForeground(RED_ACCENT); break;
                        }
                    }
                }
                return cell;
            }
        });

        table.getColumnModel().getColumn(4).setCellRenderer(new TableCellRenderer() {
            private final JProgressBar pBar = new JProgressBar(0, 100);
            {
                pBar.setStringPainted(true);
                pBar.setFont(new Font("Arial", Font.BOLD, 12));
                pBar.setBorder(BorderFactory.createEmptyBorder(3, 4, 3, 4));
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Integer) {
                    int valInt = (Integer) value;
                    pBar.setValue(valInt);
                    pBar.setString(valInt + " pts");

                    if (valInt >= 85) {
                        pBar.setForeground(GREEN_ACCENT);
                    } else if (valInt >= 60) {
                        pBar.setForeground(PRIMARY_ACCENT);
                    } else {
                        pBar.setForeground(RED_ACCENT);
                    }
                }

                if (isSelected) {
                    pBar.setBackground(table.getSelectionBackground());
                } else {
                    pBar.setBackground(table.getBackground());
                }
                return pBar;
            }
        });
    }

    private void applyTheme() {
        Color currentBg = isDarkMode ? DARK_BG : LIGHT_BG;
        Color currentPanelBg = isDarkMode ? DARK_PANEL : LIGHT_PANEL;
        Color currentText = isDarkMode ? DARK_TEXT : LIGHT_TEXT;
        Color currentBorder = isDarkMode ? DARK_BORDER : LIGHT_BORDER;

        getContentPane().setBackground(currentBg);
        headerPanel.setBackground(currentPanelBg);
        inputPanel.setBackground(currentPanelBg);
        dashboardPanel.setBackground(currentBg);
        tableContainerPanel.setBackground(currentPanelBg);
        table.setBackground(currentPanelBg);
        table.setGridColor(currentBorder);

        titleLabel.setForeground(PRIMARY_ACCENT);
        table.setForeground(currentText);
        table.getTableHeader().setBackground(PRIMARY_ACCENT);
        table.getTableHeader().setForeground(Color.WHITE);

        for (Component c : inputPanel.getComponents()) {
            if (c instanceof JLabel) c.setForeground(currentText);
            if (c instanceof JTextField) {
                c.setBackground(isDarkMode ? new Color(50, 50, 55) : Color.WHITE);
                c.setForeground(currentText);
                ((JTextField) c).setCaretColor(currentText);
                ((JTextField) c).setBorder(BorderFactory.createLineBorder(currentBorder));
            }
        }

        for (Component card : dashboardPanel.getComponents()) {
            if (card instanceof JPanel) {
                card.setBackground(currentPanelBg);
                ((JPanel) card).setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(currentBorder, 1),
                        BorderFactory.createEmptyBorder(10, 12, 10, 12)
                ));
                for (Component cardComp : ((JPanel) card).getComponents()) {
                    if (cardComp instanceof JLabel) {
                        JLabel lbl = (JLabel) cardComp;
                        if (lbl.getFont().getSize() == 22) {
                            lbl.setForeground(currentText);
                        } else {
                            lbl.setForeground(isDarkMode ? DARK_TEXT_MUTED : LIGHT_TEXT_MUTED);
                        }
                    }
                }
            }
        }

        updatePanelTitledBorder(inputPanel, "Student Registration Panel", currentText, currentBorder);
        updatePanelTitledBorder(distributionPanel, "Grade Range Spread", currentText, currentBorder);
        updatePanelTitledBorder(tableContainerPanel, "Registered Students List", currentText, currentBorder);

        distributionPanel.setBackground(currentPanelBg);
        for (JLabel lbl : gradeCountLabels.values()) {
            lbl.setForeground(currentText);
        }

        searchField.setBackground(isDarkMode ? new Color(50, 50, 55) : Color.WHITE);
        searchField.setForeground(currentText);
        searchField.setBorder(BorderFactory.createLineBorder(currentBorder));
        searchField.setCaretColor(currentText);

        revalidate();
        repaint();
    }

    private void updatePanelTitledBorder(JPanel panel, String title, Color txtColor, Color borderColor) {
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(borderColor),
                title,
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13),
                txtColor
        ));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGradeTracker::new);
    }
}