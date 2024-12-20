package org.snakeinc.snake.model;

public class Apple extends Aliment {
    public Apple(){
        super();
        setColor(color.RED);
    }

    @Override
    public void beEatenBy(AlimentEater alimentEater) {
        alimentEater.eat(this);
    }

    

    
}
