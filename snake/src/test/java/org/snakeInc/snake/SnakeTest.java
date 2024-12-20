package org.snakeInc.snake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.snakeinc.snake.model.Anaconda;
import org.snakeinc.snake.model.Apple;
import org.snakeinc.snake.model.Aliment;
import org.snakeinc.snake.model.Python;
import org.snakeinc.snake.model.Snake;
import org.snakeinc.snake.model.Snake.Direction;

public class SnakeTest {

    @Test
    public void snakeEatApples_ReturnsCorrectBodySize() {
        Snake snake = new Python();
        Aliment apple = new Apple();
        apple.beEatenBy(snake);
        Assertions.assertEquals(2, snake.getBody().size());

    }

    @Test void snakeMovesUp_ReturnCorrectHead() {
        // Snake snake = new Python();
        // snake.move(Direction.RIGHT);
        // Assertions.assertEquals(5, snake.getHead().getX());
        // Assertions.assertEquals(4, snake.getHead().getY());
    }

}
