package view;

import Bender4.Board;
import Bender4.Box;
import Bender4.Cell;
import com.codingame.game.Referee;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.SpriteAnimation;
import modules.TooltipModule;

import java.util.HashMap;
import java.util.Map;

public class BoxView {
    private Box box;
    private Sprite sprite;
    private Board board;
    private Group boardGroup;
    private Cell location;
    private GraphicEntityModule graphics;
    private TooltipModule tooltip;

    public BoxView(Board board, Group boardGroup, Box box, GraphicEntityModule graphics, TooltipModule tooltip) {
        this.box = box;
        box.view = this;
        location = box.cell;
        this.graphics = graphics;
        this.board = board;
        this.boardGroup = boardGroup;
        this.tooltip = tooltip;

        sprite = graphics.createSprite()
                .setX(box.cell.x * BoardView.CELL_SIZE + BoardView.CELL_SIZE / 2)
                .setY(box.cell.y * BoardView.CELL_SIZE + BoardView.CELL_SIZE / 2)
                .setAnchor(0.5);
        sprite.setImage("Garbage Ball.png");
        boardGroup.add(sprite);

        Map<String, Object> params = new HashMap<>();
        params.put("Type", "Garbage ball");
        tooltip.registerEntity(sprite, params);
        tooltip.updateExtraTooltipText(sprite, "X: " + box.cell.x + "\nY: " + box.cell.y);
    }

    public void update() {
        if (board.target == box.cell) {
            String[] spriteSheet = Utils.loadSheet(graphics, "FryDead.png", BoardView.CELL_SIZE + 1, BoardView.CELL_SIZE, 2, 2);

            SpriteAnimation death = Utils.createAnimation(graphics, spriteSheet)
                    .setX(box.cell.x * BoardView.CELL_SIZE)
                    .setY(box.cell.y * BoardView.CELL_SIZE)
                    .setLoop(false);

            boardGroup.add(death);
            death.setAlpha(0);
            graphics.commitEntityState(0.9, boardGroup, death);
            death.setAlpha(1);
            graphics.commitEntityState(1, death);
        }

        sprite.setAlpha(0.9999); // minimal change to force the commit
        graphics.commitEntityState(0.35, sprite);
        if (location.x < box.cell.x) sprite.setRotation(sprite.getRotation() + Math.PI / 2);
        if (location.x > box.cell.x) sprite.setRotation(sprite.getRotation() - Math.PI / 2);

        sprite.setX(box.cell.x * BoardView.CELL_SIZE + BoardView.CELL_SIZE / 2)
                .setY(box.cell.y * BoardView.CELL_SIZE + BoardView.CELL_SIZE / 2);
        sprite.setAlpha(1);
        graphics.commitEntityState(0.9, sprite);

        location = box.cell;

        tooltip.updateExtraTooltipText(sprite, "X: " + box.cell.x + "\nY: " + box.cell.y);
    }
}