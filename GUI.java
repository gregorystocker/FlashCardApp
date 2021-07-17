import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;


public class GUI extends JFrame{
    //The GUI member variables that need to be accessed outside

    Connection connected = null;

    private JFrame frame = new JFrame("Greg`s Flashcards");
    private JPanel panel = new JPanel(new BorderLayout());
    //NORTH
    ItemChangeListener itemListener = new ItemChangeListener();
    private JLabel appNameLabel = new JLabel("Greg`s FlashCards");
    private JLabel subjectLabel = new JLabel("Subject:");
    private JComboBox subjectArea = new JComboBox();
    private JPanel northPanel = new JPanel(new FlowLayout( FlowLayout.CENTER,20,0));
    //EAST
    private JPanel eastPanel = new JPanel();
    private JButton showAnswerButton = new JButton("Show Answer");
    private JTextArea answerArea = new JTextArea();
    private JPanel answerImageArea = new JPanel();
    private ShowAnswerListener answerListener = new ShowAnswerListener();
    //SOUTH
    private JPanel southPanel = new JPanel();
    JButton previousQuestionButton = new JButton("Previous Question");
    private JButton nextQuestionButton = new JButton("Next Question");
    private NextClickListener nextListener = new NextClickListener();
    private PreviousClickListener previousListener = new PreviousClickListener();
    //WEST
    private JPanel westPanel = new JPanel();//
    private JButton addQuestionButton = new JButton("Add New Question");//
    private AddQuestionListener addQuestionListener = new AddQuestionListener();//
    //form
    private JPanel formPanel = new JPanel();
    private JLabel chooseSubjectLabel = new JLabel("Choose Subject:");//
    private JTextField chooseSubjectField = new JTextField("");//
    private JButton useCurrentBox = new JButton("Autofill Current Subject");
    private UseCurrentListener useCurrentListener = new UseCurrentListener();

    private JLabel questionTypeLabel = new JLabel("Question Type:");
    private ButtonGroup questionTypeGroup = new ButtonGroup();
    private JRadioButton questionTextRadio = new JRadioButton("Text");
    private JRadioButton questionImageRadio = new JRadioButton("Image");
    private JLabel answerTypeLabel = new JLabel("Answer Type:");
    private ButtonGroup answerTypeGroup = new ButtonGroup();
    private JRadioButton answerTextRadio = new JRadioButton("Text");
    private JRadioButton answerImageRadio = new JRadioButton("Image");
    private QuestionTextListener questionTextListener = new QuestionTextListener();
    //questionText
    private JPanel questionTextPanel = new JPanel();
    private JLabel enterQuestionLabel = new JLabel("Enter Question:");
    private JTextField enterQuestionField = new JTextField("Enter Question Here");
    //AnswerText
    private JPanel answerTextPanel = new JPanel();
    private JLabel enterAnswerLabel = new JLabel("Enter Answer:");
    private JTextField enterAnswerField = new JTextField("Enter Answer Here");
    private AnswerTextListener answerTextListener = new AnswerTextListener();
    //questionImage
    private JPanel questionImagePanel = new JPanel();
    private JLabel questionImageLabel = new JLabel("Browse for file: ");
    private QuestionImageListener questionImageListener = new QuestionImageListener();
    private JButton uploadQuestionButton = new JButton("Upload Question Image:");
    private UploadQuestionListener uploadQuestionListener = new UploadQuestionListener();
    //answerImage
    private JPanel answerImagePanel = new JPanel();
    private JLabel answerImageLabel = new JLabel("Browse for file: ");
    private AnswerImageListener answerImageListener = new AnswerImageListener();
    private JButton uploadAnswerButton = new JButton("Upload Answer Image:");
    private UploadAnswerListener uploadAnswerListener = new UploadAnswerListener();
    //submitButton
    JButton submitFormButton = new JButton("Submit");
    SubmitFormListener submitFormListener = new SubmitFormListener();
    //CENTER
    private JPanel centerPanel = new JPanel();
    private JLabel questionLabel = new JLabel("Question:");
    private JTextArea questionArea = new JTextArea();
    private JPanel questionImageArea = new JPanel();


    public void createGUI(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750,750);

        frame.add(panel);


        //North
        northPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(northPanel, BorderLayout.NORTH);
        northPanel.add(appNameLabel);
        northPanel.add(subjectLabel);
        northPanel.add(subjectArea);
        northPanel.add(chooseSubjectField);
        subjectArea.addItemListener(itemListener);

        //East

        eastPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        panel.add(eastPanel, BorderLayout.EAST);
        eastPanel.add(showAnswerButton, BorderLayout.EAST);
        showAnswerButton.addActionListener(answerListener);
        eastPanel.add(answerArea, BorderLayout.EAST);
        eastPanel.add(answerImageArea,BorderLayout.EAST);
        answerArea.setEditable(false);

        //South
        southPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(southPanel, BorderLayout.SOUTH);
        southPanel.add(previousQuestionButton, BorderLayout.SOUTH);
        southPanel.add(nextQuestionButton, BorderLayout.SOUTH);
        nextQuestionButton.addActionListener(nextListener);
        previousQuestionButton.addActionListener(previousListener);

        //West
        panel.add(westPanel, BorderLayout.WEST);
        westPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.add(addQuestionButton);
        addQuestionButton.addActionListener(addQuestionListener);
        westPanel.add(formPanel);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        JPanel a = new JPanel(new GridBagLayout());
        formPanel.add(chooseSubjectLabel);
        formPanel.add(chooseSubjectField);
        chooseSubjectField.setText("Enter Subject");
        chooseSubjectField.setMaximumSize(new Dimension(400,20));
        useCurrentBox.addActionListener(useCurrentListener);
        formPanel.add(useCurrentBox);
        formPanel.add(questionTypeLabel);
        formPanel.add(questionTextRadio);
        formPanel.add(questionImageRadio);
        questionTypeGroup.add(questionTextRadio);
        questionTextRadio.setSelected(true);
        questionTypeGroup.add(questionImageRadio);
        formPanel.add(answerTypeLabel);
        formPanel.add(answerTextRadio);
        answerTypeGroup.add(answerTextRadio);
        answerTypeGroup.add(answerImageRadio);
        answerTextRadio.setSelected(true);
        formPanel.add(answerTextRadio);
        formPanel.add(answerImageRadio);
        formPanel.setVisible(false);
        questionTextRadio.addItemListener(questionTextListener);
        answerTextRadio.addItemListener(answerTextListener);
        questionImageRadio.addItemListener(questionImageListener);
        answerImageRadio.addItemListener(answerImageListener);
        uploadQuestionButton.addActionListener(uploadQuestionListener);
        uploadAnswerButton.addActionListener(uploadAnswerListener);
        //QuestionTextPanel
        questionTextPanel.add(enterQuestionLabel);
        questionTextPanel.add(enterQuestionField);
        formPanel.add(questionTextPanel);
        answerTextPanel.add(enterAnswerLabel);
        answerTextPanel.add(enterAnswerField);
        formPanel.add(answerTextPanel);
        //QuestionImagePanel
        questionImagePanel.add(questionImageLabel);
        questionImagePanel.add(uploadQuestionButton);
        formPanel.add(questionImagePanel);
        questionImagePanel.setVisible(false);
        enterQuestionField.setColumns(4);
        //AnswerTextPanel;
        answerTextPanel.add(enterAnswerLabel);
        answerTextPanel.add(enterAnswerField);
        formPanel.add(answerTextPanel);
        answerTextPanel.add(enterAnswerLabel);
        answerTextPanel.add(enterAnswerField);
        enterAnswerField.setEditable(true);



        formPanel.add(answerTextPanel);
        //AnswerImagePanel
        answerImagePanel.add(answerImageLabel);
        answerImagePanel.add(uploadAnswerButton);
        formPanel.add(answerImagePanel);
        answerImagePanel.setVisible(false);
        //submit button
        submitFormButton.addActionListener(submitFormListener);
        formPanel.add(submitFormButton);


        // Center
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(questionLabel);
        centerPanel.add(questionArea);
        questionArea.setEditable(false);
        centerPanel.add(questionImageArea);
        questionImageArea.setVisible(false);



        //Visibility
        frame.setVisible(true);
    }//ends createGUI
    //addSubject Adds a subject to the SubjectArea in the GUI
    public void addSubject(String subject)
    {
        subjectArea.addItem(subject);
    }

    //makes questionArea visible, image area invisible and sets the text
    public void setUpQuestionText(){
        JavaSQLConnect connectObj = Cards.getJavaMySQLConnectObj();
        questionArea.setText(connectObj.getQuestion
                (Cards.getCurrentIndexInKeys()));
        questionArea.setVisible(true);
        questionImageArea.setVisible(false);
    }
    //this one should be used when the show answer, button is clicked.

    //makes answerArea visible, image answer area invisible and sets the text in answer area
    //should be called when show question is clicked and out current object is an answer
    public void setUpAnswerText(){
        JavaSQLConnect connectObj = Cards.getJavaMySQLConnectObj();
        answerArea.setText(connectObj.getAnswer());
        answerArea.setVisible(true);
        answerImageArea.setVisible(false);
    }

    //setUpQuestionImage:
    //sets the question text area to invisible and the image area to visible
    //adds the picture through fileHandler constructor to the questionImageArea
    //calls pack on the frame
    //sets max size for the area, though this doesnt seem to do much?
    public void setUpQuestionImage(){
        questionArea.setVisible(false);
        questionImageArea.setVisible(true);
        questionImageArea.setMaximumSize(new Dimension(100,100));
        //going to a text based question
        FileHandler fh = new FileHandler(new String());
        //adding the fileHandler to the correct panel
        questionImageArea.add(fh);
        //lets the frame pack images down to size
        frame.pack();
    }

    public void setUpAnswerImage(){
        answerArea.setVisible(false);
        answerImageArea.setVisible(true);
        //answerImageArea.setMaximumSize(new Dimension(20,20));
        //going to a text based question
        FileHandler fh = new FileHandler(1);
        //adding the fileHandler to the correct panel
        //answerImageArea.add(new JLabel("I Just got added to answer Image Area as a test"));
        answerImageArea.add(fh);

        //lets the frame pack images down to size
        frame.pack();
    }




    //handleQuestionCase(): this first clears what existed before in all output areas for questions
    // of the GUI, then checks whether the current question/ answer
    // is image or text or some combo of the two and
    //tells the GUI to display the correct things.
    public void handleQuestionCase(){
            cleanUpAllQuestions();
            cleanUpAllAnswers();

            JavaSQLConnect connectObj = Cards.getJavaMySQLConnectObj();

        switch(Cards.getJavaMySQLConnectObj().getImageState()){

            case "NEITHER":
                //sets the question text area visible, questionImageArea invisible and gets and sets the text
                setUpQuestionText();
                answerArea.setVisible(true);
                break;
            case "ANSWERONLY":
                setUpQuestionText();
                break;
            case "QUESTIONONLY":
                setUpQuestionImage();
                break;
            case "BOTH":
                setUpQuestionImage();
                break;
        }//ends switch statement


    }//ends handleQuestionCase


    //handleAnswerCase(): this first clears what existed before in all output areas for answers
    //    // of the GUI, then this checks whether the current question/ answer
    // is image or text or some combo of the two and
    //tells the GUI to display the correct things.
    //this should be called when someone hits show answer and we need to figure out what to display/ turn off.
    public void handleAnswerCase(){
        cleanUpAllAnswers();
        JavaSQLConnect connectObj = Cards.getJavaMySQLConnectObj();

        answerImageArea.removeAll();

        switch(Cards.getJavaMySQLConnectObj().getImageState()){
            case "NEITHER":
                //sets the question text area visible, questionImageArea invisible and gets and sets the text
                setUpAnswerText();
                break;
            case "ANSWERONLY":
                setUpAnswerImage();
                break;
            case "QUESTIONONLY":
                setUpAnswerText();
                break;
            case "BOTH":
                setUpAnswerImage();
                break;
        }//ends switch statement

    }//ends handleQuestionCase


    //switchType generically will set deactivate fromComponent and activate toComponent.
    public void switchType(Component fromComponent,Component toComponent){
        fromComponent.setVisible(false);
        toComponent.setVisible(true);

        //going to a text based question
        if(toComponent.equals(questionArea)){
           questionArea.setText(Cards.getJavaMySQLConnectObj().getQuestion
                    (Cards.getCurrentQuestionKeys().get
                            (Cards.getCurrentQuestionIndex())));
        }else if(toComponent.equals(questionImageArea)){
            FileHandler fh = new FileHandler();
            //adding the fileHandler to the correct panel
            questionImageArea.add(new FileHandler(new String()));
            //lets the frame pack images down to size
            frame.pack();
            frame.setVisible(true);
        }
    }//ends switchType

    class ChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {

            }
            else{questionImagePanel.setVisible(false);}
        }
    }//ends QuestionImageListener

    //used for clearing the GUI of old Question data
    public  void cleanUpAllQuestions(){
        questionArea.removeAll();
        questionImageArea.removeAll();
    }

    //used for clearing the GUI of old Answer data
    public  void cleanUpAllAnswers(){
        answerArea.removeAll();
        answerImageArea.removeAll();
    }




    //ItemChangeListener is an
    //inner class has access to parent class  private memeber variables
    //it listens for a change to the comboBox for subjects and changes the questions
    //accordingly
    class ItemChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            if(event.getStateChange() == ItemEvent.SELECTED){
                Object item = event.getItem();
                String subject = item.toString();

                    JavaSQLConnect connectObj = new JavaSQLConnect();

                //ESTABLISH A CONNECTION WITH THE DATABASE
                try {
                    connected = Cards.getConnected();
                    //connectObj.printSQLColumn(connected, 3); //for testing purposes

                    for(Object words: getSubjectArea().getSelectedObjects()){//getSelectedObjects returns a single element array of the current object
                        subject = words.toString();
                    }
                    //Setting the initial Question to the GUI
                    Cards.setCurrentSubject(subject);
                    ArrayList<String> questionKeyList = connectObj.getQuestionKeys( subject);
                    String firstQuestionKey = questionKeyList.get(0);
                    Cards.setCurrentQuestionKeys(questionKeyList);
                    //handleQuestionCase dynamically handles setting up current question,be it an image or a text .
                    handleQuestionCase();
                    //Cards.displayCurrentQuestionKeys(); // for debugging purposes
                    //clears the last question`s answer off of the screen
                    answerArea.setText("");
                } catch (Exception e) {
                    System.out.println(e);
                }//ends catch



            }//ends if
        }
    }//ends class ItemChangeListener
        //Goes to next question and updates Cards.currentQuestionIndex
    class NextClickListener implements ActionListener{

        public void actionPerformed(ActionEvent event){

            int nextQuestionKey;


            try {
                JavaSQLConnect connectObj = new JavaSQLConnect();
                connected = Cards.getConnected();


                // Empty Key set
                if (Cards.getCurrentQuestionKeys().size() == 0) {
                    System.out.println("No current Question Keys recorded in Cards Class.");
                    //current Question index is at the end or out of bounds
                } else if (Cards.getCurrentQuestionIndex() >= Cards.getCurrentQuestionKeys().size() - 1) {
                        Cards.setCurrentQuestionIndex(0);
                    questionArea.setText(connectObj.getQuestion
                            (Cards.getCurrentQuestionKeys().get
                                    (Cards.getCurrentQuestionIndex())));


                    //Question index is in bounds
                } else {
                    nextQuestionKey = Integer.parseInt(Cards.getCurrentQuestionKeys().get(Cards.getCurrentQuestionIndex() + 1));
                    //updates the current question in cards to increment by 1
                    Cards.setCurrentQuestionIndex(Cards.getCurrentQuestionIndex() + 1);
                    //creates a variable of this new value converted to string

                    //testing purposes

                }//ends else
                //clears the last question`s answer off of the screen



                answerArea.setText("");

                //TESTING AREA TESTING AREA TESTING AREA TESTING AREA TESTING AREA----------------------------------------

                //handleQuestionCase(): finds if out question/answer is an image or text or combo of both and displays the correct info,
                //and turns visible/invisible the correct things.
                //we call this after the index has been updated so were on the next questions index here.
                handleQuestionCase();
                //testing area

            }catch(Exception e){
                System.out.println(e);
            }


        }//ends actionPerformed

    }//ends class NextClickListener

    //PreviousClickListener is an inner class that
    // represents a listener that is is attached to the previous button.
    class PreviousClickListener implements ActionListener{

        public void actionPerformed(ActionEvent event){

            try {
                JavaSQLConnect connectObj = new JavaSQLConnect();
                connected = Cards.getConnected();
                // Empty Key set
                if (Cards.getCurrentQuestionKeys().size() == 0) {
                    System.out.println("No current Question Keys recorded in Cards Class.");

                    //If the index is at the first question or is negative, loop back up to the top.
                } else if (Cards.getCurrentQuestionIndex() <= 0) {
                    Cards.setCurrentQuestionIndex(Cards.getCurrentQuestionKeys().size() -1);
                    questionArea.setText(connectObj.getQuestion
                            (Cards.getCurrentQuestionKeys().get
                                    (Cards.getCurrentQuestionIndex())));


                    //Question index is in bounds
                } else {
                    //updates the current question in cards to increment by 1
                    Cards.setCurrentQuestionIndex(Cards.getCurrentQuestionIndex() -1);
                    //creates a variable of this new value converted to string

                    questionArea.setText(connectObj.getQuestion
                            (Cards.getCurrentQuestionKeys().get
                                    (Cards.getCurrentQuestionIndex())));


                }
                //clears the last question`s answer off of the screen

                answerArea.setText("");
                handleQuestionCase();

            }catch(Exception e){
                System.out.println(e);
            }

        }
    }//ends class PreviousClickListener

    //ShowAnswerListener gets the current answer and sets the GUI to display it
    class ShowAnswerListener implements ActionListener{

       public void actionPerformed(ActionEvent e){

          // sees what type of question answer combo we got and displays and corrects what it needs to.
           handleAnswerCase();
       }

    }//ends ShowAnswerListener

    //AddQuestionListener opens up the formPanel to fill out
    class AddQuestionListener implements ActionListener{
        public void actionPerformed(ActionEvent event){

            if(!formPanel.isVisible()) {
                formPanel.setVisible(true);
                addQuestionButton.setText("Back");
            }
            else{
                formPanel.setVisible(false);
                addQuestionButton.setText("Add New Question");
            }

        }//ends actionPerformed
    }//ends AddQuestionListener

    //QuestionTextListener says if the radio button for text is checked, show the correctPanel
    class QuestionTextListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                questionTextPanel.setVisible(true);
            }
            else{questionTextPanel.setVisible(false);}
        }
    }//ends QuestionTextListener

    //AnswerTextListener says if the radio button for text is checked, show the correctPanel
    class AnswerTextListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                answerTextPanel.setVisible(true);
            }
            else{answerTextPanel.setVisible(false);}
        }
    }//ends AnswerTextListener
    class QuestionImageListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                questionImagePanel.setVisible(true);
            }
            else{questionImagePanel.setVisible(false);}
        }
    }//ends QuestionImageListener

    class AnswerImageListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                answerImagePanel.setVisible(true);
            }
            else{answerImagePanel.setVisible(false);}
        }
    }//ends QuestionImageListener

    class UploadQuestionListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            FileHandler fh = Cards.getFileHandlerObj();
             fh.fileOpenBrowser("question"); // opens the file browser and returns the selected string url
            String questionFileName = fh.getQuestionFileName();
            System.out.println("New Question image added: " + questionFileName);
            String questionFromPath = fh.getQuestionFromPath()+ "\\";
            String questionToPath = Cards.getCurrentFolder() + "images"+ "\\";
            System.out.println("From Path: " + questionFromPath +
                    "\n" + "To Path: " + questionToPath +
                    "\n" + "fileName: " + questionFileName);
            fh.moveFile(questionFromPath,questionToPath,questionFileName);
            //after fileOpenBrowser is called, we can add the image using From and to variables
            System.out.println("New question image added: " + questionFileName);
            Cards.setCurrentQuestionImageURL(questionToPath + questionFileName);
        }
    }

    class UploadAnswerListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            FileHandler fh = Cards.getFileHandlerObj();
            // opens the file browser and returns the selected string url
            //fileOpenBrowser updates it`s from and to File variables within the FileHander Class
            fh.fileOpenBrowser("answer");
            String answerFileName = fh.getAnswerFileName();
            String answerFromPath = fh.getAnswerFromPath()+ "\\";
            String answerToPath = Cards.getCurrentFolder() + "images"+ "\\";
            System.out.println("From Path: " + answerFromPath+
                    "\n" + "To Path: " + answerToPath +
                    "\n" + "fileName: " + answerFileName);
            fh.moveFile(answerFromPath,answerToPath,answerFileName);
            //after fileOpenBrowser is called, we can add the image using From and to variables
            // static String[] newQuestion = {"subject", "question", "answer", "questionImg", "answerImg"};
            System.out.println("New answer image added: " + answerFileName);
            Cards.setCurrentAnswerImageURL(answerToPath + answerFileName);
}
    }//ends uploadAnswerListener

    class SubmitFormListener implements ActionListener{
        public void actionPerformed(ActionEvent event){

            //sets subject
            Cards.getNewQuestion()[0] = chooseSubjectField.getText();

            //sets question and questionImg
            if(questionImagePanel.isVisible()){
                //currentAnswerImageURL;
                //static String question QuestionImageURL;
                Cards.getNewQuestion()[3] = Cards.getCurrentQuestionImageURL();
                Cards.getNewQuestion()[1] = null;
            }else{
                //{"subject", "question", "answer", "questionImg", "answerImg"};
                Cards.getNewQuestion()[1] = enterQuestionField.getText();
                Cards.getNewQuestion()[3] = null;

                //Cards.getNewQuestion()[3] holds the image URL

            }
            if(answerImagePanel.isVisible()){
                //currentAnswerImageURL;
                //static String question QuestionImageURL;
                Cards.getNewQuestion()[4] = Cards.getCurrentAnswerImageURL();
                Cards.getNewQuestion()[2] = null;
            }else{
                //{"subject", "question", "answer", "questionImg", "answerImg"};
                Cards.getNewQuestion()[2] = enterAnswerField.getText();
                Cards.getNewQuestion()[4] = null;
            }
            //updating the SQL Database with given information
            Cards.getJavaMySQLConnectObj().addNewQuestion(Cards.getNewQuestion()[0],
            Cards.getNewQuestion()[1],Cards.getNewQuestion()[2],Cards.getNewQuestion()[3],Cards.getNewQuestion()[4]);



        }//ends actionPerformed
    }//ends SubmitFormListener

    class UseCurrentListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            chooseSubjectField.setText(Cards.getCurrentSubject());
        }
    }//ends UseCurrentListener





                //AUTO GENERATED GETTERS AND SETTERS:
    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JButton getShowAnswerButton() {
        return showAnswerButton;
    }

    public void setShowAnswerButton(JButton showAnswerButton) {
        this.showAnswerButton = showAnswerButton;
    }

    public JLabel getAppNameLabel() {
        return appNameLabel;
    }

    public void setAppNameLabel(JLabel appNameLabel) {
        this.appNameLabel = appNameLabel;
    }

    public JLabel getSubjectLabel() {
        return subjectLabel;
    }

    public void setSubjectLabel(JLabel subjectLabel) {
        this.subjectLabel = subjectLabel;
    }

    public JComboBox getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(JComboBox subjectArea) {
        this.subjectArea = subjectArea;
    }

    public JPanel getNorthPanel() {
        return northPanel;
    }

    public void setNorthPanel(JPanel northPanel) {
        this.northPanel = northPanel;
    }

    public JPanel getEastPanel() {
        return eastPanel;
    }

    public void setEastPanel(JPanel eastPanel) {
        this.eastPanel = eastPanel;
    }

    public JPanel getSouthPanel() {
        return southPanel;
    }

    public void setSouthPanel(JPanel southPanel) {
        this.southPanel = southPanel;
    }

    public JPanel getWestPanel() {
        return westPanel;
    }

    public void setWestPanel(JPanel westPanel) {
        this.westPanel = westPanel;
    }

    public JButton getAddQuestionButton() {
        return addQuestionButton;
    }

    public void setAddQuestionButton(JButton addQuestionButton) {
        this.addQuestionButton = addQuestionButton;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public void setCenterPanel(JPanel centerPanel) {
        this.centerPanel = centerPanel;
    }

    public JLabel getQuestionLabel() {
        return questionLabel;
    }

    public void setQuestionLabel(JLabel questionLabel) {
        this.questionLabel = questionLabel;
    }

    public JTextArea getQuestionArea() {
        return questionArea;
    }

    public void setQuestionArea(JTextArea questionArea) {
        this.questionArea = questionArea;
    }

    public JFrame getFrame(){
        return this.frame;
    }
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getQuestionImageArea() {
        return questionImageArea;
    }

    public void setQuestionImageArea(JPanel questionImageArea) {
        this.questionImageArea = questionImageArea;
    }
}//ends GUI
