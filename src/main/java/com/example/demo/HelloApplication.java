package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600.0,400.0 );
        stage.setTitle("TodoList");
        stage.setScene(scene);

        stage.show();
        stage.setResizable(false);

    }

    public static void main(String[] args) {

        launch();
    }
}