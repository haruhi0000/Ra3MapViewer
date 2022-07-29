package com.haruhi.ra3mapviewer.controller;

import com.haruhi.ra3mapviewer.entity.MapMetaData;
import com.haruhi.ra3mapviewer.entity.Position;
import com.haruhi.ra3mapviewer.entity.Ra3Map;
import com.haruhi.ra3mapviewer.factory.LocalMapListCellFactory;
import com.haruhi.ra3mapviewer.factory.Ra3MapFactory;
import com.haruhi.ra3mapviewer.util.Component;
import com.haruhi.ra3mapviewer.util.Context;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.DirectoryChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * @author 61711
 */
public class MainController implements Initializable {
    @FXML
    public ListView<Ra3Map> listView1;
    @FXML
    public ImageView bigImageView;
    @FXML
    public Pane pane1;
    @FXML
    public ContextMenu menu1;
    @FXML
    public MenuItem menuItem1;
    public MenuItem menuItem2;

    public Pane bigImageOutLLayerPane;
    public Label mapDirectoryLabel;

    List<Circle> playerCircles = new ArrayList<>(6);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bigImageOutLLayerPane.setPrefWidth(Context.BIG_IMAGEVIEW_WIDTH + 30);
        bigImageOutLLayerPane.setPrefHeight(Context.BIG_IMAGEVIEW_HEIGHT + 30);
        pane1.setMinWidth(Context.BIG_IMAGEVIEW_WIDTH);
        bigImageView.setFitWidth(Context.BIG_IMAGEVIEW_WIDTH);
        listView1.setCellFactory(LocalMapListCellFactory::new);
        // 地图文件夹集合
        ObservableList<Ra3Map> ra3MapList = FXCollections.observableArrayList();
        listView1.setItems(ra3MapList);
        // 默认地址路径
        loadLocalMap(Context.mapPath);
        for (int i = 0; i < 6; i++) {
            Circle circle = new Circle(0, 0, 20.0, Color.valueOf("#0084ff00"));
            circle.setStrokeWidth(4.0);
            circle.setStrokeType(StrokeType.INSIDE);
            circle.setStroke(Color.valueOf("#000dff"));
            circle.setVisible(false);
            playerCircles.add(circle);
            pane1.getChildren().add(circle);
        }

        //initializeRemoteData();
    }



    /**
     * 初始化
     */
    private void loadLocalMap(String mapPath) {
        listView1.getItems().clear();
        mapDirectoryLabel.setText(mapPath);
        File mapDir = new File(mapPath);

        File[] mapList = mapDir.listFiles(File::isDirectory);
        if (mapList == null) {
            mapList = new File[0];
        }

        for (int i = 0; i < mapList.length; i++) {
            Ra3Map ra3Map = Ra3MapFactory.getRa3Map(mapList[i]);
            ra3Map.setId(i);
            listView1.getItems().add(ra3Map);
        }

    }

    /**
     * 本地地图列表点击事件
     */
    @FXML
    public void onListViewClick() {
        System.out.println(listView1.getSelectionModel().getSelectedItem());
        Ra3Map ra3Map = listView1.getSelectionModel().getSelectedItem();
        if (ra3Map == null) {
            return;
        }
        Image image = ra3Map.getArtTga();
        bigImageView.setImage(image);

        System.out.println(bigImageView.getFitWidth() + "::::" + bigImageView.getFitHeight());
        System.out.println(image.getWidth() + "::::" + image.getHeight());
        pane1.setLayoutX((bigImageOutLLayerPane.getWidth() - Context.BIG_IMAGEVIEW_WIDTH) / 2);
        pane1.setLayoutY((bigImageOutLLayerPane.getHeight() - bigImageView.getLayoutBounds().getHeight()) / 2);
        MapMetaData mapMetaData = ra3Map.getMapMetaData();
        if (!mapMetaData.getInvalid()) {
            double ratio = Math.min(Context.BIG_IMAGEVIEW_HEIGHT / image.getHeight(), Context.BIG_IMAGEVIEW_WIDTH / image.getWidth());
            double rw = (image.getWidth() * 0.1 * ratio) / (mapMetaData.getWidth() - (mapMetaData.getBorderSize() * 2));
            double rh = (image.getHeight() * 0.1 * ratio) / (mapMetaData.getHeight() - (mapMetaData.getBorderSize() * 2));
            List<Position> playerPositions = mapMetaData.getStartPositionList();
            for (int i = 0; i < playerCircles.size(); i++) {
                Circle circle = playerCircles.get(i);
                if (i <= playerPositions.size() - 1) {
                    Position position = playerPositions.get(i);
                    int x = (int) (position.getX() * rw);
                    int y = (int) (position.getY() * rh);
                    y = (int) (image.getHeight() * ratio - y);
                    circle.setCenterX(x);
                    circle.setCenterY(y);
                    circle.setVisible(true);
                } else {
                    circle.setVisible(false);
                }
            }
        } else {
            for (Circle playerCircle : playerCircles) {
                playerCircle.setVisible(false);
            }
        }
    }


    /**
     * 列表项点击事件
     *
     */
    public void onMenuItemClick() {
        MultipleSelectionModel<Ra3Map> selectionModel = listView1.getSelectionModel();
        Ra3Map selectedItem = selectionModel.getSelectedItem();
        if (selectedItem.getPath().exists()) {
            try {
                Desktop.getDesktop().open(selectedItem.getPath());
            } catch (IOException e) {
                Component.showError(e.getMessage());
            }
        } else {
            Component.showError("文件夹不存在");
        }
    }

    public void onMapDirectoryButtonClick() {
        System.out.println(mapDirectoryLabel.getText());
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择地图文件夹");
        File tempDirectory = directoryChooser.showDialog(Context.mainStage);
        if (tempDirectory != null) {
            mapDirectoryLabel.setText(tempDirectory.getAbsolutePath());
            Preferences.userRoot().node(Context.appName).put("mapPath", tempDirectory.getAbsolutePath());
            loadLocalMap(tempDirectory.getAbsolutePath());
        }
    }

    public void onMenuItem2Click() {
        MultipleSelectionModel<Ra3Map> selectionModel = listView1.getSelectionModel();
        Ra3Map selectedItem = selectionModel.getSelectedItem();
        if (selectedItem.getPath().exists()) {
            Desktop.getDesktop().moveToTrash(selectedItem.getPath());
        } else {
            Component.showError("文件夹不存在");
        }
        listView1.getItems().remove(selectedItem);
    }
}