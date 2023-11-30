package cinema.controller;

import cinema.exception.BookingException;
import cinema.exception.WrongPasswordException;
import cinema.exception.WrongTokenException;
import cinema.pojo.CinemaRoom;
import cinema.pojo.Seat;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
public class Controller {
    private final CinemaRoom cinemaRoom;
    private final ConcurrentMap<String, Seat> bookedSeats;

    public Controller() {
        cinemaRoom = new CinemaRoom(9, 9);
        bookedSeats = new ConcurrentHashMap<>();
    }

    @GetMapping("/seats")
    public ResponseEntity<?> getCinemaRoom() {
        return ResponseEntity.ok(cinemaRoom);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam String password) {
        if(!"super_secret".equals(password)) {
            throw new WrongPasswordException("The password is wrong!");
        }
        return ResponseEntity.ok(Map.of("income", bookedSeats.values().stream()
                                                                          .map(Seat::getPrice)
                                                                          .reduce(0, Integer::sum),
                                        "available", cinemaRoom.getSeats().size(),
                                        "purchased", bookedSeats.size()));
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> book(@Valid @RequestBody Seat seat) {
        if (!cinemaRoom.getSeats().contains(seat)) {
            throw new BookingException("The ticket has been already purchased!");
        }
        cinemaRoom.removeSeat(seat);
        String token = UUID.randomUUID().toString();
        seat.setPrice(seat.getRow() <= 4 ? 10 : 8);
        bookedSeats.put(token, seat);
        return ResponseEntity.ok(Map.of("token", token, "ticket", seat));
    }

    @PostMapping("/return")
    public ResponseEntity<?> book(@RequestBody Map<String, String> map) {
        String token = map.get("token");
        if (!bookedSeats.containsKey(token)) {
            throw new WrongTokenException("Wrong token!");
        }
        Seat seat = bookedSeats.get(token);
        bookedSeats.remove(token);
        cinemaRoom.addSeat(seat);
        return ResponseEntity.ok(Map.of("ticket", seat));
    }
}
