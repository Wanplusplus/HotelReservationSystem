import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RoomWindow {
    private static Map<Integer, String> guestList = new HashMap<>();
    private static Map<Integer, Integer> bookingPrices = new HashMap<>();
    private static Map<Integer, String> bookingAddons = new HashMap<>();
    private static Map<Integer, Integer> bookingDays = new HashMap<>(); // New map to store days stayed
    
    private JFrame roomFrame;
    private RoomButton roomButton;
    private JCheckBox spaBox, poolBox, fitnessCenterBox;
    private JCheckBox buffetBox, coffeeTeaBox, wineCocktailBox;
    private JCheckBox chauffeurServiceBox, sunsetDinnerBox, liveEntertainmentBox;
    private JCheckBox lateCheckOutBox, valetParkingBox, champagneChocolatesBox;
    private JTextField daysInput;
    private JLabel priceLabel, guestLabel, daysLabel; // Added daysLabel
    private int finalPrice;
    private boolean isBooked;

    public RoomWindow(RoomButton roomButton) {
        this.roomButton = roomButton;
        this.finalPrice = roomButton.getBasePrice();
        this.isBooked = roomButton.isBooked();

        roomFrame = new JFrame("Room " + String.format("%03d", roomButton.getRoomNumber()));
        roomFrame.setSize(350, 500);
        roomFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        roomFrame.setLocationRelativeTo(null);
        roomFrame.setLayout(new GridLayout(10, 1, 10, 10));

        JLabel roomInfo = new JLabel("Room " + String.format("%03d", roomButton.getRoomNumber()), SwingConstants.CENTER);
        guestLabel = new JLabel(isBooked ? "Guest: " + guestList.get(roomButton.getRoomNumber()) : "Available", SwingConstants.CENTER);
        priceLabel = new JLabel("Price: $" + finalPrice, SwingConstants.CENTER);
        daysLabel = new JLabel(isBooked ? "Days Stayed: " + bookingDays.get(roomButton.getRoomNumber()) : "", SwingConstants.CENTER); // Initialize daysLabel

        if (isBooked) {
            showBookingDetails();
            return; // Exit the constructor if the room is booked
        }

        JPanel daysPanel = new JPanel();
        daysPanel.add(new JLabel("Days Stayed:"));
        daysInput = new JTextField(5);
        daysInput.setText("1");
        daysPanel.add(daysInput);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> updatePrice());

        JButton backButton = new JButton("Return");
        JButton bookButton = new JButton("Book Now");

        backButton.addActionListener(e -> roomFrame.dispose());
        bookButton.addActionListener(e -> bookRoom());

        // Create a panel for the amenities checkboxes
        JPanel addonsPanel = new JPanel();
        addonsPanel.setLayout(new GridLayout(4, 3));

        // Leisure & Wellness
        poolBox = new JCheckBox("🌊 Swimming Pool Access (+$20)");
        spaBox = new JCheckBox("💆 Spa & Massage Package (+$30)");
        fitnessCenterBox = new JCheckBox("🏋 Fitness Center Access (+$15)");

        // Dining & Beverages
        buffetBox = new JCheckBox("🍽 Unlimited Buffet (+$50)");
        coffeeTeaBox = new JCheckBox("☕ Premium Coffee & Tea Service (+$10)");
        wineCocktailBox = new JCheckBox("🍷 Wine & Cocktail Package (+$40)");

        // Exclusive Experiences
        chauffeurServiceBox = new JCheckBox("🚗 Private Chauffeur Service (+$100)");
        sunsetDinnerBox = new JCheckBox("🌅 Sunset Dinner by the Beach (+$150)");
        liveEntertainmentBox = new JCheckBox("🎭 Live Entertainment Access (+$75)");

        // Convenience & Luxury
        lateCheckOutBox = new JCheckBox("🛏 Late Check-out & Early Check-in (+$30)");
        valetParkingBox = new JCheckBox("🚗 Valet Parking & Car Rental (+$20)");
        champagneChocolatesBox = new JCheckBox("🍾 In-room Champagne & Chocolates (+$60)");

        // Add action listeners to update price when checkboxes are selected
        poolBox.addActionListener(e -> updatePrice());
        spaBox.addActionListener(e -> updatePrice());
        fitnessCenterBox.addActionListener(e -> updatePrice());
        buffetBox.addActionListener(e -> updatePrice());
        coffeeTeaBox.addActionListener(e -> updatePrice());
        wineCocktailBox.addActionListener(e -> updatePrice());
        chauffeurServiceBox.addActionListener(e -> updatePrice());
        sunsetDinnerBox.addActionListener(e -> updatePrice());
        liveEntertainmentBox.addActionListener(e -> updatePrice());
        lateCheckOutBox.addActionListener(e -> updatePrice());
        valetParkingBox.addActionListener(e -> updatePrice());
        champagneChocolatesBox.addActionListener(e -> updatePrice());

        // Add checkboxes to the addons panel
        addonsPanel.add(poolBox);
        addonsPanel.add(spaBox);
        addonsPanel.add(fitnessCenterBox);
        addonsPanel.add(buffetBox);
        addonsPanel.add(coffeeTeaBox);
        addonsPanel.add(wineCocktailBox);
        addonsPanel.add(chauffeurServiceBox);
        addonsPanel.add(sunsetDinnerBox);
        addonsPanel.add(liveEntertainmentBox);
        addonsPanel.add(lateCheckOutBox);
        addonsPanel.add(valetParkingBox);
        addonsPanel.add(champagneChocolatesBox);

        // Add components to the frame
        roomFrame.add(roomInfo);
        roomFrame.add(guestLabel);
        roomFrame.add(daysLabel); // Add daysLabel to the frame
        roomFrame.add(priceLabel);
        roomFrame.add(daysPanel);
        roomFrame.add(okButton);
        roomFrame.add(addonsPanel);
        roomFrame.add(backButton);
        roomFrame.add(bookButton);

        roomFrame.setVisible(true);
    }

    private void showBookingDetails() {
        // Show the booking details for the booked room
        String guestName = guestList.get(roomButton.getRoomNumber());
        int bookedPrice = bookingPrices.get(roomButton.getRoomNumber());
        int daysStayed = bookingDays.get(roomButton.getRoomNumber());
        String addons = bookingAddons.get(roomButton.getRoomNumber());

        guestLabel.setText("Guest: " + guestName);
        daysLabel.setText("Days Stayed: " + daysStayed); // Update daysLabel
        priceLabel.setText("Price: $" + bookedPrice);
        
        // Create a button to remove the guest
        JButton removeGuestButton = new JButton("Remove Guest");
        removeGuestButton.addActionListener(e -> removeGuest());

        roomFrame.add(removeGuestButton);
        roomFrame.setVisible(true);
    }

    private void updatePrice() {
        int days = getDaysStayed();
        if (days < 1) {
            JOptionPane.showMessageDialog(roomFrame, "Please enter a valid number of days.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate the base price based on the floor
        finalPrice = roomButton.getBasePrice();

        // If days is greater than 1, add the daily rate
        if (days > 1) {
            finalPrice += (days - 1) * getDailyRate(roomButton.getBasePrice());
        }

        // Calculate extra costs based on selected amenities (not multiplied by days)
        int extra = 0;
        StringBuilder selectedAddons = new StringBuilder();
        if (poolBox.isSelected()) {
            extra += 20;
            selectedAddons.append("Swimming Pool Access, ");
        }
        if (spaBox.isSelected()) {
            extra += 30;
            selectedAddons.append("Spa & Massage Package, ");
        }
        if (fitnessCenterBox.isSelected()) {
            extra += 15;
            selectedAddons.append("Fitness Center Access, ");
        }
        if (buffetBox.isSelected()) {
            extra += 50;
            selectedAddons.append("Unlimited Buffet, ");
        }
        if (coffeeTeaBox.isSelected()) {
            extra += 10;
            selectedAddons.append("Premium Coffee & Tea Service, ");
        }
        if (wineCocktailBox.isSelected()) {
            extra += 40;
            selectedAddons.append("Wine & Cocktail Package, ");
        }
        if (chauffeurServiceBox.isSelected()) {
            extra += 100;
            selectedAddons.append("Private Chauffeur Service, ");
        }
        if (sunsetDinnerBox.isSelected()) {
            extra += 150;
            selectedAddons.append("Sunset Dinner by the Beach, ");
        }
        if (liveEntertainmentBox.isSelected()) {
            extra += 75;
            selectedAddons.append("Live Entertainment Access, ");
        }
        if (lateCheckOutBox.isSelected()) {
            extra += 30;
            selectedAddons.append("Late Check-out & Early Check-in, ");
        }
        if (valetParkingBox.isSelected()) {
            extra += 20;
            selectedAddons.append("Valet Parking & Car Rental, ");
        }
        if (champagneChocolatesBox.isSelected()) {
            extra += 60;
            selectedAddons.append("In-room Champagne & Chocolates, ");
        }

        // Remove the last comma and space
        if (selectedAddons.length() > 0) {
            selectedAddons.setLength(selectedAddons.length() - 2);
        }

        finalPrice += extra; // Add extras to the final price
        priceLabel.setText("Price: $" + finalPrice);
        bookingAddons.put(roomButton.getRoomNumber(), selectedAddons.toString());
    }

    private int getDaysStayed() {
        try {
            int days = Integer.parseInt(daysInput.getText());
            return Math.max(1, days); // Ensure at least 1 day
        } catch (NumberFormatException e) {
            return 1; // Default to 1 day if input is invalid
        }
    }

    private int getDailyRate(int basePrice) {
        // Determine the daily rate based on the base price
        if (basePrice == 35) return 10;   // Floor 1
        if (basePrice == 75) return 25;   // Floor 2
        if (basePrice == 250) return 100; // Suite
        if (basePrice == 500) return 445; // Balcony
        return 0;
    }

    private void bookRoom() {
        int days = getDaysStayed();
        finalPrice += days * getDailyRate(roomButton.getBasePrice());

        if (!isBooked) {
            String guestName;
            do {
                guestName = JOptionPane.showInputDialog(roomFrame, "Enter Guest Name:");
            } while (guestName == null || guestName.trim().isEmpty());

            guestList.put(roomButton.getRoomNumber(), guestName);
            bookingPrices.put(roomButton.getRoomNumber(), finalPrice);
            bookingDays.put(roomButton.getRoomNumber(), days); // Store the number of days stayed
            roomButton.markAsBooked();
        } else {
            JOptionPane.showMessageDialog(roomFrame, "Stay extended for " + days + " more days!");
        }

        priceLabel.setText("Price: $" + finalPrice);
        daysLabel.setText("Days Stayed: " + days); // Update daysLabel
        roomFrame.dispose();
    }

    private void removeGuest() {
        int confirm = JOptionPane.showConfirmDialog(roomFrame, "Remove guest from this room?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            guestList.remove(roomButton.getRoomNumber());
            bookingPrices.remove(roomButton.getRoomNumber());
            bookingAddons.remove(roomButton.getRoomNumber());
            bookingDays.remove(roomButton.getRoomNumber()); // Remove days stayed
            roomButton.clearBooking();
            roomFrame.dispose();
        }
    }

    public static void showGuestList() {
        StringBuilder guestDetails = new StringBuilder("Current Guests:\n");
        for (Map.Entry<Integer, String> entry : guestList.entrySet()) {
            guestDetails.append("Room ").append(String.format("%03d", entry.getKey())).append(": ").append(entry.getValue()).append("\n");
        }
        JOptionPane.showMessageDialog(null, guestDetails.length() > 15 ? guestDetails.toString() : "No guests currently booked.");
    }
}