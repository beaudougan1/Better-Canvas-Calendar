package Calendar;


import AssignmentCalculations.Assignment;
import AssignmentCalculations.Course;
import AssignmentCalculations.Student;

import java.awt.*;
//import java.awt.Label
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


import javax.swing.*;


public class Background extends JPanel{
    public static LocalDate selected;
    private static final long serialVersionUID = 1L;
    public static boolean high;


    public Background(int year, int month, LocalDate selected, JPanel mainPanel) { //function that essentially creates the whole calendar window
        Background.selected = selected;

        setLayout(new BorderLayout(30,30)); // Sets size of window
        setBorder(BorderFactory.createEmptyBorder(40,20,30,20));
        setBackground(Color.WHITE);

        JPanel top = new JPanel(new BorderLayout(10,10)); //adds a panel on top of window
        top.setBackground(null);

        JLabel date = new JLabel(LocalDate.of(year, month, 1).format(DateTimeFormatter.ofPattern("MMMM yyyy"))); //finds current date and year
        date.setHorizontalAlignment(JLabel.CENTER);
        date.setFont(new Font("Roboto", Font.BOLD, 30));
        date.setForeground(Color.BLACK);
        top.add(date, BorderLayout.CENTER); //adds it to said top JPanel


        JLabel left = new JLabel(new ImageIcon("Code/Canvas_API/src/main/java/Calendar/pics/left.png")); //Calls downloaded image of left arrow
        left.setCursor(new Cursor(Cursor.HAND_CURSOR));
        left.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) { //when clicked, it erases the entire calendar and refreshes it with new previous month, circumstantial previous year, and that month's and year's days in correct days of week spaces
                mainPanel.removeAll();
                if(month != 1){
                    mainPanel.add(new Background(year, month - 1, selected, mainPanel ));
                } else {
                    mainPanel.add(new Background(year - 1, 12, selected, mainPanel ));
                }
                mainPanel.add(new AssignmentsDisplay()); //Adds new assignment display with given new dates
                mainPanel.revalidate();
            }
        });
        top.add(left, BorderLayout.WEST); //adds it on left side


        JLabel right = new JLabel(new ImageIcon("Code/Canvas_API/src/main/java/Calendar/pics/right.png")); //Calls downloaded image of right arrow
        right.setCursor(new Cursor(Cursor.HAND_CURSOR));
        right.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) { //when clicked, it erases the entire calendar and refreshes it with new forwarded month, circumstantial forwarded year, and that month's and year's days in correct days of week spaces
                mainPanel.removeAll();
                if(month != 12){
                    mainPanel.add(new Background(year, month + 1, selected, mainPanel ));
                } else {
                    mainPanel.add(new Background(year + 1, 1, selected, mainPanel ));
                }
                mainPanel.add(new AssignmentsDisplay()); //Adds new assignment display with given new dates
                mainPanel.revalidate();
            }
        });
        top.add(right, BorderLayout.EAST); //adds it on right side
        add(top, BorderLayout.NORTH);


        JPanel days = new JPanel(new GridLayout(7,7)); //Makes a grid the represent the "dates" on a standard calendar, 49 days to be safe
        days.setBackground(null);

        Color dates = Color.BLACK; //Adds the names of the days on the top row of the days JPanel
        days.add(new Calendar.Label("Sun",dates, Color.WHITE, false));
        days.add(new Calendar.Label("Mon",dates, Color.WHITE, false));
        days.add(new Calendar.Label("Tue",dates, Color.WHITE, false));
        days.add(new Calendar.Label("Wed",dates, Color.WHITE, false));
        days.add(new Calendar.Label("Thu",dates, Color.WHITE, false));
        days.add(new Calendar.Label("Fri",dates, Color.WHITE, false));
        days.add(new Calendar.Label("Sat",dates, Color.WHITE, false));

        String[] weekDays = new String[] {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"}; //string to name out the days of the week

        LocalDate firstD = LocalDate.of(year, month, 1);
        ArrayList<Course> stuCourses = Login.stu.getCourses(); //gets courses from API
        ArrayList<LocalDate> dueDates = new ArrayList<>(); //New list of due dates
        for (Course course : stuCourses) {
            ArrayList<Assignment> stuAssignments = course.getAssignments(); //collects assignments from different courses
            for (Assignment assignment : stuAssignments) {
                String dueDate = assignment.getDueDate(); //gets due date from each assignment
                // Skip assignment if the due date is null or empty
                if (dueDate == null || dueDate.trim().isEmpty() || "null".equals(dueDate.trim()) || "00-00-0000".equals(dueDate.trim())) {
                    continue;
                }
                LocalDate assignmentDueDate = LocalDate.parse(dueDate); // Parse the due date only if it's not null or empty
                if (assignmentDueDate.getYear() == year && assignmentDueDate.getMonthValue() == month) { //adds due date if the year and month match
                    dueDates.add(assignmentDueDate);
                }
            }
        }

        int j = 0;
        while (!firstD.getDayOfWeek().toString().equals(weekDays[j])) { //adds labels to each day of the month corresponding to day of week until it hits the last day
            days.add(new Calendar.Label("", Color.LIGHT_GRAY, Color.BLACK, false));
            j++;
        }
        int numDays = YearMonth.of(year, month).lengthOfMonth(); //uses yearmonth.of to find number of days in a month on a given year


        for (int i = 1; i <= numDays; i++){ //iterates through each day and checks if the date is the date the user selects, making it gray
            final int day = i;
            Label label;
            if(selected.getYear() == year && selected.getMonthValue() == month && selected.getDayOfMonth() == i){
                label = new Calendar.Label(i + "", Color.GRAY, Color.BLACK, true);
            } else {
                label = new Calendar.Label(i + "", Color.LIGHT_GRAY, Color.BLACK, true); //if not selected it turns light gray, unselected
            }
            boolean isDate = dueDates.contains(LocalDate.of(year, month, day));  //checks due dates and see if it matches with a date on the calendar
            if(isDate){
                label = new Calendar.Label(i + "", Color.decode("#8B0000"), Color.BLACK, true); //if it does it turns it red, showing theres an assignment that day
            }

            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseClicked(MouseEvent e) { //makes each label clickable, making it dark gray and redisplaying assignment list of theres assignments that day
                    mainPanel.removeAll();
                    LocalDate selected = LocalDate.of(year, month, day);
                    mainPanel.add(new Background(year, month, selected, mainPanel));
                    mainPanel.add(new AssignmentsDisplay());
                    mainPanel.revalidate();
                }
            });
            days.add(label); //adds labels to the day spaces
        }


        for (int i = 0; i < (42 - (j + numDays)); i++){ //add empty light gray labels to the unused slots in the month
            days.add(new Calendar.Label("", Color.LIGHT_GRAY,Color.BLACK,true));
        }
        add(days, BorderLayout.CENTER); //adds days to calendar
    }
}
