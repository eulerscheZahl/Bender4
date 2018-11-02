package com.codingame.game;
import com.codingame.gameengine.core.AbstractSoloPlayer;

public class Player extends AbstractSoloPlayer {

    int expectedOutput = 1;
    public void setExpectedOutputLines(int n) {
        expectedOutput = 0;
    }
    @Override
    public int getExpectedOutputLines() {
        return expectedOutput;
    }
}
