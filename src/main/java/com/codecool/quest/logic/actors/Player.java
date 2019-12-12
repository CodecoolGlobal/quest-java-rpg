package com.codecool.quest.logic.actors;

import com.codecool.quest.Main;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.items.Gold;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Weapon;
import javafx.scene.control.ListView;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Player extends Actor {
    private String tileName = "player";
    private boolean isLeverOpen = false;
    private int countSecretDoorOpen = 0;
    private boolean wcIsUsed;
    private Item itemToPickUp;
    private String itemName;
    private int pubCoordX = 0;
    private int pubCoordY = 0;
    private boolean playedCard = false;
    private List<String> cards = new ArrayList<>();
    public Map<String, Integer> inventoryMap = new HashMap<>();
    private ListView<String> inventory = new ListView<>();
    private String actualMap = "/map.txt";
    private int level = 1;
    private int xp = 0;

    public Player(Cell cell) {
        super(cell);
        this.setDamage(5);
        this.wcIsUsed = false;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int lvl) {
        this.level = lvl;
    }

    public int getXp() {
        return this.xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getTileName() {
        setTileName();
        return tileName;
    }

    public void setTileName() {
        if (inventoryMap.containsKey("weapon") && !inventoryMap.containsKey("cloak")) {
            this.tileName = "player-w";
        } else if (inventoryMap.containsKey("weapon") && inventoryMap.containsKey("cloak")) {
            this.tileName = "player-w-c";
        } else if (!inventoryMap.containsKey("weapon") && inventoryMap.containsKey("cloak")) {
            this.tileName = "player-c";
        } else if (!inventoryMap.containsKey("weapon") && !inventoryMap.containsKey("cloak")) {
            this.tileName = "player";
        }
    }

    //Player method
    public void openLeverDoor() {
        if (isNeighbourLever() && !isLeverOpen) {
            this.getCell().getNeighbor(-1, 1).setType(CellType.LEVERDOOROPEN);
            this.getCell().getNeighbor(0, 1).setType(CellType.LEVEROPEN);
            isLeverOpen = true;
        } else if (isNeighbourLever() && isLeverOpen) {
            this.getCell().getNeighbor(-1, 1).setType(CellType.LEVERDOOR);
            this.getCell().getNeighbor(0, 1).setType(CellType.LEVER);
            isLeverOpen = false;
        }
    }

    //Player method
    public void openPub() {
        if (isNeighbourPub()) {
            this.getCell().getNeighbor(pubCoordX, pubCoordY).setType(CellType.HOUSEOPEN);
        }
    }

    //Player method
    private boolean isNeighbourLever() {
        try {
            String leverUpName = this.getCell().getNeighbor(0, 1).getTileName();
            if (leverUpName.equals("lever-up") || leverUpName.equals("lever-down")) return true;
        } catch (Exception ignored) {

        }

        return false;
    }

    //Player method
    private boolean isNeighbourPub() {
        String pubNameUp = this.getCell().getNeighbor(0, -1).getTileName();
        String pubNameDown = this.getCell().getNeighbor(0, 1).getTileName();
        if (pubNameUp.equals("house-center")) {
            pubCoordX = 0;
            pubCoordY = -1;
            return true;
        } else if (pubNameDown.equals("house-center")) {
            pubCoordX = 0;
            pubCoordY = 1;
            return true;
        }
        return false;
    }

    public void setActualMap(String mapName) {
        this.actualMap = mapName;
    }

    //Player method
    private void showSecretTunnel() {
        if (this.getCell().getTileName().equals("secret-door")) {

            countSecretDoorOpen++;
            this.getCell().getNeighbor(-1, 0).setType(CellType.FLOOR);
            this.getCell().getNeighbor(-2, 0).setType(CellType.FLOOR);
            this.getCell().getNeighbor(-3, 0).setType(CellType.FLOOR);
            this.getCell().getNeighbor(-4, 0).setType(CellType.FLOOR);
            if (countSecretDoorOpen == 1) {
                new Weapon(this.getCell().getNeighbor(-3, 0));
            }
            this.getCell().getNeighbor(-2, -1).setType(CellType.WALL);
            this.getCell().getNeighbor(-3, -1).setType(CellType.WALL);
            this.getCell().getNeighbor(-4, -1).setType(CellType.WALL);
            this.getCell().getNeighbor(-5, -1).setType(CellType.WALL);
            this.getCell().getNeighbor(-5, 0).setType(CellType.WALL);

        }
    }

    //Player method
    private boolean hasWeapon() {
        return inventoryMap.containsKey("weapon");
    }

    //Player method
    private boolean hasKey() {
        return inventoryMap.containsKey("key");
    }

    private boolean hasCloak() {
        return inventoryMap.containsKey("cloak");
    }

    private boolean hasGold() {
        return inventoryMap.containsKey("gold");
    }

    //Player method
    public void pickUpItem() {
        if (itemToPickUp != null) {
            itemName = itemToPickUp.getTileName();
        }

        if (!"".equals(itemName)) {
            if (!inventoryMap.containsKey(itemName)) {
                inventoryMap.put(itemName, 1);
            } else {
                Integer value = inventoryMap.get(itemName);
                inventoryMap.replace(itemName, value + 1);
            }
            if (itemName.equals("weapon")) this.addDamage(3);
            if (itemName.equals("cloak")) this.addDefense(1);

            inventory.getItems().clear();

            for (Map.Entry<String, Integer> entry : inventoryMap.entrySet()) {
                inventory.getItems().add(entry.getKey() + ": " + entry.getValue());
            }
            this.getCell().setItem(null);
            itemToPickUp = null;
            itemName = "";
        }
    }

    //Player method - need one for monsters too
    private void attack(Cell neighbour) {
        int enemyHealth = neighbour.getActor().getHealth();
        int actualDamage = this.getDamage();
        if (enemyHealth > 0) {
            neighbour.getActor().setHealth(enemyHealth - actualDamage);
            if (neighbour.getActor().getHealth() > 0) {
                this.setHealth(this.getHealth() - neighbour.getActor().getDamage() + this.getDefense());
                if (this.getHealth() <= 0) {
                    this.getCell().setActor(null);
                }
            } else {
                if (neighbour.getActor().getTileName().equals("skeleton")) {
                    this.xp += 2;
                } else if (neighbour.getActor().getTileName().equals("ghost")) {
                    this.xp += 3;
                }
                checkLevel();
                neighbour.setActor(null);

            }
        }
    }

    private void checkLevel() {
        if (this.xp > 9 && this.level == 1) {
            this.level = 2;
            this.addDamage(1);
            this.setHealth(12);
        }
        if (this.xp > 29 && this.level == 2) {
            this.level = 3;
            this.addDamage(1);
            this.setHealth(14);
        }
    }

    public Map<String, Integer> getInventoryMap() {
        return inventoryMap;
    }

    public void move(int dx, int dy) {
        try {
            if (((isPassable(dx, dy) || isPassableAsPlayer(dx, dy)) && this.getHealth() > 0)
                    ||
                    Main.nameLabel.getText().equals(Main.cheatCode)) {
                Cell nextCell = cell.getNeighbor(dx, dy);
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
                itemToPickUp = this.cell.isItemInCell();
                if (this.actualMap.equals("/map.txt")) {
                    showSecretTunnel();
                }
            }
        } catch (NullPointerException ignored) {

        }
    }


    private boolean isPassableAsPlayer(int x, int y) {
        Cell neighbor = this.getCellNeighbour(x, y);
        boolean neighborIsDoor = isNeighbourActionCell(x, y, "door");
        boolean neighborIsOpenDoor = isNeighbourActionCell(x, y, "door-open");
        boolean isLeverDoorOpen = isNeighbourActionCell(x, y, "lever-door-open");
        boolean isPubOpen = isNeighbourActionCell(x, y, "house-center-open");
        boolean isSecretDoor = isNeighbourActionCell(x, y, "secret-door");

        openNewLevelDoorIfHasKey(neighbor, neighborIsDoor);

        if (this.getCell().getNeighbor(x, y).getActor() != null) {
            attack(this.getCell().getNeighbor(x, y));
            return false;
        }

        return neighborIsOpenDoor || isLeverDoorOpen || isPubOpen || isSecretDoor;
    }

    private void openNewLevelDoorIfHasKey(Cell neighbor, boolean neighborIsDoor) {
        if (hasKey() && neighborIsDoor) {
            neighbor.setOpenDoor();
            inventoryMap.remove("key");
        }
    }

    public boolean isPlayerAtSpecificDoor(String specificDoor) {
        String tileName = this.getCell().getTileName();
        return tileName.equals(specificDoor);
    }

    public void pubPeopleInteraction(GameMap map) {
        Cell cellBar = map.getCell(12, 4);
        Cell cellCloak = map.getCell(20, 13);
        Cell cellCard = map.getCell(8, 10);

        try {
            Cell neighbor = this.cell.getNeighbor(0, -3);
            Cell neighborNextUp = this.cell.getNeighbor(0, -1);
            String neighborName = this.cell.getNeighbor(0, -3).getTileName();
            if (neighborName.equals("bartender")) {
                neighbor.getNeighbor(0, -1).setType(CellType.QUESTION);
            } else if (neighborNextUp.getTileName().equals("cloak-man") || neighborNextUp.getTileName().equals("card-man")) {
                neighborNextUp.getNeighbor(0, -1).setType(CellType.QUESTION);
            }

            if (!neighborName.equals("bartender") && cellBar.getType() == CellType.QUESTION) {
                cellBar.setType(CellType.FLOOR);
            } else if (!neighborNextUp.getTileName().equals("cloak-man") && cellCloak.getType() == CellType.QUESTION) {
                cellCloak.setType(CellType.FLOOR);
            } else if (!neighborNextUp.getTileName().equals("card-man") && cellCard.getType() == CellType.QUESTION) {
                cellCard.setType(CellType.FLOOR);
            }
        } catch (Exception ignored) {

        }

    }

    public void getGoldForCloak(GameMap map) {
        Cell cellCloak = map.getCell(20, 13);

        if (cellCloak.getType().equals(CellType.QUESTION)) {
            if (hasCloak()) {
                removeItemFromInventory("cloak");
            }
        }
    }

    private void removeItemFromInventory(String item) {
        Integer value = inventoryMap.get(item);
        inventoryMap.replace(item, value - 1);
        if (value - 1 <= 0) {
            inventoryMap.remove(item);
            this.setTileName();
        }
        if (item.equals("cloak")) {
            this.addDefense(-1);
            getGoldForCloak();
        } else if (item.equals("weapon")) {
            this.addDamage(-3);
        }
    }

    private void getGoldForCloak() {
        int goldValue = inventoryMap.getOrDefault("gold", 0);
        inventoryMap.put("gold", goldValue + 3);
    }

    private boolean hasEnoughGold() {
        if (inventoryMap.containsKey("gold") && inventoryMap.get("gold") >= 5) {
            return true;
        }
        return false;
    }

    public void playForWeapon(GameMap map) {
        Cell cellCard = map.getCell(8, 10);
        if (hasWeapon() && cellCard.getType() == CellType.QUESTION && !playedCard) {
            getRandomCard(map);
        }
    }


    private void getRandomCard(GameMap map) {
        Random random = new Random();
        cards.add("seven");
        cards.add("eight");
        cards.add("nine");
        cards.add("jumbo");
        cards.add("queen");
        cards.add("king");
        cards.add("ace");

        Integer index = random.nextInt(7);
        String randomCard = cards.get(index);
        displayCardOnTableForPlayer(randomCard, map);

        index = random.nextInt(7);
        String randomCardStranger = cards.get(index);
        displayCardOnTableForStranger(randomCardStranger, map);

        String winner = checkCardValues(cards, randomCard, randomCardStranger);
        winWeapon(winner);

        playedCard = true;
    }

    private void displayCardOnTableForPlayer(String card, GameMap map) {
        Cell cardPlayer = map.getCell(12, 12);

        fillArrayWithCardCellTypes(card, cardPlayer);
    }

    private void displayCardOnTableForStranger(String card, GameMap map) {
        Cell cardPlayer = map.getCell(10, 12);

        fillArrayWithCardCellTypes(card, cardPlayer);
    }

    private String checkCardValues(List<String> cardList, String playerCard, String strangerCard) {
        int playerCardNumber = 0;
        int strangerCardNumber = 0;
        for (String card : cardList) {
            if (card.equals(playerCard)) {
                playerCardNumber = cardList.indexOf(card);
                System.out.println(playerCardNumber);
            }

            if (card.equals(strangerCard)) {
                strangerCardNumber = cardList.indexOf(card);
                System.out.println(strangerCardNumber);
            }

        }
        if (playerCardNumber > strangerCardNumber) {
            return "player";
        } else if (strangerCardNumber > playerCardNumber) {
            return "stranger";
        }
        return "tie";
    }

    private void winWeapon(String winner) {
        System.out.println(winner);
        int weaponValue = inventoryMap.getOrDefault("weapon", 0);
        if (winner.equals("player")) {
            inventoryMap.put("weapon", weaponValue + 1);
            this.addDamage(3);
            this.setTileName();
        } else if (winner.equals("stranger")) {
            inventoryMap.put("weapon", weaponValue-1);
            this.addDamage(-3);
            if (weaponValue-1 <= 0) removeItemFromInventory("weapon");
        }

    }

    private void fillArrayWithCardCellTypes(String card, Cell cardPlayer) {
        List<CellType> cellTypes = new ArrayList<>();
        cellTypes.add(CellType.SEVEN);
        cellTypes.add(CellType.EIGHT);
        cellTypes.add(CellType.NINE);
        cellTypes.add(CellType.JUMBO);
        cellTypes.add(CellType.QUEEN);
        cellTypes.add(CellType.KING);
        cellTypes.add(CellType.ACE);

        for (CellType cellType : cellTypes) {
            if (cellType.getTileName().equals(card)) {
                cardPlayer.setType(cellType);
            }
        }
    }


    public void bartenderInteraction(GameMap map) {
        Cell cellBar = map.getCell(12, 4);

        if (cellBar.getType() == CellType.QUESTION && hasEnoughGold()) {
            int potionNumber = inventoryMap.getOrDefault("potion", 0);
            int goldValue = inventoryMap.get("gold");
            inventoryMap.put("potion", potionNumber + 1);
            inventoryMap.put("gold", goldValue - 5);
            if (goldValue - 5 <= 0) {
                inventoryMap.remove("gold");
            }
        }
    }

    public void usePotion() {
        if (inventoryMap.containsKey("potion")) {
            int potionNumber = inventoryMap.get("potion");
            inventoryMap.replace("potion", potionNumber - 1);
            setHealth(10);
            if (potionNumber - 1 <= 0) {
                inventoryMap.remove("potion");
            }
        }
    }

    public void getHPWhenWCIsUsed() {
        Cell cellLeft = this.getCellNeighbour(-1, 0);
        if (cellLeft.getTileName().equals("wc") && !wcIsUsed) {
            this.setHealth(this.getHealth() + 1);
            this.wcIsUsed = true;
        }
    }

    public boolean getWcIsUsed() {
        return wcIsUsed;
    }

    public void setWcIsUsed(boolean used) {
        this.wcIsUsed = used;
    }


}

