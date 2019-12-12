package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.items.Item;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Item item;
    private GameMap gameMap;
    private int x, y;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }


    public Item isItemInCell() {
        if (this.getActor().getCell().getItem() != null) return this.getActor().getCell().getItem();
        Item emptyItem = new Item() {
            @Override
            public String getTileName() {
                return "";
            }
        };
        return emptyItem;
    }

    public void setItem(Item item) {this.item = item;}

    public Item getItem() {
        return item;
    }

    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public void setOpenDoor() {
        this.setType(CellType.DOOROPEN);
    }

    public void setDoorClose() {
        type.setTileName("door");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {this.x = x;}

    public void setY(int y) {this.y = y;}
}
