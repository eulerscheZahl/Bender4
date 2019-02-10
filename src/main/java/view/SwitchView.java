package view;

import java.util.HashMap;
import java.util.Map;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.SpriteAnimation;

import Bender4.Board;
import Bender4.Switch;
import modules.TooltipModule;

public class SwitchView {
	private Switch sw;
	private Board board;
	private TooltipModule tooltip;
	private GraphicEntityModule graphics;
	private Sprite coilFoot;
	private Sprite switchSprite;
	private SpriteAnimation fieldSprite;
	private static final int[] colorPalette = { 0xff0000, 0x00ff00, 0x0000ff, 0xff00ff, 0xffff00, 0x00ffff, 0xff8888, 0x88ff88, 0x8888ff, 0x000000, 0xffff88, 0xff88ff, 0x88ffff };

	private static String[] fieldSheet;
	private static String[] spriteSheet;

	public SwitchView(Board board, Group boardGroup, Switch sw, GraphicEntityModule graphics, TooltipModule tooltip) {
		this.board = board;
		this.sw = sw;
		this.tooltip = tooltip;
		sw.view = this;

		fieldSheet = Utils.loadSheet(graphics, "coil.png", "a", BoardView.CELL_SIZE, BoardView.CELL_SIZE, 4, 2);
		spriteSheet = Utils.loadSheet(graphics, "switch.png", "b", BoardView.CELL_SIZE, BoardView.CELL_SIZE, 2, 1);

		switchSprite = Utils.createBoardSprite(graphics, spriteSheet[sw.isBlocking ? 1 : 0], sw.switchPos.x, sw.switchPos.y).setTint(colorPalette[sw.switchID]);
		coilFoot = Utils.createBoardSprite(graphics, fieldSheet[3], sw.blockingPos.x, sw.blockingPos.y).setTint(colorPalette[sw.switchID]);
		Sprite coilHead = Utils.createBoardSprite(graphics, fieldSheet[2], sw.blockingPos.x, sw.blockingPos.y);
		fieldSprite = Utils.createAnimation(graphics, new String[] { fieldSheet[0], fieldSheet[1] }).setX(BoardView.CELL_SIZE * sw.blockingPos.x).setY(BoardView.CELL_SIZE * sw.blockingPos.y).setTint(colorPalette[sw.switchID]);

		boardGroup.add(switchSprite);
		boardGroup.add(coilFoot);
		boardGroup.add(coilHead);
		boardGroup.add(fieldSprite);

		fieldSprite.setAlpha(sw.isBlocking ? 1 : 0);

		Map<String, Object> params = new HashMap<>();
		params.put("Type", "Switch");
		params.put("ID", String.valueOf(sw.switchID));
		tooltip.registerEntity(switchSprite, params);

		params = new HashMap<>();
		params.put("Type", "Field");
		params.put("ID", String.valueOf(sw.switchID));
		tooltip.registerEntity(coilFoot, params);

		updateTooltipState();
	}

	private void updateTooltipState() {
		tooltip.updateExtraTooltipText(switchSprite, "state: " + (sw.isBlocking ? "on" : "off"));
		tooltip.updateExtraTooltipText(coilFoot, "state: " + (sw.isBlocking ? "on" : "off"));
	}

	public void toggle() {
		switchSprite.setImage(spriteSheet[sw.isBlocking ? 1 : 0]);
		fieldSprite.setAlpha(sw.isBlocking ? 1 : 0);
		updateTooltipState();
	}
}
