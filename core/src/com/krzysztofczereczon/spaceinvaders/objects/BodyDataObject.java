package com.krzysztofczereczon.spaceinvaders.objects;


public class BodyDataObject {

    public Object object;
    public boolean isFlaggedForDelete;
    public String type;

    public BodyDataObject(Object object, String type, boolean destroy){
        this.object = object;
        this.isFlaggedForDelete = destroy;
        this.type = type;
    }
}
