package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.items.Item;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;
    private Skeleton skeleton;
    public List<Skeleton> skeletonList = new ArrayList<>();

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


    public Player getPlayer() {
        return player;
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }

    public List<Skeleton> getAllSkeletons() {return skeletonList;}


    public void setSkeleton(Skeleton skeleton) {
        this.skeleton = skeleton;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
