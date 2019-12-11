package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Gold extends Item{
    public Gold(Cell cell){
        super(cell);
    }

    public Gold() {}

    public String getTileName() {
        return "gold";
    }
}
