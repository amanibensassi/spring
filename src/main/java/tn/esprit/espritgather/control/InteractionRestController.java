package tn.esprit.espritgather.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.espritgather.entity.Interaction;
import tn.esprit.espritgather.service.InteractionServiceImpl;

import java.util.List;
import java.util.Map;

@Tag(name = "Gestion Interaction")
@RestController
@AllArgsConstructor
@RequestMapping("/interaction")
@CrossOrigin(origins = "http://localhost:4200")
public class InteractionRestController {
    private final InteractionServiceImpl interactionService;

    @Operation(description = "Récupérer toutes les interactions de la base de données")
    @GetMapping("/interactions")
    public List<Interaction> getInteractions() {
        return interactionService.retrieveAllInteractions();
    }

    @GetMapping("/retrieve-interaction/{interaction-id}")
    public Interaction retrieveInteraction(@PathVariable("interaction-id") Long id) {
        return interactionService.retrieveInteraction(id);
    }

    @PostMapping("/add-interaction/{idP}/{idU}")
    public ResponseEntity<Interaction> addInteraction(@RequestBody Interaction interaction, @PathVariable("idP") Long idpub,@PathVariable("idU") Long idusr) {
        return interactionService.addInteraction(interaction,idpub,idusr);
    }

    @DeleteMapping("/remove-interaction/{interaction-id}")
    public void removeInteraction(@PathVariable("interaction-id") Long id) {
        interactionService.removeInteraction(id);
    }

    @PutMapping("/modify-interaction")
    public Interaction modifyInteraction(@RequestBody Interaction interaction) {
        return interactionService.modifyInteraction(interaction);
    }
   @GetMapping("/retrieve-interaction-publication/{idPub}/{idU}")
    public Interaction retrieveInteractionByPublicationAndUser(@PathVariable("idPub") Long idP,@PathVariable("idU") Long idU) {
        return interactionService.findInteractionByPublicationAndUser(idP,idU);
    }
}
