package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Monster extends Actor {

    private boolean isDead;
    private List<Item> items = new ArrayList<>();

    public Monster(Cell cell) {
        super(cell);
        isDead = false;
        fillItemList();
    }

    private void fillItemList(){
        items.add(new Gold());
        items.add(new Cloak());
        items.add(new Weapon());
    }

    private List<Item> getItemList (){
        return items;
    }

    public void monsterIsDead(){
        if (this.getHealth() <= 0) {
            Random random = new Random();
            int randomNumber = random.nextInt(5);
            if (randomNumber == 3 && !this.isDead) {
                int randomItemIndex = random.nextInt(getItemList().size());
                dropItem(getItemList().get(randomItemIndex));
            }
            this.isDead = true;
        }
    }

    public void monsterMoveDirection() {
        boolean isBoss = this.getCell().getActor().getTileName().equals("boss");
        int[] actualMove = movementCoordinates.get(rnd.nextInt(movementCoordinates.size()));
        int x = actualMove[0];
        int y = actualMove[1];
        if (getHealth() > 0 && !isBoss) {
            monsterMove(x, y);
        }
        if(this.getCell().getActor() instanceof Boss && this.getHealth() > 0){
            ((Boss) this.getCell().getActor()).bossMove();
        }
    }

    private void monsterMove(int dx, int dy) {

        try {
            Boolean isDoor = this.getCell().getTileName().equals("village-door");
            if (isPassable(dx, dy) && getHealth() > 0) {
                Cell nextCell = cell.getNeighbor(dx, dy);
                if (!nextCell.getTileName().equals("village-door")) {
                    cell.setActor(null);
                    nextCell.setActor(this);
                    cell = nextCell;
                }
            }
        } catch (NullPointerException ignored) {

        }
    }

    private void dropItem(Item item){
        this.getCell().setActor(null);
        this.getCell().setItem(item);
    }
}
