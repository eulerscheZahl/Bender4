package view;

import Bender4.Robot;
import com.codingame.game.Referee;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;

public class RobotView {
    private Robot robot;

    private SpriteAnimation sprite;
    private String[] spriteSheet;
    private GraphicEntityModule graphics;

    public RobotView(Group boardGroup, Robot robot, GraphicEntityModule graphics) {
        this.robot = robot;
        robot.view = this;
        this.graphics = graphics;

        spriteSheet = graphics.createSpriteSheetLoader()
                .setSourceImage("bender.png")
                .setName("b")
                .setWidth(BoardView.CELL_SIZE)
                .setHeight(BoardView.CELL_SIZE)
                .setImageCount(8)
                .setImagesPerRow(4)
                .setOrigCol(0)
                .setOrigRow(0)
                .load();

        sprite = graphics.createSpriteAnimation()
                .setImages(new String[] {spriteSheet[0], spriteSheet[4]})
                .setX(BoardView.CELL_SIZE * robot.cell.x)
                .setY(BoardView.CELL_SIZE * robot.cell.y)
                .setDuration(Referee.FRAME_DURATION)
                .setLoop(true)
                .setStarted(true);
        boardGroup.add(sprite);
    }

    public void move() {
        sprite.setImages(new String[] {spriteSheet[robot.direction], spriteSheet[robot.direction + 4]});
        graphics.commitEntityState(0, sprite);
        sprite.setX(BoardView.CELL_SIZE * robot.cell.x)
                .setY(BoardView.CELL_SIZE * robot.cell.y);
    }
}
