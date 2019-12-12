package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Boss;
import com.codecool.quest.logic.actors.Monster;
import com.codecool.quest.logic.actors.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class Main extends Application {
    GameMap map = MapLoader.loadMap("/map.txt");
    GameMap firstLevel = map;
    GameMap secondMap;
    GameMap bonusMap;
    GameMap bossMap;
    private int counter = 0;
    private String mapName;

    public Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);

    GraphicsContext context = canvas.getGraphicsContext2D();

    Label healthLabel = new Label();
    Label damageLabel = new Label();
    Label defenseLabel = new Label();
    Label levelLabel = new Label();
    Label xpLabel = new Label();
    ListView<String> inventory = new ListView<>();

    public static Label nameLabel = new Label();
    public static String cheatCode = "Cinci";
    public Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        inventory.setFocusTraversable(false);
        stage = primaryStage;
        GridPane ui = setGridPane();
        BorderPane borderPane = setBorderPane(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
        getPlayerName();
        String characterName = "Name: " + nameLabel.getText();
        ui.add(new Label(characterName), 0, 0);
        refresh();
    }

    private GridPane setGridPane() {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.add(levelLabel, 0, 1);
        ui.add(xpLabel, 0, 2);
        ui.add(healthLabel, 0, 3);
        ui.add(damageLabel, 0, 4);
        ui.add(defenseLabel, 0, 5);
        ui.add(new Label("Inventory:"), 0, 7);
        ui.add(inventory, 0, 8);

        return ui;
    }

    private BorderPane setBorderPane(GridPane ui) {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        return borderPane;
    }

    private void getPlayerName() {
        try {
            TextInputDialog dialog = new TextInputDialog("Bob");

            dialog.setTitle("Greetings Adventurer");
            dialog.setHeaderText("What's your name?");
            dialog.setContentText("Name:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(name -> {
                this.nameLabel.setText(name);
            });
        } catch (NullPointerException e) {
            System.out.println("Wrong character name");
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                if (map.getPlayer().getHealth() < 1) {
                    break;
                }
                moveAllMonsters(map.monsterList);
                map.getPlayer().move(0, -1);
                checkIfMonsterIsDead();
                moveBetweenLevelsUpDirection();
                this.map.getPlayer().pubPeopleInteraction(map);
                refresh();
                break;
            case DOWN:
                if (map.getPlayer().getHealth() < 1) {
                    break;
                }
                moveAllMonsters(map.monsterList);
                map.getPlayer().move(0, 1);
                checkIfMonsterIsDead();
                moveBetweenLevelsDownDirection();
                refresh();
                break;
            case LEFT:
                if (map.getPlayer().getHealth() < 1) {
                    break;
                }
                moveAllMonsters(map.monsterList);
                map.getPlayer().move(-1, 0);
                checkIfMonsterIsDead();
                refresh();
                break;
            case RIGHT:
                if (map.getPlayer().getHealth() < 1) {
                    break;
                }
                moveAllMonsters(map.monsterList);
                map.getPlayer().move(1, 0);
                checkIfMonsterIsDead();
                moveBetweenLevelsRightDirection();
                refresh();
                break;
            case A:
                if (map.getPlayer().getHealth() < 1) {
                    break;
                }
                moveAllMonsters(map.monsterList);
                map.getPlayer().pickUpItem();
                refresh();
                break;
            case D:
                if (map.getPlayer().getHealth() < 1) {
                    break;
                }
                moveAllMonsters(map.monsterList);
                map.getPlayer().openLeverDoor();
                map.getPlayer().openPub();
                refresh();
                break;
            case S:
                if (map.getPlayer().getHealth() < 1) {
                    break;
                }
                map.getPlayer().getGoldForCloak(map);
                map.getPlayer().playForWeapon(map);
                map.getPlayer().bartenderInteraction(map);
                map.getPlayer().getHPWhenWCIsUsed();
                refresh();
                break;
            case P:
                if (map.getPlayer().getHealth() < 1) {
                    break;
                }
                map.getPlayer().usePotion();
                refresh();
                break;
        }

    }

    private void moveBetweenLevelsRightDirection() {
        if (map.getPlayer().isPlayerAtSpecificDoor("door-open") && counter == 1) {
            enterNewLevel("/map3.txt");
        } else if (map.getPlayer().isPlayerAtSpecificDoor("door-open") && counter > 1) {
            enterPreviousLevel(secondMap);
        }
    }

    private void moveBetweenLevelsDownDirection() {
        if (map.getPlayer().isPlayerAtSpecificDoor("house-center-open")) {
            enterPreviousLevel(firstLevel);
        }
    }

    private void moveBetweenLevelsUpDirection() {
        if (map.getPlayer().isPlayerAtSpecificDoor("house-center-open")) {
            enterNewLevel("/bonus.txt");
        } else if (map.getPlayer().isPlayerAtSpecificDoor("door-open") && counter == 0) {
            enterNewLevel("/map2.txt");
        } else if (map.getPlayer().isPlayerAtSpecificDoor("door-open") && counter > 0) {
            enterPreviousLevel(firstLevel);
        }
    }

    private void refresh() {
        displayInventory();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }

            }
        }
        levelLabel.setText("Hero level: " + map.getPlayer().getLevel());
        xpLabel.setText("XP: " + map.getPlayer().getXp());
        healthLabel.setText("Health: " + map.getPlayer().getHealth());
        damageLabel.setText("Attack: " + map.getPlayer().getDamage());
        defenseLabel.setText("Defense: " + map.getPlayer().getDefense());


    }

    private void displayInventory() {
        inventory.getItems().clear();
        Map<String, Integer> inventoryMap = map.getPlayer().getInventoryMap();
        for (Map.Entry<String, Integer> entry : inventoryMap.entrySet()) {
            inventory.getItems().add(entry.getKey() + ": " + entry.getValue());
        }
    }


    private void moveAllMonsters(List monsterList) {
        Random random = new Random();
        for (int i = 0; i < monsterList.size(); i++) {
            int randomNumber = random.nextInt(2);
            if (randomNumber == 1) {
                Monster monster = (Monster) monsterList.get(i);
                monster.monsterMoveDirection();
            }
        }
    }

    private void enterNewLevel(String level) {
        increaseCounter(level);
        setMapName(level);
        Player currentPlayer = this.map.getPlayer();
        this.map = MapLoader.loadMap(level);
        if (level.equals("/bonus.txt")) {
            bonusMap = this.map;
        } else if (level.equals("/map2.txt")) {
            secondMap = this.map;
        } else if (level.equals("/map3.txt")) {
            bossMap = this.map;
        }
        this.map.getPlayer().setActualMap(mapName);
        saveStats(currentPlayer);
    }

    private void setStageSize() {
        canvas.setWidth(map.getWidth() * Tiles.TILE_WIDTH);
        canvas.setHeight(map.getHeight() * Tiles.TILE_WIDTH);
        stage.setWidth(map.getWidth() * 32 + 200);
        stage.setHeight(map.getHeight() * 32 + 30);
    }

    private void increaseCounter(String levelName) {
        if (!levelName.equals("/bonus.txt")) {
            counter++;
        }
    }

    private void enterPreviousLevel(GameMap previousMap) {
        Player currentPlayer = this.map.getPlayer();
        if (previousMap == firstLevel) {
            if (this.map != previousMap) {
                this.map = previousMap;
            } else {
                this.map = secondMap;
            }
        } else if (previousMap == secondMap) {
            if (this.map != previousMap) {
                this.map = previousMap;
            } else {
                this.map = bossMap;
            }
        }
        saveStats(currentPlayer);
    }

    private void saveStats(Player currentPlayer) {
        this.map.getPlayer().setHealth(currentPlayer.getHealth());
        this.map.getPlayer().setDamage(currentPlayer.getDamage());
        this.map.getPlayer().setDefense(currentPlayer.getDefense());
        this.map.getPlayer().setLevel(currentPlayer.getLevel());
        this.map.getPlayer().setXp(currentPlayer.getXp());
        this.map.getPlayer().setWcIsUsed(currentPlayer.getWcIsUsed());
        this.map.getPlayer().inventoryMap = currentPlayer.getInventoryMap();
        this.map.getPlayer().setTileName();

        setStageSize();
    }

    private void setMapName(String mapName) {
        this.mapName = mapName;
    }

    private void checkIfMonsterIsDead(){
        try {
            this.map.getSkeleton().monsterIsDead();
            this.map.getGhost().monsterIsDead();
        } catch (Exception ignored){

        }
    }

}
