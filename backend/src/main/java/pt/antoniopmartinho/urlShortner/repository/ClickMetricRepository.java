package pt.antoniopmartinho.urlShortner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.antoniopmartinho.urlShortner.model.ClickMetricModel;

public interface ClickMetricRepository extends JpaRepository<ClickMetricModel, Long> {
}
