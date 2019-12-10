package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Ghost;
import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.items.Key;
import com.codecool.quest.logic.items.Cloak;
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
                        case '.':
                            cell.setType(CellType.TUNNEL);
                            break;
                        case ':':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.TUNNEL);
                            map.setSkeleton(new Skeleton(cell));
                            map.monsterList.add(map.getSkeleton());
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            map.setGhost(new Ghost(cell));
                            map.monsterList.add(map.getGhost());
                            break;
                        case '@':
                            cell.setType(CellType.TUNNEL);
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
                            cell.setType(CellType.TUNNEL);
                            map.setHelmet(new Cloak(cell));
                            break;
                        case 'd':
                            cell.setType(CellType.DOOR);
                            break;
                        case '4':
                            cell.setType(CellType.HOUSELEFT);
                            break;
                        case '5':
                            cell.setType(CellType.HOUSECENTER);
                            break;
                        case '6':
                            cell.setType(CellType.HOUSERIGHT);
                            break;
                        case '1':
                            cell.setType(CellType.ROOFLEFT);
                            break;
                        case '2':
                            cell.setType(CellType.ROOFCENTER);
                            break;
                        case '3':
                            cell.setType(CellType.ROOFRIGHT);
                            break;
                        case 'v':
                            cell.setType(CellType.WATER);
                            break;
                        case 'r':
                            cell.setType(CellType.GRASS);
                            break;
                        case 'f':
                            cell.setType(CellType.FOREST);
                            break;
                        case 'c':
                            cell.setType(CellType.CEMETERY);
                            break;
                        case 'C':
                            cell.setType(CellType.CEMETRYPRO);
                            break;
                        case 'b':
                            cell.setType(CellType.BONES);
                            break;
                        case 'B':
                            cell.setType(CellType.BONESPRO);
                            break;
                        case 'l':
                            cell.setType(CellType.LEVER);
                            break;
                        case 'L':
                            cell.setType(CellType.LEVERDOOR);
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
