package com.haruhi.ra3mapviewer.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author haruhi0000
 */
public final class MapMetaData {
    private Integer borderSize;
    private Integer width;
    private Integer height;
    private Integer numPlayers;
    private List<Position> startPositionList;
    private Boolean invalid;

    public MapMetaData() {
        this.borderSize = 0;
        this.width = 0;
        this.height = 0;
        this.numPlayers = 0;
        this.startPositionList = new ArrayList<>(0);
        this.invalid = true;
    }



    public MapMetaData(Integer borderSize, Integer width, Integer height, Integer numPlayers, List<Position> startPositionList) {
        this.borderSize = borderSize;
        this.width = width;
        this.height = height;
        this.numPlayers = numPlayers;
        this.startPositionList = startPositionList;
        this.invalid = false;
    }
    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }
    public Integer getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(Integer borderSize) {
        this.borderSize = borderSize;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(Integer numPlayers) {
        this.numPlayers = numPlayers;
    }

    public List<Position> getStartPositionList() {
        return startPositionList;
    }

    public void setStartPositionList(List<Position> startPositionList) {
        this.startPositionList = startPositionList;
    }

    @Override
    public String toString() {
        return "MapMetaData{" +
                "borderSize=" + borderSize +
                ", width=" + width +
                ", height=" + height +
                ", numPlayers=" + numPlayers +
                ", startPositionList=" + startPositionList +
                ", invalid=" + invalid +
                '}';
    }
}
