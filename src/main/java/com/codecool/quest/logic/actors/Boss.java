package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Boss extends Monster{
    public Boss(Cell cell) {
        super(cell);
        this.setDamage(5);
        this.setHealth(20);
    }
    @Override
    public String getTileName() {
        return "boss";
    }


    public void monsterMove(){

    };

}
