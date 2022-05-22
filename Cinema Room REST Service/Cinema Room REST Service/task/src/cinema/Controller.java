package cinema;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Controller {
    @Autowired
    CinemaRoom cinemaRoom;
    @Autowired
    SeatsService seatsService;

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

}
