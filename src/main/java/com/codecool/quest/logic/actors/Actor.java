package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Actor implements Drawable {
    List<int[]> movementCoordinates = new ArrayList<>();
    Random rnd = new Random();
    Cell cell;
    private int health = 10;
    private int damage;
    private int defense = 0;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        int[][] cords = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        movementCoordinates.addAll(Arrays.asList(cords));
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



    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    //abstract method - split into functions for Player and monsters
    public boolean isPassable(int x, int y) {
        if (doesCellExist(x, y) == null) return false;

        boolean neighborIsDoor = this.getCell().getNeighbor(x, y).getTileName().equals("door");
        boolean neighborIsOpenDoor = this.getCell().getNeighbor(x, y).getTileName().equals("door-open");
        if (hasKey() && neighborIsDoor) {
            neighbor.setOpenDoor();
            inventoryMap.remove("key");
        }

        if (neighborIsOpenDoor) return true;
        boolean isLeverDoorOpen = this.getCell().getNeighbor(x, y).getTileName().equals("lever-door-open");
        if (isLeverDoorOpen) {
            return true;
        }

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

    private Cell doesCellExist(int x, int y) {
        return this.getCell().getNeighbor(x, y);
    }


    public void setDamage(int newDamage) {
        this.damage += newDamage;
    }

    public int getDamage() {
        return this.damage;
    }

}
