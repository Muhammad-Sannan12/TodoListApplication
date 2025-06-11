package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.time.LocalDate;

public class HelloController {
    @FXML
    private ListView<String> myListView;
    @FXML
    private TextField myInputField;
    @FXML
    private Label welcomeText;
    @FXML
    private DatePicker datePicker;
    @FXML
    void initialize() {
        // Load existing tasks from DB when app starts
        loadTasksFromDatabase();
    }
    @FXML
    void addTaskButton(MouseEvent event) {
        String userInput = myInputField.getText().trim();
        LocalDate date = datePicker.getValue();

        if (!userInput.isEmpty()) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO tasks (description, due_date) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userInput);
                pstmt.setDate(2, (date != null) ? Date.valueOf(date) : null);
                pstmt.executeUpdate();

                // Add to ListView
                myListView.getItems().add(userInput + " (Due Date: " + (date != null ? date : "No date") + ")");
                myInputField.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void removeItem(MouseEvent event) {
        int selectedID = myListView.getSelectionModel().getSelectedIndex();
        if (selectedID >= 0) {
            String selectedItem = myListView.getItems().get(selectedID);
            String taskDescription = selectedItem.split(" \\(Due Date:")[0]; // Extract description

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM tasks WHERE description = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, taskDescription);
                pstmt.executeUpdate();

                myListView.getItems().remove(selectedID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadTasksFromDatabase() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT description, due_date FROM tasks";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String desc = rs.getString("description");
                Date dueDate = rs.getDate("due_date");
                myListView.getItems().add(desc + " (Due Date: " + (dueDate != null ? dueDate.toLocalDate() : "No date") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

