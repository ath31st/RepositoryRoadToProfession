package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CinemaRoom {
    private final int totalRows = 9;
    private final int totalColumns = 9;
    @JsonIgnore
    private final int priceTicketFirstFourRows = 10;
    @JsonIgnore
    private final int priceTicketAfterFourRows = 8;
    private List<Seat> availableSeats;

    public int getTotalRows() {
        return totalRows;
    }


    public int getTotalColumns() {
        return totalColumns;
    }

    public int getPriceTicketFirstFourRows() {
        return priceTicketFirstFourRows;
    }

    public int getPriceTicketAfterFourRows() {
        return priceTicketAfterFourRows;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }
}
