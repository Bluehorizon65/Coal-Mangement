import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminGUI extends JFrame {
    private int userId;

    public AdminGUI(int userId) {
        ImageIcon img=new ImageIcon("d:/java/download.jpeg");
        setIconImage(img.getImage());    
        this.userId = userId;
        setTitle("Admin Dashboard Done By Hisham Raihan(URK23CS1028) and Marvaan Mahamood(URK23CS1301)");
        setSize(900, 500);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set dark theme colors
        Color backgroundColor = new Color(255, 255, 204); // Light yellow background
        Color buttonColor = new Color(0, 150, 0); // Green for buttons
        Color textColor = new Color(0, 0, 0); // Black for text
        Color buttonTextColor = Color.WHITE; // White for button text

        // Create a panel for the buttons using GridBagLayout for more flexible arrangement
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(backgroundColor);  // Set background color for the panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;  // Allow components to fill the cell both horizontally and vertically
        gbc.weightx = 1;  // Make buttons stretch horizontally
        gbc.weighty = 1;  // Make buttons stretch vertically

        // Create buttons
        JButton showStatusButton = createStyledButton("Show All Workers' Status", buttonColor, buttonTextColor);
        JButton updateAreaButton = createStyledButton("Assign Area", buttonColor, buttonTextColor);
        JButton updateBioButton = createStyledButton("Update Worker Info", buttonColor, buttonTextColor);
        JButton viewTasksButton = createStyledButton("Remove Worker", buttonColor, buttonTextColor);
        JButton addWorkerButton = createStyledButton("Add Worker", buttonColor, buttonTextColor);
        JButton addCoalMiningScheduleButton = createStyledButton("Add Coal Mining Schedule", buttonColor, buttonTextColor);
        JButton ChangePasswordButton = createStyledButton("Change Password", buttonColor, buttonTextColor);
        JButton RegisterWorkerButton = createStyledButton("Register Worker", buttonColor, buttonTextColor);


        // Add buttons to the panel using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(showStatusButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(updateAreaButton, gbc);

        gbc.gridy = 2;
        buttonPanel.add(updateBioButton, gbc);

        gbc.gridy = 3;
        buttonPanel.add(viewTasksButton, gbc);

        gbc.gridy = 4;
        buttonPanel.add(addWorkerButton, gbc);

        gbc.gridy = 5;
        buttonPanel.add(addCoalMiningScheduleButton, gbc);

        gbc.gridy = 6;
        buttonPanel.add(ChangePasswordButton, gbc);

        gbc.gridy = 7;
        buttonPanel.add(RegisterWorkerButton, gbc);

        // Set the layout for the frame and add components
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.CENTER);
        getContentPane().setBackground(backgroundColor);  // Set background color for the main frame

        // Add action listeners to buttons
        showStatusButton.addActionListener(e -> openShowStatusWindow(backgroundColor, textColor));
        updateAreaButton.addActionListener(e -> openUpdateAreaWindow(backgroundColor, textColor));
        updateBioButton.addActionListener(e -> openUpdateBioWindow(backgroundColor, textColor));
        viewTasksButton.addActionListener(e -> openRemoveWorkerWindow(backgroundColor, textColor));
        addWorkerButton.addActionListener(e -> openAddWorkerWindow(backgroundColor, textColor));
        addCoalMiningScheduleButton.addActionListener(e -> openAddCoalMiningScheduleWindow(backgroundColor, textColor));
        ChangePasswordButton.addActionListener(e -> openChangePasswordWindow(backgroundColor, textColor));
        RegisterWorkerButton.addActionListener(e -> RegisterWorker(backgroundColor, textColor));




    }

    private JButton createStyledButton(String text, Color buttonColor, Color textColor) {
        JButton button = new JButton(text);
        button.setBackground(buttonColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 50));
        return button;
    }

    // Method to open Show Status Window
    private void openShowStatusWindow(Color backgroundColor, Color textColor) {
        JFrame showStatusFrame = new JFrame("Show All Workers' Status");
        showStatusFrame.setSize(400, 300);
        showStatusFrame.setLocationRelativeTo(null);
        showStatusFrame.setLayout(new BorderLayout());

        JTextArea statusArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(statusArea);
        statusArea.setEditable(false);
        statusArea.setBackground(backgroundColor);
        statusArea.setForeground(textColor);

        JButton backButton = createStyledButton("Back", backgroundColor.darker(), textColor);
        backButton.addActionListener(e -> showStatusFrame.dispose());

        showStatusFrame.add(scrollPane, BorderLayout.CENTER);
        showStatusFrame.add(backButton, BorderLayout.SOUTH);
        showStatusFrame.getContentPane().setBackground(backgroundColor);

        showStatusFrame.setVisible(true);

        // Fetch and display worker status
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT username, area, bio FROM workers")) {
            ResultSet rs = stmt.executeQuery();
            StringBuilder sb = new StringBuilder();
            int c = 0;
            while (rs.next()) {
                c++;
                String username = rs.getString("username");
                String area = rs.getString("area");
                String bio = rs.getString("bio");
                sb.append(c).append(". ").append("User Name: ").append(username).append(", Area: ").append(area).append(", Bio: ").append(bio).append("\n\n");
            }

            if (sb.length() > 0) {
                statusArea.setText(sb.toString());
            } else {
                statusArea.setText("No data found.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to open Update Area Window
    private void openUpdateAreaWindow(Color backgroundColor, Color textColor) {
        JFrame updateAreaFrame = new JFrame("Assign Area");
        updateAreaFrame.setSize(400, 300);
        updateAreaFrame.setLocationRelativeTo(null);
        updateAreaFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(backgroundColor);
        inputPanel.setForeground(textColor);

        JLabel usernameLabel = createStyledLabel("Username:", textColor);
        JTextField usernameField = new JTextField(20);
        usernameField.setBackground(backgroundColor);
        usernameField.setForeground(textColor);

        JLabel areaLabel = createStyledLabel("Area:", textColor);
        JTextField areaField = new JTextField(20);
        areaField.setBackground(backgroundColor);
        areaField.setForeground(textColor);

        JLabel workerIdLabel = createStyledLabel("Worker ID:", textColor);
        JTextField workeridField = new JTextField(20);
        workeridField.setBackground(backgroundColor);
        workeridField.setForeground(textColor);

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(areaLabel);
        inputPanel.add(areaField);
        inputPanel.add(workerIdLabel);
        inputPanel.add(workeridField);

        JButton updateButton = createStyledButton("Update", backgroundColor.darker(), textColor);
        updateButton.addActionListener(e -> {
            String area = areaField.getText();
            String username = usernameField.getText();
            String worker_id = workeridField.getText();

            try (Connection conn = MySQLConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE workers SET area = ? WHERE username = ? AND worker_id = ?")) {
                stmt.setString(1, area);
                stmt.setString(2, username);
                stmt.setString(3, worker_id);
                int updated = stmt.executeUpdate();

                JOptionPane.showMessageDialog(updateAreaFrame, updated > 0 ? "Area updated successfully for " + username : "No worker found with username: " + username);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JButton backButton = createStyledButton("Back", backgroundColor.darker(), textColor);
        backButton.addActionListener(e -> updateAreaFrame.dispose());

        updateAreaFrame.add(inputPanel, BorderLayout.CENTER);
        updateAreaFrame.add(updateButton, BorderLayout.EAST);
        updateAreaFrame.add(backButton, BorderLayout.SOUTH);
        updateAreaFrame.getContentPane().setBackground(backgroundColor);

        updateAreaFrame.setVisible(true);
    }

    // Method to open Update Bio Window
    private void openUpdateBioWindow(Color backgroundColor, Color textColor) {
        JFrame updateBioFrame = new JFrame("Update Worker Info");
        updateBioFrame.setSize(400, 300);
        updateBioFrame.setLocationRelativeTo(null);
        updateBioFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(backgroundColor);
        inputPanel.setForeground(textColor);

        JLabel usernameLabel = createStyledLabel("Username:", textColor);
        JTextField usernameField = new JTextField(20);
        usernameField.setBackground(backgroundColor);
        usernameField.setForeground(textColor);

        JLabel bioLabel = createStyledLabel("Bio:", textColor);
        JTextField bioField = new JTextField(20);
        bioField.setBackground(backgroundColor);
        bioField.setForeground(textColor);

        JLabel workerIdLabel = createStyledLabel("Worker ID:", textColor);
        JTextField workeridField = new JTextField(20);
        workeridField.setBackground(backgroundColor);
        workeridField.setForeground(textColor);

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(bioLabel);
        inputPanel.add(bioField);
        inputPanel.add(workerIdLabel);
        inputPanel.add(workeridField);

        JButton updateButton = createStyledButton("Update", backgroundColor.darker(), textColor);
        updateButton.addActionListener(e -> {
            String bio = bioField.getText();
            String username = usernameField.getText();
            String worker_id = workeridField.getText();

            try (Connection conn = MySQLConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE workers SET bio = ? WHERE username = ? AND worker_id = ?")) {
                stmt.setString(1, bio);
                stmt.setString(2, username);
                stmt.setString(3, worker_id);
                int updated = stmt.executeUpdate();

                JOptionPane.showMessageDialog(updateBioFrame, updated > 0 ? "Bio updated successfully for " + username : "No worker found with username: " + username);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JButton backButton = createStyledButton("Back", backgroundColor.darker(), textColor);
        backButton.addActionListener(e -> updateBioFrame.dispose());

        updateBioFrame.add(inputPanel, BorderLayout.CENTER);
        updateBioFrame.add(updateButton, BorderLayout.EAST);
        updateBioFrame.add(backButton, BorderLayout.SOUTH);
        updateBioFrame.getContentPane().setBackground(backgroundColor);

        updateBioFrame.setVisible(true);
    }

    // Method to open Remove Worker Window
    private void openRemoveWorkerWindow(Color backgroundColor, Color textColor) {
        JFrame removeWorkerFrame = new JFrame("Remove Worker");
        removeWorkerFrame.setSize(400, 300);
        removeWorkerFrame.setLocationRelativeTo(null);
        removeWorkerFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(backgroundColor);
        inputPanel.setForeground(textColor);

        JLabel usernameLabel = createStyledLabel("Username:", textColor);
        JTextField usernameField = new JTextField(20);
        usernameField.setBackground(backgroundColor);
        usernameField.setForeground(textColor);

        JLabel workerIdLabel = createStyledLabel("Worker ID:", textColor);
        JTextField workerIdField = new JTextField(20);
        workerIdField.setBackground(backgroundColor);
        workerIdField.setForeground(textColor);

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(workerIdLabel);
        inputPanel.add(workerIdField);

        JButton removeButton = createStyledButton("Remove", backgroundColor.darker(), textColor);
        removeButton.addActionListener(e -> {
            String username = usernameField.getText();
            String worker_id = workerIdField.getText();

            try (Connection conn = MySQLConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM workers WHERE username = ? AND worker_id = ?")) {
                stmt.setString(1, username);
                stmt.setString(2, worker_id);
                int deleted = stmt.executeUpdate();

                JOptionPane.showMessageDialog(removeWorkerFrame, deleted > 0 ? "Worker removed successfully." : "No worker found with username: " + username);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JButton backButton = createStyledButton("Back", backgroundColor.darker(), textColor);
        backButton.addActionListener(e -> removeWorkerFrame.dispose());

        removeWorkerFrame.add(inputPanel, BorderLayout.CENTER);
        removeWorkerFrame.add(removeButton, BorderLayout.EAST);
        removeWorkerFrame.add(backButton, BorderLayout.SOUTH);
        removeWorkerFrame.getContentPane().setBackground(backgroundColor);

        removeWorkerFrame.setVisible(true);
    }


    private void openAddWorkerWindow(Color backgroundColor, Color textColor) {
        JFrame addWorkerFrame = new JFrame("Add Worker");
        addWorkerFrame.setSize(400, 300);
        addWorkerFrame.setLocationRelativeTo(null);
        addWorkerFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(backgroundColor);
        inputPanel.setForeground(textColor);


        // user id
        JLabel useridLabel = createStyledLabel("User id:", textColor);
        JTextField useridField = new JTextField(20);
        useridField.setBackground(backgroundColor);
        useridField.setForeground(textColor);

        //worker id

        JLabel workeridLabel = createStyledLabel("Worker id:", textColor);
        JTextField workeridField = new JTextField(20);
        workeridField.setBackground(backgroundColor);
        workeridField.setForeground(textColor);

        JLabel usernameLabel = createStyledLabel("Username:", textColor);
        JTextField usernameField = new JTextField(20);
        usernameField.setBackground(backgroundColor);
        usernameField.setForeground(textColor);

        JLabel areaLabel = createStyledLabel("Area:", textColor);
        JTextField areaField = new JTextField(20);
        areaField.setBackground(backgroundColor);
        areaField.setForeground(textColor);

        JLabel bioLabel = createStyledLabel("Bio:", textColor);
        JTextField bioField = new JTextField(20);
        bioField.setBackground(backgroundColor);
        bioField.setForeground(textColor);

        inputPanel.add(useridLabel);
        inputPanel.add(useridField);
        inputPanel.add(workeridLabel);
        inputPanel.add(workeridField);
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(areaLabel);
        inputPanel.add(areaField);
        inputPanel.add(bioLabel);
        inputPanel.add(bioField);

        JButton addButton = createStyledButton("Add", backgroundColor.darker(), textColor);
        addButton.addActionListener(e -> {
            String username = usernameField.getText();
            String area = areaField.getText();
            String bio = bioField.getText();
            int userid = Integer.parseInt(useridField.getText());
            int workerid = Integer.parseInt(workeridField.getText());


            try (Connection conn = MySQLConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO workers (worker_id,user_id,area, bio,username) VALUES (?,?,?, ?, ?)")) {
                
                stmt.setInt(1,workerid);
                stmt.setInt(2,userid);
                stmt.setString(5, username);
                stmt.setString(3, area);
                stmt.setString(4, bio);
                int added = stmt.executeUpdate();

                JOptionPane.showMessageDialog(addWorkerFrame, added > 0 ? "Worker added successfully." : "Failed to add worker.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(addWorkerFrame, "Worker not registered. Please register.", "Error", JOptionPane.ERROR_MESSAGE);

            }
        });

        JButton backButton = createStyledButton("Back", backgroundColor.darker(), textColor);
        backButton.addActionListener(e -> addWorkerFrame.dispose());

        addWorkerFrame.add(inputPanel, BorderLayout.CENTER);
        addWorkerFrame.add(addButton, BorderLayout.EAST);                                    
        addWorkerFrame.add(backButton, BorderLayout.SOUTH);
        addWorkerFrame.getContentPane().setBackground(backgroundColor);

        addWorkerFrame.setVisible(true);
    }

    private JLabel createStyledLabel(String text, Color textColor) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        return label;
    }
    private void openAddCoalMiningScheduleWindow(Color backgroundColor, Color textColor) {
        JFrame addScheduleFrame = new JFrame("Add Coal Mining Schedule");
        addScheduleFrame.setSize(400, 300);
        addScheduleFrame.setLocationRelativeTo(null);
        addScheduleFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(backgroundColor);
        inputPanel.setForeground(textColor);

        JLabel areaLabel = createStyledLabel("Area:", textColor);
        JTextField areaField = new JTextField(20);
        areaField.setBackground(backgroundColor);
        areaField.setForeground(textColor);

        
        JLabel coalnameLabel = createStyledLabel("Coal name:", textColor);
        JTextField coalField = new JTextField(20);
        coalField.setBackground(backgroundColor);
        coalField.setForeground(textColor);



        JLabel startTimeLabel = createStyledLabel("Start Time (yyyy-MM-dd HH:mm):", textColor);
        JTextField startTimeField = new JTextField(20);
        startTimeField.setBackground(backgroundColor);
        startTimeField.setForeground(textColor);

        JLabel endTimeLabel = createStyledLabel("End Time (yyyy-MM-dd HH:mm):", textColor);
        JTextField endTimeField = new JTextField(20);
        endTimeField.setBackground(backgroundColor);
        endTimeField.setForeground(textColor);

        JLabel workerIdLabel = createStyledLabel("Worker ID:", textColor);
        JTextField workerIdField = new JTextField(20);
        workerIdField.setBackground(backgroundColor);
        workerIdField.setForeground(textColor);

        inputPanel.add(areaLabel);
        inputPanel.add(areaField);
        inputPanel.add(coalnameLabel);
        inputPanel.add(coalField);
        inputPanel.add(startTimeLabel);
        inputPanel.add(startTimeField);
        inputPanel.add(endTimeLabel);
        inputPanel.add(endTimeField);
        inputPanel.add(workerIdLabel);
        inputPanel.add(workerIdField);

        JButton addButton = createStyledButton("Add Schedule", backgroundColor.darker(), textColor);
        addButton.addActionListener(e -> {
            String area = areaField.getText();
            String coal_name=coalField.getText();
            String startTime = startTimeField.getText();
            String endTime = endTimeField.getText();
            int workerId = Integer.parseInt(workerIdField.getText());

            try (Connection conn = MySQLConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO specific_coal_mining (coal_name, worker_id,area, start_time, end_time) VALUES (?,?,?, ?, ?)")) {
                stmt.setString(1,coal_name);
                stmt.setString(3, area);
                stmt.setString(4, startTime);
                stmt.setString(5, endTime);
                stmt.setInt(2, workerId);
                int added = stmt.executeUpdate();

                JOptionPane.showMessageDialog(addScheduleFrame, added > 0 ? "Coal mining schedule added successfully." : "Failed to add coal mining schedule.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JButton backButton = createStyledButton("Back", backgroundColor.darker(), textColor);
        backButton.addActionListener(e -> addScheduleFrame.dispose());

        addScheduleFrame.add(inputPanel, BorderLayout.CENTER);
        addScheduleFrame.add(addButton, BorderLayout.EAST);
        addScheduleFrame.add(backButton, BorderLayout.SOUTH);
        addScheduleFrame.getContentPane().setBackground(backgroundColor);

        addScheduleFrame.setVisible(true);
    }


    private void openChangePasswordWindow(Color backgroundColor, Color textColor) {
        JFrame changePasswordFrame = new JFrame("Change Password");
        changePasswordFrame.setSize(400, 300);
        changePasswordFrame.setLocationRelativeTo(null);
        changePasswordFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(backgroundColor);
        inputPanel.setForeground(textColor);

        JLabel currentPasswordLabel = createStyledLabel("Current Password:", textColor);
        JLabel newPasswordLabel = createStyledLabel("New Password:", textColor);
        JLabel confirmPasswordLabel = createStyledLabel("Confirm Password:", textColor);

        JPasswordField currentPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        inputPanel.add(currentPasswordLabel);
        inputPanel.add(currentPasswordField);
        inputPanel.add(newPasswordLabel);
        inputPanel.add(newPasswordField);
        inputPanel.add(confirmPasswordLabel);
        inputPanel.add(confirmPasswordField);

        JButton submitButton = createStyledButton("Submit", backgroundColor.darker(), textColor);
        submitButton.addActionListener(e -> {
            String currentPassword = new String(currentPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
        
            if (newPassword.equals(confirmPassword)) {
                try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement checkStmt = conn.prepareStatement("SELECT password FROM users WHERE role = 'admin'");
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE role = 'admin'")) {
           
               // Execute the query to get the current password of the admin role
               ResultSet rs = checkStmt.executeQuery();
           
               if (rs.next() && rs.getString("password").equals(currentPassword)) {
                   // Update the password
                   updateStmt.setString(1, newPassword);
                   int updated = updateStmt.executeUpdate();
           
                   JOptionPane.showMessageDialog(changePasswordFrame, updated > 0 ? "Password changed successfully." : "Failed to change password.");
               } else {
                   JOptionPane.showMessageDialog(changePasswordFrame, "Current password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
               }
           } catch (SQLException ex) {
               ex.printStackTrace();
               JOptionPane.showMessageDialog(changePasswordFrame, "An error occurred while changing the password.", "Error", JOptionPane.ERROR_MESSAGE);
           }
            } else {
                JOptionPane.showMessageDialog(changePasswordFrame, "New password and confirm password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        changePasswordFrame.add(inputPanel, BorderLayout.CENTER);
        changePasswordFrame.add(submitButton, BorderLayout.SOUTH);
        changePasswordFrame.setVisible(true);
    }
    
   
   
    private void RegisterWorker(Color backgroundColor, Color textColor) {
        JFrame addWorkerFrame = new JFrame("Register Worker");
        addWorkerFrame.setSize(400, 300);
        addWorkerFrame.setLocationRelativeTo(null);
        addWorkerFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBackground(backgroundColor);
        inputPanel.setForeground(textColor);


        // user id
        JLabel useridLabel = createStyledLabel("User id:", textColor);
        JTextField useridField = new JTextField(20);
        useridField.setBackground(backgroundColor);
        useridField.setForeground(textColor);

        //worker id


        JLabel usernameLabel = createStyledLabel("Username:", textColor);
        JTextField usernameField = new JTextField(20);
        usernameField.setBackground(backgroundColor);
        usernameField.setForeground(textColor);

        JLabel passwordLabel = createStyledLabel("Password:", textColor);
        JTextField passwordField = new JTextField(20);
        passwordLabel.setBackground(backgroundColor);
        passwordField.setForeground(textColor);

        JLabel roleLabel = createStyledLabel("Role:", textColor);
        JTextField roleField = new JTextField(20);
        roleLabel.setBackground(backgroundColor);
        roleField.setForeground(textColor);

        inputPanel.add(useridLabel);
        inputPanel.add(useridField);
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(roleLabel);
        inputPanel.add(roleField);

        JButton addButton = createStyledButton("Add", backgroundColor.darker(), textColor);
        addButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleField.getText();
            int userid = Integer.parseInt(useridField.getText());


            try (Connection conn = MySQLConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (user_id,username,password,role) VALUES (?,?,?, ?)")) {
                
                stmt.setInt(1,userid);
                stmt.setString(2, username);
                stmt.setString(3, password);
                stmt.setString(4, role);
                int added = stmt.executeUpdate();

                JOptionPane.showMessageDialog(addWorkerFrame, added > 0 ? "Worker registerd successfully." : "Failed to register worker.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JButton backButton = createStyledButton("Back", backgroundColor.darker(), textColor);
        backButton.addActionListener(e -> addWorkerFrame.dispose());

        addWorkerFrame.add(inputPanel, BorderLayout.CENTER);
        addWorkerFrame.add(addButton, BorderLayout.EAST);                                    
        addWorkerFrame.add(backButton, BorderLayout.SOUTH);
        addWorkerFrame.getContentPane().setBackground(backgroundColor);

        addWorkerFrame.setVisible(true);
    }


    //public static void main(String[] args) {
        // Example: Instantiate the Admin GUI with a sample user ID
      //  SwingUtilities.invokeLater(() -> new AdminGUI(1).setVisible(true));
   // }
}
