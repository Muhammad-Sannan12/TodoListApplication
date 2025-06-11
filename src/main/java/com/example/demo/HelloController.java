package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
void addTaskButton(MouseEvent event) {
    String userInput = myInputField.getText().trim();
    if (!userInput.isEmpty()) {
        myListView.getItems().add(userInput + " (Due Date: " +
                (datePicker.getValue() != null ? datePicker.getValue() : "No date") + ")");
        myInputField.clear();
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

}