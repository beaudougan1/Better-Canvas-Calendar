package Calendar;

import AssignmentCalculations.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Editor {

    public static Color weightColor(int color){ //checks number of points user inputted and assigns it a number
        if(color <= 30){
            return Color.GREEN;
        } else if(color <= 65){
            return Color.YELLOW;
        } else {
            return Color.RED;
        }
    }

    public Editor() {
        Dimension fixedSize = new Dimension(450, 75); //new page editor to add a new assignment
        JFrame frame = new JFrame("Editor");
        frame.setSize(700,350);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout(20,20)); //adds a panel to the window
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40,20,20,20));
        mainPanel.setBackground(Color.WHITE);

        JPanel center = new JPanel(new GridLayout(3,2,20,20));
        center.setBackground(Color.WHITE);

        JLabel box1 = new JLabel("Assignment"); //Adds space for assignment name
        box1.setFont(new Font("Roboto",Font.PLAIN,20));
        box1.setHorizontalAlignment(JLabel.CENTER);
        center.add(box1);

        JTextField title = new JTextField(); //text box for user to enter assignment name
        title.setFont(new Font("Roboto", Font.PLAIN,20));
        title.setHorizontalAlignment(JLabel.CENTER);
        center.add(title);

        JLabel box2 = new JLabel("Class"); //Adds space for course name
        box2.setFont(new Font("Roboto",Font.PLAIN,20));
        box2.setHorizontalAlignment(JLabel.CENTER);
        center.add(box2);

        ArrayList<Course> stuCourses = Login.stu.getCourses(); //Gets courses from Student API
        ArrayList<String> courseNames = new ArrayList<>();
        for (Course course : stuCourses) {
            courseNames.add(course.getName());
        }
        String[] names = courseNames.toArray(new String[0]); //Adds course names to a new dropdown chooser for students to pick specific classes
        JComboBox<String> className = new JComboBox<>(names);
        className.setFont(new Font("Roboto", Font.PLAIN, 20));
        className.setSelectedIndex(0);
        center.add(className);

        JLabel box3 = new JLabel("Points"); //Adds space for number of points
        box3.setFont(new Font("Roboto",Font.PLAIN,20));
        box3.setHorizontalAlignment(JLabel.CENTER);
        center.add(box3);

        JTextField points = new JTextField(); //text box for user to enter number of points for the assignment
        points.setFont(new Font("Roboto", Font.PLAIN,20));
        points.setHorizontalAlignment(JLabel.CENTER);
        center.add(points);

        mainPanel.add(center, BorderLayout.CENTER);

        JPanel bot = new JPanel(new GridLayout(1,2,20,20));
        bot.setBackground(null); //adds panel to bottom of window

        JButton Cancel = new JButton("Cancel"); //adds cancel button on bottom
        Cancel.setFont(new Font("Roboto",Font.BOLD, 15));
        Cancel.setBackground(Color.RED);
        Cancel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        bot.add(Cancel);
        Cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Cancel.addMouseListener(new MouseListener() { //makes button clickable
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) { //if cancel button is clicked it closes the window
                frame.dispose();
            }
        });

        JButton Save = new JButton("Save Assignment"); //adds save button to save inputted assignment information
        Save.setFont(new Font("Roboto",Font.BOLD, 15));
        Save.setBackground(Color.GREEN);
        Save.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        bot.add(Save);
        Save.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Save.addMouseListener(new MouseListener() { //makes button clickable
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) { //if clicked, assigns user inputted information to strings
                String titleText = title.getText();
                String classNameText = (String) className.getSelectedItem();
                String pointsText = points.getText();

                int pointsInt = Integer.parseInt(pointsText); //turns string user inputted text for points into an integer
                JPanel assignment = new JPanel(new GridLayout(2, 1)); //when user clicks save, it creates a new assignment module with the inputted information
                assignment.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        BorderFactory.createMatteBorder(0, 10, 0, 0, weightColor(pointsInt)))); //uses the color function to determine color for weight
                assignment.setBackground(Color.LIGHT_GRAY);
                assignment.setPreferredSize(fixedSize);
                assignment.setMinimumSize(fixedSize);
                assignment.setMaximumSize(fixedSize);
                assignment.setCursor(new Cursor(Cursor.HAND_CURSOR));
                assignment.addMouseListener(new MouseAdapter() { //makes it clickable
                    @Override
                    public void mouseClicked(MouseEvent e) { //Basically does the same as what is done in AssignmentsDisplay to show the full assignment information with option to cancel or delete, code spans from here to line 229
                        JFrame info = new JFrame("Assignment Information");
                        info.setSize(1000,550);
                        info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        info.setLocationRelativeTo(null);
                        info.getContentPane().setBackground(Color.WHITE);
                        info.setLayout(new BorderLayout());
                        
                        JPanel main = new JPanel();
                        main.setLayout(new BorderLayout(20,20));
                        main.setBackground(Color.WHITE);
                        main.setBorder(BorderFactory.createEmptyBorder(40,20,20,20));
                        
                        JPanel center = new JPanel();
                        center.setBackground(Color.WHITE);
                        center.setLayout(new GridLayout(3,1,20,20));

                        JLabel box1 = new JLabel("Assignment: " + titleText);
                        box1.setFont(new Font("Roboto",Font.BOLD,20));
                        box1.setHorizontalAlignment(JLabel.CENTER);
                        box1.setForeground(Color.BLACK);
                        center.add(box1);

                        JLabel box2 = new JLabel("Class: " + classNameText);
                        box2.setFont(new Font("Roboto",Font.BOLD,20));
                        box2.setForeground(Color.BLACK);
                        box2.setHorizontalAlignment(JLabel.CENTER);
                        center.add(box2);

                        JLabel box3 = new JLabel("Points: " + pointsText);
                        box3.setFont(new Font("Roboto",Font.BOLD,20));
                        box3.setHorizontalAlignment(JLabel.CENTER);
                        box3.setForeground(Color.BLACK);
                        center.add(box3);

                        JLabel box4 = new JLabel("Is Completed: false");
                        box4.setFont(new Font("Roboto",Font.BOLD,20));
                        box4.setHorizontalAlignment(JLabel.CENTER);
                        box4.setForeground(Color.BLACK);
                        center.add(box4);

                        JButton done = new JButton("Done");
                        done.setFont(new Font("Roboto",Font.BOLD, 15));
                        done.setBackground(Color.decode("#228B22"));
                        done.setForeground(Color.WHITE);
                        done.setPreferredSize(new Dimension(100, 35));
                        done.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                        done.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        done.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                info.dispose();
                            }
                        });

                        JButton delete = new JButton("Delete");
                        delete.setFont(new Font("Roboto",Font.BOLD, 15));
                        delete.setBackground(Color.decode("#8B0000"));
                        delete.setForeground(Color.WHITE);
                        delete.setPreferredSize(new Dimension(100, 35));
                        delete.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                        delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        delete.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                AssignmentsDisplay.list.remove(assignment);
                                AssignmentsDisplay.list.revalidate();
                                AssignmentsDisplay.list.repaint();
                                info.dispose();
                            }
                        });
                        main.add(center, BorderLayout.CENTER);
                        JPanel bottom = new JPanel();
                        bottom.setLayout(new FlowLayout(FlowLayout.LEFT));
                        bottom.setBackground(Color.WHITE);
                        bottom.add(done);

                        JPanel del = new JPanel();
                        del.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        del.setBackground(Color.WHITE);
                        del.add(delete);

                        JPanel combined = new JPanel();
                        combined.setLayout(new BorderLayout());
                        combined.add(bottom, BorderLayout.WEST);
                        combined.add(del, BorderLayout.EAST);

                        info.add(main, BorderLayout.CENTER);
                        info.add(center, BorderLayout.CENTER);
                        info.add(combined, BorderLayout.SOUTH);

                        info.revalidate();
                        info.repaint();
                        info.setVisible(true);
                    }
                }); //end of assignment information code , same as in Assignments Display

                JLabel title = new JLabel("Title: " + titleText); //Assigns user inputted title to title label
                title.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                title.setFont(new Font("Roboto", Font.PLAIN, 18));
                title.setForeground(Color.BLACK);
                assignment.add(title);

                JLabel course = new JLabel("Course: " + classNameText); //Assigns chosen course name to course label
                course.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                course.setFont(new Font("Roboto", Font.PLAIN, 16));
                course.setForeground(Color.BLACK);
                assignment.add(course);

                JLabel point = new JLabel("Points: " + pointsText); //Assigns user inputted points to points label
                point.setBorder(BorderFactory.createEmptyBorder(5, 15, 4, 15));
                point.setFont(new Font("Roboto", Font.PLAIN, 14));
                point.setForeground(Color.DARK_GRAY);
                assignment.add(point);

                AssignmentsDisplay.list.add(assignment); //Add assignment to list
                assignment.revalidate();
                assignment.repaint();
                frame.dispose(); //close window
                
                

            }
        });
        mainPanel.add(bot, BorderLayout.SOUTH); //Add all information to main panel
        frame.getContentPane().add(mainPanel);

        frame.setVisible(true);
    }
}


