package Calendar;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

import AssignmentCalculations.Assignment;
import AssignmentCalculations.AssignmentWeights;
import AssignmentCalculations.Course;
import AssignmentCalculations.Student;
import org.junit.experimental.theories.internal.Assignments;

import static Calendar.Background.selected;

public class AssignmentsDisplay extends JPanel{
    static ArrayList<Course> stuCourses = Login.stu.getCourses(); //Creates list to put courses in
    public static JPanel list = new JPanel(); //Panel to hold list of assignments


    static{
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
    }

    private static final long serialVersionUID = 1L;

    public AssignmentsDisplay() {
        Dimension fixedSize = new Dimension(450, 75); //Code to frame the perimeter of the panel
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(40, 20, 30, 20));

        list.setBackground(Color.WHITE); //set background

        JScrollPane scroll = new JScrollPane(list); //add scrolling function in the assignment panel
        add(scroll, BorderLayout.CENTER);

        loadAssignmentsForDate(selected); //calls assignment getter function for the date that the user selects

        JButton newAssignment = new JButton("Add Assignment"); //Button for add assignments
        newAssignment.setFont(new Font("Roboto", Font.BOLD, 20));
        newAssignment.setBackground(Color.decode("#228B22"));
        newAssignment.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(newAssignment, BorderLayout.SOUTH); //put it on the bottom

        JButton progress = new JButton("Progress Bars"); //Button for show progress bars
        progress.setFont(new Font("Roboto", Font.BOLD, 20));
        progress.setBackground(Color.decode("#228B22"));
        progress.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(progress, BorderLayout.NORTH); //put it on the top

        progress.setCursor(new Cursor(Cursor.HAND_CURSOR)); //make the progress button clickable
        progress.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) { //when clicked, it opens a new progress windows and resets the visual
                new Progress();
                revalidate();
                repaint();
            }
        });

        newAssignment.setCursor(new Cursor(Cursor.HAND_CURSOR)); //make the add assignment button clickable
        newAssignment.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) { //when clicked it opens a new assignment window and resets the visual
                new Editor();
                revalidate();
                repaint();
            }
        });
    }

    public void loadAssignmentsForDate(LocalDate date) { //function that will collect assignments from API and display
        list.removeAll();
        for (Course course : stuCourses) { //get each course from API
            ArrayList<Assignment> assignments = course.getAssignments();  // Get assignments for each course
            for (Assignment assignment : assignments) {
                String dueDate = assignment.getDueDate(); //runs function that only goes through the due dates and assigns it to dueDate
                if (dueDate == null || dueDate.trim().isEmpty() || "null".equals(dueDate.trim()) || "00-00-0000".equals(dueDate.trim())) { //some due dates are labeled null so this passes through those and skips
                    continue;
                }
                LocalDate assignmentDueDate = LocalDate.parse(dueDate); //seperates the numbers in the date
                if (assignmentDueDate.equals(date)) { //if the day number from the date matches the selected number on the calendar
                    JPanel assignmentPanel = new JPanel(new GridLayout(2, 1)); //makes a small box that will represent the assignment
                    assignmentPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(10, 10, 10, 10),
                            BorderFactory.createMatteBorder(0, 10, 0, 0, weightColor(assignment)) //color bar on the side indicating the impact of the assignment to overall grade
                    ));
                    assignmentPanel.setBackground(Color.LIGHT_GRAY); //code to make the box look pretty, background color and dimensions and such
                    assignmentPanel.setPreferredSize(new Dimension(450, 75));
                    assignmentPanel.setMinimumSize(new Dimension(450, 75));
                    assignmentPanel.setMaximumSize(new Dimension(450, 75));
                    assignmentPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    assignmentPanel.addMouseListener(new MouseAdapter() { // makes the assignment box clickable
                        public void mouseClicked(MouseEvent e) {
                            JFrame info = new JFrame("Assignment Information"); //when clicked, opens a new frame window to show assignment information
                            info.setSize(1100, 450); //code to make the box a good shape
                            info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            info.setLocationRelativeTo(null);
                            info.getContentPane().setBackground(Color.WHITE);
                            info.setLayout(new BorderLayout());

                            JPanel mainPanel = new JPanel(); //code to make a panel to add the information
                            mainPanel.setLayout(new BorderLayout(20, 20));
                            mainPanel.setBackground(Color.WHITE);
                            mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

                            JPanel center = new JPanel();
                            center.setBackground(Color.WHITE);
                            center.setLayout(new GridLayout(3,1,10,20));

                            JButton done = new JButton("Done"); //add a done button
                            done.setFont(new Font("Roboto",Font.BOLD, 15));
                            done.setBackground(Color.decode("#228B22"));
                            done.setForeground(Color.WHITE);
                            done.setPreferredSize(new Dimension(100, 35));
                            done.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                            done.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            done.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) { //when clicked it closes the Assignment Information window
                                    info.dispose();
                                }
                            });

                            JButton delete = new JButton("Delete"); //add a delete button
                            delete.setFont(new Font("Roboto",Font.BOLD, 15));
                            delete.setBackground(Color.decode("#8B0000"));
                            delete.setForeground(Color.WHITE);
                            delete.setPreferredSize(new Dimension(100, 35));
                            delete.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                            delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            delete.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) { //when clicked it removes the clicked on assignment from the list and closes the window
                                   assignments.remove(assignment);
                                   list.remove(assignmentPanel);
                                    info.dispose();
                                    revalidate();
                                    repaint();
                                }
                            });

                            JLabel box1 = new JLabel("Assignment: " + assignment.getName()); //from here to line 183 its adding the different subinformation of an assignment and adding it to the center panel
                            box1.setFont(new Font("Roboto",Font.BOLD,20));
                            box1.setHorizontalAlignment(JLabel.CENTER);
                            box1.setForeground(Color.BLACK);
                            center.add(box1);

                            JLabel box2 = new JLabel("Class: " + course.getName());
                            box2.setFont(new Font("Roboto",Font.BOLD,20));
                            box2.setForeground(Color.BLACK);
                            box2.setHorizontalAlignment(JLabel.CENTER);
                            center.add(box2);

                            JLabel box3 = new JLabel("Points: " + assignment.getPoints());
                            box3.setFont(new Font("Roboto",Font.BOLD,20));
                            box3.setHorizontalAlignment(JLabel.CENTER);
                            box3.setForeground(Color.BLACK);
                            center.add(box3);

                            JLabel box4 = new JLabel("Is Completed: " + assignment.isCompleted());
                            box4.setFont(new Font("Roboto",Font.BOLD,20));
                            box4.setHorizontalAlignment(JLabel.CENTER);
                            box4.setForeground(Color.BLACK);
                            center.add(box4);

                            mainPanel.add(center, BorderLayout.CENTER); //adds information to main window panel

                            JPanel bottom = new JPanel(); //add done button to bottom of window to the left
                            bottom.setLayout(new FlowLayout(FlowLayout.LEFT));
                            bottom.setBackground(Color.WHITE);
                            bottom.add(done);

                            JPanel del = new JPanel(); //add done button to bottom of window to the right
                            del.setLayout(new FlowLayout(FlowLayout.RIGHT));
                            del.setBackground(Color.WHITE);
                            del.add(delete);

                            JPanel combined = new JPanel(); //combines the two JPanels so they can exist on the same bottom part
                            combined.setLayout(new BorderLayout());
                            combined.add(bottom, BorderLayout.WEST);
                            combined.add(del, BorderLayout.EAST);

                            info.add(mainPanel, BorderLayout.CENTER); //adds all the info to main window
                            info.add(center, BorderLayout.CENTER);
                            info.add(combined, BorderLayout.SOUTH);

                            info.revalidate();
                            info.repaint();
                            info.setVisible(true);
                        }
                    });

                    String title = assignment.getName();  //These take what the user inputted in add information and assigns it to strings
                    String courseName = course.getName();
                    String points = String.valueOf(assignment.getPoints());

                    JLabel titleLabel = new JLabel("Title: " + title); //Adds text called title and adds the title of assignment
                    titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                    titleLabel.setFont(new Font("Roboto", Font.PLAIN, 18));
                    titleLabel.setForeground(Color.BLACK);
                    assignmentPanel.add(titleLabel);

                    JLabel courseLabel = new JLabel("Course: " + courseName); //Adds text called Course and adds the name of the assignment
                    courseLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                    courseLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
                    courseLabel.setForeground(Color.BLACK);
                    assignmentPanel.add(courseLabel);

                    JLabel pointsLabel = new JLabel("Points: " + points); //Adds text called Points and adds the inputted number of points
                    pointsLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 4, 15));
                    pointsLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
                    pointsLabel.setForeground(Color.DARK_GRAY);
                    assignmentPanel.add(pointsLabel);

                    // Add the assignment panel to the list
                    list.add(assignmentPanel);
                }
            }
        }
        revalidate(); //resets the visual of the assignment list
        repaint();
    }
    public static Color weightColor(Assignment assignment){ //function called above that basically uses different class that calculates the weight of the assignment based on total points and assigns it a color
        AssignmentWeights weight = new AssignmentWeights(assignment);
        double weightPercentage = weight.calculate() * 100;

        if(weightPercentage <= 10){
            return Color.GREEN;
        } else if(weightPercentage <= 15){
            return Color.YELLOW;
        } else {
            return Color.RED;
        }
    }

}
