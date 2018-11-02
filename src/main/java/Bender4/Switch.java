package Bender4;

import view.SwitchView;

public class Switch {
    public Cell switchPos;
    public Cell blockingPos;
    public int switchID;
    public boolean isBlocking;
    public SwitchView view;

    public Switch(Board board, String input, int switchID) {
        String[] parts = input.split(" ");
        int switchX = Integer.parseInt(parts[0]);
        int switchY = Integer.parseInt(parts[1]);
        int blockingX = Integer.parseInt(parts[2]);
        int blockingY = Integer.parseInt(parts[3]);
        switchPos = board.grid[switchX][switchY];
        blockingPos = board.grid[blockingX][blockingY];
        isBlocking = Integer.parseInt(parts[4]) == 1;
        this.switchID = switchID;
        switchPos.sw = this;
        blockingPos.sw = this;
    }

    public void toggle() {
        isBlocking = !isBlocking;
        view.toggle();
    }
}
