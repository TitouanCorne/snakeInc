package org.snakeinc.server.entity;

import lombok.Data;

@Data
public class ScoreBody {
    String snake;
    Integer score;
    String difficulty;
}
