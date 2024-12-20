package org.snakeinc.server.service;

import java.util.ArrayList;
import java.util.List;

import org.snakeinc.server.entity.Score;
import org.snakeinc.server.entity.ScoreBody;
import org.snakeinc.server.entity.StatBody;
import org.snakeinc.server.repository.ScoreRepository;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository){
        this.scoreRepository = scoreRepository;
    }

    public void postScore(ScoreBody requestBody){
        Score score = new Score();
        score.setScore(requestBody.getScore());
        score.setSnake(requestBody.getSnake());
        score.setDifficulty(requestBody.getDifficulty());
        scoreRepository.save(score);
    }


    private List<Score> getScoresBySnake(String snake, String difficulty){
        List<Score> scores = new ArrayList<>();
        for (Score score : scoreRepository.findAll()){
            if(score.getSnake().equals(snake) && score.getDifficulty().equals(difficulty))
            scores.add(score);
        }
        return scores;
    }
    
    public List<Score> getAllScores(){
        List<Score> scores = new ArrayList<>();
        for (Score score : scoreRepository.findAll()){
            scores.add(score);
        }
        return scores;
    }

    

    private Integer getMax(String snake, String difficulty){
        List<Score> scores = getScoresBySnake(snake, difficulty);
        if(scores.isEmpty()){
            return null;
        }
        else{
            Integer max = 0;
            for(Score score : scores){
                if(score.getScore() > max){
                    max = score.getScore();
                }
            }
            return max;
        }
    }

    
    private Integer getMin(String snake, String difficulty){
        List<Score> scores = getScoresBySnake(snake, difficulty);
        if(scores.isEmpty()){
            return null;
        }
        else{
            Integer min = scores.getFirst().getScore();
            for(Score score : scores){
                if(score.getScore() < min){
                    min = score.getScore();
                }
            }
            return min;
        }
    }

    
    private Integer getAverage(String snake, String difficulty){
        List<Score> scores = getScoresBySnake(snake, difficulty);
        if(scores.isEmpty()){
            return null;
        }
        else{
            Integer average = 0;
            for(Score score : scores){
                average =+ score.getScore();
            }
            return Math.round(average / scores.size());
        }
    }

    public StatBody getStatsBySnake(String snake, String difficulty){
        StatBody stat = new StatBody();
        stat.setSnake(snake);
        stat.setDifficulty(difficulty);
        stat.setMin(getMin(snake, difficulty));
        stat.setMax(getMax(snake, difficulty));
        stat.setAverage(getAverage(snake,difficulty));
        return stat;
    }

    public List<StatBody> getStats(String difficulty){
        List<StatBody> stats = new ArrayList<>();
        stats.add(getStatsBySnake("anaconda", difficulty));
        stats.add(getStatsBySnake("boaConstrictor", difficulty));
        stats.add(getStatsBySnake("python", difficulty));
        return stats;
    }
}
