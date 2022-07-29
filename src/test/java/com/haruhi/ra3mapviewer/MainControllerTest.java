package com.haruhi.ra3mapviewer;

import com.haruhi.ra3mapviewer.controller.MainController;
import com.haruhi.ra3mapviewer.entity.MapMetaData;
import com.haruhi.ra3mapviewer.entity.Position;
import com.haruhi.ra3mapviewer.util.Ra3MapUtil;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author haruhi0000
 */

class MainControllerTest {
    @Test
    public void test1() {


    }

    @Test
    void getPlayerPosition2() throws DocumentException {
        MainController mainController = new MainController();
        MapMetaData mapMetaData = Ra3MapUtil
                .getPlayerPosition(new File("C:\\Users\\61711\\Projects\\ra3-tool\\src\\test\\resources\\5V1是兄弟就来砍我~小强"));
        System.out.println(mapMetaData.toString());
        for (Position position : mapMetaData.getStartPositionList()) {
            System.out.println(position.toString());
        }
    }
}