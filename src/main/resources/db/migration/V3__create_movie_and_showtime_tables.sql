CREATE TABLE movie (
                       id BIGSERIAL PRIMARY KEY,

                       title VARCHAR(255) NOT NULL,

                       description TEXT,

                       duration_minutes INT NOT NULL,

                       genre VARCHAR(50) NOT NULL
);


CREATE TABLE showtime (
                          id BIGSERIAL PRIMARY KEY,

                          movie_id BIGINT NOT NULL,

                          hall_id BIGINT NOT NULL,

                          start_time TIMESTAMP NOT NULL,

                          end_time TIMESTAMP NOT NULL,

                          CONSTRAINT fk_showtime_movie
                              FOREIGN KEY (movie_id)
                                  REFERENCES movie(id)
                                  ON DELETE CASCADE,

                          CONSTRAINT fk_showtime_hall
                              FOREIGN KEY (hall_id)
                                  REFERENCES hall(id)
                                  ON DELETE CASCADE,

                          CONSTRAINT uk_hall_start_time
                              UNIQUE (hall_id, start_time)
);
