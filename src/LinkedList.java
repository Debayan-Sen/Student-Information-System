import java.io.*;

public class LinkedList {
    Node head;

    public void insert(Student data){
        Node node = new Node();
        node.data = data;
        node.next = null;

        if(head==null){
            head = node;
        }else{
            Node n = head;
            while (n.next!=null){
                n = n.next;
            }
            n.next = node;
        }
    }

    public void insertAtStart(Student data){
        Node node = new Node();
        node.data = data;
        node.next = head;
        head = node;
    }

    public void insertAt(int index, Student data){
        Node node = new Node();
        node.data = data;
        node.next = null;

        if(index==0){
            insertAtStart(data);
        }else {
            Node n = head;
            for(int i = 0; i<index-1;i++){
                n = n.next;
            }
            node.next = n.next;
            n.next = node;
        }
    }

    public void set(int index, Student data){
        Student temp;
        Node n = head;
        for(int i = 0; i<index; i++){
            n = n.next;
        }
        temp = n.data;  //if required to undo
        n.data = data;
    }

    public Student deleteAt(int index){
        Student temp;
        if(index==0){
            temp = head.data;
            head = head.next;
        }else{
            Node n = head;
            Node n1 = null;
            for (int i = 0; i<index-1; i++){
                n = n.next;
            }
            n1 = n.next;
            temp = n1.data;
            n.next = n1.next;
        }
        return temp;
    }

    public LinkedList search(String type, String key){
        LinkedList searchList = new LinkedList();
        Node node = head;

        if (type.equals("roll")){
            do {
                if (node.data.getRoll()==Integer.parseInt(key)){
                    searchList.insert(node.data);
                }
                node = node.next;
            } while (node.next!=null);
            if (node.data.getRoll()==Integer.parseInt(key)){
                searchList.insert(node.data);
            }
        }

        if (type.equals("firstname")){
            do {
                if (node.data.getFirstName().toLowerCase().contains(key)){
                    searchList.insert(node.data);
                }
                node = node.next;
            } while (node.next!=null);
            if (node.data.getFirstName().toLowerCase().contains(key)){
                searchList.insert(node.data);
            }
        }

        if (type.equals("lastname")){
            do {
                if (node.data.getLastName().toLowerCase().contains(key)){
                    searchList.insert(node.data);
                }
                node = node.next;
            } while (node.next!=null);
            if (node.data.getLastName().toLowerCase().contains(key)){
                searchList.insert(node.data);
            }
        }

        if (searchList.size()!=0){
            return searchList;
        }else
        {
            return null;
        }
    }

    public int size(){
        int count = 0;
        if(head != null){
            Node node = head;

            while (node.next!=null){
                count++;
                node = node.next;
            }
            count++;
            return count;
        }else{
            return count;
        }
    }

    public Student get(int index){

        int flag = 0;
        int count = 0;
        Node node = head;
        while(count <= index){
            if(count==index){
                flag = 1;
                break;
            }
            if(node.next==null){
                break;
            }
            count++;
            node = node.next;
        }

        if(flag==1){
            return node.data;
        }else {
            return null;
        }
    }

    public void show(){
        Node node = head;

        while (node.next!=null){
            System.out.println(node.data.getRoll()+", "+node.data.getFirstName()+", "+node.data.getLastName()+", "+node.data.getGrade()+", "+node.data.getMarks());
            node = node.next;
        }
        System.out.println(node.data.getRoll()+", "+node.data.getFirstName()+", "+node.data.getLastName()+", "+node.data.getGrade()+", "+node.data.getMarks());
    }

    public void saveToFile(String file_path){
        File file = new File(file_path);

        MainSystem.createDirectory(file);

        try {
            FileWriter fileWriter;
            fileWriter = new FileWriter(file.getAbsoluteFile(),false);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            Node node = head;

            while (node.next!=null){
                bufferedWriter.write(node.data.getRoll()+", "+node.data.getFirstName()+", "+node.data.getLastName()+", "+node.data.getGrade()+", "+node.data.getMarks()+"\n");
                node = node.next;
            }
            bufferedWriter.write(node.data.getRoll()+", "+node.data.getFirstName()+", "+node.data.getLastName()+", "+node.data.getGrade()+", "+node.data.getMarks()+"\n");
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
                insert(student);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
