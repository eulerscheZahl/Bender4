package Bender4;

import view.BoxView;

public class Box {
    public Cell cell;
    public BoxView view;

    public Box(Cell cell) {
        this.cell = cell;
    }

    public void push(Cell from) {
        Cell to = getTargetCell(from);
        cell.box = null;
        to.box = this;
        this.cell = to;
        if (cell.hasSwitch() && cell == cell.sw.switchPos) {
            cell.sw.toggle();
        }
        view.update();
    }

    public boolean canPush(Cell from) {
        Cell to = getTargetCell(from);
        return to != null && to.isFree();
    }

    private Cell getTargetCell(Cell from) {
        int index = 0;
        while (from.neighbors[index] != cell) index++;
        return cell.neighbors[index];
    }
}
