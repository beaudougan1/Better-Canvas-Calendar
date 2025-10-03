package APIGrabber;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.List;
import java.util.Map;

public class APIInfo {
    
    //string access token
    public static boolean getAPIStatus(String accessToken){
        boolean status = false;
       
        //canvas website endpoint
        String canvasUrl = "https://csusm.instructure.com/api/v1/users/self";

        //send http get request using restassured
        //create header with access token
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)  // Add the Bearer token in the Authorization header
                .when()
                .get(canvasUrl);
        if(response.getStatusCode() == 200) {
             status = true;
        }
        //return status of api call
        return status;
    }

    //gets the endpoint for the courses
    public static String getCoursePageUrl(Integer user_ID){
        String Course_URL = "https://csusm.instructure.com/api/v1/users/" + user_ID + "/courses";

     return Course_URL;//return string of the website endpoint
    }

    //gets the endpoint for fetchassignments
    public static String getAssignmentPageURL(Integer courseID, Integer userID){
        String assignmentsURL = "https://csusm.instructure.com/api/v1/users/" + userID + "/courses/" + courseID + "/assignments";

        return assignmentsURL;//return string of the website endpoint
    }

    //get user info returns a large string with all the user info
    public static String getUserInfo(String accessToken){
        String Info;
        
        //canvas website endpoint
        String canvasUrl = "https://csusm.instructure.com/api/v1/users/self";

        //send http get request using restassured
        //create header with access token
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)  // Add the Bearer token in the Authorization header
                .when()
                .get(canvasUrl);
        if(response.getStatusCode() == 200) {
            Info = response.getBody().asString();
        }
        else{
            //if there is an error show message
            Info = "ERROR:incorrect access token";
        }

    return Info;//return string of info about user from canvas api
    }

    //using the url from the getcoursespageUrl and access token this function returns a string with course name and id
    public static String getCoursesPage(String accessToken, String URL){
        //send get request with the new endpont
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)  // Authorization header with token
                .when()
                .get(URL);

        String courseInfo = "";//string to store info
        
        List<Map<String, Object>> Courses = null;
        if (response.statusCode() == 200) {
            Courses = response.jsonPath().getList("$");
            for (Map<String, Object> Course : Courses) {
                if (Course.get("name") != null) {
                    String courseName = Course.get("name").toString();
                    String courseID = Course.get("id").toString();
                    courseInfo += courseName + ";" + courseID + "%";//+ ",";

                }
            }
        }
        //check if theres a next page with courses
        if(response.header("Link") != null && response.header("Link").contains("rel=\"next\"")) {
            String nextPageUrl = getNextPageUrl(response.header("Link"));
            String NewInfo = getCoursesPage(accessToken,nextPageUrl);// Fetch the next page
            courseInfo += NewInfo;
        } 
        
    return courseInfo;
    }

    //this function is only used by the getcoursepage and retrives the link for the next page
    private static String getNextPageUrl(String linkForNextPage) {
        String[] links = linkForNextPage.split(",");
        for (String link : links) {
            if (link.contains("rel=\"next\"")) {
                // get and return the next page URL
                return link.substring(link.indexOf("<") + 1, link.indexOf(">"));
            }
        }
        return null;//no next page
    }//end of next page

        //this gets the course information
    public static String fetchAssignmentsForCourse(String accessToken, String url) {
        //send get request
        Response response = given()
                .header("Authorization", "Bearer " + accessToken)  // Add Bearer token for authorization
                .when()
                .get(url);

        //assignment Info vars
        String assignmentInfo = "";
        String points;
        String dueDate;
        String submitted;

        //if get request successful
        if(response.statusCode()== 200){
            List<Map<String, Object>> assignments = response.jsonPath().getList("$");

            //get assignment information
        for (Map<String, Object> assignment : assignments) {
            String assignmentName = (String) assignment.getOrDefault("name", "Unknown");
            //if the assignment name is not unknown
            if (!assignmentName.equals("Unknown")) {
                // use get or deafult and if statements to account for missing info
                dueDate = (String) assignment.getOrDefault("due_at", "N/A");
                
               if(assignment.get("points_possible") != null) {
                   points = assignment.getOrDefault("points_possible", "0").toString();
               }else{
                   points = "0";
               }
               if(assignment.get("has_submitted_submissions") != null) {
                submitted = assignment.getOrDefault("has_submitted_submissions", "false").toString();
               }else{
                   submitted = "false";
               }
                //add all assignment info to a string to be easier to parse through
                assignmentInfo += assignmentName + ";" + dueDate + ";" + points + ";" + submitted + "@";
             }//end of if

            }//end for loop

        }//end of outer if
        return assignmentInfo;//returns string info seperated by ; new line at @
    }//end of func
    
    }//end of class




