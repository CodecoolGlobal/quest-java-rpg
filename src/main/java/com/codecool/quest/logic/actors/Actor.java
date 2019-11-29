package com.codecool.quest.logic.actors;

import com.codecool.quest.Main;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Weapon;
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
    private boolean isLeverOpen = false;
    private int countSecretDoorOpen = 0;
    Map<String, Integer> inventoryMap = new HashMap<>();
    ListView<String> inventory = new ListView<>();


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        int[][] cords = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        movementCoordinates.addAll(Arrays.asList(cords));
    }

    public void move(int dx, int dy) {
        try {
            if ((isPassable(dx, dy) && this.getHealth() > 0)
                    || (Main.nameLabel.getText().equals(Main.cheatCode))
                    && this.getCell().getActor().getTileName().equals("player")) {
                Cell nextCell = cell.getNeighbor(dx, dy);
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
                itemToPickUp = isItemInCell();
                showSecretTunnel();
            }
        } catch (NullPointerException e) {

        }
    }

    public void monsterMove() {
        int[] actualMove = movementCoordinates.get(rnd.nextInt(movementCoordinates.size()));
        int x = actualMove[0];
        int y = actualMove[1];
        if (this.getHealth() > 0) this.move(x, y);


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

    public boolean isPassable(int x, int y) {
        Cell neighbor = this.getCell().getNeighbor(x, y);
        if (neighbor == null) return false;

        boolean neighborIsDoor = this.getCell().getNeighbor(x, y).getTileName().equals("door");
        boolean neighborIsOpenDoor = this.getCell().getNeighbor(x, y).getTileName().equals("door-open");
        if (hasKey() && neighborIsDoor) {
            neighbor.setOpenDoor();
            inventoryMap.remove("key");
        }

        if (neighborIsOpenDoor) return true;
        boolean isLeverDoorOpen = this.getCell().getNeighbor(x, y).getTileName().equals("lever-door-open");
        if (isLeverDoorOpen) {return true;}

        boolean isPubOpen = this.getCell().getNeighbor(x, y).getTileName().equals("house-center-open");
        boolean isSecretDoor = this.getCell().getNeighbor(x, y).getTileName().equals("secret-door");
        if ((isSecretDoor || isPubOpen) &&
                !this.getCell().getActor().getTileName().equals("ghost")
                && !this.getCell().getActor().getTileName().equals("skeleton")) return true;

        if (this.getCell().getNeighbor(x, y).getActor() != null) {
            String monster = this.getCell().getNeighbor(x, y).getActor().getTileName();
            boolean isEnemy = monster.equals("skeleton") || monster.equals("ghost");
            if (isEnemy) {
                attack(this.getCell().getNeighbor(x, y));
                return false;
            }
        }

        boolean isTunnel = this.getCell().getNeighbor(x, y).getTileName().equals(CellType.FLOOR.getTileName());
        if (isTunnel) return true;
        boolean isNotEnemy = this.getCell().getNeighbor(x, y).getActor() == null;
        boolean isFloor = this.getCell().getNeighbor(x, y).getTileName().equals(CellType.TUNNEL.getTileName());
        return isFloor && isNotEnemy;
    }

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

    private boolean hasWeapon() {
        return inventoryMap.containsKey("weapon");
    }

    private boolean hasCloak() {
        return inventoryMap.containsKey("cloak");
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

    private boolean isLever() {
        String leverUpName = this.getCell().getNeighbor(0,1).getTileName();
        if (leverUpName.equals("lever-up") || leverUpName.equals("lever-down")) return true;
        return false;
    }

    private boolean isPub() {
        String pubName = this.getCell().getNeighbor(0, -1).getTileName();
        if (pubName.equals("house-center")) {
            return true;
        }
        return false;
    }

    public void openLeverDoor() {
        if (isLever() && !isLeverOpen) {
            this.getCell().getNeighbor(-1, 1).setType(CellType.LEVERDOOROPEN);
            this.getCell().getNeighbor(0, 1).setType(CellType.LEVEROPEN);
            isLeverOpen = true;
        } else if (isLever() && isLeverOpen){
            this.getCell().getNeighbor(-1, 1).setType(CellType.LEVERDOOR);
            this.getCell().getNeighbor(0, 1).setType(CellType.LEVER);
            isLeverOpen = false;
        }
    }

    public void openPub() {
        if (isPub()) {
            this.getCell().getNeighbor(0, -1).setType(CellType.HOUSEOPEN);
        }
    }
}
