package Calendar;

import AssignmentCalculations.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class Login {
    public static Student stu; //new student object, every time they need something from student class, they use this specific student object
    public Login() {
        Dimension fixedSize = new Dimension(300, 40);

        JFrame login = new JFrame("Login Page"); //New window called login page
        login.setSize(500, 650);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setLocationRelativeTo(null);
        login.getContentPane().setBackground(Color.WHITE);
        login.setLayout(new BorderLayout());  // Set BorderLayout for the main frame

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));  // Set BorderLayout for the main panel
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        JPanel center = new JPanel();
        center.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));  // Center the components
        center.setBackground(Color.WHITE);

        JLabel box1 = new JLabel("Please Paste Your Canvas API Token"); //Text to instruct user to add API token
        box1.setFont(new Font("Roboto", Font.PLAIN, 20));
        box1.setHorizontalAlignment(JLabel.CENTER);
        center.add(box1);

        JTextField token = new JTextField(); //Text box for user to input API token
        token.setFont(new Font("Roboto", Font.PLAIN, 20));
        token.setPreferredSize(fixedSize);  // Set preferred size for smaller width
        token.setMinimumSize(fixedSize);
        token.setMaximumSize(fixedSize);
        token.setHorizontalAlignment(JLabel.CENTER);
        center.add(token);


        JButton submitButton = new JButton("Submit"); //submit button to let system know user is done writing token
        submitButton.setFont(new Font("Roboto", Font.PLAIN, 20));
        submitButton.setPreferredSize(fixedSize);  // Same size for the button
        submitButton.setMinimumSize(fixedSize);
        submitButton.setMaximumSize(fixedSize);
        submitButton.setBackground(Color.decode("#228B22"));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //when clicked, it runs through to see if is valid
                String apiToken = token.getText();
                stu = new Student(apiToken);
                if (apiToken.isEmpty() || !stu.isValidToken()) { //uses another class to check if token is valid
                    JOptionPane.showMessageDialog(login, "Please enter a valid Canvas API token."); //if token is not valid
                } else {
                    JOptionPane.showMessageDialog(login, "Token submitted successfully!"); //if token is valid, closes window
                    login.dispose();
                    JFrame frame = new JFrame("Calendar"); //Starts up Calendar and basically runs the Background class with information gained from user inputted Canvas API
                    frame.setSize(1000,500);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                    frame.getContentPane().setBackground(Color.white);

                    JPanel mainPanel = new JPanel(new GridLayout(1,3,10,0));

                    LocalDate date = LocalDate.now();

                    mainPanel.add(new Background(date.getYear(), date.getMonthValue(),date, mainPanel));
                    mainPanel.add(new AssignmentsDisplay());

                    frame.getContentPane().add(mainPanel);
                    frame.setVisible(true);
                }
            }
        });
        center.add(submitButton); //Adds everything to panel to make nice and neat :)

        mainPanel.add(center, BorderLayout.CENTER);
        login.getContentPane().add(mainPanel, BorderLayout.CENTER);
        login.setVisible(true);
    }

}
