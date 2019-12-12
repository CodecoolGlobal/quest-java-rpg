package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.items.Crown;

public class Boss extends Monster{
    public boolean isBossTransformed = this.getCell().getActor().getTileName().equals("boss2");
    public boolean isBossDead = this.getCell().getActor().getHealth() <= 0;
    public boolean isBossLowHP(){
      if (this.getCell().getActor().getHealth() <= 5){
          return true;
      }else{
          return false;
      }
    }

    public Boss(Cell cell) {
        super(cell);
        this.setDamage(1);
        this.setHealth(31);
    }
    @Override
    public String getTileName() {
        if (isBossLowHP()) {
            return "boss2";

        }
        return "boss";
    }


    public void monsterMove(){

    }
}
