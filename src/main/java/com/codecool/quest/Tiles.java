package com.codecool.quest;

import com.codecool.quest.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;
    public static Rotate rotate = new Rotate();
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
        tileMap.put("grass2", new Tile(7, 0));
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
        tileMap.put("boss", new Tile(30, 6));
        tileMap.put("crown", new Tile(12,24));
        tileMap.put("castle-wall", new Tile(11,18));
        tileMap.put("castle-wall2", new Tile(10,18));
        tileMap.put("bartender", new Tile(26, 9));
        tileMap.put("cloak-man", new Tile(26, 9));
        tileMap.put("card-man", new Tile(26, 9));
        tileMap.put("potion", new Tile(16, 25));
        tileMap.put("question", new Tile(22, 25));
        tileMap.put("village-house", new Tile(18, 10));
        tileMap.put("village-house2", new Tile(19, 10));
        tileMap.put("village-door", new Tile(21, 11));
        tileMap.put("campfire", new Tile(14, 10));
        tileMap.put("npc", new Tile(25, 1));
        tileMap.put("npc-pet", new Tile(21, 9));
        tileMap.put("bridge", new Tile(6, 4));
        tileMap.put("torch", new Tile(4,15));
        tileMap.put("candle", new Tile(5,15));
        tileMap.put("gold", new Tile(9, 26));
        tileMap.put("card-back", new Tile(19, 17));
        tileMap.put("boss2", new Tile(27, 6));
        tileMap.put("seven", new Tile(26, 17));
        tileMap.put("eight", new Tile(27, 16));
        tileMap.put("nine", new Tile(28, 17));
        tileMap.put("jumbo", new Tile(29, 16));
        tileMap.put("queen", new Tile(30, 17));
        tileMap.put("king", new Tile(31, 16));
        tileMap.put("ace", new Tile(20, 17));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
