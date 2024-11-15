import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JComboBox<String> roleComboBox;
    
    public LoginGUI() {
        JOptionPane.showMessageDialog(null,"Project Done By Hisham Raihan(URK23CS1028) and Marvan Mahamood(URK23CS1301)","Creators",JOptionPane.INFORMATION_MESSAGE);
        setTitle("Coal Management System Done By Hisham Raihan(URK23CS1028) and Marvaan Mahamood(URK23CS1301)");
        setSize(900, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon img=new ImageIcon("d:/java/download.jpeg");
        setIconImage(img.getImage());    
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        roleComboBox = new JComboBox<>(new String[]{"worker", "admin"});
        
        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleComboBox);
        panel.add(loginButton);
        panel.add(registerButton);
        
        add(panel);
        
        loginButton.addActionListener(new LoginAction());
        registerButton.addActionListener(new RegisterAction());
    }
    
    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();
            
            try (Connection conn = MySQLConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT user_id FROM users WHERE username = ? AND password = ? AND role = ?")) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    if ("worker".equals(role)) {
                        new WorkerGUI(userId).setVisible(true);
                    } else {
                        new AdminGUI(userId).setVisible(true);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials.");
                }
                
            } catch (SQLException ex) {
               // JOptionPane.showMessageDialog(null, "The worker is not registered , pls register first");
                ex.printStackTrace();
            }
        }
    }
    
    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();
            
            try (Connection conn = MySQLConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)")) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registration successful.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        new LoginGUI().setVisible(true);
    }
}
