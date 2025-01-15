package org.snakeinc.snake.model;

import lombok.Data;

@Data
public class StatBody {
    private String snake;
    private String difficulty;
    private Integer max;
    private Integer min;
    private Integer average;
}
