DROP TABLE IF EXISTS match_odds;
DROP TABLE IF EXISTS match;

CREATE TABLE match (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    match_date DATE,
    match_time TIME,
    team_a VARCHAR(100),
    team_b VARCHAR(100),
    sport VARCHAR(20)
);

CREATE TABLE match_odds (
    id SERIAL PRIMARY KEY,
    match_id INTEGER REFERENCES match(id) ON DELETE CASCADE,
    specifier VARCHAR(10),
    odd NUMERIC(5,2)
);