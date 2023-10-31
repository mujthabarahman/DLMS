package UserInterface;
import javax.swing.*;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Add {
    public static JFrame displayAddFrame() {
        JFrame frame = new JFrame("Add Frame");
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel cards = new JPanel(new CardLayout());


        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new GridBagLayout());

        final boolean[] isBookPanelSelected = new boolean[1];
        GridBagConstraints gbc = new GridBagConstraints();
        Font boldFont = new Font("Arial", Font.BOLD, 22);
        Font plainFont = new Font("Arial", Font.PLAIN, 19);
        //Dimension buttonSize = new Dimension(100,28);
        Dimension HbuttonSize = new Dimension(140,33);
        Dimension tfieldSize = new Dimension(270,35);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel bookLabel = new JLabel("Add Book Details");
        bookLabel.setFont(boldFont);
        bookPanel.add(bookLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        JLabel bLabel = new JLabel("Book ");
        bLabel.setFont(plainFont);
        bookPanel.add(bLabel, gbc);
        gbc.gridx++;
        JTextField bookNameField = new JTextField(20);
        bookNameField.setFont(plainFont);
        bookNameField.setPreferredSize(tfieldSize);
        bookPanel.add(bookNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        JLabel authorLabel = new JLabel("Author ");
        authorLabel.setFont(plainFont);
        bookPanel.add(authorLabel, gbc);
        gbc.gridx++;
        JTextField authorField = new JTextField(20);
        authorField.setFont(plainFont);
        authorField.setPreferredSize(tfieldSize);
        bookPanel.add(authorField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        JLabel categoryLabel = new JLabel("Category ");
        categoryLabel.setFont(plainFont);
        bookPanel.add(categoryLabel, gbc);
        gbc.gridx++;
        JTextField categoryField = new JTextField(20);
        categoryField.setFont(plainFont);
        categoryField.setPreferredSize(tfieldSize);
        bookPanel.add(categoryField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;


        JLabel languageLabel = new JLabel("Language ");
        languageLabel.setFont(plainFont);
        bookPanel.add(languageLabel, gbc);
        gbc.gridx++;
        JTextField languageField = new JTextField(20);
        languageField.setFont(plainFont);
        languageField.setPreferredSize(tfieldSize);
        bookPanel.add(languageField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);

        JLabel dateLabel = new JLabel("Date ");
        dateLabel.setFont(plainFont);
        bookPanel.add(dateLabel, gbc);
        gbc.gridx++;
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
        datePicker.setFont(plainFont);
        datePicker.setPreferredSize(tfieldSize);
        bookPanel.add(datePicker, gbc);
        gbc.gridx = 1;
        gbc.gridy++;

        JButton bupdateButton = new JButton("Update");
        bupdateButton.setPreferredSize(HbuttonSize);
        bupdateButton.setFont(plainFont);
        bupdateButton.setBackground(Color.white);
        bookPanel.add(bupdateButton, gbc);

        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new GridBagLayout());
        gbc.gridx = 1;
        gbc.gridy = 0;
        
        
        JLabel transactionLabel = new JLabel("Add Transaction Details");
        transactionLabel.setFont(boldFont);
        transactionPanel.add(transactionLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy++;


        JLabel studentLabel = new JLabel("Student ");
        studentLabel.setFont(plainFont);
        transactionPanel.add(studentLabel, gbc);
        gbc.gridx++;
        JTextField studentTextField = new JTextField(20);
        studentTextField.setFont(plainFont);
        studentTextField.setPreferredSize(tfieldSize);
        transactionPanel.add(studentTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;


        JLabel bookNameLabel = new JLabel("Book ");
        bookNameLabel.setFont(plainFont);
        transactionPanel.add(bookNameLabel, gbc);
        gbc.gridx++;
        JTextField bookField = new JTextField(20);
        bookField.setFont(plainFont);
        bookField.setPreferredSize(tfieldSize);
        transactionPanel.add(bookField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        
        UtilDateModel Tmodel = new UtilDateModel();
        UtilDateModel Rmodel = new UtilDateModel();
        JDatePanelImpl TdatePanel = new JDatePanelImpl(Tmodel);
        JDatePanelImpl RdatePanel = new JDatePanelImpl(Rmodel);


        JLabel tdateLabel = new JLabel("Transaction ");
        tdateLabel.setFont(plainFont);
        transactionPanel.add(tdateLabel, gbc);
        gbc.gridx++;
        JDatePickerImpl TdatePicker = new JDatePickerImpl(TdatePanel);
        TdatePicker.setFont(plainFont);
        TdatePicker.setPreferredSize(tfieldSize);
        transactionPanel.add(TdatePicker, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        
        JLabel rdateLabel = new JLabel("Return ");
        rdateLabel.setFont(plainFont);
        transactionPanel.add(rdateLabel, gbc);
        gbc.gridx++;
        JDatePickerImpl RdatePicker = new JDatePickerImpl(RdatePanel);
        RdatePicker.setFont(plainFont);
        RdatePicker.setPreferredSize(tfieldSize);
        transactionPanel.add(RdatePicker, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        
        JLabel statusLabel = new JLabel("Status ");
        statusLabel.setFont(plainFont);
        transactionPanel.add(statusLabel, gbc);
        gbc.gridx++;
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Returned", "Not Returned"});
        transactionPanel.add(statusComboBox, gbc);
        gbc.gridx = 1;
        gbc.gridy++;

        JButton tupdateButton = new JButton("Update");
        tupdateButton.setPreferredSize(HbuttonSize);
        tupdateButton.setBackground(Color.white);
        tupdateButton.setFont(plainFont);
        transactionPanel.add(tupdateButton, gbc);
        
        
        cards.add(bookPanel, "BookPanel");
        cards.add(transactionPanel, "TransactionPanel");

        JPanel buttonPanel = new JPanel();
        JButton bookButton = new JButton("Book");
        bookButton.setFont(plainFont);
        bookButton.setPreferredSize(HbuttonSize);
        bookButton.setBackground(Color.white);
        JButton transactionButton = new JButton("Transaction");
        transactionButton.setFont(plainFont);
        transactionButton.setPreferredSize(HbuttonSize);
        transactionButton.setBackground(Color.white);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) cards.getLayout();
                cardLayout.show(cards, "BookPanel");
            }
        });
        
        transactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) cards.getLayout();
                cardLayout.show(cards, "TransactionPanel");
            }
        });

        bupdateButton.addActionListener(e -> {
            if (bookNameField.getText().isEmpty() || authorField.getText().isEmpty() ||
                    datePicker.getJFormattedTextField().getText().isEmpty() || categoryField.getText().isEmpty() || languageField.getText().isEmpty()) {
                // Handle the error when the required fields are not filled
                JOptionPane.showMessageDialog(null, "Please fill all the given fields!", "Error", JOptionPane.ERROR_MESSAGE);
                // Clear text fields here
                bookNameField.setText(bookNameField.getText());
                authorField.setText(authorField.getText());
                datePicker.getJFormattedTextField().setText(datePicker.getJFormattedTextField().getText());
                languageField.setText(languageField.getText());
                categoryField.setText(categoryField.getText());
            } else {
                // Handle the successful case here
                String book = bookNameField.getText();
                String author = authorField.getText();
                String category = categoryField.getText();
                String language = languageField.getText();
                UtilDateModel lmodel = (UtilDateModel) datePicker.getModel();
                Date selectedDate = lmodel.getValue();
                java.sql.Date date = new java.sql.Date(selectedDate.getTime());
                System.out.println("success");
                addToBookCollection(book, author, date, language, category);
                BookManagementUI.reloadTable();
                
                bookNameField.setText("");
                authorField.setText("");
                datePicker.getJFormattedTextField().setText("");
                languageField.setText("");
                categoryField.setText("");
            }
        });
        tupdateButton.addActionListener(e -> {
            if (studentTextField.getText().isEmpty() || bookField.getText().isEmpty() ||
                    TdatePicker.getJFormattedTextField().getText().isEmpty() || 
                    RdatePicker.getJFormattedTextField().getText().isEmpty() ) {
                // Handle the error when the required fields are not filled
                JOptionPane.showMessageDialog(null, "Please fill all the given fields!", "Error", JOptionPane.ERROR_MESSAGE);
                // Clear text fields here
                studentTextField.setText(studentTextField.getText());
                bookField.setText(bookField.getText());
                TdatePicker.getJFormattedTextField().setText(TdatePicker.getJFormattedTextField().getText());
                RdatePicker.getJFormattedTextField().setText(RdatePicker.getJFormattedTextField().getText());
            } else {
                // Handle the successful case here
                System.out.println("success");
                String student = studentTextField.getText();
                String book = bookField.getText();
                String status = statusComboBox.getSelectedItem().toString();
                UtilDateModel Tlocalmodel = (UtilDateModel) TdatePicker.getModel();
                Date selectedTDate = Tlocalmodel.getValue();
                java.sql.Date Tdate = new java.sql.Date(selectedTDate.getTime());
                UtilDateModel Rlocalmodel = (UtilDateModel) RdatePicker.getModel();
                Date selectedRDate = Rlocalmodel.getValue();
                java.sql.Date Rdate = new java.sql.Date(selectedRDate.getTime());
                System.out.println("success");
                addToTransactionCollection(student, book, Tdate, Rdate, status);

                bookNameField.setText("");
                authorField.setText("");
                datePicker.getJFormattedTextField().setText("");
                languageField.setText("");
                categoryField.setText("");

            }
        });

        buttonPanel.add(bookButton);
        buttonPanel.add(transactionButton);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));

        mainPanel.add(cards, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        frame.add(mainPanel);
        frame.setVisible(true);
        return frame;
    }
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/dlms";
    private static final String USER = "root";
    private static final String PASSWORD = "Mujthaba@7356";
    public static void addToBookCollection(String book, String author, java.sql.Date date, String language, String category) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            String query = "INSERT INTO books (name, author, category, language, date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book); // Replace with your book name
            preparedStatement.setString(2, author); // Replace with your author name
            preparedStatement.setString(3, category); // Replace with your category
            preparedStatement.setString(4, language); // Replace with your language
            preparedStatement.setDate(5, date); // Use the 'sqlDate' from the previous example

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();


            JOptionPane.showMessageDialog(null, "Successfully Added to the Database", "Success", JOptionPane.DEFAULT_OPTION);
                // Clear text fields here
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addToTransactionCollection(String student, String book, java.sql.Date Tdate, java.sql.Date Rdate, String status) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            String query = "INSERT INTO transactions (user_name, book_name, transaction_date, return_date, transaction_status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student); // Replace with your book name
            preparedStatement.setString(2, book); // Replace with your author name
            preparedStatement.setDate(3, Tdate); // Replace with your category
            preparedStatement.setDate(4, Rdate); // Replace with your language
            preparedStatement.setString(5, status); // Use the 'sqlDate' from the previous example

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();


            JOptionPane.showMessageDialog(null, "Successfully Added to the Database", "Success", JOptionPane.DEFAULT_OPTION);
                // Clear text fields here
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
