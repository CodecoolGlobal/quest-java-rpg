package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Pet extends Item{
    public Pet(Cell cell){
        super(cell);
    }

    public String getTileName() {
        return "npc-pet";
    }
}
