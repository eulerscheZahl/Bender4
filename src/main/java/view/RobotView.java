package view;

import Bender4.Board;
import Bender4.Robot;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;

public class RobotView {
    private Robot robot;

    private Sprite sprite;

    public RobotView(Group boardGroup, Robot robot, GraphicEntityModule graphics) {
        this.robot = robot;
        robot.view = this;

        sprite = graphics.createSprite().setImage("robot.png")
                .setX(BoardView.CELL_SIZE * robot.cell.x)
                .setY(BoardView.CELL_SIZE * robot.cell.y);
        boardGroup.add(sprite);
    }

    public void move() {
        sprite.setX(BoardView.CELL_SIZE * robot.cell.x)
                .setY(BoardView.CELL_SIZE * robot.cell.y);
    }
}
