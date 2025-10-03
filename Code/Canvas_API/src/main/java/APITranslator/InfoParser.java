package APITranslator;

import APIGrabber.APIInfo;
import java.util.ArrayList;
import org.json.*;


public class InfoParser {
    // gets a list of assignment info
    public static ArrayList<String> getAssignmentInfo(String accessToken, Integer courseID){
        Integer userID = getUserID(accessToken);//get user id
        ArrayList<String> assignmentInfo = new ArrayList<>();
        String[] assignments = APIInfo.fetchAssignmentsForCourse(accessToken, APIInfo.getAssignmentPageURL(courseID, userID)).split("@");
        for(String assignment : assignments){
            assignmentInfo.add(assignment);
        }
        //returns an arraylist with assignments with date and time, points, and completion status seperated by ;
        return assignmentInfo;
    }
    
    //gets arraylist with course names and ID's
    public static ArrayList<String> getCourseInfo(String accessToken) {
        Integer userID = getUserID(accessToken);//get userID
        ArrayList<String> courseIDs = new ArrayList<>();
        String[] courseInfo = APIInfo.getCoursesPage(accessToken, APIInfo.getCoursePageUrl(userID)).split("%");
        for(int i = 0; i < courseInfo.length; i++){
            courseIDs.add(courseInfo[i]);//add to arraylist
        }
        //returns array with all course names and ids seperated by :
        return courseIDs;
    }
    //gets the user ID using access token this function is used by other funcs in this class
    public static Integer getUserID(String accessToken) {
        String UserInfo = APIInfo.getUserInfo(accessToken);//string of all user info
        JSONObject obj = new JSONObject(UserInfo);//make json object to grab userid
        int userID = obj.getInt("id");//grab user id
        return userID;
    }
//gets the list of course names
    public static String getListOfCourseNames(String accessToken) {
        Integer userID = getUserID(accessToken);
        String url = APIInfo.getCoursePageUrl(userID);
        String courseInfo = APIInfo.getCoursesPage(accessToken,url);
        return courseInfo;
    }

   
}
