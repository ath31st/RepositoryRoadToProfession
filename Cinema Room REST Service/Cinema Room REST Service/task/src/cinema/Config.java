package cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class Config {
    @Autowired
    private CinemaRoom cinemaRoom;

    @Bean
    public List<Seat> seats() {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= cinemaRoom.getTotalRows(); i++) {
            for (int j = 1; j <= cinemaRoom.getTotalColumns(); j++) {
                if (i <= 4) {
                    seats.add(new Seat(i, j, cinemaRoom.getPriceTicketFirstFourRows()));
                } else {
                    seats.add(new Seat(i, j, cinemaRoom.getPriceTicketAfterFourRows()));
                }
            }
        }
        return seats;
    }
    @Bean
    public HashMap<UUID,Seat> tickets(){
       return new HashMap<>();
    }
}
