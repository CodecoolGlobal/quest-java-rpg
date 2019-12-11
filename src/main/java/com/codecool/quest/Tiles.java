package com.codecool.quest;

import com.codecool.quest.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();

    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("secret-door", new Tile(11, 18));
        tileMap.put("tunnel", new Tile(2, 0));
        tileMap.put("floor", new Tile(16, 0));
        tileMap.put("player", new Tile(24, 7));
        tileMap.put("player-w", new Tile(26, 0));
        tileMap.put("player-w-c", new Tile(24, 1));
        tileMap.put("player-c", new Tile(24, 2));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("ghost", new Tile(26, 6));
        tileMap.put("key", new Tile(17, 23));
        tileMap.put("weapon", new Tile(0, 26));
        tileMap.put("cloak", new Tile(6, 23));
        tileMap.put("door", new Tile(0, 9));
        tileMap.put("door-open", new Tile(2, 9));
        tileMap.put("water", new Tile(8, 4));
        tileMap.put("roof-left", new Tile(10, 15));
        tileMap.put("roof-center", new Tile(11, 15));
        tileMap.put("roof-right", new Tile(12, 15));
        tileMap.put("house-left", new Tile(10, 16));
        tileMap.put("house-center", new Tile(3, 3));
        tileMap.put("house-center-open", new Tile(4, 3));
        tileMap.put("house-right", new Tile(12, 16));
        tileMap.put("grass", new Tile(5, 0));
        tileMap.put("forest", new Tile(3, 2));
        tileMap.put("forest2", new Tile(2, 1));
        tileMap.put("forest3", new Tile(5, 1));
        tileMap.put("cemetery", new Tile(1, 14));
        tileMap.put("Cemetery", new Tile(2, 14));
        tileMap.put("bones", new Tile(0, 15));
        tileMap.put("Bones", new Tile(1, 15));
        tileMap.put("lever-up", new Tile(28, 24));
        tileMap.put("lever-down", new Tile(30, 24));
        tileMap.put("lever-door-locked", new Tile(3, 4));
        tileMap.put("lever-door-open", new Tile(4, 4));
        tileMap.put("pillar-up", new Tile(9, 16));
        tileMap.put("pillar-center", new Tile(9, 17));
        tileMap.put("pillar-down", new Tile(9, 18));
        tileMap.put("pillar-horizontal", new Tile(8, 16));
        tileMap.put("bar-B", new Tile(20, 30));
        tileMap.put("bar-A", new Tile(19, 30));
        tileMap.put("bar-R", new Tile(23, 31));
        tileMap.put("bartender", new Tile(26, 9));
        tileMap.put("cloak-man", new Tile(26, 9));
        tileMap.put("card-man", new Tile(26, 9));
        tileMap.put("potion", new Tile(16, 25));
        tileMap.put("question", new Tile(22, 25));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
