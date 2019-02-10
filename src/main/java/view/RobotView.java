package view;

import java.util.HashMap;
import java.util.Map;

import com.codingame.game.Referee;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;

import Bender4.Cell;
import Bender4.Robot;
import modules.TooltipModule;

public class RobotView {
	private Robot robot;

	private SpriteAnimation sprite;
	private SpriteAnimation spark;
	private String[] spriteSheet;
	private GraphicEntityModule graphics;
	private int lastDir = -1;

	public RobotView(Group boardGroup, Robot robot, GraphicEntityModule graphics, TooltipModule tooltip) {
		this.robot = robot;
		robot.view = this;
		this.graphics = graphics;

		spriteSheet = Utils.loadSheet(graphics, "bender.png", BoardView.CELL_SIZE, BoardView.CELL_SIZE, 8, 4);
		String[] sparkSheet = Utils.loadSheet(graphics, "spark.png", BoardView.CELL_SIZE, BoardView.CELL_SIZE, 2, 1);

		sprite = Utils.createAnimation(graphics, new String[] { spriteSheet[1], spriteSheet[5] }).setX(BoardView.CELL_SIZE * robot.cell.x).setY(BoardView.CELL_SIZE * robot.cell.y);

		spark = Utils.createAnimation(graphics, sparkSheet).setDuration(Referee.FRAME_DURATION / 4).setAlpha(0);

		boardGroup.add(sprite);
		boardGroup.add(spark);

		Map<String, Object> params = new HashMap<>();
		params.put("Type", "Bender");
		tooltip.registerEntity(sprite, params);
	}

	private boolean dead = false;

	public void kill() {
		dead = true;
		String[] fieldDeathSheet = Utils.loadSheet(graphics, "benderDeath.png", BoardView.CELL_SIZE, BoardView.CELL_SIZE, 6, 6);
		sprite.setImages(fieldDeathSheet).play();
		graphics.commitEntityState(0, sprite);
		sprite.setLoop(false);
	}

	public void win() {
		String[] victorySheet = Utils.loadSheet(graphics, "benderVictory.png", BoardView.CELL_SIZE, BoardView.CELL_SIZE, 12, 4);
		sprite.setX(sprite.getX() + 14);
		graphics.commitEntityState(0.1, sprite);
		sprite.setImages(victorySheet).setDuration(Referee.FRAME_DURATION * 4).pause().setLoop(false);
		graphics.commitEntityState(0.101, sprite);
		sprite.reset().play();
		graphics.commitEntityState(0.102, sprite);
	}

	public void move() {
		if (dead)
			return;
		if (robot.direction != lastDir) {
			sprite.setImages(new String[] { spriteSheet[robot.direction], spriteSheet[robot.direction + 4] });
			graphics.commitEntityState(0, sprite);
			lastDir = robot.direction;
		}
		if (robot.hitWall) {
			int dx = Cell.dx[robot.direction] * 20;
			int dy = Cell.dy[robot.direction] * 5;
			sprite.setX(BoardView.CELL_SIZE * robot.cell.x + dx).setY(BoardView.CELL_SIZE * robot.cell.y + dy);
			graphics.commitEntityState(0.5, sprite);

			dx = Cell.dx[robot.direction] * 25;
			dy = Cell.dy[robot.direction] * 25;
			spark.setX(BoardView.CELL_SIZE * robot.cell.x + dx).setY(BoardView.CELL_SIZE * robot.cell.y + dy);
			graphics.commitEntityState(0, spark);
			spark.setAlpha(1);
			graphics.commitEntityState(0.5, spark);
			spark.setAlpha(0);
			graphics.commitEntityState(0.75, spark);
		}
		sprite.setX(BoardView.CELL_SIZE * robot.cell.x).setY(BoardView.CELL_SIZE * robot.cell.y);
	}
}
