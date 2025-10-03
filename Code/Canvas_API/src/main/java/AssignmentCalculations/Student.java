package AssignmentCalculations;

import APITranslator.InfoCategorizer;

import java.util.ArrayList;
//Student object is used to get information from the APITranslator classes and transfer them in a way that a students courses can be tracked along with assignments relating to each course
public class Student {
    private ArrayList<Course> courses;
    private String accessToken;

    public Student(String accessToken){
        this.accessToken = accessToken;
        //array list to store students courses in
        courses = new ArrayList<>();
        InfoCategorizer.setAccessToken(accessToken);
        //add parsed courses retrieved below
        ArrayList<String[]> coursesData = InfoCategorizer.getCourses();
        if (InfoCategorizer.isValidToken(accessToken)) {
            InfoCategorizer.setAccessToken(accessToken);
            //loop through all courses and add them to an array list
            for(String[] course : coursesData){
                courses.add(new Course(course[0], Integer.parseInt(course[1])));
            }
            //loop through all the courses and store assignments they have in them
            for (Course course : courses) {
                //store all the assignments data in a string array in assignment data
                ArrayList<String[]> assignmentData = InfoCategorizer.getAssignments(course.getCourseID());
                //if the class has assignments
                if (assignmentData != null) {
                    //loop through the assignments and add them to the course object for the given course
                    for(String[] assignment : assignmentData){
                        String name = assignment[0];
                        String[] dueArray = assignment[1].split("T");
                        String dueDate = dueArray[0];
                        String due = dueDate;
                        Double points = Double.parseDouble(assignment[2]);
                        Boolean completed = Boolean.parseBoolean(assignment[3]);
                        course.addAssignment(new Assignment(name, due, points, completed, course));
                    }
                }
            }
        }
    }
    //function used to add a course to a Student object
    public void addCourse(Course course){
        courses.add(course);
    }
    //function used to get courses assigned to a Student
    public ArrayList<Course> getCourses(){
        return courses;
    }
    //check if a canvas api token is valid
    public boolean isValidToken(){
        return InfoCategorizer.isValidToken(accessToken);
    }
}
