package com.example.des;

import com.example.des.controller.SearchViewController;
import com.example.des.controller.SystemViewController;
import com.example.des.controller.WriteViewController;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// this is the main app to control all views
// use the mediator design pattern
public class DesApplication extends Application {

    private Stage stage;
    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        showSystemView();
        stage.show();
    }
    // go to write diaries
    public void showWriteView() {
        try {
            stage.setTitle("Write");
            stage.getIcons().add(new Image("file:images/write.png"));

            WriteViewController wController = (WriteViewController)replaceSceneContent("write-view.fxml");
            wController.setMainApp(this);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    // go to watch and search diaries
    public void showSearchView() {
        try {
            stage.setTitle("Search");
            stage.getIcons().add(new Image("file:images/search.png"));

            SearchViewController fController = (SearchViewController) replaceSceneContent("search-view.fxml");
            fController.setMainApp(this);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    // this is the system view, and other views could use the main app reference to control it
    public void showSystemView() {
        try {
            stage.setTitle("It Keeps Secret");
            stage.getIcons().add(new Image("file:images/home.png"));

            SystemViewController sdController = (SystemViewController) replaceSceneContent("system-view.fxml");
            sdController.setMainApp(this);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    // a function to load the new view and replace the old one
    private Object replaceSceneContent(String fxmlFile) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DesApplication.class.getResource(fxmlFile));

        AnchorPane ap = null;
        try {
            ap = (AnchorPane)loader.load();
        }catch(IOException e) {
            e.printStackTrace();
        }

        scene = new Scene(ap);
        stage.setScene(scene);
        stage.setResizable(false);

        return loader.getController();
    }
    public static void main(String[] args) {
        launch();
    }
}