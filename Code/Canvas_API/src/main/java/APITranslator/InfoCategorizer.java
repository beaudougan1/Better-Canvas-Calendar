package APITranslator;
import APIGrabber.APIInfo;
import java.util.ArrayList;

public class InfoCategorizer {
    
    private static String accessToken;//access token 
    //sets access token to token from user
    public static void setAccessToken(String token) {
        accessToken = token;
    }
    
    public static String getAccessToken(){
        //get from this class
        return accessToken;
    }
    
    //checks if api call was successful
    public static boolean isValidToken(String token){
      boolean valid = APIInfo.getAPIStatus(token);
      return valid;
    }//end if a func

    // in array you grab name = array[0] courseID = array[1]
    //this function returns an arraylist with course names and IDs
    public static ArrayList<String[]> getCourses() {
        if(isValidToken(accessToken)) {
            ArrayList<String[]> courses = new ArrayList<>();
            ArrayList<String> courseInfo = InfoParser.getCourseInfo(getAccessToken());
            for (String course : courseInfo) {
                String[] courseData = course.split(";");
                courses.add(courseData);
            }
            return courses;//return courses
        }
        return null;//null if no courses
    }
//gets assignment info 
    public static ArrayList<String[]> getAssignments(Integer courseID) {
        if(isValidToken(accessToken)) {
            ArrayList<String[]> assignments = new ArrayList<>();
            ArrayList<String> assignmentInfo = InfoParser.getAssignmentInfo(accessToken, courseID);
            for (String assignment : assignmentInfo) {
                String[] assignmentData = assignment.split(";");
                assignments.add(assignmentData);
            }
            
            if (assignments.isEmpty()) {//empty list
                return null;
            }
            return assignments;//if not null
        }
        else{//if access token not valid
            return null;
        }
    }//end of func

    //get course id based on index of course in array list
    public static Integer getCourseID(Integer courseIndex) {
        ArrayList<String[]> courses = getCourses();
        return Integer.parseInt(courses.get(courseIndex)[1]);
    }
    
    //get course ID based on index of course name in array list 
    public static String getCourseName(Integer courseIndex) {
        ArrayList<String[]> courses = getCourses();
        return courses.get(courseIndex)[0];
    }


}
