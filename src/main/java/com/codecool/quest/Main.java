package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Skeleton;
import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.List;
import java.util.Map;
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


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        inventory.setFocusTraversable(false);

        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(healthLabel, 0, 0);
        ui.add(damageLabel, 0, 1);
        ui.add(defenseLabel, 0, 2);
        ui.add(new Label(""), 0, 3);
        ui.add(new Label("Inventory:"), 0, 4);
        ui.add(inventory, 0, 5);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();

    }


    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                moveAllSkeletons(map.skeletonList);
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                moveAllSkeletons(map.skeletonList);
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                moveAllSkeletons(map.skeletonList);
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                moveAllSkeletons(map.skeletonList);
                map.getPlayer().move(1, 0);
                refresh();
                break;
            case A:
                moveAllSkeletons(map.skeletonList);
                map.getPlayer().pickUpItem();
                refresh();
        }

    }

    private void refresh() {
        fillInventory();
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

    private void fillInventory() {
        inventory.getItems().clear();
        Map<String, Integer> inventoryMap = map.getPlayer().getInventoryMap();
        for (Map.Entry<String, Integer> entry : inventoryMap.entrySet()) {
            inventory.getItems().add(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void moveAllSkeletons(List skeletonList) {
        Random random = new Random();

        for (int i = 0; i < skeletonList.size(); i++) {
            int randomNumber = random.nextInt(2);
            if (randomNumber == 1) {
                Skeleton skeleton = (Skeleton) skeletonList.get(i);
                skeleton.monsterMove();
            }
        }
    }


}
