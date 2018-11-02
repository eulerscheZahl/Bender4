package view;

import Bender4.Board;
import Bender4.Switch;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;

public class SwitchView {
    private Switch sw;
    private Board board;
    private GraphicEntityModule graphics;
    private Sprite switchSprite;
    private Sprite blockSprite;
    private static final int rows = 12;

    private static String[] spriteSheet;

    public SwitchView(Board board, Group boardGroup, Switch sw, GraphicEntityModule graphics) {
        this.board = board;
        this.sw = sw;
        sw.view = this;

        if (spriteSheet == null) {
            spriteSheet = graphics.createSpriteSheetLoader()
                    .setSourceImage("switches.png")
                    .setName("s")
                    .setWidth(24)
                    .setHeight(24)
                    .setImageCount(3 * rows)
                    .setImagesPerRow(rows)
                    .setOrigCol(0)
                    .setOrigRow(0)
                    .load();
        }
        switchSprite = graphics.createSprite().setImage(spriteSheet[sw.switchID])
                .setX(BoardView.CELL_SIZE * sw.switchPos.x)
                .setY(BoardView.CELL_SIZE * sw.switchPos.y);
        blockSprite = graphics.createSprite().setImage(spriteSheet[sw.switchID + rows * (sw.isBlocking ? 1 : 2)])
                .setX(BoardView.CELL_SIZE * sw.blockingPos.x)
                .setY(BoardView.CELL_SIZE * sw.blockingPos.y);
        boardGroup.add(switchSprite);
        boardGroup.add(blockSprite);
    }

    public void toggle() {
        blockSprite.setImage(spriteSheet[sw.switchID + rows * (sw.isBlocking ? 1 : 2)]);
    }
}
