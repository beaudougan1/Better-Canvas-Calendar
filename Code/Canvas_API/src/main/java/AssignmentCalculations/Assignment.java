package AssignmentCalculations;

//Assignment object stores all needed information for a given assignment
public class Assignment {
    private double points;
    private Course course;
    private String dueDate;
    private String name;
    private boolean completed;
    //constructor for Assignment Object
    public Assignment(String name, String dueDate, double points, boolean completed, Course course){
        this.points = points;
        this.course = course;
        this.dueDate = dueDate;
        this.name = name;
        this.completed = completed;
    }
    //getter for assignment name
    public String getName(){
        return name;
    }
    //getter for assignment points
    public double getPoints(){
        return points;
    }
    //getter for course object relating to assignment
    public Course getCourse(){
        return course;
    }
    //getter for assignment due date
    public String getDueDate(){
        return dueDate;
    }
    //Allows you to mark an assignment as complete or not complete
    public void changeAssignmentStatus(){
        completed = !completed;
    }
    //Boolean to check if an assignment is completed or not
    public boolean isCompleted(){
        return completed;
    }
}
