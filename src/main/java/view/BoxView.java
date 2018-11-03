package view;

import Bender4.Board;
import Bender4.Box;
import Bender4.Cell;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;

public class BoxView {
    private Box box;
    private Sprite sprite;
    private Board board;
    private Group boardGroup;
    private Cell location;
    private GraphicEntityModule graphics;

    public BoxView(Board board, Group boardGroup, Box box, GraphicEntityModule graphics) {
        this.box = box;
        box.view = this;
        location = box.cell;
        this.graphics = graphics;
        this.board = board;
        this.boardGroup = boardGroup;

        sprite = graphics.createSprite()
                .setX(box.cell.x * BoardView.CELL_SIZE + BoardView.CELL_SIZE / 2)
                .setY(box.cell.y * BoardView.CELL_SIZE + BoardView.CELL_SIZE / 2)
                .setAnchor(0.5);
        sprite.setImage("Garbage Ball.png");
        boardGroup.add(sprite);
    }

    public void update() {
        if (board.target == box.cell) {
            Sprite death = graphics.createSprite().setImage("FryDead.png")
                    .setX(box.cell.x * BoardView.CELL_SIZE)
                    .setY(box.cell.y * BoardView.CELL_SIZE)
                    .setAlpha(0);
            boardGroup.add(death);
            graphics.commitEntityState(0.7, death);
            death.setAlpha(1);
        }

        sprite.setAlpha(0.9999); // minimal change to force the commit
        graphics.commitEntityState(0.35, sprite);
        if (location.x < box.cell.x) sprite.setRotation(sprite.getRotation() + Math.PI / 2);
        if (location.x > box.cell.x) sprite.setRotation(sprite.getRotation() - Math.PI / 2);

        sprite.setX(box.cell.x * BoardView.CELL_SIZE + BoardView.CELL_SIZE / 2)
                .setY(box.cell.y * BoardView.CELL_SIZE + BoardView.CELL_SIZE / 2);
        sprite.setAlpha(1);

        location = box.cell;
    }
}