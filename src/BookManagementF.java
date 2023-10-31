
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;


public class BookManagementF {
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
    }; // Store the table model as an instance variable
    private static Map<Integer, Vector<Object>> originalData = new HashMap<>();


    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/dlms";
    private static final String USER = "root";
    private static final String PASSWORD = "Mujthaba@7356";

    public JScrollPane displayBooks() {
        JPanel panel = new JPanel(new GridLayout(1, 0));

        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10)); 
        panel.setBackground(Color.white);

        JScrollPane scrollPane = new JScrollPane(panel);

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM books";
            ResultSet resultSet = statement.executeQuery(query);


            String[] columnNames = { "Index", "Name", "Author", "Category", "Language", "Date" };


            
            table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
            table.setFont(new Font("Arial", Font.PLAIN, 16));
            table.setRowHeight(30);
            table.setShowVerticalLines(true);
            table.setGridColor(Color.LIGHT_GRAY);
            table.setIntercellSpacing(new Dimension(10, 5));
            table.setPreferredScrollableViewportSize(new Dimension(500, 70));
            table.setFillsViewportHeight(true);
            model.setColumnIdentifiers(columnNames);


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
            
            // Add components to the search panel
            searchPanel.add(searchField);
            searchPanel.add(searchButton);

            while (resultSet.next()) {
                int index = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");
                String language = resultSet.getString("language");
                Date date = resultSet.getDate("date");
                //Vector<Object> row = new Vector<>();
                // Add other data to the row
                //ImageIcon deleteIcon = new ImageIcon(getClass().getResource("3687412.png"));
                //row.add(deleteIcon);

                Object[] data = { index, name, author, category, language, date.toString()};
                model.addRow(data);
                //table.getColumnModel().getColumn(7).setCellRenderer(new ImageRenderer());
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

            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);


            // Add original data to the map
            for (int i = 0; i < model.getRowCount(); i++) {
                Vector<Object> rowData = new Vector<>();
                for (int j = 0; j < model.getColumnCount(); j++) {
                    rowData.add(model.getValueAt(i, j));
                }
                originalData.put((int) rowData.get(0), rowData);
            }

            // Customize the sorting behavior for the 'Index' column
            sorter.setComparator(0, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            });

            JTableHeader tableHeader = table.getTableHeader();
            tableHeader.setDefaultRenderer(new HeaderRenderer(tableHeader.getDefaultRenderer()));
            table.setTableHeader(tableHeader);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scrollPane;
    }

    static class HeaderRenderer implements TableCellRenderer {
        DefaultTableCellRenderer renderer;

        public HeaderRenderer(TableCellRenderer renderer) {
            this.renderer = (DefaultTableCellRenderer) renderer;
            this.renderer.setHorizontalAlignment(SwingConstants.LEFT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setIcon(UIManager.getIcon("Table.ascendingSortIcon"));
            label.setHorizontalTextPosition(SwingConstants.LEFT);
            return label;
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
            String query = "UPDATE books SET name = ?, author = ?, category = ?, language = ?, date = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
    
            preparedStatement.setString(1, (String) rowData.get(1)); // Assuming name is at index 1
            preparedStatement.setString(2, (String) rowData.get(2)); // Assuming author is at index 2
            preparedStatement.setString(3, (String) rowData.get(3)); // Assuming category is at index 3
            preparedStatement.setString(4, (String) rowData.get(4)); // Assuming language is at index 4
            preparedStatement.setString(5, rowData.get(5).toString()); // Assuming date is at index 5
            preparedStatement.setInt(6, index);
    
            preparedStatement.executeUpdate();
    
            System.out.println("Successfully updated row with index: " + index);
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
    public static void reloadTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the existing data from the table
    
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM books";
            ResultSet resultSet = statement.executeQuery(query);
    
            while (resultSet.next()) {
                int index = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");
                String language = resultSet.getString("language");
                Date date = resultSet.getDate("date");
                //Vector<Object> row = new Vector<>();
                // Add other data to the row
                //ImageIcon deleteIcon = new ImageIcon(BookManagementUI.Class().getResource("3687412.png"));
                //row.add(deleteIcon);
                Object[] data = {index, name, author, category, language, date.toString()};
                model.addRow(data); // Add fetched data to the table model
                //table.getColumnModel().getColumn(7).setCellRenderer(new ImageRenderer());
            }
    
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void displaySearchedBooks(String searchText) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        // Use the searchText to filter the results in the database query
        try {
            // Connect to the database and execute the search query using the 'searchText'
            Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM books WHERE name LIKE ? OR author LIKE ? OR category LIKE ? OR language LIKE ?");
            statement.setString(1, "%" + searchText + "%");
            statement.setString(2, "%" + searchText + "%");
            statement.setString(3, "%" + searchText + "%");
            statement.setString(4, "%" + searchText + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int index = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");
                String language = resultSet.getString("language");
                Date date = resultSet.getDate("date");
                //Vector<Object> row = new Vector<>();
                // Add other data to the row
                //ImageIcon deleteIcon = new ImageIcon(getClass().getResource("3687412.png"));
                //row.add(deleteIcon);
                Object[] data = {index, name, author, category, language, date.toString()};
                model.addRow(data); // Add fetched data to the table model
                //table.getColumnModel().getColumn(7).setCellRenderer(new ImageRenderer());
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
    /*private static class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setIcon((ImageIcon) value);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        }
    }*/
}
