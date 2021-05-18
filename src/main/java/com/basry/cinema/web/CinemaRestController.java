package com.basry.cinema.web;

import com.basry.cinema.dao.MovieRepository;
import com.basry.cinema.dao.TicketRepository;
import com.basry.cinema.entities.Movie;
import com.basry.cinema.entities.Ticket;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CinemaRestController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(path = "/imageMovie/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id") Long id) throws IOException {
        Movie movie = movieRepository.findById(id).get();
        String imageName = movie.getImage();
        File file = new File(System.getProperty("user.home") + "/cinema/images/" + imageName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }


    @PostMapping("/payTickets")
    @Transactional
    public List<Ticket> payerTickets(@RequestBody TicketFrom ticketFrom) {
        List<Ticket> listTickets = new ArrayList<>();
        ticketFrom.getTickets().forEach(idTicket -> {
            Ticket ticket = ticketRepository.findById(idTicket).get();
            ticket.setNameCustomer(ticketFrom.getCustomerName());
            ticket.setReserve(true);
            ticket.setCodePayment(ticketFrom.getCodePayment());
            ticketRepository.save(ticket);
            listTickets.add(ticket);
        });
        return listTickets;
    }
}

@Data
class TicketFrom {
    private String customerName;
    private int codePayment;
    private List<Long> tickets = new ArrayList<>();
}
