package cinema;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class Controller {
    @Autowired
    CinemaRoom cinemaRoom;
    @Autowired
    SeatsService seatsService;
    @Autowired
    StatService statService;

    @GetMapping("/seats")
    public CinemaRoom getSeats() {
        cinemaRoom.setAvailableSeats(seatsService.getSeats());
        return cinemaRoom;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Ticket> getTicket(@RequestBody Seat seat) {
        return seatsService.purchaseTicket(seat);
    }
    @PostMapping("/return")
    public ResponseEntity returnTicket(@RequestBody Ticket ticket){
        return seatsService.returnedTicket(ticket);
    }
    @PostMapping("/stats")
    public ResponseEntity getStats(@RequestParam(value = "password",required = false) String password){
       return statService.getStats(password);
    }
}
