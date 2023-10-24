package UserInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Add {
    public static JFrame displayAddFrame() {
        JFrame frame = new JFrame("Add Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JButton bookButton = new JButton("Book");
        JButton transactionButton = new JButton("Transaction");

        JPanel bookPanel = new JPanel();
        JLabel bookLabel = new JLabel("Add Book Details");
        JTextField bookField = new JTextField(20);
        bookPanel.add(bookLabel);
        bookPanel.add(bookField);

        JPanel transactionPanel = new JPanel();
        JLabel transactionLabel = new JLabel("Add Transaction Details");
        JTextField transactionField = new JTextField(20);
        transactionPanel.add(transactionLabel);
        transactionPanel.add(transactionField);

        mainPanel.add(bookPanel, BorderLayout.CENTER);
        transactionPanel.setVisible(false);
        mainPanel.add(transactionPanel, BorderLayout.CENTER);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookPanel.setVisible(true);
                transactionPanel.setVisible(false);
                frame.revalidate();
                frame.repaint();
            }
        });

        transactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transactionPanel.setVisible(true);
                bookPanel.setVisible(false);
                frame.revalidate();
                frame.repaint();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bookButton);
        buttonPanel.add(transactionButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        frame.add(mainPanel);
        frame.setVisible(true);
        return frame;
    }
}
