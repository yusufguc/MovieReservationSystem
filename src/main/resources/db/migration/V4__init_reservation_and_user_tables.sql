CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP NOT NULL
);


CREATE TABLE reservation (
                             id BIGSERIAL PRIMARY KEY,

                             user_id BIGINT NOT NULL,
                             showtime_id BIGINT NOT NULL,

                             status VARCHAR(50) NOT NULL,
                             created_at TIMESTAMP NOT NULL,

                             CONSTRAINT fk_reservation_user
                                 FOREIGN KEY (user_id)
                                     REFERENCES users(id),

                             CONSTRAINT fk_reservation_showtime
                                 FOREIGN KEY (showtime_id)
                                     REFERENCES showtime(id)
);


CREATE TABLE reservation_seat (
                                  id BIGSERIAL PRIMARY KEY,

                                  reservation_id BIGINT NOT NULL,
                                  seat_id BIGINT NOT NULL,
                                  showtime_id BIGINT NOT NULL,

                                  CONSTRAINT fk_reservationseat_reservation
                                      FOREIGN KEY (reservation_id)
                                          REFERENCES reservation(id)
                                          ON DELETE CASCADE,

                                  CONSTRAINT fk_reservationseat_seat
                                      FOREIGN KEY (seat_id)
                                          REFERENCES seat(id),

                                  CONSTRAINT fk_reservationseat_showtime
                                      FOREIGN KEY (showtime_id)
                                          REFERENCES showtime(id),

                                  CONSTRAINT uq_showtime_seat
                                      UNIQUE (showtime_id, seat_id)
);
