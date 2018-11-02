package Bender4;

public class Cell {
    public int x;
    public int y;
    public boolean isWall;
    public Box box;
    public Cell[] neighbors = new Cell[4];
    public static final char[] directions = {'U', 'D', 'R', 'L'};
    public static final int[] dx = {0, 0, 1, -1};
    public static final int[] dy = {-1, 1, 0, 0};
    public Switch sw;

    public Cell(int x, int y, char c) {
        this.x = x;
        this.y = y;
        isWall = c == '#';
        if (c == '+') box = new Box(this);
    }

    public boolean isFree() {
        return !isWall && !hasBox();
    }

    public boolean hasBox() {
        return box != null;
    }

    public boolean hasSwitch() {
        return sw != null;
    }
}
