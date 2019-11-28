package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Helmet extends Item{
    public Helmet(Cell cell){
        super(cell);
    }

    public String getTileName() {
        return "cloak";
    }
}
