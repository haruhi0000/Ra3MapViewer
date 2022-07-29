package com.haruhi.ra3mapviewer.util;

import com.haruhi.ra3mapviewer.entity.MapMetaData;
import com.haruhi.ra3mapviewer.entity.Position;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haruhi0000
 */
public class AppUtil {
    @Deprecated
    public static MapMetaData getPlayerPosition(File xmlFile) throws DocumentException {
        MapMetaData mapMetaData = new MapMetaData();
        SAXReader reader = new SAXReader();
        Map<String, String> map = new HashMap<>(7);
        map.put("xmlns", "uri:ea.com:eala:asset");
        reader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = reader.read(xmlFile);
        Element root = document.getRootElement();
        Node metaData = root.selectSingleNode("/xmlns:AssetDeclaration/xmlns:GameMap/xmlns:MapMetaData");
        if (metaData == null) {
            return mapMetaData;
        }
        List<Position> positions = new ArrayList<>(6);
        mapMetaData.setBorderSize(getMapMetaValue(metaData, "BorderSize"));
        mapMetaData.setHeight(getMapMetaValue(metaData, "Height"));
        mapMetaData.setWidth(getMapMetaValue(metaData, "Width"));
        mapMetaData.setNumPlayers(getMapMetaValue(metaData, "NumPlayers"));
        mapMetaData.setStartPositionList(positions);

        List<Node> list = root.selectNodes("/xmlns:AssetDeclaration/xmlns:GameMap/xmlns:MapMetaData/xmlns:StartPosition");
        if (list == null) {
            return mapMetaData;
        }
        for (Node node : list) {
            Node name = node.selectSingleNode("@Name");
            String playerStart;
            if(name != null) {
                playerStart = name.getText();
            } else {
                continue;
            }
            if (playerStart.startsWith("Player") && playerStart.endsWith("Start")) {
                Node position = node.selectSingleNode(".//*");
                if(position != null) {
                    if ("Position".equals(position.getName())) {
                        positions.add(new Position(getMapMetaValueToFloat(position, "x"),
                                getMapMetaValueToFloat(position, "y")));
                    }
                }
            }
        }
        return mapMetaData;
    }

    private static int getMapMetaValue(Node metaData, String attitudeName) {
        int borderSizeValue = 0;
        Node borderSize = metaData.selectSingleNode("@" + attitudeName);
        if(borderSize != null) {
            if (borderSize.getText().matches("[0-9]+")) {
                borderSizeValue = Integer.parseInt(borderSize.getText());
            }
        }
        return borderSizeValue;
    }
    private static float getMapMetaValueToFloat(Node metaData, String attitudeName) {
        float borderSizeValue = 0;
        Node borderSize = metaData.selectSingleNode("@" + attitudeName);
        if(borderSize != null) {
            if (borderSize.getText().matches("[0-9]+")) {
                borderSizeValue = Float.parseFloat(borderSize.getText());
            }
        }
        return borderSizeValue;
    }
}
