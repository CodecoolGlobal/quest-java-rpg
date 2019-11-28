package com.codecool.quest.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    DOOR("door"),
    SECRETDOOR("secret-door"),
    TUNNEL("tunnel"),
    ROOFLEFT("roof-left"),
    ROOFCENTER("roof-center"),
    ROOFRIGHT("roof-right"),
    HOUSELEFT("house-left"),
    HOUSECENTER("house-center"),
    HOUSERIGHT("house-right");

    private String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }

    public void setTileName(String newTileName){
        this.tileName = newTileName;
    }
}
