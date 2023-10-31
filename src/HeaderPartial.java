import javax.swing.*;
import javax.swing.border.EmptyBorder;

import UserInterface.Add;
import UserInterface.BookManagementUI;
import UserInterface.TransactionUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeaderPartial {
    public static JPanel createHeader() {

        EmptyBorder panelMargin = new EmptyBorder(10, 10, 10, 10);

        JPanel headerPanel = new JPanel(new BorderLayout());
        // Set the size of the header panel
        int headerPanelHeight = 100;
        headerPanel.setBounds(0, 0, 800, headerPanelHeight);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(panelMargin);

        JLabel titleLabel = new JLabel("Durga LMS");
        titleLabel.setForeground(Color.black);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.setPreferredSize(new Dimension(800, 60));

        int buttonWidth = 150;
        int buttonHeight = 30;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        buttonPanel.setBackground(Color.white);

        JButton updateButton = new RoundButton("Update",Color.white,0);
        updateButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        //updateButton.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add padding
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle navigation to the home page
                System.out.println("inside actionlistener");
                BookManagementUI.handleUpdateButtonClick();
            }
        });
        buttonPanel.add(updateButton);

        JButton transactionButton = new RoundButton("Transaction",Color.white,0);
        transactionButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        //transactionButton.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add padding
        transactionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle navigation to the books page
                navigateToTransactionPage();
            }
        });
        buttonPanel.add(transactionButton);


        JButton AddRemoveButton = new RoundButton("Add",Color.white,0);
        AddRemoveButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        //AddRemoveButton.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add padding
        AddRemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle navigation to the books page
                navigateToAddRemovePage();
            }
        });
        buttonPanel.add(AddRemoveButton);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        // Add more buttons for other pages as needed

        return headerPanel;
    }


    public static void navigateToTransactionPage() {
        TransactionUI.displayTransactions();
    }

    public static void navigateToAddRemovePage() {
        Add.displayAddFrame();
    }

    // Add more navigation methods for other pages as needed
}
