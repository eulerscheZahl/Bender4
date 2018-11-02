package view;

import Bender4.Board;
import Bender4.Cell;
import com.codingame.gameengine.module.entities.*;

public class BoardView {
    private Board board;
    public static final int CELL_SIZE = 48;
    public Group boardGroup;
    public Group functionsGroup;
    private static String[] wallSprites;

    public BoardView(Board board, GraphicEntityModule graphics) {
        this.board = board;
        wallSprites = graphics.createSpriteSheetLoader()
                .setSourceImage("walls.png")
                .setName("w")
                .setWidth(CELL_SIZE)
                .setHeight(CELL_SIZE)
                .setImageCount(16)
                .setImagesPerRow(4)
                .setOrigCol(0)
                .setOrigRow(0)
                .load();

        boardGroup = graphics.createGroup();
        boardGroup.setScale(1080.0 / (board.height * CELL_SIZE));
        functionsGroup = graphics.createGroup().setX(1120);
        BufferedGroup gridGroup = graphics.createBufferedGroup();
        boardGroup.add(gridGroup);
        Group innerGroup = graphics.createGroup();
        gridGroup.add(innerGroup);
        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                Sprite sprite = graphics.createSprite().setX(x * CELL_SIZE).setY(y * CELL_SIZE);
                if (board.grid[x][y].isWall) sprite.setImage(selectWall(board.grid, board.width, board.height, x, y));
                else sprite.setImage("floor.png");
                innerGroup.add(sprite);
            }
        }
        Sprite target = graphics.createSprite().setImage("target.png")
                .setX(board.target.x * CELL_SIZE)
                .setY(board.target.y * CELL_SIZE);
        boardGroup.add(target);
    }

    private static String selectWall(Cell[][] grid, int width, int height, int x, int y) {
        int index = 0;
        if (y > 0 && grid[x][y - 1].isWall) index += 1;
        if (y + 1 < height && grid[x][y + 1].isWall) index += 2;
        if (x > 0 && grid[x - 1][y].isWall) index += 8;
        if (x + 1 < width && grid[x + 1][y].isWall) index += 4;
        return wallSprites[index];
    }
}
