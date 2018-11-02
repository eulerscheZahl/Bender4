package view;

import Bender4.Cell;
import Bender4.Robot;
import com.codingame.game.Referee;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.SpriteAnimation;

public class RobotView {
    private Robot robot;

    private SpriteAnimation sprite;
    private SpriteAnimation spark;
    private String[] spriteSheet;
    private GraphicEntityModule graphics;
    private int lastDir = -1;

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

        String[] sparkSheet = graphics.createSpriteSheetLoader()
                .setSourceImage("spark.png")
                .setName("x")
                .setWidth(BoardView.CELL_SIZE)
                .setHeight(BoardView.CELL_SIZE)
                .setImageCount(2)
                .setImagesPerRow(1)
                .setOrigCol(0)
                .setOrigRow(0)
                .load();

        sprite = graphics.createSpriteAnimation()
                .setImages(new String[] {spriteSheet[1], spriteSheet[5]})
                .setX(BoardView.CELL_SIZE * robot.cell.x)
                .setY(BoardView.CELL_SIZE * robot.cell.y)
                .setDuration(Referee.FRAME_DURATION)
                .setLoop(true)
                .setStarted(true);

        spark = graphics.createSpriteAnimation()
                .setImages(sparkSheet)
                .setDuration(Referee.FRAME_DURATION / 4)
                .setLoop(true)
                .setStarted(true);
        spark.setAlpha(0);

        boardGroup.add(sprite);
        boardGroup.add(spark);
    }

    public void move() {
        if (robot.direction != lastDir) {
            sprite.setImages(new String[]{spriteSheet[robot.direction], spriteSheet[robot.direction + 4]});
            graphics.commitEntityState(0, sprite);
            lastDir = robot.direction;
        }
        if (robot.hitWall) {
            int dx = Cell.dx[robot.direction] * 20;
            int dy = Cell.dy[robot.direction] * 5;
            System.err.println("dx: " + dx + "   dy: " + dy);
            sprite.setX(BoardView.CELL_SIZE * robot.cell.x + dx)
                    .setY(BoardView.CELL_SIZE * robot.cell.y + dy);
            graphics.commitEntityState(0.5, sprite);

            dx = Cell.dx[robot.direction] * 25;
            dy = Cell.dy[robot.direction] * 25;
            spark.setX(BoardView.CELL_SIZE * robot.cell.x + dx)
                    .setY(BoardView.CELL_SIZE * robot.cell.y + dy);
            graphics.commitEntityState(0, spark);
            spark.setAlpha(1);
            graphics.commitEntityState(0.5, spark);
            spark.setAlpha(0);
            graphics.commitEntityState(0.75, spark);
        }
        sprite.setX(BoardView.CELL_SIZE * robot.cell.x)
                .setY(BoardView.CELL_SIZE * robot.cell.y);
    }
}
