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

    public void addHealth(int healthToAdd) {this.health += healthToAdd;}

    public int getDefense() {
        return this.defense;
    }

    public void setDefense(int newDefense) {
        this.defense = newDefense;
    }

    public void addDefense(int defenseToAdd) {
        this.defense += defenseToAdd;
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
        Cell neighbour = getCellNeighbour(x, y);
        boolean isGround = isNeighbourActionCell(x, y, "tunnel");
        boolean isFloor = isNeighbourActionCell(x, y, "floor");
        boolean isGrass = isNeighbourActionCell(x, y, "grass2");
        boolean isBridge = isNeighbourActionCell(x, y, "bridge");
        boolean isVillageDoor = isNeighbourActionCell(x, y, "village-door");
        boolean isNeighbourNotActor = neighbour.getActor() == null;

        return (isFloor || isGround || isGrass || isBridge || isVillageDoor) && isNeighbourNotActor;

    }

    Cell getCellNeighbour(int x, int y) {
        return this.getCell().getNeighbor(x, y);
    }

    boolean isNeighbourActionCell(int x, int y, String tileName) {
        return this.getCell().getNeighbor(x, y).getTileName().equals(tileName);
    }


    public void setDamage(int newDamage) {
        this.damage = newDamage;
    }

    public void addDamage(int damageToAdd) {this.damage += damageToAdd;}

    public int getDamage() {
        return this.damage;
    }

}
