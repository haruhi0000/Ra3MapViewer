package com.haruhi.ra3mapviewer.util;

import javafx.scene.control.Alert;

/**
 * @author haruhi0000
 */
public class Component {
    /**
     * 显示错误提示对话框
     * @param message   错误信息
     */
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(Context.mainStage);
        alert.setTitle("Ra3 Tool");
        alert.setHeaderText("错误");
        alert.setContentText(message);
        alert.show();
    }
}
