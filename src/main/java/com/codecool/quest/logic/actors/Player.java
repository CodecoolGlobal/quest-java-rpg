package com.codecool.quest.logic.actors;

import com.codecool.quest.Main;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.GameMap;
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
    private int pubCoordX;
    private int pubCoordY;
    public Map<String, Integer> inventoryMap = new HashMap<>();
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
            System.out.println("Open door");
            this.getCell().getNeighbor(pubCoordX, pubCoordY).setType(CellType.HOUSEOPEN);
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
        String pubNameUp = this.getCell().getNeighbor(0, -1).getTileName();
        String pubNameDown = this.getCell().getNeighbor(0, 1).getTileName();
        if (pubNameUp.equals("house-center") ) {
            pubCoordX = 0;
            pubCoordY = -1;
            return true;
        } else if (pubNameDown.equals("house-center")) {
            pubCoordX = 0;
            pubCoordY = 1;
            return true;
        }
        return false;
    }

    //Player method
    private void showSecretTunnel() {
        if (this.getCell().getTileName().equals("secret-door") &&  ) {
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
            if (hasWeapon() && this.getDamage() != 10) this.setDamage(getDamage() + 5);
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
        Cell neighbor = this.getCellNeighbour(x, y);
        boolean neighborIsDoor = isNeighbourActionCell(x, y, "door");
        boolean neighborIsOpenDoor = isNeighbourActionCell(x, y, "door-open");
        boolean isLeverDoorOpen = isNeighbourActionCell(x, y, "lever-door-open");
        boolean isPubOpen = isNeighbourActionCell(x, y, "house-center-open");
        boolean isSecretDoor = isNeighbourActionCell(x, y, "secret-door");

        openNewLevelDoorIfHasKey(neighbor, neighborIsDoor);

        if (this.getCell().getNeighbor(x, y).getActor() != null) {
            attack(this.getCell().getNeighbor(x, y));
            return false;
        }

        return neighborIsOpenDoor || isLeverDoorOpen || isPubOpen || isSecretDoor;
    }

    private void openNewLevelDoorIfHasKey(Cell neighbor, boolean neighborIsDoor) {
        if (hasKey() && neighborIsDoor) {
            neighbor.setOpenDoor();
            inventoryMap.remove("key");
        }
    }

    public boolean isPlayerAtSpecificDoor(String specificDoor) {
        String tileName = this.getCell().getTileName();
        return tileName.equals(specificDoor);
    }

    public void bartenderInteraction(GameMap map) {
        Cell cellBar = map.getCell(17, 4);
        Cell cellCloak = map.getCell(20, 14);
        Cell cellCard = map.getCell(17, 4);

        try {
            Cell neighbor = this.cell.getNeighbor(0, -3);
            Cell neighborNextUp = this.cell.getNeighbor(0, -1);
            Cell neighborNextRight = this.cell.getNeighbor(1, 0);
            Cell neighborNextLeft = this.cell.getNeighbor(-1, 0);
            String neighborName = this.cell.getNeighbor(0, -3).getTileName();
            if (neighborName.equals("bartender")) {
                neighbor.getNeighbor(0, -1).setType(CellType.QUESTION);
            }

            if (!neighborName.equals("bartender") && cellBar.getType() == CellType.QUESTION) {
                cellBar.setType(CellType.FLOOR);
            }
        } catch (Exception ignored) {

        }

    }


}

