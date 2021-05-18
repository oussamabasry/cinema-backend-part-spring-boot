package com.basry.cinema.service;

import com.basry.cinema.dao.*;
import com.basry.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public void initCities() {
        Stream.of("Casablanca", "Marrakech", "Tanger", "Agadir", "Rabat").forEach(cityName -> {
            City city = new City();
            city.setName(cityName);
            cityRepository.save(city);
        });
    }

    @Override
    public void initCinema() {
        cityRepository.findAll().forEach(city -> {
            Stream.of("Megarma", "IMAX", "FOUNOUN", "CHAHRAZAD", "DAOULIZ").forEach(cinemaName -> {
                Cinema cinema = new Cinema();
                cinema.setName(cinemaName);
                cinema.setRoomNumber(3+(int)(Math.random()*7));
                cinema.setCity(city);
                cinemaRepository.save(cinema);
            });
        });
    }

    @Override
    public void initRooms() {
        cinemaRepository.findAll().forEach(cinema -> {
           for(int i=0; i<cinema.getRoomNumber();i++){
               Room room = new Room();
               room.setName("Room "+(i+1));
               room.setCinema(cinema);
               room.setPlaceNumber(15+(int)(Math.random()*20));
               roomRepository.save(room);
           }
        });
    }

    @Override
    public void initPlaces() {
        roomRepository.findAll().forEach(room->{
            for(Integer i=0; i < room.getPlaceNumber();i++){
                Place place = new Place();
                place.setPlaceNumber(i+1);
                place.setRoom(room);
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSessions() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00", "15:00","17:00","19:00","20:00").forEach(sessionHour ->{
            Session session = new Session();
            try {
                session.setStartHour(dateFormat.parse(sessionHour));
                sessionRepository.save(session);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initCategories() {
        Stream.of("History", "Action","Fiction","Drama").forEach(cat->{
            Category category = new Category();
            category.setName(cat);
            categoryRepository.save(category);
        });
    }

    @Override
    public void initMovies() {
        double[] durations = new double[]{1,1.5,5,2.5,2,3};
        List<Category> categories = categoryRepository.findAll();
        Stream.of("Game of Thrones", "Seigneur des anneaux", "Spider man", "IRON Man","Cat Women").forEach(movieTitle ->{
            Movie movie = new Movie();
            movie.setTitle(movieTitle);
            movie.setDuration(durations[new Random().nextInt(durations.length)]);
            movie.setImage(movieTitle.replaceAll(" ","")+".jpg");
            movie.setCategory(categories.get(new Random().nextInt(categories.size())));
            movieRepository.save(movie);
        });
    }

    @Override
    public void initProjections() {
        double[] prices = new double[] {30,50,60,70,90,100};
        cityRepository.findAll().forEach(city->{
            city.getCinemas().forEach(cinema -> {
                cinema.getRooms().forEach(room->{
                    movieRepository.findAll().forEach(movie -> {
                        sessionRepository.findAll().forEach(session -> {
                            Projection projection = new Projection();
                            projection.setProjectionDate( new Date());
                            projection.setMovie(movie);
                            projection.setPrice(prices[new Random().nextInt(prices.length)]);
                            projection.setRoom(room);
                            projection.setSession(session);
                            projectionRepository.save(projection);
                        });
                    });
                });
            });
        });
    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(proj->{
            proj.getRoom().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrice(proj.getPrice());
                ticket.setProjection(proj);
                ticket.setReserve(false);
                ticketRepository.save(ticket);
            });
        });
    }
}
