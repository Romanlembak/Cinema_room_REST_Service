package cinema.config;

import cinema.pojo.CinemaRoom;
import cinema.pojo.Seat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class AppConfig {
    @Bean
    public CinemaRoom cinemaRoom() {
        return new CinemaRoom(9, 9);
    }

    @Bean
    public ConcurrentMap<String, Seat> bookedSeats() {
        return new ConcurrentHashMap<>();
    }
}
