import java.sql.Connection;
import java.util.ArrayList;

//Cards: This class holds all the 'cards'. All of the static variabales
// that need to be accessed by multiple classes are held here.
//It holds information about the current state, and connection.

//the top of the inheritance hiearchy
public class Cards{
    static JavaSQLConnect JavaMySQLConnectObj;
    //set in: Main
    static GUI GUIobj = new GUI();
    static FileHandler FileHandlerObj = new FileHandler();
    static String currentFolder;

    public static GUI getGUIobj() {
        return GUIobj;
    }
    public static void setGUIobj(GUI GUIobj) {
        Cards.GUIobj = GUIobj;
    }

    static String currentAnswerImageURL;
    static String currentQuestionImageURL;

    public static String getCurrentIndexInKeys(){return Cards.getCurrentQuestionKeys().get(Cards.getCurrentQuestionIndex());}
    public static String[] getNewQuestion() {
        return newQuestion;
    }

    public static void setNewQuestion(String[] newQuestion) {
        Cards.newQuestion = newQuestion;
    }

    //Holds the array with info that is updated when new question is added
    static String[] newQuestion = {"subject", "question", "answer", "questionImg", "answerImg"};

    //declares what is represented by an image, answer, question or both?

    static String currentType;  // Can be "ANSWERONLY" , "QUESTIONONLY", ""NEITHER or "BOTH"

    public static  String getCurrentType() {
        return currentType;
    }

    public static  void setCurrentType(String currentType){
        Cards.currentType = currentType;
    }

    static Connection connected;
    //set in:

    //current question holds the INDEX of the question in currentQuestionKeys not the actual key number.
    static int currentQuestionIndex;


    static String currentSubject;
    //set in: Main


    static ArrayList<String> subjectList = new ArrayList();
    //set in: Main

    static ArrayList<String> currentQuestionKeys = new ArrayList();

    public static ArrayList<String> getSubjectList() {
        return subjectList;
    }

    public static void setSubjectList(ArrayList<String> subjectList) {
        Cards.subjectList = subjectList;
    }


    public static String getCurrentSubject(){
        return currentSubject;
    }

    public static JavaSQLConnect getJavaMySQLConnectObj() {
        return JavaMySQLConnectObj;
    }


    public static void setJavaMySQLConnectObj(JavaSQLConnect mjavaMySQLConnectObj) {
        JavaMySQLConnectObj = mjavaMySQLConnectObj;
    }

    public static void setCurrentSubject(String mcurrentSubject) {
        currentSubject = mcurrentSubject;
    }

    public static void addCurrentQuestionKey(String key){
        currentQuestionKeys.add(key);
    }


     public static int  getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

     public static  void setCurrentQuestionIndex(int  mcurrentQuestion) {
        currentQuestionIndex = mcurrentQuestion;

    }


    public static ArrayList<String>getCurrentQuestionKeys() {
        return currentQuestionKeys;
    }

     public static  void setCurrentQuestionKeys(ArrayList<String> mcurrentQuestionKeys) {
        currentQuestionKeys = mcurrentQuestionKeys;
    }

    public static Connection getConnected() {
        return connected;
    }

    public static void setConnected(Connection connected) {
        Cards.connected = connected;
    }

    public static void displayCurrentQuestionKeys(){
        for(String key : currentQuestionKeys){
            System.out.println(key);
        }

    }

    public static String getCurrentAnswerImageURL() {
        return currentAnswerImageURL;
    }

    public static void setCurrentAnswerImageURL(String currentAnswerImageURL) {
        Cards.currentAnswerImageURL = currentAnswerImageURL;
    }

    public static String getCurrentQuestionImageURL() {
        return currentQuestionImageURL;
    }

    public static void setCurrentQuestionImageURL(String currentQuestionImageURL) {
        Cards.currentQuestionImageURL = currentQuestionImageURL;
    }

    public static FileHandler getFileHandlerObj() {
        return FileHandlerObj;
    }

    public static void setFileHandlerObj(FileHandler fileHandlerObj) {
        FileHandlerObj = fileHandlerObj;
    }



    public static String getCurrentFolder() {
        return currentFolder;
    }

    public static void setCurrentFolder(String currentFolder) {
        Cards.currentFolder = currentFolder;
    }
}//ends Cards
