package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.items.Item;
import javafx.scene.control.ListView;

import java.util.HashMap;
import java.util.Map;

public class Player extends Actor {
    ListView<String> inventory = new ListView<>();
    Map<String, Integer> inventoryMap = new HashMap<>();

    private String itemName;
    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }
}

