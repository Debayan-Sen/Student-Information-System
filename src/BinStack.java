import java.io.*;

public class BinStack {
    int capacity = 2;
    Student binBox[] = new Student[capacity];
    int top = 0;

    public void push(Student data){
        if (size()==capacity){
            expand();
        }
        binBox[top] = data;
        top++;
    }

    private void expand() {
        int length = size();
        Student newBin[] = new Student[capacity*2];
        System.arraycopy(binBox, 0, newBin, 0, length);
        binBox = newBin;
        capacity *= 2;
    }

    public Student pop(){
        Student data = null;
        if(isEmpty()){
            System.out.println("stack empty");
        }else {
            top--;
            data = binBox[top];
            binBox[top]=null;
            shrink();
        }
        return data;
    }

    private void shrink() {
        int length = size();
        if (length<=(capacity/2)/2){
            capacity = capacity/2;
        }
        Student newBin[] = new Student[capacity];
        System.arraycopy(binBox, 0, newBin, 0, length);
        binBox = newBin;
    }

    public Student peek(){
        Student data;
        data = binBox[top-1];
        return data;
    }

    public boolean isEmpty(){
        return top<=0;
    }

    public int size() {
        return top;
    }

    public Student get(int index){
        return binBox[index];
    }

    public void show(){
        for (Student student : binBox){
            if (student != null){
                System.out.println(student.getRoll() + ", " + student.getFirstName() + ", " + student.getLastName() + ", " + student.getGrade() + ", " + student.getMarks());
            }
        }
    }

    public void saveToFile(String file_path){
        File file = new File(file_path);

        MainSystem.createDirectory(file);

        try {
            FileWriter fileWriter;
            fileWriter = new FileWriter(file.getAbsoluteFile(),false);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

//            Student binStudent[] = binBox;

//            int i = 0;
            for (Student binStudent : binBox){
                if (binStudent != null){
                    bufferedWriter.write(binStudent.getRoll() + ", " + binStudent.getFirstName() + ", " + binStudent.getLastName() + ", " + binStudent.getGrade() + ", " + binStudent.getMarks() + "\n");
                }
            }
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readFromFile(String file_path){
        File file = new File(file_path);

        if (!file.exists()) {
            System.out.println("File not exists!!");
            //TODO:log for file not exists
        }

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while((line=br.readLine())!=null){
                String[] arr = line.split(",");
                int Roll = Integer.parseInt(arr[0].trim());
                String FirstName = arr[1].trim();
                String LastName = arr[2].trim();
                String Grade = arr[3].trim();
                int Marks = Integer.parseInt(arr[4].trim());

                Student student = new Student(Roll,FirstName,LastName,Marks,Grade);
                push(student);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
