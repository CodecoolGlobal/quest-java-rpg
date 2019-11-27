package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.items.Item;
import javafx.scene.control.ListView;

import java.util.HashMap;
import java.util.Map;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private Item itemToPickUp;
    private String itemName;
    Map<String, Integer> inventoryMap = new HashMap<>();
    ListView<String> inventory = new ListView<>();


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        if (isPassable(dx, dy)) {
            Cell nextCell = cell.getNeighbor(dx, dy);
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
            itemToPickUp = isItemInCell();
        }
    }

    public Item isItemInCell() {
        if (this.getCell().getItem() != null) return this.getCell().getItem();
        Item emptyItem = new Item() {
            @Override
            public String getTileName() {
                return "";
            }
        };
        return emptyItem;
    }

    public int getHealth() {
        return health;
    }

    public Map<String, Integer> getInventoryMap() {
        return inventoryMap;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public boolean isPassable(int x, int y) {
        Cell neighbor = this.getCell().getNeighbor(x, y);
        if (neighbor == null) return false;
        boolean isNotEnemy = this.getCell().getNeighbor(x, y).getActor() == null;
        boolean isFloor = this.getCell().getNeighbor(x, y).getTileName().equals(CellType.FLOOR.getTileName());
        return isFloor && isNotEnemy;
    }

    public void pickUpItem() {
        itemName = itemToPickUp.getTileName();

        if (!"".equals(itemName)) {
            if (!inventoryMap.containsKey(itemName)) {
                inventoryMap.put(itemName, 1);
            } else {
                Integer value = inventoryMap.get(itemName);
                inventoryMap.replace(itemName, value + 1);
            }

            inventory.getItems().clear();

            for (Map.Entry<String, Integer> entry : inventoryMap.entrySet()) {
                inventory.getItems().add(entry.getKey() + ": " + entry.getValue());
            }
            this.getCell().setItem(null);
        }
    }
}
