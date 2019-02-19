package view;

import java.util.LinkedList;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.SpriteAnimation;

import Bender4.Interpreter;

public class FunctionView {
	private LinkedList<Sprite> actions = new LinkedList<>();
	private Group functionGroup;
	private Group bodyGroup;
	private Interpreter.FunctionExecution function;
	private GraphicEntityModule graphics;

	private static String[] spriteSheet;
	private static String[] functionSheet;
	private static final int ACTIONS_SHOWN = 11;
	private int offset;
	private static final int IMAGE_SIZE = 50;
	private SpriteAnimation frame;

	public FunctionView(Interpreter.FunctionExecution functionExecution, int depth, int name, Group functionsGroup, GraphicEntityModule graphics) {
		if (depth >= 7)
			return; // most likely infinite recursion, don't show that
		this.graphics = graphics;
		this.function = functionExecution;
		spriteSheet = Utils.loadSheet(graphics, "functions.png", "g", IMAGE_SIZE, IMAGE_SIZE, 14, 14);
		functionSheet = Utils.loadSheet(graphics, "functionbox.png", "h", 630, 88, 2, 1);

		frame = Utils.createAnimation(graphics, functionSheet).setX(50).setY(80 + 100 * depth).setZIndex(1);
		functionsGroup.add(frame);
		functionGroup = graphics.createGroup().setX(80).setY(100 + 100 * depth);
		functionsGroup.add(functionGroup);
		bodyGroup = graphics.createGroup().setX(100);
		functionGroup.add(bodyGroup);

		Sprite nameSprite = graphics.createSprite().setImage(spriteSheet[name]).setX(-1).setY(-1);
		functionGroup.add(nameSprite);

		while (actions.size() < ACTIONS_SHOWN && offset < function.function.length()) {
			Sprite sprite = graphics.createSprite();
			setImage(sprite, function.function.charAt(offset));
			sprite.setX((IMAGE_SIZE + 2) * offset - 1).setY(-1);
			bodyGroup.add(sprite);
			actions.add(sprite);
			offset++;
		}
	}

	private void setImage(Sprite sprite, char c) {
		if (c >= '1' && c <= '9')
			sprite.setImage(spriteSheet[c - '0']);
		if (c == 'U')
			sprite.setImage(spriteSheet[10]);
		if (c == 'L')
			sprite.setImage(spriteSheet[11]);
		if (c == 'D')
			sprite.setImage(spriteSheet[12]);
		if (c == 'R')
			sprite.setImage(spriteSheet[13]);
	}

	public boolean delay = false;

	public void step() {
		if (delay) {
			delay = false;
			return;
		}
		if (bodyGroup == null)
			return;
		bodyGroup.setX(bodyGroup.getX() - (IMAGE_SIZE + 2));
		Sprite action = actions.pollFirst();
		if (offset >= function.function.length()) {
			action.setAlpha(0);
		} else {
			action.setX((IMAGE_SIZE + 2) * offset - 1);
			graphics.commitEntityState(0, action);
			setImage(action, function.function.charAt(offset));
		}
		actions.addLast(action);
		offset++;
	}

	public void pause() {
		if (frame != null)
			frame.pause();
	}

	public void resume() {
		if (frame != null) {
			frame.play();
			graphics.commitEntityState(0, frame);
		}
	}

	public void terminate() {
		if (functionGroup != null)
			functionGroup.setAlpha(0);
		if (frame != null) {
			frame.setAlpha(0);
			graphics.commitEntityState(0, frame);
		}
	}
}
