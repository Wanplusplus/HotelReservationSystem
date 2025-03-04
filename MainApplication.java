import javax.swing.*;
import java.awt.*;

public class MainApplication {
    private JFrame mainFrame;
    private JPanel gridPanel;
    private JLabel floorLabel;
    private int currentFloor = 1;
    private final int totalFloors = 4;

    public MainApplication(String userName) {
        mainFrame = new JFrame("Room Selector");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());

        // Create a panel to hold the welcome message and floor label
        JPanel welcomePanel = new JPanel(new GridLayout(2, 1)); // Centering 
        JLabel welcomeLabel = new JLabel("Welcome, " + userName, SwingConstants.CENTER);
        floorLabel = new JLabel(getFloorLabel(), SwingConstants.CENTER);

        welcomePanel.add(welcomeLabel);
        welcomePanel.add(floorLabel);

        // Create an empty panel to add space on the left
        JPanel spacerPanel = new JPanel();
        spacerPanel.setPreferredSize(new Dimension(250, 0)); // Adjust the width as needed

        // Add the spacer and welcomePanel to the headerPanel
        headerPanel.add(spacerPanel, BorderLayout.WEST); // Add spacer to the left
        headerPanel.add(welcomePanel, BorderLayout.CENTER); // Center the welcome area

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Create a button to show all guests
        JButton showGuestsButton = new JButton("Show All Guests");
        showGuestsButton.addActionListener(e -> RoomWindow.showGuestList());
        buttonPanel.add(showGuestsButton); // Add the button to the button panel

        // Create a button to return to the login menu
        JButton returnButton = new JButton("Return to Login");
        returnButton.addActionListener(e -> {
            mainFrame.dispose(); // Close the main application window
            // Show the login dialog again
            String[] credentials = NameInputDialog.showDialog(null);
            String userName1 = credentials[0]; // Get the username 
            if (userName1 != null && !userName1.isEmpty()) {
                new MainApplication(userName1); // Start a new instance of MainApplication
            } else {
                System.exit(0); // Exit if no username is provided
            }
        });
        buttonPanel.add(returnButton); // Add the return button to the button panel

        headerPanel.add(buttonPanel, BorderLayout.EAST); // Add the button panel to the header panel

        mainFrame.add(headerPanel, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(3, 3, 15, 15));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());

        JButton leftButton = ArrowControls.createArrowsButton("left", e -> scrollLeft());
        JButton rightButton = ArrowControls.createArrowsButton("right", e -> scrollRight());

        leftPanel.add(leftButton);
        rightPanel.add(rightButton);

        mainFrame.add(gridPanel, BorderLayout.CENTER);
        mainFrame.add(leftPanel, BorderLayout.WEST);
        mainFrame.add(rightPanel, BorderLayout.EAST);

        mainFrame.setVisible(true);
        loadRooms();
    }

    private void scrollLeft() {
        if (currentFloor > 1) {
            currentFloor--;
            loadRooms();
        }
    }

    private void scrollRight() {
        if (currentFloor < totalFloors) {
            currentFloor++;
            loadRooms();
        }
    }

    private void loadRooms() {
        gridPanel.removeAll();
        floorLabel.setText(getFloorLabel());

        int basePrice = getBasePriceForFloor();
        int startRoom = (currentFloor - 1) * 9 + 1;
        int endRoom = Math.min(currentFloor * 9, 36);

        for (int i = startRoom; i <= endRoom; i++) {
            RoomButton button = new RoomButton(i, basePrice);
            button.setPreferredSize(new Dimension(100, 60));
            gridPanel.add(button);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private int getBasePriceForFloor() {
        switch (currentFloor) {
            case 1: return 35;
            case 2: return 75;
            case 3: return 250;
            case 4: return 500;
            default: return 0;
        }
    }

    private String getFloorLabel() {
        switch (currentFloor) {
            case 1: return "Floor 1";
            case 2: return "Floor 2";
            case 3: return "Suite";
            case 4: return "Balcony";
            default: return "Unknown Floor";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] credentials = NameInputDialog.showDialog(null);
            String userName = credentials[0]; // Get the username
            if (userName != null && !userName.isEmpty()) {
                new MainApplication(userName);
            } else {
                System.exit(0); // Exit if no username is provided
            }
        });
    }
}