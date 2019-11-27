package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.items.Item;
import javafx.scene.control.ListView;

import java.util.*;

public abstract class Actor implements Drawable {
    private List<int[]> movementCoordinates = new ArrayList<>();
    Random rnd = new Random();
    private Cell cell;
    private int health = 10;
    private Item itemToPickUp;
    private String itemName;
    public int damage;
    public int defense = 0;
    Map<String, Integer> inventoryMap = new HashMap<>();
    ListView<String> inventory = new ListView<>();


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        int[][] cords = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        movementCoordinates.addAll(Arrays.asList(cords));
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

    public void monsterMove() {
        int[] actualMove = movementCoordinates.get(rnd.nextInt(movementCoordinates.size()));
        int x = actualMove[0];
        int y = actualMove[1];
        this.move(x, y);

            /*} catch (InterruptedException e) {
                e.printStackTrace();
            }*/
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

    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    public int getDefense() {
        return this.defense;
    }

    public void setDefense(int newDefense) {
        this.defense += newDefense;
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

    private boolean isPassable(int x, int y) {
        Cell neighbor = this.getCell().getNeighbor(x, y);
        if (neighbor == null) return false;

        boolean neighborIsDoor = this.getCell().getNeighbor(x, y).getTileName().equals("door");
        boolean neighborIsOpenDoor = this.getCell().getNeighbor(x, y).getTileName().equals("door-open");
        if (hasKey() && neighborIsDoor) {
            neighbor.setOpenDoor();
            inventoryMap.remove("key");
        }

        if (neighborIsOpenDoor) return true;

        boolean isSecretDoor = this.getCell().getNeighbor(x, y).getTileName().equals("secret-door");
        if (isSecretDoor) return true;

        if (this.getCell().getNeighbor(x, y).getActor() != null) {
            boolean isEnemy = this.getCell().getNeighbor(x, y).getActor().getTileName().equals("skeleton");
            if (isEnemy) {
                attack(this.getCell().getNeighbor(x, y));
                return false;
            }
        }

        boolean isNotEnemy = this.getCell().getNeighbor(x, y).getActor() == null;
        boolean isFloor = this.getCell().getNeighbor(x, y).getTileName().equals(CellType.FLOOR.getTileName());
        return isFloor && isNotEnemy;
    }

    private void attack(Cell neighbour) {
        int enemyHealth = neighbour.getActor().getHealth();
        int actualDamage = this.getDamage();
        if (enemyHealth > 0) {
            neighbour.getActor().setHealth(enemyHealth - actualDamage);
            if (neighbour.getActor().getHealth() > 0) {
                this.setHealth(this.getHealth() - neighbour.getActor().getDamage() + this.getDefense());
            } else {
                neighbour.setActor(null);
            }
        }
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
            if (hasWeapon() && this.getDamage() != 10) this.setDamage(5);
            if (hasHelmet() && this.getDefense() != 1) this.setDefense(1);

            inventory.getItems().clear();

            for (Map.Entry<String, Integer> entry : inventoryMap.entrySet()) {
                inventory.getItems().add(entry.getKey() + ": " + entry.getValue());
            }
            this.getCell().setItem(null);
        }
    }

    private boolean hasWeapon() {
        return inventoryMap.containsKey("weapon");
    }

    private boolean hasHelmet() {
        return inventoryMap.containsKey("helmet");
    }

    private boolean hasKey() {
        return inventoryMap.containsKey("key");
    }

    public void setDamage(int newDamage) {
        this.damage += newDamage;
    }

    public int getDamage() {
        return this.damage;
    }

    private void showSecretTunnel() {
        
    }
}
