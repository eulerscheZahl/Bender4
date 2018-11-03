package view;

import Bender4.Box;
import Bender4.Cell;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;

public class BoxView {
    private Box box;
    private Sprite sprite;
    private Cell location;

    public BoxView(Group boardGroup, Box box, GraphicEntityModule graphics) {
        this.box = box;
        box.view = this;
        location = box.cell;

        sprite = graphics.createSprite()
                .setX(box.cell.x * BoardView.CELL_SIZE + BoardView.CELL_SIZE / 2)
                .setY(box.cell.y * BoardView.CELL_SIZE + BoardView.CELL_SIZE / 2)
                .setAnchor(0.5);
        sprite.setImage("Garbage Ball.png");
        boardGroup.add(sprite);
    }

    public void update() {
        if (location.x < box.cell.x) sprite.setRotation(sprite.getRotation() + Math.PI / 2);
        if (location.x > box.cell.x) sprite.setRotation(sprite.getRotation() - Math.PI / 2);

        sprite.setX(box.cell.x * BoardView.CELL_SIZE + BoardView.CELL_SIZE / 2)
                .setY(box.cell.y * BoardView.CELL_SIZE + BoardView.CELL_SIZE / 2);

        location = box.cell;
    }
}
