package AssignmentCalculations;

import java.util.ArrayList;
//This object is used to keep track of a course and the assignments relating to it
public class Course {
    //variable declarations
    private String name;
    private int courseID;
    //this array stores the assignments for the courses
    private ArrayList<Assignment> assignments = new ArrayList<>();
    //Constructor for Course object
    public Course(String name, int courseID){
        this.name = name;
        this.courseID = courseID;
    }
    //Used to add an assignment to the arraylist for the given course
    public void addAssignment(Assignment assignment){
        assignments.add(assignment);
    }
    //Used to remove an assignment from a given course
    public void removeAssignment(Assignment assignment){
        assignments.remove(assignment);
    }
    //Used to get the assignments arraylist
    public ArrayList<Assignment> getAssignments(){
        return assignments;
    }
    //getter for course name
    public String getName(){
        return name;
    }
    //getter for course ID
    public int getCourseID(){
        return courseID;
    }
}
