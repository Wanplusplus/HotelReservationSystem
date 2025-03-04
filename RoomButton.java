import javax.swing.*;
import java.awt.*;

public class RoomButton extends JButton {
    private int roomNumber;
    private int basePrice;
    private boolean booked;
    private String guestName;

    public RoomButton(int roomNumber, int basePrice) {
        super(String.format("%03d", roomNumber));
        this.roomNumber = roomNumber;
        this.basePrice = basePrice;
        this.booked = false;
        this.guestName = null;

        setPreferredSize(new Dimension(80, 40));
        addActionListener(e -> new RoomWindow(this));
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public boolean isBooked() {
        return booked;
    }

    public void markAsBooked() {
        booked = true;
        setBackground(Color.RED);
    }

    public void setGuest(String guestName) {
        this.guestName = guestName;
        setText(String.format("%03d\n%s", roomNumber, guestName));
    }

    public void clearBooking() {
        booked = false;
        guestName = null;
        setText(String.format("%03d", roomNumber));
        setBackground(null);
    }
}