package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.*;
import com.codecool.quest.logic.items.*;
import javafx.scene.transform.Rotate;

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
                            cell.setType(CellType.GROUND);
                            break;
                        case ':':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.GROUND);
                            map.setSkeleton(new Skeleton(cell));
                            map.monsterList.add(map.getSkeleton());
                            break;
                        case '*':
                            cell.setType(CellType.GROUND);
                            map.setYeti(new Yeti(cell));
                            map.monsterList.add(map.getYeti());
                            break;
                        case 'g':
                            if (file.equals("/map2.txt")) {
                                cell.setType(CellType.GROUND);
                            } else {
                                cell.setType(CellType.FLOOR);
                            }
                            map.setGhost(new Ghost(cell));
                            map.monsterList.add(map.getGhost());
                            break;
                        case '@':
                            map.setPlayer(new Player(cell));
                            if (!file.equals("/map.txt") && !file.equals("/map2.txt")) {
                                cell.setType(CellType.FLOOR);
                            } else {
                                cell.setType(CellType.GROUND);
                            }
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            map.setKey(new Key(cell));
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            map.setWeapon(new Weapon(cell));
                            break;
                        case 'a':
                            cell.setType(CellType.GROUND);
                            map.setHelmet(new Cloak(cell));
                            break;
                        case 'd':
                            cell.setType(CellType.DOOR);
                            break;
                        case 'e':
                            cell.setType(CellType.DOOROPEN);
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
                        case 'ú':
                            cell.setType(CellType.WATER2);
                            break;
                        case 'r':
                            cell.setType(CellType.GRASS);
                            break;
                        case '0':
                            cell.setType(CellType.GRASS2);
                            break;
                        case 'f':
                            cell.setType(CellType.FOREST);
                            break;
                        case 'F':
                            cell.setType(CellType.FOREST2);
                            break;
                        case 'q':
                            cell.setType(CellType.FORESTDEAD);
                            break;
                        case 'h':
                            cell.setType(CellType.FOREST3);
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
                        case '^':
                            cell.setType(CellType.PILLARUP);
                            break;
                        case '|':
                            cell.setType(CellType.PILLARCENTER);
                            break;
                        case 'ˇ':
                            cell.setType(CellType.PILLARDOWN);
                            break;
                        case '-':
                            cell.setType(CellType.PILLARHORIZONTAL);
                            break;
                        case 'D':
                            cell.setType(CellType.BAR_B);
                            break;
                        case 'A':
                            cell.setType(CellType.BAR_A);
                            break;
                        case 'R':
                            cell.setType(CellType.BAR_R);
                            break;
                        case 'O':
                            cell.setType(CellType.FLOOR);
                            map.setBoss(new Boss(cell));
                            map.monsterList.add(map.getBoss());
                            break;
                        case '%':
                            cell.setType(CellType.FLOOR);
                            map.setCrown(new Crown(cell));
                            break;
                        case 'y':
                            cell.setType(CellType.CASTLEWALL);
                            break;
                        case 'Y':
                            cell.setType(CellType.CASTLEWALL2);
                            break;
                        case 't':
                            cell.setType(CellType.BARTENDER);
                            break;
                        case 'p':
                            cell.setType(CellType.POTION);
                            break;
                        case 'T':
                            cell.setType(CellType.CLOAKMAN);
                            break;
                        case 'P':
                            cell.setType(CellType.CARDMAN);
                            break;
                        case 'V':
                            cell.setType(CellType.VILLAGEDOOR);
                            break;
                        case 'Z':
                            cell.setType(CellType.VILLAGEHOUSE);
                            break;
                        case 'X':
                            cell.setType(CellType.VILLAGEHOUSE2);
                            break;
                        case 'M':
                            cell.setType(CellType.CAMPFIRE);
                            break;
                        case 'G':
                            cell.setType(CellType.BRIDGE);
                            break;
                        case 'N':
                            cell.setType(CellType.NPC);
                            break;
                        case 'm':
                            cell.setType(CellType.GROUND);
                            map.setPet(new Pet(cell));
                            break;
                        case 'í':
                            cell.setType(CellType.TORCH);
                            break;
                        case 'Í':
                            cell.setType(CellType.CANDLE);
                            break;
                        case '$':
                            cell.setType(CellType.PILLARHORIZONTAL);
                            map.setGold(new Gold(cell));
                            break;
                        case 'S':
                            cell.setType(CellType.CARDBACK);
                            break;
                        case '7':
                            cell.setType(CellType.WC);
                            break;
                        case '8':
                            cell.setType(CellType.C);
                            break;
                        case '9':
                            cell.setType(CellType.O);
                            break;
                        case 'J':
                            cell.setType(CellType.D);
                            break;
                        case 'Q':
                            cell.setType(CellType.E);
                            break;
                        case 'K':
                            cell.setType(CellType.L);
                            break;
                        case ',':
                            cell.setType(CellType.Q);
                            break;
                        case 'u':
                            cell.setType(CellType.U);
                            break;
                        case 'U':
                            cell.setType(CellType.S);
                            break;
                        case '<':
                            cell.setType(CellType.T);
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
