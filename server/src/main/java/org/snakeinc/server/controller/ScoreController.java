package org.snakeinc.server.controller;

import java.util.List;

import org.snakeinc.server.entity.Score;
import org.snakeinc.server.entity.ScoreBody;
import org.snakeinc.server.entity.StatBody;
import org.snakeinc.server.service.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ScoreController {

    private final ScoreService scoreService;
    public ScoreController(ScoreService scoreService){ //dépendence à FirstService !
        this.scoreService = scoreService;
    }

    @PostMapping("/api/v1/score")
    public ResponseEntity<?> postScore(@RequestBody ScoreBody RequestBody){
        List<String> snakes = List.of("boaConstrictor", "python", "anaconda");
        List<String> difficulties = List.of("easy", "normal", "hard");
        if(RequestBody.getScore() >= 0 && 
            snakes.contains(RequestBody.getSnake()) &&
            difficulties.contains(RequestBody.getDifficulty())){
            scoreService.postScore(RequestBody);
            return ResponseEntity.ok("Score enregistré avec succès.");
        }else{
            return ResponseEntity.badRequest().body("Données non conformes envoyées.");
        }
    }

    @GetMapping("api/v1/scores")
    public List<Score> getAllScores(){
        return scoreService.getAllScores();
    }

    @GetMapping("api/v1/scores/stats")
    public List<StatBody> getStats(@RequestParam String difficulty){
        return scoreService.getStats(difficulty);
    }

    @GetMapping("api/v1/scores/statsbysnake")
    public StatBody getStatsBySnake(@RequestParam String snake, @RequestParam String difficulty){
        return scoreService.getStatsBySnake(snake, difficulty);
    }
}
