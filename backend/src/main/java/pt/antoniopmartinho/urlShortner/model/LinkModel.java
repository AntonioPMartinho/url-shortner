package pt.antoniopmartinho.urlShortner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "links")
@Getter
@Setter
@NoArgsConstructor
public class LinkModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_code", nullable = false, unique = true, length = 128)
    private String shortCode;

    @Column(name = "original_url", nullable = false, columnDefinition = "TEXT")
    private String originalUrl;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(nullable = false)
    private long clicks = 0;
}
