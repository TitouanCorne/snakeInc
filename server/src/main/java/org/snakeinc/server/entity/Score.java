package org.snakeinc.server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Data //permet d'avoir automatiquement un getter, un setter, ... (lombok)
public class Score {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //clé primaire
    
    private String snake;
    private int score;
    private String difficulty;
}
