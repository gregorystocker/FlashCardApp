import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;



public class Main {

    //Strictly for testing
    public int getNum(){return 1;}


    public static void main(String[] args) {

        //CREATE THE BAREBONES GUI
        GUI GUIobj = new GUI();
        Cards.setGUIobj(GUIobj);
        GUIobj.createGUI();
        JavaSQLConnect connectObj = new JavaSQLConnect();
        Cards.setJavaMySQLConnectObj(connectObj);
        ArrayList<String> subjectList = new ArrayList();
        ArrayList<String> questionKeys = new ArrayList();
        Connection connected = null;

        //ESTABLISH A CONNECTION WITH THE DATABASE
        try {
            connected = connectObj.getConnection();
            Cards.setConnected(connected);
            //create a new file for images if not already created.
            FileHandler fileHandlerObj = new FileHandler();

            //updating Cards and getting and creating the images folder
            Cards.setFileHandlerObj(fileHandlerObj);
            String currentFolder = fileHandlerObj.getCurrentFolder();
            System.out.println("Current Folder is :" + currentFolder + " \n");
            Cards.setCurrentFolder(currentFolder);

            //this step should be in an install wizard instead. this line creates the images folder,
            // only needed the first time.
            Cards.getFileHandlerObj().createFolder(currentFolder, "images");

            //connectObj.printSQLColumn(3); //for testing purposes
            subjectList = connectObj.getSubjectArrayList(1);
            //trims out duplicate subjects
            subjectList = removeDuplicates(subjectList);
            Cards.setSubjectList(subjectList);

            //goes through the subjects and and sets the GUI to display them in subjectArea
            for(String subject: subjectList){
                GUIobj.addSubject(subject);
            }
            String subject = "";

            //getSelectedObjects returns a SINGLE element array of the current object
            for(Object words: GUIobj.getSubjectArea().getSelectedObjects()){
                subject = words.toString();
                Cards.setCurrentSubject(subject);
            }
            //Setting the initial Question to the GUI
            ArrayList<String> questionKeyList = connectObj.getQuestionKeys( subject);
             Cards.setCurrentQuestionKeys(questionKeyList);
            String firstQuestionKey = questionKeyList.get(0);
            Cards.setCurrentQuestionIndex(0);


            //static String[] newQuestion = {"subject", "question", "answer", "questionImg", "answerImg"};
             //connectObj.addNewQuestion(Cards.getNewQuestion()[0],Cards.getNewQuestion()[1],Cards.getNewQuestion()[2],
             //Cards.getNewQuestion()[3],Cards.getNewQuestion()[4]);
            GUIobj.getQuestionArea().setText( connectObj.getQuestion(firstQuestionKey));

            /*testing area*/
           // GUIobj.getQuestionArea().add(fh);
            /*end testing area*/

        } catch (SQLException e) {
            System.out.println(e);
        }//ends catch

    }//ends public static void main(String[] args)

    //static methods--------------------------------------------------------------------


    public static ArrayList<String> removeDuplicates(ArrayList<String> subjectList){
        ArrayList<String> noDuplicates = new ArrayList();
        boolean hasDuplicate = false;
        for(int i = 0; i < subjectList.size(); i++){
            hasDuplicate = false;//resets for each index
            for(int j = 0; j < i; j++){
                if(subjectList.get(i).equals(subjectList.get(j))){
                    hasDuplicate = true;
                }  //ends if
            }//ends inner for
            if(!hasDuplicate){//if there are no prior duplicates,
                noDuplicates.add(subjectList.get(i));// record this in the new list
            }//ends if
        }//ends outer for
        return noDuplicates;
    }//ends removeDuplicates

    public static void initialize(JavaSQLConnect connectObj, Connection connected,ArrayList<String> subjectList, GUI GUIobj){
        try{
        connected = connectObj.getConnection();
        Cards.setConnected(connected);

        //connectObj.printSQLColumn(3); //for testing purposes
        subjectList = connectObj.getSubjectArrayList( 1);
        //trims out duplicate subjects
        subjectList= removeDuplicates(subjectList);
        Cards.setSubjectList(subjectList);




        //goes through the subjects and and sets the GUI to display them in subjectArea
        for(String subject: subjectList){
            GUIobj.addSubject(subject);
        }

        String subject = "";

        //getSelectedObjects returns a SINGLE element array of the current object
        for(Object words: GUIobj.getSubjectArea().getSelectedObjects()){
            subject = words.toString();
            Cards.setCurrentSubject(subject);
        }
        //Setting the initial Question to the GUI
        ArrayList<String> questionKeyList = connectObj.getQuestionKeys( subject);
        Cards.setCurrentQuestionKeys(questionKeyList);
        String firstQuestionKey = questionKeyList.get(0);
        Cards.setCurrentQuestionIndex(0);


        //static String[] newQuestion = {"subject", "question", "answer", "questionImg", "answerImg"};
        //connectObj.addNewQuestion(Cards.getNewQuestion()[0],Cards.getNewQuestion()[1],Cards.getNewQuestion()[2],
        //Cards.getNewQuestion()[3],Cards.getNewQuestion()[4]);

        GUIobj.getQuestionArea().setText( connectObj.getQuestion(firstQuestionKey));
    } catch (SQLException e) {
        System.out.println(e);
    }//ends catch
    }



}//ends Main




