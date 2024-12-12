package org.snakeinc.snake.model;

public class Broccoli extends Aliment {
    public Broccoli(){
        super();
        setColor(color.green);
    }

    @Override
    public void beEatenBy(AlimentEater alimentEater) {
        alimentEater.eat(this);
    }
}
