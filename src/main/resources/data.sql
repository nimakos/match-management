INSERT INTO match (description, match_date, match_time, team_a, team_b, sport)
VALUES
('OSFP-PAO', '2021-03-31', '12:00:00', 'OSFP', 'PAO', 'FOOTBALL'),
('AEK-PAOK', '2021-04-01', '18:00:00', 'AEK', 'PAOK', 'BASKETBALL');

INSERT INTO match_odds (match_id, specifier, odd)
VALUES
(1, '1', 1.4),
(1, 'X', 1.5),
(1, '2', 2.2),
(2, '1', 1.9),
(2, '2', 1.8);