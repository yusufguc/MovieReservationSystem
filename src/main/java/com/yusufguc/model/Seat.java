package com.yusufguc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "seat",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"hall_id", "row", "seat_number"})
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String row;  // A, B, C, D, E

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;   //fixed between 1-10

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    @JsonIgnore
    private  Hall hall;
}

