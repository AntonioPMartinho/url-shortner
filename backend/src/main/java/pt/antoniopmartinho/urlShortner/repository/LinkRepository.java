package pt.antoniopmartinho.urlShortner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.antoniopmartinho.urlShortner.model.LinkModel;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface LinkRepository extends JpaRepository<LinkModel, Long> {
    Optional<LinkModel> findByShortCode(String shortCode);
    List<LinkModel> findByOwnerId(UUID ownerId);
}
