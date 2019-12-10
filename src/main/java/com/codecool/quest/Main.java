package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Actor;
import javafx.application.Application;
import javafx.geometry.Insets;
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
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label damageLabel = new Label();
    Label defenseLabel = new Label();
    ListView<String> inventory = new ListView<>();

    public static Label nameLabel = new Label();
    public static String cheatCode = "Cinci";


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        inventory.setFocusTraversable(false);

        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.add(healthLabel, 0, 1);
        ui.add(damageLabel, 0, 2);
        ui.add(defenseLabel, 0, 3);
        ui.add(new Label("Inventory:"), 0, 5);
        ui.add(inventory, 0, 6);
        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
        getPlayerName();
        String characterName = "Name: " + nameLabel.getText();
        ui.add(new Label(characterName),0,0);
        refresh();
    }

    public void getPlayerName(){
        try {
            TextInputDialog dialog = new TextInputDialog("Bob");

            dialog.setTitle("Greetings Adventurer");
            dialog.setHeaderText("What's your name?");
            dialog.setContentText("Name:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(name -> {
                this.nameLabel.setText(name);
            });
        } catch (NullPointerException e){
            System.out.println("Wrong character name");
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                if (map.getPlayer().getHealth() < 1) {
                    break;
                }
                moveAllMonsters(map.monsterList); //rearrange monsterList to MapLoader??
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                if (map.getPlayer().getHealth() < 1) {
                    break;
                }
                moveAllMonsters(map.monsterList);
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                if (map.getPlayer().getHealth() < 1) {
                    break;
                }
                moveAllMonsters(map.monsterList);
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                if (map.getPlayer().getHealth() < 1) {
                    break;
                }
                moveAllMonsters(map.monsterList);
                map.getPlayer().move(1, 0);
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


    //Skeleton method
    private void moveAllMonsters(List monsterList) {
        Random random = new Random();

        for (int i = 0; i < monsterList.size(); i++) {
            int randomNumber = random.nextInt(2);
            if (randomNumber == 1) {
                Actor monster = (Actor) monsterList.get(i);
                monster.monsterMoveDirection();
            }
        }
    }



}
