package cinema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {
    private UUID token;
    private Seat seat;

    public Ticket() {
    }

    public Ticket(UUID token, Seat seat) {
        this.token = token;
        this.seat = seat;
    }

    public UUID getToken() {
        return token;
    }

    @JsonProperty(value = "ticket")
    public Seat getSeat() {
        return seat;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
