package org.snakeinc.server;

import static org.mockito.ArgumentMatchers.any;

import org.assertj.core.util.diff.myers.Snake;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.snakeinc.server.controller.ScoreController;
import org.snakeinc.server.entity.ScoreBody;
import org.snakeinc.server.service.ScoreService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServerApplicationTests {

	// @Test
    // void testPostRequest() {

    // }
    private final ScoreService scoreService = Mockito.mock(ScoreService.class); // Mock du service
    private final ScoreController scoreController = new ScoreController(scoreService); // Initialisation du contrôleur
    @Test
    void postScore_ShouldReturnOk_WhenDataIsValid() {
        // Préparer les données valides
        ScoreBody validRequest = new ScoreBody();
        validRequest.setScore(100);
        validRequest.setSnake("python");
        validRequest.setDifficulty("normal");

        // Simuler le comportement du service
        Mockito.doNothing().when(scoreService).postScore(any(ScoreBody.class));

        // Appeler la méthode et vérifier le résultat
        ResponseEntity<?> response = scoreController.postScore(validRequest);

        // Assertions
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // HTTP 200 OK
        assertThat(response.getBody()).isEqualTo("Score enregistré avec succès.");
    }

    @Test
    void postScore_ShouldReturnBadRequest_WhenDataIsInvalid() {
        // Préparer les données invalides
        ScoreBody invalidRequest = new ScoreBody();
        invalidRequest.setScore(10); 
        invalidRequest.setSnake("cobra"); // Serpent invalide
        invalidRequest.setDifficulty("normal");

        // Appeler la méthode et vérifier le résultat
        ResponseEntity<?> response = scoreController.postScore(invalidRequest);

        // Assertions
        assertThat(response.getStatusCodeValue()).isEqualTo(400); // HTTP 400 Bad Request
        assertThat(response.getBody()).isEqualTo("Données non conformes envoyées.");
    }
}
