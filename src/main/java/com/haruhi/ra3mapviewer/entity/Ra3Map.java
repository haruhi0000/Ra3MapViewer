package com.haruhi.ra3mapviewer.entity;

import com.haruhi.ra3mapviewer.exception.TgaReaderException;
import com.haruhi.ra3mapviewer.util.Context;
import com.haruhi.ra3mapviewer.util.Ra3MapUtil;
import com.haruhi.ra3mapviewer.util.TGAReader;
import javafx.scene.image.Image;
import org.dom4j.DocumentException;

import java.io.File;
import java.util.Objects;

/**
 * @author haruhi0000
 */
public class Ra3Map {
    private Integer id;
    private String name;
    private Image tga;
    private Image artTga;
    private File map;
    private File xml;
    private File path;
    private MapMetaData mapMetaData;
    private Boolean valid;
    public Ra3Map() {

    }

    public Ra3Map(File path) {
        this.path = path;
        this.name = path.getName();
        File tga = new File(path.getAbsolutePath() + File.separator + path.getName() + ".tga");

        try {
            this.tga = TGAReader.convertToFxImage(tga);
        } catch (TgaReaderException e) {
            this.tga = Context.image0000;
        }
        File artTga = new File(path.getAbsolutePath() + File.separator + path.getName() + "_art.tga");

        try {
            this.artTga = TGAReader.convertToFxImage(artTga);
        } catch (TgaReaderException e) {
            this.artTga = Context.image0000;
        }
        this.map = new File(path.getAbsolutePath() + File.separator + path.getName() + ".map");
        this.xml = new File(path.getAbsolutePath() + File.separator + "map.xml");
        if(xml.exists()) {
            try {
                this.mapMetaData = Ra3MapUtil.getPlayerPosition(xml);
            } catch (DocumentException e) {
                this.mapMetaData = new MapMetaData();
            }
        }
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public MapMetaData getMapMetaData() {
        return mapMetaData;
    }

    public void setMapMetaData(MapMetaData mapMetaData) {
        this.mapMetaData = mapMetaData;
    }

    public Image getTga() {
        return tga;
    }

    public void setTga(Image tga) {
        this.tga = tga;
    }

    public Image getArtTga() {
        return artTga;
    }

    public void setArtTga(Image artTga) {
        this.artTga = artTga;
    }

    public File getMap() {
        return map;
    }

    public void setMap(File map) {
        this.map = map;
    }

    public File getXml() {
        return xml;
    }

    public void setXml(File xml) {
        this.xml = xml;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ra3Map ra3Map = (Ra3Map) o;

        return Objects.equals(path, ra3Map.path);
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }
}
