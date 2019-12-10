package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public interface Monster {

    static void monsterMove(Actor monster, int dx, int dy) {
        try {
            if (monster.isPassable(dx, dy) && monster.getHealth() > 0) {
                Cell nextCell = monster.cell.getNeighbor(dx, dy);
                monster.cell.setActor(null);
                nextCell.setActor(monster);
                monster.cell = nextCell;

            }
        } catch (NullPointerException ignored) {

        }
    }

    static void monsterMoveDirection(Actor monster) {
        int[] actualMove = monster.movementCoordinates.get(monster.rnd.nextInt(monster.movementCoordinates.size()));
        int x = actualMove[0];
        int y = actualMove[1];
        if (monster.getHealth() > 0) monsterMove(monster, x, y);
            /*} catch (InterruptedException e) {
                e.printStackTrace();
            }*/
    }
}
