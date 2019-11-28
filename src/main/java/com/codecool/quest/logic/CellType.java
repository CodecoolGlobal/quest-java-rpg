package com.codecool.quest.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    DOOR("door"),
    SECRETDOOR("secret-door"),
    TUNNEL("tunnel");

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
