package UserInterface;

import java.awt.*;
import java.sql.*;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;


public class TransactionUI {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/dlms";
    private static final String USER = "root";
    private static final String PASSWORD = "Mujthaba@7356";

    public static JFrame displayTransactions() {
        JFrame frame = new JFrame("Transactions");
        frame.setSize(800, 600);
        frame.setVisible(true);
        JPanel panel = new JPanel(new GridLayout(1, 0));

        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10)); 
        panel.setBackground(Color.white);

        JScrollPane scrollPane = new JScrollPane(panel);

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM transactions ORDER BY CASE WHEN transaction_status = 'Not Returned' THEN 1 ELSE 2 END, transaction_date;";
            ResultSet resultSet = statement.executeQuery(query);


            String[] columnNames = { "Index", "Student", "Book", "Transaction", "Return", "Status" };
            DefaultTableModel model = new DefaultTableModel();


            JTable table = new JTable(model){
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
            
            // Add components to the search panel
            searchPanel.add(searchField);
            searchPanel.add(searchButton);

            while (resultSet.next()) {
                int index = resultSet.getRow();
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

            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);

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
        frame.add(scrollPane, BorderLayout.CENTER);
        return frame;
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
}
