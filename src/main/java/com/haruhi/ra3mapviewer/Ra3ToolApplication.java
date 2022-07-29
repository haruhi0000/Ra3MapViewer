package com.haruhi.ra3mapviewer;

import com.haruhi.ra3mapviewer.util.Context;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * @author 61711
 */
public class Ra3ToolApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Preferences preferences = Preferences.userRoot().node(Context.appName);
        Context.mapPath = preferences.get("mapPath", String.format("%s\\AppData\\Roaming\\Red Alert 3\\Maps",
                System.getProperty("user.home")));
        Context.mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Ra3ToolApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 520);
        scene.getStylesheets().add("/css/app.css");
        stage.setTitle("Ra3++!");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/icon.png"));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}