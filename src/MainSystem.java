import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

public class MainSystem {

    public static String student_file_path = "D:\\IdeaProjects\\Student Information System\\Docs\\students.txt";
    public static String log_file_path = "D:\\IdeaProjects\\Student Information System\\Docs\\log.txt";
    public static String bin_file_path = "D:\\IdeaProjects\\Student Information System\\Docs\\bin.txt";
    public static String studentCount_file_path = "D:\\IdeaProjects\\Student Information System\\Docs\\student count.txt";

    public static void main(String args[]){

//        Student student1 = new Student(1, "good", "boi", 100,"AA");
//        Student student2 = new Student(2,"better", "boy",101,"AA");
//        Student student3 = new Student(3,"yo", "boi",65,"B+");
//
////        LinkedList list = new LinkedList();
////        list.insertAtStart(student1);
////        list.insertAtStart(student2);
////        list.insert(student3);
//
////        list.saveToFile(file_path);
////        list.readFromFile(file_path);

        int lastRoll = getLatestRoll(studentCount_file_path)+1;
        System.out.println(lastRoll);

//
//        LogLinkedList logList = new LogLinkedList();
//        logList.insertAtStart(new Operation("add", 9, LocalDate.now() +" "+ LocalTime.now().toString().substring(0,8)));
//
//        logList.saveToFile(log_file_path);
//
//        logList.show();

//        list.show();
//
//        int a=list.size();
//        System.out.println(list.head);

//        BinStack bin = new BinStack();
////        bin.push(student2);
////        bin.push(student2);
////        bin.push(student2);
////        bin.push(student1);
////        bin.push(student2);
////        bin.push(student1);
////        bin.push(student1);
////        bin.show();
////        System.out.print(bin.size());
////        bin.saveToFile(bin_file_path);
//        bin.readFromFile(bin_file_path);
//        bin.show();
//        System.out.print(bin.size());
//
//        System.out.print(bin.peek().getRoll());

//        // storing input in variable
//        int n = 4;
//        // create string array called names
//        String names[]
//                = { "Sourabh", "Anoop" , "Harsh", "Alok", "Tanuj" };
//        // inbuilt sort function
//        Arrays.sort(names);
//        // print output array
//        System.out.println(
//                "The names in alphabetical order are: ");
//        for (int i = 0; i < n; i++) {
//            System.out.println(names[i]);
//        }

    }

    public static int getLatestRoll(String file_path) {

        int lastRoll = 0;
        File file = new File(file_path);

        if (!file.exists()) {
            System.out.println("File not exists!!");
            //TODO:log for file not exists
        }

        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            if ((line = br.readLine())!=null){
                lastRoll = Integer.parseInt(line);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return lastRoll;
    }

    public static void settLatestRoll(String file_path, String roll) {

        File file = new File(file_path);

        if (!file.exists()){
            createDirectory(file);
        }

        try {
            FileWriter fileWriter;
            fileWriter = new FileWriter(file.getAbsoluteFile(),false);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(roll);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void createDirectory(File file) {
        File directory = new File(file.getParent());

        try {
            if (!directory.exists()){
                directory.mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
