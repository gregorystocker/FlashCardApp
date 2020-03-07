import java.awt.*;
import java.awt.font.ImageGraphicAttribute;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.File;


/**
 * This class demonstrates how to load an Image from an external file
 */

   /*TESTER MAIN CALLS

    public static void main(String[] args) {


        //loading and displaying an image
        JFrame f = new JFrame("Load Image Sample");
        f.add(new FileHandler());
        f.pack();//this makes all the frames contents to large sizes
        f.setVisible(true);
        FileHandler fh = new FileHandler();
        fh.createFolder("C:\\Users\\grego\\IdeaProjects\\imageLoader\\src\\", "images");
        // From "C:\\Users\\grego\\OneDrive\\Pictures\\107p7.jpg"
        // To C:\\Users\\grego\\IdeaProjects\\imageLoader\\src\\images\\107p7.jpg
        fh.moveFile("C:\\Users\\grego\\OneDrive\\Pictures\\",
                "C:\\Users\\grego\\IdeaProjects\\imageLoader\\src\\images\\",
                "102p11tension.jpg");

        fh.fileOpenBrowser();

    }//ends public static void main
    */
public class FileHandler extends Component {

    BufferedImage img = new BufferedImage(60,6,Image.SCALE_SMOOTH);
    String path;
    File file;
    boolean bool;
    String answerFileName;
    String answerFromPath;
    String questionFileName;
    String questionFromPath;



    //public void paint(Graphics g) {
       // g.drawImage(img, 0, 0, null);
    //}
    public void paint(Graphics g) {
        try {
            String currentQuestionKey = Cards.getCurrentQuestionKeys().get(Cards.currentQuestionIndex);
            img = ImageIO.read(new File(Cards.getCurrentFolder() + "images"+ "\\" + Cards.getJavaMySQLConnectObj().getQuestionImg()));

        } catch (IOException e) {
        }
        g.drawImage(img, 100, 100, null);
    }
    public FileHandler(int dummyInt) {
        try {

            System.out.println("The url I`m trying is:" + Cards.getJavaMySQLConnectObj().getAnswerImg());
            img = ImageIO.read(new File(Cards.getJavaMySQLConnectObj().getAnswerImg()));

        } catch (IOException e) {
        }
    }
    public FileHandler(String dummyVariableImage) {
        try {

            System.out.println("The question`s url I`m trying is:" + Cards.getJavaMySQLConnectObj().getQuestionImg());
            img = ImageIO.read(new File(Cards.getJavaMySQLConnectObj().getQuestionImg()));

        } catch (IOException e) {
        }
    }


    public FileHandler(String dummyVariableImage, int w, int h, int knownIndex) {
        try {

            System.out.println("The answer`s url I`m trying is:" + Cards.getJavaMySQLConnectObj().getQuestionImg(20));
            img = ImageIO.read(new File(Cards.getJavaMySQLConnectObj().getQuestionImg(20)));

        } catch (IOException e) {
        }

    }
    public FileHandler() {

    }
    public String getCurrentFolder(){
        String current ="";
        try {

             current = new java.io.File(".").getCanonicalPath() + "\\src\\";
        }catch(IOException e){System.out.println(e);}
        return current;
    }

    public Dimension getPreferredSize() {
        if (img == null) {
            return new Dimension(100, 100);
        } else {
            return new Dimension(img.getWidth(null), img.getHeight(null));
        }
    }

        //createFolder creates a new folder given path and name as Strings
        public void createFolder(String location, String name){
            path = location + name;
            //Creating a File object
            file = new File(path);
            //Creating the directory
            bool = file.mkdir(); //this returns false if the file has already been created.
            if (bool) {
                System.out.println("Directory created successfully");
            } else {
                System.out.println("Sorry couldn’t create specified directory");
            }
        }//ends CreateFolder

        //moveFile moves a file`s location and returns a String representation of the new URL of the file
        public String moveFile(String fromPath, String toPath, String name){
            //Moving a file from one place to another
            File file = new File(fromPath + name);
            // renaming the file and moving it to a new location
            //just by calling this rename function and giving a differnet path we can move it where we want
            if(file.renameTo
                    (new File(toPath + name)))
            {
                // if file is copied where we want, delete the original file location now
                //file.delete();
                System.out.println("File moved successfully");
                return toPath + name;
            }
            else
            {
                System.out.println("Failed to move the file");
                return null;
            }

        }//ends moveFile

    //moveFile moves a file`s location and returns a String representation of the new URL of the file
    public String moveFileAndDelete(String fromPath, String toPath, String name){
        //Moving a file from one place to another
        File file = new File(fromPath + name);
        // renaming the file and moving it to a new location
        //just by calling this rename function and giving a differnet path we can move it where we want
        if(file.renameTo
                (new File(toPath + name)))
        {
            // if file is copied where we want, delete the original file location now
            file.delete();
            System.out.println("File moved successfully");
            return toPath + name;
        }
        else {
            System.out.println("Failed to move the file");
            return null;
        }
    }//ends moveFileandDelete
        //fileOpenBrowser opens up the browse and returns the chosen file
        public String fileOpenBrowser(){
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            String filename = file.getName();
            String parent = file.getParent();
            answerFromPath = parent;
            answerFileName = filename;
            System.out.println("The from path is: " + parent);
            System.out.println("You have selected: " + filename);
            return filename;
        }
    public String fileOpenBrowser(String questionOrAnswer){
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        String filename = file.getName();
        String parent = file.getParent();
        if ( questionOrAnswer.equals("question")){
           questionFromPath = parent;
           questionFileName = filename;
        }else if(questionOrAnswer.equals("answer")){
            answerFromPath = parent;
            answerFileName = filename;
        }


        System.out.println("The from path is: " + parent);
        System.out.println("You have selected: " + filename);
        return filename;
    }

    public String getAnswerFileName() {
        return answerFileName;
    }

    public void setAnswerFileName(String answerFileName) {
        this.answerFileName = answerFileName;
    }

    public String getAnswerFromPath() {
        return answerFromPath;
    }

    public void setAnswerFromPath(String answerFromPath) {
        this.answerFromPath = answerFromPath;
    }

    public String getQuestionFileName() {
        return questionFileName;
    }

    public void setQuestionFileName(String questionFileName) {
        this.questionFileName = questionFileName;
    }

    public String getQuestionFromPath() {
        return questionFromPath;
    }

    public void setQuestionFromPath(String questionFromPath) {
        this.questionFromPath = questionFromPath;
    }


}//ends ImageLoader class