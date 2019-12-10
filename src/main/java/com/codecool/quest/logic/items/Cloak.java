package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Cloak extends Item{
    public Cloak(Cell cell){ //re-name class - Cloak
        super(cell);
    }

    public String getTileName() {
        return "cloak";
    }
}
