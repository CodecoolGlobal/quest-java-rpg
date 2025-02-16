package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.*;
import com.codecool.quest.logic.items.Item;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private Monster monster;
    private Skeleton skeleton;
    private Ghost ghost;
    private Yeti yeti;
    private Boss boss;
    private Item key;
    private Item gold;
    private Item weapon;
    private Item helmet;
    private Item crown;
    private Item pet;
    public List<Actor> monsterList = new ArrayList<>();

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {

        if (x >= width || x < 0 || y>= height || y<0) {
            return null;
        }
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Monster getMonster(){
        return monster;
    }


    public Player getPlayer() {
        return player;
    }

    public Skeleton getSkeleton() { return skeleton; }

    public Yeti getYeti() { return yeti; }

    public Boss getBoss() { return  boss; }

    public void setSkeleton(Skeleton skeleton) {this.skeleton = skeleton;}

    public void setYeti(Yeti yeti) {this.yeti = yeti;}

    public void setGhost(Ghost ghost) {this.ghost = ghost;}

    public void setBoss(Boss boss) {this.boss = boss;}

    public Ghost getGhost() {return ghost;}

    public void setKey(Item key) {this.key=key;}

    public void setGold(Item gold) {this.gold=gold;}

    public Item getKey() {return key;}

    public void setHelmet(Item helmet) {this.helmet = helmet;}

    public Item getHelmet() {return helmet;}

    public void setWeapon(Item weapon) {this.weapon = weapon;}

    public void setPet(Item pet) {this.pet = pet;}

    public Item getWeapon() {return weapon;}

    public void setCrown(Item crown) {this.crown = crown;}

    public Item getCrown() {return crown;}

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
