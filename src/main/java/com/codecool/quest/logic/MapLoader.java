package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Ghost;
import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.items.Key;
import com.codecool.quest.logic.items.Helmet;
import com.codecool.quest.logic.items.Weapon;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String file) {
        InputStream is = MapLoader.class.getResourceAsStream(file);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '+':
                            cell.setType(CellType.SECRETDOOR);
                            break;
                        case '&':
                            cell.setType(CellType.TUNNEL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.setSkeleton(new Skeleton(cell));
                            map.monsterList.add(map.getSkeleton());
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            map.setGhost(new Ghost(cell));
                            map.monsterList.add(map.getGhost());
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            map.setKey(new Key(cell));
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            map.setWeapon(new Weapon(cell));
                            break;
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            map.setHelmet(new Helmet(cell));
                            break;
                        case 'd':
                            cell.setType(CellType.DOOR);
                            break;
                        case 'v':
                            cell.setType(CellType.WATER);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
