package cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeatsService {
    @Autowired
    @Qualifier("seats")
    private final List<Seat> seats;
    @Autowired
    @Qualifier("tickets")
    private final HashMap<UUID, Seat> tickets;

    public SeatsService(List<Seat> seats, HashMap<UUID, Seat> tickets) {
        this.seats = seats;
        this.tickets = tickets;
    }

    private Optional<Seat> findSeat(Seat seat) {
        return seats.stream()
                .filter(seat1 -> seat.getRow() == seat1.getRow() & seat.getColumn() == seat1.getColumn())
                .findFirst();
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public ResponseEntity<Ticket> purchaseTicket(Seat seat) {
        if (findSeat(seat).isPresent()) {
            if (!findSeat(seat).get().isPurchased()) {
                Seat tmpSeat = findSeat(seat).get();
                seats.remove(tmpSeat);
                tmpSeat.setPurchased(true);
                seats.add(tmpSeat);

                UUID uuid = UUID.randomUUID();
                Ticket ticket = new Ticket(uuid, tmpSeat);
                tickets.put(uuid, tmpSeat);
                return ResponseEntity.ok().body(ticket);
            } else {
                return new ResponseEntity(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity returnedTicket(Ticket ticket) {
        UUID token = ticket.getToken();
        if (tickets.containsKey(token)) {
            Seat tmpSeat = tickets.get(token);
            tickets.remove(token);
            return ResponseEntity.ok().body(Map.of("returned_ticket", tmpSeat));
        } else {
            return new ResponseEntity(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
    }
}
