package com.codecool.quest.logic.actors;

import com.codecool.quest.Main;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Weapon;
import javafx.scene.control.ListView;

import java.util.HashMap;
import java.util.Map;

public class Player extends Actor {
    private String tileName = "player";
    private boolean isLeverOpen = false;
    private int countSecretDoorOpen = 0;
    private Item itemToPickUp;
    private String itemName;
    private Map<String, Integer> inventoryMap = new HashMap<>();
    private ListView<String> inventory = new ListView<>();

    public Player(Cell cell) {
        super(cell);
        this.setDamage(5);
    }

    public String getTileName() {
        setTileName();
        return tileName;
    }

    public void setTileName() {
        if (inventoryMap.containsKey("weapon") && !inventoryMap.containsKey("cloak")) {
            this.tileName = "player-w";
        } else if (inventoryMap.containsKey("weapon") && inventoryMap.containsKey("cloak")) {
            this.tileName = "player-w-c";
        } else if (!inventoryMap.containsKey("weapon") && inventoryMap.containsKey("cloak")) {
            this.tileName = "player-c";
        }
    }

    //Player method
    public void openLeverDoor() {
        if (isNeighbourLever() && !isLeverOpen) {
            this.getCell().getNeighbor(-1, 1).setType(CellType.LEVERDOOROPEN);
            this.getCell().getNeighbor(0, 1).setType(CellType.LEVEROPEN);
            isLeverOpen = true;
        } else if (isNeighbourLever() && isLeverOpen) {
            this.getCell().getNeighbor(-1, 1).setType(CellType.LEVERDOOR);
            this.getCell().getNeighbor(0, 1).setType(CellType.LEVER);
            isLeverOpen = false;
        }
    }

    //Player method
    public void openPub() {
        if (isNeighbourPub()) {
            this.getCell().getNeighbor(0, -1).setType(CellType.HOUSEOPEN);
        }
    }

    //Player method
    private boolean isNeighbourLever() {
        String leverUpName = this.getCell().getNeighbor(0, 1).getTileName();
        if (leverUpName.equals("lever-up") || leverUpName.equals("lever-down")) return true;
        return false;
    }

    //Player method
    private boolean isNeighbourPub() {
        String pubName = this.getCell().getNeighbor(0, -1).getTileName();
        if (pubName.equals("house-center")) {
            return true;
        }
        return false;
    }

    //Player method
    private void showSecretTunnel() {
        if (this.getCell().getTileName().equals("secret-door")) {
            countSecretDoorOpen++;
            this.getCell().getNeighbor(-1, 0).setType(CellType.FLOOR);
            this.getCell().getNeighbor(-2, 0).setType(CellType.FLOOR);
            this.getCell().getNeighbor(-3, 0).setType(CellType.FLOOR);
            this.getCell().getNeighbor(-4, 0).setType(CellType.FLOOR);
            if (countSecretDoorOpen == 1) {
                new Weapon(this.getCell().getNeighbor(-3, 0));
            }
            this.getCell().getNeighbor(-2, -1).setType(CellType.WALL);
            this.getCell().getNeighbor(-3, -1).setType(CellType.WALL);
            this.getCell().getNeighbor(-4, -1).setType(CellType.WALL);
            this.getCell().getNeighbor(-5, -1).setType(CellType.WALL);
            this.getCell().getNeighbor(-5, 0).setType(CellType.WALL);
        }
    }

    //Player method
    private boolean hasWeapon() {
        return inventoryMap.containsKey("weapon");
    }

    //Player method
    private boolean hasKey() {
        return inventoryMap.containsKey("key");
    }

    //Player method
    public void pickUpItem() {
        if (itemToPickUp != null) {
            itemName = itemToPickUp.getTileName();
        }

        if (!"".equals(itemName)) {
            if (!inventoryMap.containsKey(itemName)) {
                inventoryMap.put(itemName, 1);
            } else {
                Integer value = inventoryMap.get(itemName);
                inventoryMap.replace(itemName, value + 1);
            }
            if (hasWeapon() && this.getDamage() != 10) this.setDamage(5);
            if (itemName.equals("cloak")) this.setDefense(1);

            inventory.getItems().clear();

            for (Map.Entry<String, Integer> entry : inventoryMap.entrySet()) {
                inventory.getItems().add(entry.getKey() + ": " + entry.getValue());
            }
            this.getCell().setItem(null);
            itemToPickUp = null;
            itemName = "";
        }
    }

    //Player method - need one for monsters too
    private void attack(Cell neighbour) {
        System.out.println("In attack method");
        int enemyHealth = neighbour.getActor().getHealth();
        int actualDamage = this.getDamage();
        if (enemyHealth > 0) {
            neighbour.getActor().setHealth(enemyHealth - actualDamage);
            if (neighbour.getActor().getHealth() > 0) {
                this.setHealth(this.getHealth() - neighbour.getActor().getDamage() + this.getDefense());
                if (this.getHealth() <= 0) {
                    this.getCell().setActor(null);
                }
            } else {
                neighbour.setActor(null);
            }
        }
    }

    public Map<String, Integer> getInventoryMap() {
        return inventoryMap;
    }

    public void move(int dx, int dy) {
        try {
            if (((isPassable(dx, dy) || isPassableAsPlayer(dx, dy)) && this.getHealth() > 0)
                    ||
                    Main.nameLabel.getText().equals(Main.cheatCode)) {
                Cell nextCell = cell.getNeighbor(dx, dy);
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
                itemToPickUp = this.cell.isItemInCell();
                showSecretTunnel();
            }
        } catch (NullPointerException ignored) {

        }
    }


    private boolean isPassableAsPlayer(int x, int y) {
        System.out.println("Inside the isPassableAsPlayer method");
        Cell neighbor = this.getCellNeighbour(x, y);
        boolean neighborIsDoor = isNeighbourActionCell(x, y, "door");
        boolean neighborIsOpenDoor = isNeighbourActionCell(x, y, "door-open");
        boolean isLeverDoorOpen = isNeighbourActionCell(x, y, "lever-door-open");
        boolean isPubOpen = isNeighbourActionCell(x, y, "house-center-open");
        boolean isSecretDoor = isNeighbourActionCell(x, y, "secret-door");

        if (hasKey() && neighborIsDoor) {
            neighbor.setOpenDoor();
            inventoryMap.remove("key");
        }

        if (this.getCell().getNeighbor(x, y).getActor() != null) {
            attack(this.getCell().getNeighbor(x, y));
            return false;
        }

        return neighborIsOpenDoor || isLeverDoorOpen || isPubOpen || isSecretDoor;
    }


}

