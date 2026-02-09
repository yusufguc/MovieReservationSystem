ALTER TABLE reservation_seat
DROP CONSTRAINT IF EXISTS uq_showtime_seat;

ALTER TABLE reservation_seat
DROP COLUMN IF EXISTS showtime_id;

ALTER TABLE reservation_seat
    ADD CONSTRAINT uk_reservation_seat_reservation_seat
        UNIQUE (reservation_id, seat_id);
