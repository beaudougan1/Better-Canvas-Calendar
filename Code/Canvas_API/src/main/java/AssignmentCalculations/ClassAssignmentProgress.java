package AssignmentCalculations;

import java.util.ArrayList;
//function which calculates the progress completed in total assignments for a given course
public class ClassAssignmentProgress implements AssignmentCalculator{
    //initialize variables
    private double assignmentProgress;
    //takes in Course as a parameter
public ClassAssignmentProgress(Course course) {
    //create arraylist to store assignments in
    ArrayList<Assignment> assignments = new ArrayList<>();
    assignments = course.getAssignments();
    //initialize completed and total points variables 1 point is an assignment
    double completedPoints = 0;
    double totalPoints = 0;
    //loop through assignments adding a point in completed and total points when an assignment is completed and adding just to total points if not
    for(Assignment assignment : assignments){
        if(assignment.isCompleted()){
            completedPoints += assignment.getPoints();
        }
        totalPoints += assignment.getPoints();
    }
    //ensure we are never dividing by 0
    if(totalPoints > 0){
        assignmentProgress = completedPoints/totalPoints;
    } else {
        assignmentProgress = 0;
    }
}
    //calculate function returns the assignmentProgress value for the given course
    public double calculate(){
        return assignmentProgress * 100;
    }
}
