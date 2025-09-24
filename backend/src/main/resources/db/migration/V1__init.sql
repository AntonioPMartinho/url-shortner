
CREATE TABLE IF NOT EXISTS links (
  id          BIGSERIAL PRIMARY KEY,
  short_code  VARCHAR(128) NOT NULL UNIQUE,
  original_url TEXT        NOT NULL,
  created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
  expires_at  TIMESTAMPTZ,
  owner_id    UUID,                -- id do utilizador (Keycloak), pode ser NULL (link anónimo)
  clicks      BIGINT       NOT NULL DEFAULT 0
);

-- Registo de cliques para métricas
CREATE TABLE IF NOT EXISTS click_metric (
  id          BIGSERIAL PRIMARY KEY,
  link_id     BIGINT       NOT NULL REFERENCES links(id) ON DELETE CASCADE,
  clicked_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
  ip_address  VARCHAR(100),
  user_agent  TEXT,
  referer     TEXT
);

-- Índices úteis
CREATE INDEX IF NOT EXISTS idx_links_short_code        ON links(short_code);
CREATE INDEX IF NOT EXISTS idx_click_metric_link_id    ON click_metric(link_id);
