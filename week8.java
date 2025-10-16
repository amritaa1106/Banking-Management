import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class week8 extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bankcontent";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Discodisco11*";
    
    private JTable accountTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, balanceField;
    private JButton addButton, deleteButton, updateButton, refreshButton, testConnectionButton;
    private JLabel statusLabel;
    
    public week8() {
        initializeGUI();
        setupDatabase();
        loadAccountData();
    }
    
    private void initializeGUI() {
        setTitle("Banking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel topPanel = createTopPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel bottomPanel = createBottomPanel();
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        panel.add(nameField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Balance:"), gbc);
        gbc.gridx = 3;
        balanceField = new JTextField(10);
        panel.add(balanceField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        addButton = new JButton("Add");
        addButton.addActionListener(e -> addAccount());
        panel.add(addButton, gbc);
        
        gbc.gridx = 1;
        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateAccount());
        panel.add(updateButton, gbc);
        
        gbc.gridx = 2;
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteAccount());
        panel.add(deleteButton, gbc);
        
        gbc.gridx = 3;
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadAccountData());
        panel.add(refreshButton, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        testConnectionButton = new JButton("Test Connection");
        testConnectionButton.addActionListener(e -> testConnection());
        panel.add(testConnectionButton, gbc);
        
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"Account No", "Name", "Balance", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        accountTable = new JTable(tableModel);
        accountTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateFieldsFromSelection();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(accountTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Ready");
        panel.add(new JLabel("Status: "));
        panel.add(statusLabel);
        return panel;
    }
    
    private void setupDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            updateStatus("Driver loaded", false);
        } catch (ClassNotFoundException e) {
            updateStatus("Driver not found", true);
            JOptionPane.showMessageDialog(this, "MySQL Driver not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    private void testConnection() {
        try (Connection conn = getConnection()) {
            updateStatus("Connection successful", false);
            JOptionPane.showMessageDialog(this, "Connected to: " + conn.getCatalog(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            updateStatus("Connection failed", true);
            JOptionPane.showMessageDialog(this, "Connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadAccountData() {
        try (Connection conn = getConnection()) {
            String query = "SELECT acc_no, name, balance, status FROM Accounts ORDER BY acc_no";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            tableModel.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("acc_no"),
                    rs.getString("name"),
                    String.format("$%.2f", rs.getDouble("balance")),
                    rs.getString("status")
                };
                tableModel.addRow(row);
            }
            
            updateStatus("Records: " + tableModel.getRowCount(), false);
            
        } catch (SQLException e) {
            updateStatus("Load error", true);
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addAccount() {
        if (!validateInput()) return;
        
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO Accounts (name, balance) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, nameField.getText().trim());
            stmt.setDouble(2, Double.parseDouble(balanceField.getText().trim()));
            
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                updateStatus("Account added", false);
                clearFields();
                loadAccountData();
            }
            
        } catch (SQLException e) {
            updateStatus("Add error", true);
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateAccount() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an account", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateInput()) return;
        
        try (Connection conn = getConnection()) {
            int accountNo = (Integer) tableModel.getValueAt(selectedRow, 0);
            String query = "UPDATE Accounts SET name = ?, balance = ? WHERE acc_no = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, nameField.getText().trim());
            stmt.setDouble(2, Double.parseDouble(balanceField.getText().trim()));
            stmt.setInt(3, accountNo);
            
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                updateStatus("Account updated", false);
                clearFields();
                loadAccountData();
            }
            
        } catch (SQLException e) {
            updateStatus("Update error", true);
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteAccount() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an account", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Delete account?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = getConnection()) {
                int accountNo = (Integer) tableModel.getValueAt(selectedRow, 0);
                String query = "DELETE FROM Accounts WHERE acc_no = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, accountNo);
                
                int result = stmt.executeUpdate();
                
                if (result > 0) {
                    updateStatus("Account deleted", false);
                    clearFields();
                    loadAccountData();
                }
                
            } catch (SQLException e) {
                updateStatus("Delete error", true);
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void populateFieldsFromSelection() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow != -1) {
            nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            String balance = tableModel.getValueAt(selectedRow, 2).toString();
            balance = balance.replace("$", "").replace(",", "");
            balanceField.setText(balance);
        }
    }
    
    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter account name", "Error", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        try {
            double balance = Double.parseDouble(balanceField.getText().trim());
            if (balance < 0) {
                JOptionPane.showMessageDialog(this, "Balance cannot be negative", "Error", JOptionPane.ERROR_MESSAGE);
                balanceField.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid balance", "Error", JOptionPane.ERROR_MESSAGE);
            balanceField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void clearFields() {
        nameField.setText("");
        balanceField.setText("");
        accountTable.clearSelection();
    }
    
    private void updateStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setForeground(isError ? Color.RED : Color.BLACK);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new week8().setVisible(true));
    }
}