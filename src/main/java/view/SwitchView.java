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

        fieldSheet = Utils.loadSheet(graphics, "coil.png", BoardView.CELL_SIZE, BoardView.CELL_SIZE, 4, 2);
        spriteSheet = Utils.loadSheet(graphics, "switch.png", BoardView.CELL_SIZE, BoardView.CELL_SIZE, 2, 1);

        switchSprite = Utils.createBoardSprite(graphics, spriteSheet[sw.isBlocking ? 1 : 0], sw.switchPos.x, sw.switchPos.y)
                .setTint(colorPalette[sw.switchID]);
        fieldSprite = Utils.createAnimation(graphics, new String[] {fieldSheet[0], fieldSheet[1]})
                .setX(BoardView.CELL_SIZE * sw.blockingPos.x)
                .setY(BoardView.CELL_SIZE * sw.blockingPos.y)
                .setTint(colorPalette[sw.switchID]);
        Sprite coilFoot = Utils.createBoardSprite(graphics, fieldSheet[3], sw.blockingPos.x, sw.blockingPos.y)
                .setTint(colorPalette[sw.switchID]);
        Sprite coilHead = Utils.createBoardSprite(graphics, fieldSheet[2], sw.blockingPos.x, sw.blockingPos.y);

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
