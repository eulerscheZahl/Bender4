package view;

import Bender4.Box;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;

public class BoxView {
    private Box box;
    private Sprite sprite;
    private static final int rows = 12;

    public BoxView(Group boardGroup, Box box, GraphicEntityModule graphics) {
        this.box = box;
        box.view = this;

        sprite = graphics.createSprite()
                .setX(box.cell.x * BoardView.CELL_SIZE)
                .setY(box.cell.y * BoardView.CELL_SIZE);
        sprite.setImage("box.png");
        boardGroup.add(sprite);
    }

    public void update() {
        sprite.setX(box.cell.x * BoardView.CELL_SIZE)
                .setY(box.cell.y * BoardView.CELL_SIZE);
    }
}
