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
    HOUSEOPEN("house-center-open"),
    HOUSERIGHT("house-right"),
    WATER("water"),
    GRASS("grass"),
    CEMETERY("cemetery"),
    CEMETRYPRO("Cemetery"),
    BONES("bones"),
    BONESPRO("Bones"),
    FOREST("forest"),
    FOREST2("forest2"),
    FOREST3("forest3"),
    LEVER("lever-up"),
    LEVEROPEN("lever-down"),
    LEVERDOOR("lever-door-locked"),
    LEVERDOOROPEN("lever-door-open");

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
