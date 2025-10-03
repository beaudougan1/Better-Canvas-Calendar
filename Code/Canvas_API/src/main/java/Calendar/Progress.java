package Calendar;

import AssignmentCalculations.Course;
import AssignmentCalculations.Student;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Arc2D;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Progress extends JPanel {
    private double[] percentage;
    private String[] courseNames;

    public Progress() {
        ArrayList<Course> courses = Login.stu.getCourses(); //Collects courses from student class
        percentage = new double[courses.size()]; //to store completed courses
        courseNames = new String[courses.size()]; //to store cours enames

        for (int i = 0; i < courses.size(); i++) { //for loop for all available courses in Student class
            Course course = courses.get(i);
            int total = 0;
            int completed = 0;
            ArrayList<AssignmentCalculations.Assignment> assignments = course.getAssignments(); //Loops through each assignment
            for (AssignmentCalculations.Assignment assignment : assignments) {
                    total++;
                if(assignment.isCompleted()){ //Checks if completed boolean and if it is adds one iteration
                    completed++;
                }
            }
            if(total > 0){
                percentage[i] = (double) completed / total * 100; //percentage done is completed divided by total multiplied by 100
            } else {
                percentage[i] = 0;
            }
            courseNames[i] = course.getName(); //gets name of the iterated course
        }

        JFrame frame = new JFrame("Progress"); //New window called progress
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)); //adds panel to store infomration
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(this, BorderLayout.CENTER);
        frame.setVisible(true);

        JPanel legendPanel = new JPanel(); //legendPanel basically is a key that explains colors or symbals in a visual
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setBackground(Color.WHITE);

        for(int i = 0; i < percentage.length; i++){ //loops through as many courses the student has
            JPanel legendItem = new JPanel(); //makes new panel
            legendItem.setLayout(new FlowLayout(FlowLayout.LEFT));
            legendItem.setBackground(Color.WHITE);

            JLabel colorBox = new JLabel(); //adds a box to show a color
            colorBox.setPreferredSize(new Dimension(20, 20));
            colorBox.setOpaque(true);
            colorBox.setBackground(getColorForCourse(i)); //sets color that hasn't been used yet to course

            String progress = String.format("%s: %.1f%%", courseNames[i], percentage[i]); //displays the course name and percentage completed for that course
            JLabel label = new JLabel(progress);

            legendItem.add(colorBox);
            legendItem.add(label);
            legendPanel.add(legendItem);
        }
        frame.getContentPane().add(legendPanel, BorderLayout.NORTH);
        frame.setVisible(true); //adds information to window

        JButton Cancel = new JButton("Cancel"); //Cancel button added
        Cancel.setFont(new Font("Roboto",Font.BOLD, 15));
        Cancel.setBackground(Color.RED);
        Cancel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        frame.add(Cancel);
        Cancel.setPreferredSize(new Dimension(60, 30));
        frame.getContentPane().add(Cancel, BorderLayout.SOUTH);
        Cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Cancel.addMouseListener(new MouseListener() { //if clicked it closes the window
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
    }

    private Color getColorForCourse(int index){ //list of colors functions can use to assign colors to different classes
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.ORANGE, Color.CYAN, Color.YELLOW};
        return colors[index % colors.length]; //no student is going to have more than 7 classes unless their going over the credit limit
    }

    @Override
    public void paintComponent(Graphics g) { //Paint the circles to display the progress bar
        super.paintComponent(g);  // Ensure JPanel is correctly painted
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.translate(this.getWidth() / 2, this.getHeight() / 2);     // Center the drawing
        g2.rotate(Math.toRadians(270)); //drawing starts at top of circle

        int radius = 100; //starts at very top of the circumference of circle
        for(int i = 0; i < percentage.length; i++){ //until it hits the percentage length, it draws an arc with a color in a circle until it hits limit
            Arc2D.Float arc = new Arc2D.Float(Arc2D.OPEN);
            arc.setFrameFromCenter(new Point(0,0), new Point(radius, radius));
            arc.setAngleStart(1);
            arc.setAngleExtent(-(percentage[i] * 3.6));
            g2.setColor(getColorForCourse(i));
            g2.setStroke(new BasicStroke(8));
            g2.draw(arc);

            radius -= 12; //decreases by 12, basically making the next circle/arc smaller so it doesnt collide with previous circle/arc
        }


        g2.setColor(Color.BLACK); //sets a circle to contain all classes arc
        g2.setStroke(new BasicStroke(4));
        g2.draw(new Ellipse2D.Float(-110, -110, 220, 220));

        double totalProgress = 0;
        for(double progress : percentage){ //adds all the progresses per class and adds it to totalProgress
            totalProgress += progress;
        }
        totalProgress = totalProgress / percentage.length; //divides it by number of classes to find average completion
        g2.setColor(Color.BLACK); //sets font and position of said total progress number
        g2.rotate(Math.toRadians(90));
        g.setFont(new Font("Roboto",Font.BOLD,15));
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D rec = fm.getStringBounds(String.format("%.2f%%", totalProgress), g);
        int x = (-(int) rec.getWidth() / 2);
        int y = (-(int) rec.getHeight() / 2) + fm.getAscent();
        g2.drawString(String.format("%.2f%%",totalProgress), x, y); //adds number to center of circle, only by 2 decimal places

    }
}