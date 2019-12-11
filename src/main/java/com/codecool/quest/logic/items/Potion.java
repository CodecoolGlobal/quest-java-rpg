package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Potion extends Item{
    public Potion(Cell cell){
        super(cell);
    }

    public Potion() {}

    public String getTileName() {
        return "potion";
    }
}
