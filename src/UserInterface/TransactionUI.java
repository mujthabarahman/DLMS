package UserInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;



public class TransactionUI {

    static DefaultTableModel model = new DefaultTableModel();
    static JTable table = new JTable(model){
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component component = super.prepareRenderer(renderer, row, column);
            if (row % 2 == 0) {
                component.setBackground(Color.WHITE);
            } else {
                component.setBackground(new Color(240, 240, 240)); // Light gray
            }
            return component;
        }
    };
    private static Map<Integer, Vector<Object>> originalData = new HashMap<>();
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/dlms";
    private static final String USER = "root";
    private static final String PASSWORD = "Mujthaba@7356";

    public static JFrame displayTransactions() {
        JFrame frame = new JFrame("Transactions");
        frame.setSize(900, 600);
        frame.setVisible(true);
        JPanel panel = new JPanel(new GridLayout(1, 0));

        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10)); 
        panel.setBackground(Color.white);

        JScrollPane scrollPane = new JScrollPane(panel);

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM transactions;";
            ResultSet resultSet = statement.executeQuery(query);


            String[] columnNames = { "Index", "Student", "Book", "Transaction", "Return", "Status" };
            
            table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
            table.setFont(new Font("Arial", Font.PLAIN, 16));
            table.setRowHeight(30);
            table.setShowVerticalLines(true);
            table.setGridColor(Color.LIGHT_GRAY);
            table.setIntercellSpacing(new Dimension(10, 5));
            table.setPreferredScrollableViewportSize(new Dimension(500, 70));
            table.setFillsViewportHeight(true);
            model.setColumnIdentifiers(columnNames);
            model.setRowCount(0);


            JPanel searchPanel = new JPanel();
            searchPanel.setBackground(Color.white);
            searchPanel.setPreferredSize(new Dimension(800, 60)); // Set the preferred size of the search panel
            
            JTextField searchField = new JTextField(); // Adjust the size as needed
            searchField.setPreferredSize(new Dimension(500, 30));
            
            JButton searchButton = new JButton("Search");
            searchButton.setPreferredSize(new Dimension(80, 30));
            searchButton.setBackground(new Color(40,148,1));
            searchButton.setForeground(Color.white);
            
            searchButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    displaySearchedBooks(searchField.getText());
                }
            });

            int buttonWidth = 150;
            int buttonHeight = 30;
            JButton updateButton = new RoundButton("Update",Color.white,0);
            updateButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            //updateButton.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add padding
            updateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Handle navigation to the home page
                    System.out.println("inside actionlistener");
                    handleUpdateButtonClick();
                }
            });
            
            // Add components to the search panel
            searchPanel.add(searchField);
            searchPanel.add(searchButton);
            searchPanel.add(updateButton);

            while (resultSet.next()) {
                int index = resultSet.getInt("transaction_id");
                String student = resultSet.getString("user_name");
                String book = resultSet.getString("book_name");
                Date transaction_date = resultSet.getDate("transaction_date");
                Date return_date = resultSet.getDate("return_date");
                String status = resultSet.getString("transaction_status");
                Object[] data = { index, student, book, transaction_date, return_date, status };
                model.addRow(data);
            }

            resultSet.close();
            statement.close();
            connection.close();

            panel.add(searchPanel, BorderLayout.NORTH);

            JScrollPane scrollPane2 = new JScrollPane(table);
            // Customize the scrollbar colors
            JScrollBar verticalScrollBar = scrollPane2.getVerticalScrollBar();
            verticalScrollBar.setBackground(new Color(240,240,240)); // Change the background color of the scrollbar
            verticalScrollBar.setForeground(Color.gray); // Change the foreground color of the scrollbar


            panel.add(scrollPane2, BorderLayout.CENTER);

            // Add original data to the map
            for (int i = 0; i < model.getRowCount(); i++) {
                Vector<Object> rowData = new Vector<>();
                for (int j = 0; j < model.getColumnCount(); j++) {
                    rowData.add(model.getValueAt(i, j));
                }
                originalData.put((int) rowData.get(0), rowData);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        frame.add(scrollPane, BorderLayout.CENTER);
        return frame;
    }

    
    private static void displaySearchedBooks(String searchText) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        // Use the searchText to filter the results in the database query
        try {
            // Connect to the database and execute the search query using the 'searchText'
            Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions WHERE user_name LIKE ? OR book_name LIKE ? OR transaction_status LIKE ?;");
            statement.setString(1, "%" + searchText + "%");
            statement.setString(2, "%" + searchText + "%");
            statement.setString(3, "%" + searchText + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int index = resultSet.getInt("transaction_id");
                String student = resultSet.getString("user_name");
                String book = resultSet.getString("book_name");
                Date Tdate = resultSet.getDate("transaction_date");
                Date Rdate = resultSet.getDate("return_date");
                String status = resultSet.getString("transaction_status");
                Object[] data = {index, student, book, Tdate, Rdate, status};
                model.addRow(data); // Add fetched data to the table model
            }

            // Rest of the code remains the same as the 'displayBooks' method, but use the resultSet from the search query
            resultSet.close();
            statement.close();
            connection.close();
            // ...
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void handleUpdateButtonClick() {
        DefaultTableModel cmodel = model;
        // Check the table for any updates and update the database accordingly
        for (int i = 0; i < cmodel.getRowCount(); i++) {
            Vector<Object> rowData = new Vector<>();
            for (int j = 0; j < cmodel.getColumnCount(); j++) {
                rowData.add(cmodel.getValueAt(i, j));
            }
            int index = (int) cmodel.getValueAt(i, 0);
            if (originalData.containsKey(index)) {
                // Compare with the original data and update the database if necessary
                if (!isVectorEqual(originalData.get(index), rowData)) {
                    updateDatabase(rowData, index);
                }
            }
        }
    }
    private static boolean isVectorEqual(Vector<Object> vector1, Vector<Object> vector2) {
        if (vector1.size() != vector2.size()) {
            return false;
        }
        for (int i = 0; i < vector1.size(); i++) {
            if (!vector1.get(i).equals(vector2.get(i))) {
                return false;
            }
        }
        return true;
    }
    

    private static void updateDatabase(Vector<Object> rowData, int index) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            String query = "UPDATE transactions SET user_name = ?, book_name = ?, transaction_date = ?, return_date = ?, transaction_status = ? WHERE transaction_id = ?";
            preparedStatement = connection.prepareStatement(query);
    
            preparedStatement.setString(1, (String) rowData.get(1)); 
            preparedStatement.setString(2, (String) rowData.get(2)); 
            preparedStatement.setString(3, rowData.get(3).toString());
            preparedStatement.setString(4, rowData.get(4).toString());
            preparedStatement.setString(5, (String) rowData.get(5));
            preparedStatement.setInt(6, index);
    
            preparedStatement.executeUpdate();
    
            System.out.println("Successfully updated row with index: " + index);
            displaySearchedBooks("");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
