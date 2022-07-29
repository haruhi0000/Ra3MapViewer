package com.haruhi.ra3mapviewer.factory;

import com.haruhi.ra3mapviewer.entity.MapMetaData;
import com.haruhi.ra3mapviewer.entity.Ra3Map;
import com.haruhi.ra3mapviewer.exception.TgaReaderException;
import com.haruhi.ra3mapviewer.util.Context;
import com.haruhi.ra3mapviewer.util.Ra3MapUtil;
import com.haruhi.ra3mapviewer.util.TGAReader;
import javafx.scene.image.Image;
import org.dom4j.DocumentException;

import java.io.File;

/**
 * @author haruhi0000
 */
public class Ra3MapFactory {
    /**
     * 获取地图信息
     * @param ra3MapDirectory   地图路径
     * @return  地图信息类
     */
    public static Ra3Map getRa3Map(File ra3MapDirectory) {
        Ra3Map ra3Map = new Ra3Map();
        ra3Map.setPath(ra3MapDirectory);
        ra3Map.setName(ra3MapDirectory.getName());
        ra3Map.setMap(new File(ra3MapDirectory.getAbsolutePath() + File.separator
                + ra3MapDirectory.getName() + ".map"));
        ra3Map.setXml(new File(ra3MapDirectory.getAbsolutePath() + File.separator + "map.xml"));
        if(ra3Map.getMap().exists()) {
            ra3Map.setValid(true);
        } else {
            ra3Map.setValid(false);
        }

        try {
            Image tga = TGAReader.convertToFxImage(new File(ra3MapDirectory.getAbsolutePath() + File.separator
                    + ra3MapDirectory.getName() + ".tga"));
            ra3Map.setTga(tga);
        } catch (TgaReaderException e) {
            ra3Map.setTga(Context.image0000);
        }
        try {
            Image artTga = TGAReader.convertToFxImage(new File(ra3MapDirectory.getAbsolutePath() + File.separator
                    + ra3MapDirectory.getName() + "_art.tga"));
            ra3Map.setArtTga(artTga);
        } catch (TgaReaderException e) {
            ra3Map.setArtTga(Context.image0000);
        }
        try {
            MapMetaData mapMetaData = Ra3MapUtil.getPlayerPosition(ra3MapDirectory);
            ra3Map.setMapMetaData(mapMetaData);
        } catch (DocumentException e) {
            ra3Map.setMapMetaData(new MapMetaData());
        }
        return ra3Map;
    }
}
