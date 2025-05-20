package com.bezkoder.spring.r2dbc.postgresql.unit;

import com.bezkoder.spring.r2dbc.postgresql.controller.TutorialController;
import com.bezkoder.spring.r2dbc.postgresql.model.Tutorial;
import com.bezkoder.spring.r2dbc.postgresql.service.TutorialService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = TutorialController.class)
class TutorialControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TutorialService tutorialService;

    @Test
    void shouldReturnTutorialById() {
        // Données de test
        int tutorialId = 1;
        Tutorial tutorial = new Tutorial(tutorialId, "Titre", "Description", true);

        // Configuration du mock
        Mockito.when(tutorialService.findById(tutorialId))
                .thenReturn(Mono.just(tutorial));

        // Exécution de la requête et vérification
        webTestClient.get()
                .uri("/tutorials/{id}", tutorialId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(tutorialId)
                .jsonPath("$.title").isEqualTo("Titre")
                .jsonPath("$.description").isEqualTo("Description")
                .jsonPath("$.published").isEqualTo(true);
    }
}
