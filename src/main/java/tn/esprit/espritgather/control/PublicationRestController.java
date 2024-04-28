package tn.esprit.espritgather.control;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.espritgather.entity.Publication;
import tn.esprit.espritgather.service.IPublicationService;

import java.util.List;

@Tag(name = "Gestion Publication")
@RestController
@AllArgsConstructor
@RequestMapping("/publication")
@CrossOrigin(origins = "http://localhost:4200")
public class PublicationRestController {
    IPublicationService publicationService;

    @Operation(description = "Récupérer toutes les publications de la base de données")
    @GetMapping("/publications")
    public List<Publication> getPublications() {
        List<Publication> publications = publicationService.retrieveAllPublications();
        return publications;
    }

    @GetMapping("/retrieve-publication/{publication-id}")
    public Publication retrievePublication(@PathVariable("publication-id") Long id) {
        Publication publication = publicationService.retrievePublication(id);
        return publication;
    }

    @PostMapping("/add-publication")
    public Publication addPublication(@RequestBody Publication publication) {
        Publication addedPublication = publicationService.addPublication(publication);
        return addedPublication;
    }

    @DeleteMapping("/remove-publication/{publication-id}")
    public void removePublication(@PathVariable("publication-id") Long id) {
        publicationService.removePublication(id);
    }

    @PutMapping("/modify-publication")
    public Publication modifyPublication(@RequestBody Publication publication) {
        Publication modifiedPublication = publicationService.modifyPublication(publication);
        return modifiedPublication;
    }
}
