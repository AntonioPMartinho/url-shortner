package pt.antoniopmartinho.urlShortner.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import pt.antoniopmartinho.urlShortner.model.LinkModel;
import java.time.OffsetDateTime;

@Entity
@Table(name = "click_metric")
@Data
@NoArgsConstructor
public class ClickMetricModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "link_id", nullable = false)
    private LinkModel link;

    @Column(name = "clicked_at", nullable = false)
    private OffsetDateTime clickedAt = OffsetDateTime.now();

    @Column(name = "ip_address", length = 100)
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "referer")
    private String referer;
}
