import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
public class WorkerGUI extends JFrame {
    private int userId;
    private JButton showStatusButton;
    private JButton updateAreaButton;
    private JButton updateBioButton;
    private JButton viewTasksButton;
    private JButton changePasswordButton;
    private JButton reportCoalMinedButton;
    private JButton viewScheduledCoalButton;
    private JButton generateCoalReportButton;
    private JButton generateCoalGraphButton;

    public WorkerGUI(int userId) {
        ImageIcon img=new ImageIcon("d:/java/download.jpeg");
        setIconImage(img.getImage());    
        this.userId = userId;
        setTitle("Worker Dashboard Done By Hisham Raihan(URK23CS1028) and Marvaan Mahamood(URK23CS1301)");
        setSize(900, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set GUI background color to light grey
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // GUI label for Worker Dashboard
        JLabel titleLabel = new JLabel("Worker Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.LIGHT_GRAY);
        titleLabel.setForeground(new Color(144, 238, 144)); // Light green color for label text
        add(titleLabel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(9, 1));
        menuPanel.setBackground(Color.LIGHT_GRAY);

        // Define light green color for buttons
        Color lightGreen = new Color(144, 238, 144);
        
        showStatusButton = createStyledButton("Show Status", lightGreen);
        updateAreaButton = createStyledButton("Update Area", lightGreen);
        updateBioButton = createStyledButton("Update Bio", lightGreen);
        viewTasksButton = createStyledButton("View Assigned Tasks", lightGreen);
        reportCoalMinedButton = createStyledButton("Report Coal Mined", lightGreen);
        viewScheduledCoalButton = createStyledButton("View Scheduled Coal Mining", lightGreen);
        generateCoalReportButton = createStyledButton("Generate Coal Report", lightGreen);
        generateCoalGraphButton = createStyledButton("Generate Coal Graph", lightGreen);
        changePasswordButton = createStyledButton("Change Password", lightGreen);
        

        menuPanel.add(showStatusButton);
        menuPanel.add(updateAreaButton);
        menuPanel.add(updateBioButton);
        menuPanel.add(viewTasksButton);
        menuPanel.add(reportCoalMinedButton);
        menuPanel.add(viewScheduledCoalButton);
        menuPanel.add(generateCoalReportButton);
        menuPanel.add(changePasswordButton);
        menuPanel.add(generateCoalGraphButton);

        add(menuPanel, BorderLayout.CENTER);

        // Event Listeners
        showStatusButton.addActionListener(e -> new ShowStatusFrame(userId).setVisible(true));
        updateAreaButton.addActionListener(e -> new UpdateAreaFrame(userId).setVisible(true));
        updateBioButton.addActionListener(e -> new UpdateBioFrame(userId).setVisible(true));
        viewTasksButton.addActionListener(e -> new ViewTasksFrame(userId).setVisible(true));
        reportCoalMinedButton.addActionListener(e -> new ReportCoalMinedFrame(userId).setVisible(true));
        viewScheduledCoalButton.addActionListener(e -> new ViewScheduledCoalFrame(userId).setVisible(true));
        changePasswordButton.addActionListener(e -> new ChangePasswordFrame(userId).setVisible(true));
        generateCoalReportButton.addActionListener(e -> new GenerateCoalReportFrame(userId).setVisible(true));
        generateCoalGraphButton.addActionListener(e -> new GenerateCoalGraphFrame(userId).setVisible(true));
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    // Frames for different actions
    private static class ShowStatusFrame extends JFrame {
        public ShowStatusFrame(int userId) {
            setTitle("Show Status");
            setSize(300, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JTextArea statusArea = new JTextArea();
            add(new JScrollPane(statusArea), BorderLayout.CENTER);

            try (Connection conn = MySQLConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT area, bio FROM workers WHERE user_id = ?")) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String area = rs.getString("area");
                    String bio = rs.getString("bio");
                    statusArea.setText("Area: " + area + "\nBio: " + bio);
                } else {
                    statusArea.setText("No data found.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class UpdateAreaFrame extends JFrame {
        public UpdateAreaFrame(int userId) {
            setTitle("Update Area");
            setSize(300, 150);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel();
            JLabel label = new JLabel("Enter new area:");
            JTextField areaField = new JTextField(15);
            JButton updateButton = new JButton("Update");

            panel.add(label);
            panel.add(areaField);
            panel.add(updateButton);
            add(panel, BorderLayout.CENTER);

            updateButton.addActionListener(e -> {
                String area = areaField.getText();
                try (Connection conn = MySQLConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("UPDATE workers SET area = ? WHERE user_id = ?")) {
                    stmt.setString(1, area);
                    stmt.setInt(2, userId);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Area updated successfully.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    private static class UpdateBioFrame extends JFrame {
        public UpdateBioFrame(int userId) {
            setTitle("Update Bio");
            setSize(300, 150);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel();
            JLabel label = new JLabel("Enter new bio:");
            JTextField bioField = new JTextField(15);
            JButton updateButton = new JButton("Update");

            panel.add(label);
            panel.add(bioField);
            panel.add(updateButton);
            add(panel, BorderLayout.CENTER);

            updateButton.addActionListener(e -> {
                String bio = bioField.getText();
                try (Connection conn = MySQLConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("UPDATE workers SET bio = ? WHERE user_id = ?")) {
                    stmt.setString(1, bio);
                    stmt.setInt(2, userId);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Bio updated successfully.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    private static class ViewTasksFrame extends JFrame {
        public ViewTasksFrame(int userId) {
            setTitle("View Assigned Tasks");
            setSize(300, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JTextArea tasksArea = new JTextArea();
            add(new JScrollPane(tasksArea), BorderLayout.CENTER);

            try (Connection conn = MySQLConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT task_description FROM tasks JOIN workers ON tasks.worker_id = workers.user_id WHERE workers.user_id = ?")) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    sb.append(rs.getString("task_description")).append("\n");
                }
                tasksArea.setText(sb.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class ReportCoalMinedFrame extends JFrame {
        public ReportCoalMinedFrame(int userId) {
            setTitle("Report Coal Mined");
            setSize(300, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel(new GridLayout(5, 2));
            JLabel coalNameLabel = new JLabel("Enter coal name:");
            JTextField coalNameField = new JTextField(15);
            JLabel amountLabel = new JLabel("Enter amount mined:");
            JTextField amountField = new JTextField(15);
            JLabel dateLabel = new JLabel("Enter date (YYYY-MM-DD):");
            JTextField dateField = new JTextField(15);
            JLabel workeridLabel = new JLabel("Enter worker id:");
            JTextField workeridDField = new JTextField(15);
            JButton reportButton = new JButton("Report");

            panel.add(coalNameLabel);
            panel.add(coalNameField);
            panel.add(amountLabel);
            panel.add(amountField);
            panel.add(dateLabel);
            panel.add(dateField);
            panel.add(workeridLabel);
            panel.add(workeridDField);
            panel.add(reportButton);

            add(panel, BorderLayout.CENTER);

            reportButton.addActionListener(e -> {
                String coalName = coalNameField.getText();
                int amount = Integer.parseInt(amountField.getText());
                String date = dateField.getText();
                int workerid = Integer.parseInt(workeridDField.getText());

                try (Connection conn = MySQLConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO coal_mined (coal_name, amount, date_of_mining,workerid) VALUES (?, ?, ?,?)")) {
                    stmt.setString(1, coalName);
                    stmt.setInt(2, amount);
                    stmt.setString(3, date);
                    stmt.setInt(4,workerid);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Coal mining reported successfully.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    private static class ViewScheduledCoalFrame extends JFrame {
        public ViewScheduledCoalFrame(int userId) {
            setTitle("View Scheduled Coal Mining");
            setSize(400, 300); // Increase the frame size to make more space
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
            JTextArea scheduleArea = new JTextArea();
            scheduleArea.setLineWrap(true); // Enable line wrapping to improve text readability
            scheduleArea.setWrapStyleWord(true);
            add(new JScrollPane(scheduleArea), BorderLayout.CENTER);
        
            // Add a panel with a label and text field to enter worker ID
            JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Add padding to improve spacing
            JLabel workerIdLabel = new JLabel("Enter Worker ID: ");
            JTextField workerIdField = new JTextField(10);
            JButton fetchButton = new JButton("Fetch Schedule");
            inputPanel.add(workerIdLabel);
            inputPanel.add(workerIdField);
            inputPanel.add(fetchButton);
            add(inputPanel, BorderLayout.NORTH);
        
            fetchButton.addActionListener(e -> {
                String workerIdStr = workerIdField.getText();
                if (!workerIdStr.isEmpty()) {
                    try {
                        int workerId = Integer.parseInt(workerIdStr);
        
                        // Execute the SQL query to fetch schedules for the given worker ID
                        try (Connection conn = MySQLConnection.getConnection();
                             PreparedStatement stmt = conn.prepareStatement("SELECT coal_id, coal_name, start_time, end_time, area FROM specific_coal_mining WHERE worker_id = ?")) {
                            stmt.setInt(1, workerId);
                            ResultSet rs = stmt.executeQuery();
        
                            StringBuilder sb = new StringBuilder();
                            while (rs.next()) {
                                sb.append("Coal ID: ").append(rs.getInt("coal_id")).append("\n");
                                sb.append("Coal Name: ").append(rs.getString("coal_name")).append("\n");
                                sb.append("Start Date: ").append(rs.getString("start_time")).append("\n");
                                sb.append("End Date: ").append(rs.getString("end_time")).append("\n");
                                sb.append("Area: ").append(rs.getString("area")).append("\n");
                                sb.append("\n");
                            }
        
                            if (sb.length() == 0) {
                                sb.append("No schedule found for Worker ID: ").append(workerId);
                            }
        
                            scheduleArea.setText(sb.toString());
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            scheduleArea.setText("Error fetching data for Worker ID: " + workerId);
                        }
                    } catch (NumberFormatException ex) {
                        scheduleArea.setText("Invalid Worker ID entered.");
                    }
                } else {
                    scheduleArea.setText("Please enter a Worker ID.");
                }
            });
        }
    }
    private static class GenerateCoalReportFrame extends JFrame {
        public GenerateCoalReportFrame(int userId) {
            setTitle("Coal Report");
            setSize(600, 400);
            setLayout(new BorderLayout());

            // Table for displaying the report
            String[] columnNames = {"Coal ID", "Coal Name", "Amount", "Date of Mining"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable reportTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(reportTable);
            add(scrollPane, BorderLayout.CENTER);

            // Fetch report data from the database
            try (Connection conn = MySQLConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT coal_id, coal_name, amount, date_of_mining FROM coal_mined WHERE workerid = ?")) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int coalId = rs.getInt("coal_id");
                    String coalName = rs.getString("coal_name");
                    int amount = rs.getInt("amount");
                    String dateOfMining = rs.getString("date_of_mining");

                    // Add row to the table
                    tableModel.addRow(new Object[]{coalId, coalName, amount, dateOfMining});
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fetching report data.");
            }
        }
    }


    private static class ChangePasswordFrame extends JFrame {
        public ChangePasswordFrame(int userId) {
            setTitle("Change Password");
            setSize(300, 150);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel(new GridLayout(3, 2));
            JLabel currentPasswordLabel = new JLabel("Current Password:");
            JPasswordField currentPasswordField = new JPasswordField(15);
            JLabel newPasswordLabel = new JLabel("New Password:");
            JPasswordField newPasswordField = new JPasswordField(15);
            JButton changeButton = new JButton("Change");

            panel.add(currentPasswordLabel);
            panel.add(currentPasswordField);
            panel.add(newPasswordLabel);
            panel.add(newPasswordField);
            panel.add(changeButton);

            add(panel, BorderLayout.CENTER);

            changeButton.addActionListener(e -> {
                String currentPassword = new String(currentPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());

                try (Connection conn = MySQLConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE user_id = ? AND password = ?")) {
                    stmt.setInt(1, userId);
                    stmt.setString(2, currentPassword);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE user_id = ?")) {
                            updateStmt.setString(1, newPassword);
                            updateStmt.setInt(2, userId);
                            updateStmt.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Password changed successfully.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Current password is incorrect.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }
    private static class GenerateCoalGraphFrame extends JFrame {
        public GenerateCoalGraphFrame(int userId) {
           // setTitle("Coal Graph");
          //  setSize(300, 150);
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Fetch data for the graph
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT coal_name, amount FROM coal_mined WHERE workerid = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            // Populate the dataset
            while (rs.next()) {
                String coalName = rs.getString("coal_name");
                int amount = rs.getInt("amount");
                dataset.addValue(amount, "Amount", coalName); // Add to dataset
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data for the graph.");
            return; // Exit if there's an error
        }

        // Create the chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Coal Amount by Type",
                "Coal Name",
                "Amount",
                dataset
        );

        // Create a panel to display the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 300));

        // Create a new frame for the chart
        JFrame chartFrame = new JFrame("Coal Amount Graph");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(600, 400);
        chartFrame.add(chartPanel, BorderLayout.CENTER);
        chartFrame.setLocationRelativeTo(null); // Center the chart frame
        chartFrame.setVisible(true); // Show the c
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WorkerGUI gui = new WorkerGUI(1); // Replace 1 with the appropriate user_id
            gui.setVisible(true);
        });
    }
}
