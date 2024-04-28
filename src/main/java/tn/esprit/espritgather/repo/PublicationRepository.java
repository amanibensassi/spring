package tn.esprit.espritgather.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.espritgather.entity.Publication;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
