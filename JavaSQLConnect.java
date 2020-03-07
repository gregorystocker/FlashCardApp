import java.io.File;
import java.sql.*;
import java.util.Properties;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


public class JavaSQLConnect {


    //standard variables
    String userName = "root";
    String password = "gjsk8r123";
    String dbms = "mysql";
    String serverName = "localhost";
    String portNumber = "3306";
    String dbName = "flashcardsdb";
    String tableName = "writtencards";
    String questionText = "what is 2 + 2?";
    //JDBC variables
    Connection conn = null;
    Connection connected = null;
    Properties connectionProps = new Properties();
    Statement statement = null;
    ResultSet resultset = null;
    //This function was gotten from the official docs.
    public Connection getConnection() throws SQLException {
        //Connection conn = null;
        //Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);
        if (this.dbms.equals("mysql")) {
            conn = DriverManager.getConnection(
                    "jdbc:" + this.dbms + "://" +
                            this.serverName +
                            ":" + this.portNumber + "/" +
                            this.dbName,
                    connectionProps);
        } else if (this.dbms.equals("derby")) {
            conn = DriverManager.getConnection(
                    "jdbc:" + this.dbms + ":" +
                            this.dbName +
                            ";create=true",
                    connectionProps);
        }
        System.out.println("Connected to database");
        Cards.setConnected(conn);
        return conn;

        //resultset = statement.executeQuery("SELECT * FROM writtencards");
    }

    public void printSQLColumn(int column){
        try {
            connected = Cards.getConnected();
            statement = connected.createStatement();
            resultset = statement.executeQuery("SELECT * FROM writtencards");
            while (resultset.next()) {
                //The column index is
                System.out.println(resultset.getString(column)); //or rs.getString("column name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }//ends printSQLTable

    //argument question should be exact text of the question
    public String getAnswer(String question){

        try {
            connected = Cards.getConnected();
            statement = connected.createStatement();
            resultset = statement.executeQuery("SELECT answer FROM " + tableName +
                    " WHERE question = " + "\"" + question + "\"");
            while (resultset.next()) {
                //The column index is
                String answer = resultset.getString("answer"); //or rs.getString("column name");
                return answer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }//ends returnSQLValue

    public String getAnswer(){

        try {
            connected = Cards.getConnected();
            statement = connected.createStatement();
            resultset = statement.executeQuery("SELECT answer FROM " + tableName +
                    " WHERE questionKey = " + "\"" + Cards.getCurrentQuestionKeys().get(Cards.getCurrentQuestionIndex()) + "\"");
            while (resultset.next()) {
                //The column index is
                String answer = resultset.getString("answer"); //or rs.getString("column name");
                return answer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }//ends returnSQLValue

    public ArrayList<String> getSubjectArrayList(int column) throws SQLException{
            ArrayList<String> subjectList = new ArrayList();
        try {
            connected = Cards.getConnected();
            statement = connected.createStatement();
            resultset = statement.executeQuery("SELECT * FROM writtencards");
            while (resultset.next()) {
                //The column index is
                subjectList.add(resultset.getString(column));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(subjectList == null){
            System.out.println("\nEmpty subject list\n");
        }
        return subjectList;

    }//ends getSubjectArrayList

    public String getQuestions(String subject){

        try {
            connected = Cards.getConnected();
            statement = connected.createStatement();
            resultset = statement.executeQuery("SELECT question FROM " + tableName +
                    " WHERE subj = " + "\"" + subject+ "\"");
            while (resultset.next()) {
                //The column index is
                String question = resultset.getString("question"); //or rs.getString("column name");
                return question;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }//ends getQuestions

    //Given a number for the questionKey, gives the question
    public String getQuestion( String questionKey){
                //SELECT question FROM writtencards WHERE questionKey = '1';
        try {
            connected = Cards.getConnected();
            statement = connected.createStatement();
            resultset = statement.executeQuery("SELECT question FROM " + tableName +
                    " WHERE questionKey = " + "\"" + questionKey + "\"");
            while (resultset.next()) {
                //The column index is
                String question = resultset.getString("question"); //or rs.getString("column name");
                return question;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }//ends getQuestion


    //getImageState returns the string BOTH, NEITHER, ANSWER or QUESTION representing what is an image.
    //also updates the Cards.currentType variable
    public String getImageState(){
            String state = null;
            String question = null;
            String answer = null;
        try {
            connected = Cards.getConnected();
            statement = connected.createStatement();
            resultset = statement.executeQuery("SELECT answerImg FROM writtencards WHERE questionKey = '"
                    + Cards.getCurrentQuestionKeys().get(Cards.getCurrentQuestionIndex())  + "'");
            while (resultset.next()) {
                //The column index is
                answer = resultset.getString("answerImg"); //or rs.getString("column name");
            }
            resultset = statement.executeQuery("SELECT questionImg FROM writtencards WHERE questionKey = '"
                    + Cards.getCurrentQuestionKeys().get(Cards.getCurrentQuestionIndex())  + "'");
            while (resultset.next()) {
                //The column index is
                question = resultset.getString("questionImg"); //or rs.getString("column name");

                if(question == null && answer == null){
                    state = "NEITHER";
                }else if(question == null && answer != null){
                    state = "ANSWERONLY";
                }else if(question != null && answer ==null){
                    state = "QUESTIONONLY";
                }else{
                    state = "BOTH";
                }
                Cards.setCurrentType(state);
                return state;
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return state;
    }//ends getImageState

    //getImageState returns the string BOTH, NEITHER, ANSWER or QUESTION representing what is an image.
    //also updates the Cards.currentType variable
    //pass in Cards.getCurrentQuestionKeys().get(Cards.getCurrentQuestionIndex()) for the current one
    public String getImageState(String index){
        String state = null;
        String question = null;
        String answer = null;
        try {
            connected = Cards.getConnected();
            statement = connected.createStatement();
            resultset = statement.executeQuery("SELECT answerImg FROM writtencards WHERE questionKey = '"
                    + Cards.getCurrentQuestionKeys().get(Cards.getCurrentQuestionIndex())  + "'");
            while (resultset.next()) {
                //The column index is
                answer = resultset.getString("answerImg"); //or rs.getString("column name");
            }
            resultset = statement.executeQuery("SELECT questionImg FROM writtencards WHERE questionKey = '"
                    + index  + "'");
            while (resultset.next()) {
                //The column index is
                question = resultset.getString("questionImg"); //or rs.getString("column name");

                if(question == null && answer == null){
                    state = "NEITHER";
                }else if(question == null && answer != null){
                    state = "ANSWERONLY";
                }else if(question != null && answer ==null){
                    state = "QUESTIONONLY";
                }else{
                    state = "BOTH";
                }
                Cards.setCurrentType(state);
                return state;
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        return state;
    }//ends getImageState

    //Given a number for the questionKey, gives the questionImg
    public String getQuestionImg(){
        //SELECT question FROM writtencards WHERE questionKey = '1';
        try {
            String questionImg = "";
            connected = Cards.getConnected();
            statement = connected.createStatement();
            resultset = statement.executeQuery("SELECT questionImg FROM " + tableName +
                    " WHERE questionKey = " + "\"" + Cards.getCurrentQuestionKeys().get(Cards.getCurrentQuestionIndex()) + "\"");
            while (resultset.next()) {
                //The column index is
                 questionImg = resultset.getString("questionImg"); //or rs.getString("column name");
            }
            return questionImg;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }//ends getAnswerImg

    //this one is more for debugging and calling images out of order, gets the string representation of the URL
    public String getQuestionImg(int knownIndex){
        //SELECT question FROM writtencards WHERE questionKey = '1';
        try {
            String questionImg = "";
            connected = Cards.getConnected();
            statement = connected.createStatement();
            resultset = statement.executeQuery("SELECT questionImg FROM " + tableName +
                    " WHERE questionKey = " + "\"" + Integer.toString(knownIndex) + "\"");
            while (resultset.next()) {
                //The column index is
                questionImg = resultset.getString("questionImg"); //or rs.getString("column name");
            }
            return questionImg;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }//ends getQuestionImg(int knownIndex) for testing purposes

    //Given a number for the questionKey, gives the questionImg
    public String getAnswerImg(){
        //SELECT question FROM writtencards WHERE questionKey = '1';
        try {
            connected = Cards.getConnected();
            statement = connected.createStatement();
            resultset = statement.executeQuery("SELECT answerImg FROM " + tableName +
                    " WHERE questionKey = " + "\"" + Cards.getCurrentQuestionKeys().get(Cards.getCurrentQuestionIndex()) + "\"");
            while (resultset.next()) {
                //The column index is
                String answerImg = resultset.getString("answerImg"); //or rs.getString("column name");
                return answerImg;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }//ends getAnswerImg


    public ArrayList<String> getQuestionKeys( String subject){
    ArrayList<String> questionKeysList = new ArrayList();
        try {
        connected = Cards.getConnected();
        statement = connected.createStatement();
        resultset = statement.executeQuery("SELECT questionKey FROM writtencards WHERE subj = " + "'" + subject + "'");
        while (resultset.next()) {
            //The column index is
            questionKeysList.add(resultset.getString("questionKey"));
        }
        Cards.setCurrentQuestionKeys(questionKeysList);//Updates Cards to the current question keys
    } catch (SQLException e) {
        e.printStackTrace();
    }
        if(questionKeysList == null){
        System.out.println("\nEmpty subject list\n");
    }
        return questionKeysList;

}//ends getSubjectArrayList

    //adds a new Question to the SQL Database when given the info, then updates the Cards class and reconnectes to update the subject list
    public void addNewQuestion(String subject, String question, String answer, String questionImg, String answerImg){
        try {

            //Here we are making it so that entering null will not be seen as a string "null" but as the keyword NULL and all other
            //entries are treated as 'single quote strings'
            connected = Cards.getConnected();
            statement = connected.createStatement();

            //using String.valueOf was neccessary for some reason and the values had to be entirely separate from the original
            //parameters
            String b,c,d,e,f;
            b = String.valueOf(subject);
            c = String.valueOf(question);
            d = String.valueOf(answer);
            e = String.valueOf(questionImg);
            f = String.valueOf(answerImg);
            ArrayList<String> a = formatStringsForSQL2(b,c,d,e,f);
            System.out.println(a);


                 statement.executeUpdate( "INSERT INTO writtencards VALUES\n" +
            "(" + a.get(0) +  "," + a.get(1) + "," + a.get(2) +","  + "NULL" + "," + a.get(3) + "," + a.get(4) + ")");

            //original working statement without null exception
            //statement.executeUpdate( "INSERT INTO writtencards VALUES\n" +
            //        "    ('" + subject + "', '" + question +"', '" + answer + "', NULL, '" + questionImg + "','" + answerImg +"')");

            Main.initialize(Cards.getJavaMySQLConnectObj(),Cards.getJavaMySQLConnectObj().getConnection(),new ArrayList<String>(), Cards.getGUIobj());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//ends getSubjectArrayList

    public ArrayList<String> formatStringsForSQL(String subject, String question, String answer, String questionImg, String answerImg){

        //Java strings are immutable which is why I need to create a newList as to not manipulate the old strings.
        ArrayList<String> list = new ArrayList();
        ArrayList<String> newList = new ArrayList();
        list.add(subject);
        list.add(question);
        list.add(answer);
        list.add(questionImg);
        list.add(answerImg);

        int counter = 0;
        for(String c : list){


            if(c.equals("null") || c.equals("NULL")){
                newList.add(counter, c.toUpperCase());
            }else{
                newList.add(counter, "'" + c + "'");
                for (int i = 0; i < c.length(); i++) {
                    char ch = c.charAt(i);
                }

            }
            counter++;
        }


            return newList;
    }

    //formatStringsForSQL2 adds extra file separators to account for the transition to sql leaving the /s out
    public ArrayList<String> formatStringsForSQL2(String subject, String question, String answer, String questionImg, String answerImg) {

        //Java strings are immutable which is why I need to create a newList as to not manipulate the old strings.
        ArrayList<String> list = new ArrayList();
        ArrayList<String> newList = new ArrayList();
        ArrayList<StringBuilder> sbList = new ArrayList();
        list.add(subject);
        list.add(question);
        list.add(answer);
        list.add(questionImg);
        list.add(answerImg);

        int counter = 0;
        for (String c : list) {


            if (c.equals("null") || c.equals("NULL")) {
                newList.add(counter, c.toUpperCase());
            } else {
                newList.add(counter, "'" + c + "'");


            }
            counter++;
        }

        //copying newList into StringBuilder sbList
        for (int i = 0; i < newList.size(); i++) {
            sbList.add(new StringBuilder(newList.get(i)));
        }

        for (int i = 0; i < sbList.size(); i++) {

            for (int j = 0; j < sbList.get(i).length(); j++) {
                StringBuilder word = sbList.get(i);
                if (word.charAt(j) == '/' || word.charAt(j) =='\\') {
                    word.replace(j,j+1,File.separator );
                    word.insert(j,File.separator);
                    j++;
                }

            }




        }
        System.out.println(sbList);
        ArrayList<String> finalList = new ArrayList();
        for(StringBuilder s : sbList){
            finalList.add(s.toString());
        }
        return finalList;
    }

}//ends JavaSQLCOnnect

