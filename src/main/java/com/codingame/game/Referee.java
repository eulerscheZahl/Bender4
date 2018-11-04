package com.codingame.game;
import Bender4.*;
import com.codingame.gameengine.core.AbstractPlayer;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.SoloGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.google.inject.Inject;
import view.BoardView;
import view.BoxView;
import view.RobotView;
import view.SwitchView;

public class Referee extends AbstractReferee {
    // Uncomment the line below and comment the line under it to create a Solo Game
    @Inject
    private SoloGameManager<Player> gameManager;
    @Inject
    private GraphicEntityModule graphicEntityModule;

    private Board board;
    private Robot robot;
    private BoardView view;

    public static final int FRAME_DURATION = 500;

    @Override
    public void init() {
        gameManager.setFrameDuration(FRAME_DURATION);
        gameManager.setMaxTurns(1000);

        String[] input = gameManager.getTestCaseInput().get(0).split("\\|");
        int width = Integer.parseInt(input[0].split(" ")[0]);
        int height = Integer.parseInt(input[0].split(" ")[1]);
        String[] grid = new String[height];
        for (int y = 0; y < height; y++) {
            grid[y] = input[y + 1];
        }
        int startX = Integer.parseInt(input[height + 1].split(" ")[0]);
        int startY = Integer.parseInt(input[height + 1].split(" ")[1]);
        int targetX = Integer.parseInt(input[height + 2].split(" ")[0]);
        int targetY = Integer.parseInt(input[height + 2].split(" ")[1]);
        int switchCount = Integer.parseInt(input[height + 3]);

        board = new Board(width, height, grid);
        robot = new Robot(board, startX, startY);
        board.target = board.grid[targetX][targetY];
        for (int s = 0; s < switchCount; s++) {
            board.switches.add(new Switch(board, input[height + 4 + s], s));
        }

        view = new BoardView(board, graphicEntityModule);
        for (Switch s : board.switches) {
            new SwitchView(board, view.boardGroup, s, graphicEntityModule);
        }
        for (Box b : board.boxes) {
            new BoxView(board, view.boardGroup, b, graphicEntityModule);
        }
        new RobotView(view.boardGroup, robot, graphicEntityModule);
    }

    private Interpreter interpreter;
    private boolean finished = false;
    private String gameMessage = null;
    private int extend = 0;
    private boolean lost = false;

    @Override
    public void gameTurn(int turn) {
        if (gameMessage != null) {
            if (extend > 0) {
                extend--;
            } else {
                if (lost) gameManager.loseGame(gameMessage);
                else gameManager.winGame(gameMessage);
                return;
            }
        }
        if (gameMessage == null && finished) {
            gameMessage = "Bender reached Fry";
            extend = 1;
            gameManager.setFrameDuration(4 * FRAME_DURATION);
            robot.view.win();
            return;
        } else if (robot.cell.hasSwitch() && robot.cell.sw.isBlocking && robot.cell.sw.blockingPos == robot.cell) {
            if (gameMessage == null) {
                gameMessage = "Bender entered a magnetic field";
                extend = 1;
                lost = true;
                robot.view.kill();
            }
        } else if (board.target.hasBox()) {
            if (gameMessage == null) gameMessage = "Fry was squashed by a box";
            lost = true;
        }
        if (robot.cell == board.target) finished = true;
        Player player = gameManager.getPlayer();
        try {
            if (interpreter == null) {
                for (String s : gameManager.getTestCaseInput().get(0).split("\\|")) {
                    player.sendInputLine(s);
                    System.err.println(s);
                }
                player.execute();
                interpreter = new Interpreter(player.getOutputs().get(0), view.functionsGroup, graphicEntityModule);
                gameManager.putMetadata("Points", String.valueOf(player.getOutputs().get(0).length()));
            } else {
                player.setExpectedOutputLines(0);
                player.execute();
            }
        } catch (AbstractPlayer.TimeoutException ex) {
            ;
        }

        if (!interpreter.hasCommandsLeft) {
            gameManager.loseGame("Invalid path");
            return;
        }
        interpreter.step(robot, gameManager);
    }
}
