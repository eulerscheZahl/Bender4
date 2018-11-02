package Bender4;

import java.util.ArrayList;

public class Board {
    public int width;
    public int height;
    public Cell target;
    public Cell[][] grid;

    public ArrayList<Switch> switches = new ArrayList<>();
    public ArrayList<Box> boxes = new ArrayList<>();

    public Board(int width, int height, String[] grid) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.grid[x][y] = new Cell(x, y, grid[y].charAt(x));
                if (this.grid[x][y].hasBox())
                    boxes.add(this.grid[x][y].box);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int dir = 0; dir < Cell.directions.length; dir++) {
                    int x_ = x + Cell.dx[dir];
                    int y_ = y + Cell.dy[dir];
                    if (x_ < 0 || x_ >= width || y_ < 0 || y_ >= height) continue;
                    this.grid[x][y].neighbors[dir] = this.grid[x_][y_];
                }
            }
        }
    }
}