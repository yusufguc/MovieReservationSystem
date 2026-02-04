INSERT INTO hall (name) VALUES
                            ('HALL_1'),
                            ('HALL_2'),
                            ('HALL_3'),
                            ('HALL_4');


INSERT INTO seat (row, seat_number, hall_id)
SELECT
    r.row,
    s.seat_number,
    h.id
FROM hall h
         CROSS JOIN (
    SELECT unnest(ARRAY['A','B','C','D','E']) AS row
) r
         CROSS JOIN generate_series(1,10) s(seat_number);
