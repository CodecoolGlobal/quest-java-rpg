package com.codecool.quest.logic;

import javafx.scene.transform.Rotate;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    DOOR("door"),
    SECRETDOOR("secret-door"),
    GROUND("tunnel"),
    ROOFLEFT("roof-left"),
    ROOFCENTER("roof-center"),
    ROOFRIGHT("roof-right"),
    HOUSELEFT("house-left"),
    HOUSECENTER("house-center"),
    HOUSEOPEN("house-center-open"),
    HOUSERIGHT("house-right"),
    WATER("water"),
    GRASS("grass"),
    GRASS2("grass2"),
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
    LEVERDOOROPEN("lever-door-open"),
    PILLARUP("pillar-up"),
    PILLARCENTER("pillar-center"),
    PILLARDOWN("pillar-down"),
    PILLARHORIZONTAL("pillar-horizontal"),
    BAR_B("bar-B"),
    BAR_A("bar-A"),
    BAR_R("bar-R"),
    CASTLEWALL("castle-wall"),
    CASTLEWALL2("castle-wall2"),
    BARTENDER("bartender"),
    CLOAKMAN("cloak-man"),
    CARDMAN("card-man"),
    POTION("potion"),
    QUESTION("question"),
    CAMPFIRE("campfire"),
    NPC("npc"),
    NPCPET("npc-pet"),
    VILLAGEHOUSE("village-house"),
    VILLAGEHOUSE2("village-house2"),
    VILLAGEDOOR("village-door"),
    CARDBACK("card-back"),
    BRIDGE("bridge"),
    TORCH("torch"),
    CANDLE("candle");

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
