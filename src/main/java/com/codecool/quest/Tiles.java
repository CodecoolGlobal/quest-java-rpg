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
        tileMap.put("player", new Tile(25, 0));
        tileMap.put("player-w", new Tile(27, 0));
        tileMap.put("player-w-h", new Tile(28, 0));
        tileMap.put("player-h", new Tile(30, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("ghost", new Tile(26, 6));
        tileMap.put("key", new Tile(17, 23));
        tileMap.put("weapon", new Tile(4, 30));
        tileMap.put("helmet", new Tile(4, 22));
        tileMap.put("door", new Tile(0, 9));
        tileMap.put("door-open", new Tile(2, 9));
        tileMap.put("roof-left", new Tile(10, 15));
        tileMap.put("roof-center", new Tile(11, 15));
        tileMap.put("roof-right", new Tile(12, 15));
        tileMap.put("house-left", new Tile(10, 16));
        tileMap.put("house-center", new Tile(11, 16));
        tileMap.put("house-right", new Tile(12, 16));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
