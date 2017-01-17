package com.leibown.yangshendemo;

import android.graphics.Path;
import android.graphics.Region;

/**
 * Created by Administrator on 2017/1/17.
 */

public class SmallLabelInfo {

    private int locationX;
    private int locationY;

    private Region region;
    private Path circlePath;
    private Path circleBorderPath;

    private String title;

    public SmallLabelInfo(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public SmallLabelInfo() {
    }

    public Path getCircleBorderPath() {
        return circleBorderPath;
    }

    public void setCircleBorderPath(Path circleBorderPath) {
        this.circleBorderPath = circleBorderPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Path getCirclePath() {
        return circlePath;
    }

    public void setCirclePath(Path circlePath) {
        this.circlePath = circlePath;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    @Override
    public String toString() {
        return "SmallLabelInfo{" +
                "locationX=" + locationX +
                ", locationY=" + locationY +
                ", region=" + region +
                ", circlePath=" + circlePath +
                ", circleBorderPath=" + circleBorderPath +
                ", title='" + title + '\'' +
                '}';
    }
}
