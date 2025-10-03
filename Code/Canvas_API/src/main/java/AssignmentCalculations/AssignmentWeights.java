package AssignmentCalculations;

import java.util.ArrayList;
public class AssignmentWeights implements AssignmentCalculator{
    //initialize variables
    private double weight;
    //AssignmentWeights class shows the weight of the parameter assignments points compared to other assignments points
    public AssignmentWeights(Assignment assign){
        //an arraylist of assignments to store the assignments in
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        assignments = assign.getCourse().getAssignments();
        //check the assignments class and add up the points for all assignments
        double totalPoints = 0;
        for (Assignment assignment : assignments) {
            totalPoints += assignment.getPoints();
        }
        //set weight to the points for given assignment divided by total points for the class
        weight = assign.getPoints()/totalPoints;
    }
    //getter for assignment weight
    public double calculate(){
        return weight;
    }
}
