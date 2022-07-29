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
 * RA3地图文件工具类
 *
 * @author haruhi0000
 */
public class Ra3MapUtil {
    /**
     * 解析map.xml文件，获取玩家坐标
     *
     * @param mapDirectory 地图文件夹
     * @return MapMetaData 地图信息
     * @throws DocumentException map.xml解析失败
     */
    public static MapMetaData getPlayerPosition(File mapDirectory) throws DocumentException {
        MapMetaData myMapMetaData;
        File xmlFile = new File(mapDirectory.getAbsolutePath() + File.separator + "map.xml");
        SAXReader reader = new SAXReader();
        Map<String, String> map = new HashMap<>(7);
        // 增加命名空间，否则解析不到数据
        map.put("xmlns", "uri:ea.com:eala:asset");
        reader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = reader.read(xmlFile);
        Element root = document.getRootElement();
        Node mapMetaData = root.selectSingleNode("/xmlns:AssetDeclaration/xmlns:GameMap/xmlns:MapMetaData");
        List<Position> positions = new ArrayList<>(6);
        int borderSize = Integer.parseInt(mapMetaData.selectSingleNode("@BorderSize").getText());
        int width = Integer.parseInt(mapMetaData.selectSingleNode("@Width").getText());
        int height = Integer.parseInt(mapMetaData.selectSingleNode("@Height").getText());
        int numPlayers = Integer.parseInt(mapMetaData.selectSingleNode("@NumPlayers").getText());
        myMapMetaData = new MapMetaData(borderSize, width, height, numPlayers, positions);
        List<Node> list = root.selectNodes("/xmlns:AssetDeclaration/xmlns:GameMap/xmlns:MapMetaData/xmlns:StartPosition");
        for (Node node : list) {
            Node name = node.selectSingleNode("@Name");
            String playerStart = name.getText();
            if (playerStart.startsWith("Player") && playerStart.endsWith("Start")) {
                Node position = node.selectSingleNode(".//*");
                if ("Position".equals(position.getName())) {
                    // null !
                    String xStr = position.selectSingleNode("@x").getText();
                    String yStr = position.selectSingleNode("@y").getText();
                    float x = Float.parseFloat(xStr);
                    float y = Float.parseFloat(yStr);
                    positions.add(new Position(x, y));
                }
            }
        }
        return myMapMetaData;
    }
}
