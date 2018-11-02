package Bender4;

import view.RobotView;

public class Robot {
    public Cell cell;
    public RobotView view;
    public int direction = 0;

    public Robot(Board board, int x, int y) {
        this.cell = board.grid[x][y];
    }

    public void move(char execute) {
        Cell next = cell;
        int dir = -1;
        for (int i = 0; i < Cell.directions.length; i++) {
            if (Cell.directions[i] == execute) {
                dir = i;
                direction = dir;
            }
        }
        if (dir != -1) next = cell.neighbors[dir];
        if (next.isWall || next.hasBox() && !next.box.canPush(cell)) {
            view.move();
            return;
        }
        if (next != cell && next.hasSwitch()) {
            Switch sw = next.sw;
            if (sw.switchPos == next) sw.toggle();
        }
        if (next.hasBox()) {
            next.box.push(cell);
        }

        cell = next;
        view.move();
    }
}