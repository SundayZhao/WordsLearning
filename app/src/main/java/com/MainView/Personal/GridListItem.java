package com.MainView.Personal;

import java.util.UUID;

public class GridListItem {
    private Long itemId=0L;
    private int normalImageId=0;
    private int selectedImageId=0;
    private String text="";
    private int callbackId=0;


    public GridListItem( String text,int callbackId,int normalImageId, int selectedImageId){
        this.itemId= Long.valueOf(Math.abs(UUID.randomUUID().hashCode())+"");
        this.normalImageId=normalImageId;
        this.selectedImageId=selectedImageId;
        this.text=text;
        this.callbackId=callbackId;
    }
    public Long getItemId() {
        return itemId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public int getNormalImageId() {
        return normalImageId;
    }

    public void setNormalImageId(int normalImageId) {
        this.normalImageId = normalImageId;
    }

    public int getSelectedImageId() {
        return selectedImageId;
    }

    public void setSelectedImageId(int selectedImageId) {
        this.selectedImageId = selectedImageId;
    }

    public int getCallbackId() {
        return callbackId;
    }
}
