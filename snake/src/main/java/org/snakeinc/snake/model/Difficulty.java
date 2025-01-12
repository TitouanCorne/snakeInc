package org.snakeinc.snake.model;

import lombok.Data;

@Data
public class Difficulty {
    public enum Mode {
        EASY, NORMAL, HARD;
    }

    private Mode mode;

    public Difficulty(Mode mode) {
        this.mode = mode;
    }

}
