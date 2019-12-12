package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public abstract class Monster extends Actor {

    public Monster(Cell cell) {
        super(cell);
    }

    public void monsterMoveDirection() {

        int[] actualMove = movementCoordinates.get(rnd.nextInt(movementCoordinates.size()));
        int x = actualMove[0];
        int y = actualMove[1];
        if (getHealth() > 0) monsterMove(x, y);
            /*} catch (InterruptedException e) {
                e.printStackTrace();
            }*/
    }

    private void monsterMove(int dx, int dy) {

        try {
            Boolean isDoor = this.getCell().getTileName().equals("village-door");
            boolean isBoss = this.getCell().getActor().getTileName().equals("boss");
            if (isPassable(dx, dy) && getHealth() > 0 && !isBoss) {
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
}
