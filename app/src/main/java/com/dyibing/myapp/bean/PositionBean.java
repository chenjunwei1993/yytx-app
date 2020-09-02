package com.dyibing.myapp.bean;

public class PositionBean {
    private int positionX;
    private int positionY;
    private boolean isFragment;

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public boolean isFragment() {
        return isFragment;
    }

    public void setFragment(boolean fragment) {
        isFragment = fragment;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

}
