package org.snakeinc.server.entity;

import lombok.Data;

@Data
public class StatBody {
    String snake;
    String difficulty;
    Integer max;
    Integer min;
    Integer average; 
}
