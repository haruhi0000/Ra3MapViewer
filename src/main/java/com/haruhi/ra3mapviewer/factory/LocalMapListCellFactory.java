package com.haruhi.ra3mapviewer.factory;

import com.haruhi.ra3mapviewer.entity.Ra3Map;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.HashMap;
import java.util.Map;

/**
 * @author haruhi0000
 */
public class LocalMapListCellFactory extends ListCell<Ra3Map> {
    static Map<Ra3Map, HBox> map = new HashMap<>();
    ListView<Ra3Map> ra3MapListView;

    public LocalMapListCellFactory(ListView<Ra3Map> ra3MapListView) {
        super();
        this.ra3MapListView = ra3MapListView;
    }

    @Override
    protected void updateItem(Ra3Map ra3Map, boolean empty) {
        super.updateItem(ra3Map, empty);

        // 设置背景颜色
        if((this.getIndex() % 2) == 1) {
            this.setStyle("-fx-background-color: #280607");
        } else {
            this.setStyle("-fx-background-color: #000000");
        }
        if (ra3Map != null && !empty) {
            HBox hbox = map.get(ra3Map);
            if(hbox == null) {
                hbox = new HBox();
                map.put(ra3Map, hbox);
                Label label = new Label("(empty)");
                label.setWrapText(true);
                label.setMaxWidth(200);
                label.getStyleClass().add("map-name-text");
                Pane pane = new Pane();
                ImageView imageView = new ImageView();
                hbox.getChildren().addAll(imageView, label, pane);
                HBox.setHgrow(pane, Priority.ALWAYS);
                hbox.setAlignment(Pos.CENTER);
                imageView.setFitWidth(64);
                imageView.setFitHeight(64);
                imageView.setImage(ra3Map.getTga());
                label.setText(ra3Map.getName());
                setText(null);
            }
            setGraphic(hbox);
        } else {
            setGraphic(null);
        }
    }
}
