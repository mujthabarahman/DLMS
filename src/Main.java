import javax.swing.*;

import java.awt.*;

public class Main {
    private static JFrame frame;
    
    public static void main(String[] args) {
        // Display the main UI frame
        frame = new JFrame("Durga Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the header
        JPanel headerPanel = HeaderPartial.createHeader();
        BookManagementF x = new BookManagementF();
        JScrollPane homePanel = x.displayBooks();
        // Create the content panel
        

        // Add your content panels here using contentPanel.add(component, "panelName");

        // Set up the frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(homePanel, BorderLayout.CENTER);

        // Set the layout manager for the main frame
        frame.setSize(900, 600);
        frame.setVisible(true);
        
    }

    
}
