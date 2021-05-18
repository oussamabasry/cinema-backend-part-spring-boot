package com.basry.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Projection implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date projectionDate;
    private Double price;
    @ManyToOne
    private Room room;
    @ManyToOne
    private Movie movie;
    @OneToMany(mappedBy = "projection")
    private Collection<Ticket> tickets;
    @ManyToOne
    private Session session;
}
