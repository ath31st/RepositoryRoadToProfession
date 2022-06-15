package cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class StatService {
    private int current_income = 0;
    private int number_of_available_seats = 0;
    private int number_of_purchased_tickets = 0;
    @Autowired
    @Qualifier("seats")
    private final List<Seat> seats;
    @Autowired
    @Qualifier("tickets")
    private final HashMap<UUID, Seat> tickets;

    public StatService(List<Seat> seats, HashMap<UUID, Seat> tickets) {
        this.seats = seats;
        this.tickets = tickets;
    }

    public ResponseEntity getStats(String password){
        String password1 = "super_secret";
        if (password1.equals(password)){
            prepareStats();
            return new ResponseEntity(Map.of("current_income",current_income,
                    "number_of_available_seats",number_of_available_seats,
                    "number_of_purchased_tickets",number_of_purchased_tickets),HttpStatus.OK);
        } else {
            return new ResponseEntity(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
    }
    private void prepareStats(){
        number_of_available_seats = seats.size() - tickets.size();
        number_of_purchased_tickets = tickets.size();
        current_income = tickets.values().stream().mapToInt(Seat::getPrice).sum();
    }
}
