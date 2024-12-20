package org.snakeinc.server.repository;


import org.snakeinc.server.entity.Score;
import org.springframework.data.repository.CrudRepository;

public interface ScoreRepository extends CrudRepository<Score, Integer> { //FirstRepository = Entity à gérer, Integer = primary key
    
}
