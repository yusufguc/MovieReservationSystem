CREATE TABLE hall (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE seat (
                      id BIGSERIAL PRIMARY KEY,
                      row VARCHAR(1) NOT NULL,
                      seat_number INTEGER NOT NULL,
                      hall_id BIGINT NOT NULL,
                      CONSTRAINT fk_seat_hall
                          FOREIGN KEY (hall_id)
                              REFERENCES hall(id)
                              ON DELETE CASCADE,
                      CONSTRAINT uq_seat UNIQUE (hall_id, row, seat_number)
);
