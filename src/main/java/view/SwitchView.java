package view;

import Bender4.Board;
import Bender4.Switch;
import com.codingame.game.Referee;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.SpriteAnimation;

public class SwitchView {
    private Switch sw;
    private Board board;
    private GraphicEntityModule graphics;
    private Sprite switchSprite;
    private SpriteAnimation fieldSprite;
    private static final int[] colorPalette = { 0xff0000, 0x00ff00, 0x0000ff, 0xff00ff, 0xffff00, 0x00ffff, 0xff8888, 0x88ff88, 0x8888ff, 0x000000, 0xffff88, 0xff88ff, 0x88ffff };

    private static String[] fieldSheet;
    private static String[] spriteSheet;

    public SwitchView(Board board, Group boardGroup, Switch sw, GraphicEntityModule graphics) {
        this.board = board;
        this.sw = sw;
        sw.view = this;

        if (spriteSheet == null) {
            fieldSheet = graphics.createSpriteSheetLoader()
                    .setSourceImage("coil.png")
                    .setName("s")
                    .setWidth(48)
                    .setHeight(48)
                    .setImageCount(4)
                    .setImagesPerRow(2)
                    .setOrigCol(0)
                    .setOrigRow(0)
                    .load();
            spriteSheet = graphics.createSpriteSheetLoader()
                    .setSourceImage("switch.png")
                    .setName("t")
                    .setWidth(48)
                    .setHeight(48)
                    .setImageCount(2)
                    .setImagesPerRow(1)
                    .setOrigCol(0)
                    .setOrigRow(0)
                    .load();
        }

        switchSprite = graphics.createSprite().setImage(spriteSheet[sw.isBlocking ? 1 : 0])
                .setX(BoardView.CELL_SIZE * sw.switchPos.x)
                .setY(BoardView.CELL_SIZE * sw.switchPos.y)
                .setTint(colorPalette[sw.switchID]);
        fieldSprite = graphics.createSpriteAnimation()
                .setImages(new String[] {fieldSheet[0], fieldSheet[1]})
                .setX(BoardView.CELL_SIZE * sw.blockingPos.x)
                .setY(BoardView.CELL_SIZE * sw.blockingPos.y)
                .setTint(colorPalette[sw.switchID])
                .setDuration(Referee.FRAME_DURATION)
                .setLoop(true)
                .setStarted(true);
        Sprite coilFoot = graphics.createSprite().setImage(fieldSheet[3])
                .setX(BoardView.CELL_SIZE * sw.blockingPos.x)
                .setY(BoardView.CELL_SIZE * sw.blockingPos.y)
                .setTint(colorPalette[sw.switchID]);
        Sprite coilHead = graphics.createSprite().setImage(fieldSheet[2])
                .setX(BoardView.CELL_SIZE * sw.blockingPos.x)
                .setY(BoardView.CELL_SIZE * sw.blockingPos.y);

        boardGroup.add(switchSprite);
        boardGroup.add(fieldSprite);
        boardGroup.add(coilFoot);
        boardGroup.add(coilHead);

        fieldSprite.setAlpha(sw.isBlocking ? 1 : 0);
    }

    public void toggle() {
        switchSprite.setImage(spriteSheet[sw.isBlocking ? 1 : 0]);
        fieldSprite.setAlpha(sw.isBlocking ? 1 : 0);
    }
}
