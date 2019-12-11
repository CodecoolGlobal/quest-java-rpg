package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Crown extends Item{
    public Crown(Cell cell){ //re-name class - Cloak
        super(cell);
    }

    public String getTileName() {
        return "crown";
    }
}
