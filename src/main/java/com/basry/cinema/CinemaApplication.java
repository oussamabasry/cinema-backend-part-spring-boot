package com.basry.cinema;

import com.basry.cinema.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {
    @Autowired
    private ICinemaInitService cinemaInitService;

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        cinemaInitService.initCities();
        cinemaInitService.initCinema();
        cinemaInitService.initRooms();
        cinemaInitService.initPlaces();
        cinemaInitService.initSessions();
        cinemaInitService.initCategories();
        cinemaInitService.initMovies();
        cinemaInitService.initProjections();
        cinemaInitService.initTickets();
    }
}
