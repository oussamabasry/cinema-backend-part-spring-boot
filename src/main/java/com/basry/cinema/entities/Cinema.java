package com.basry.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class Cinema implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String name;
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private Integer roomNumber;
    @OneToMany(mappedBy = "cinema")
    private Collection<Room> rooms;
    @ManyToOne
    private City city;
}
