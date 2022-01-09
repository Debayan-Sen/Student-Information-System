import java.io.*;

public class LogLinkedList {
    LogNode head;

    public void insert(Operation data){
        LogNode node = new LogNode();
        node.data = data;
        node.next = null;

        if(head==null){
            head = node;
        }else{
            LogNode n = head;
            while (n.next!=null){
                n = n.next;
            }
            n.next = node;
        }
    }

    public void insertAtStart(Operation data){
        LogNode node = new LogNode();
        node.data = data;
        node.next = head;
        head = node;
    }

    public void show() {
        LogNode node = head;

        while (node.next!=null){
            System.out.println(node.data.getOperationName()+", "+node.data.getRoll()+", "+node.data.getTime());
            node = node.next;
        }
        System.out.println(node.data.getOperationName()+", "+node.data.getRoll()+", "+node.data.getTime());
    }

    public int size(){
        int count = 0;
        if(head != null){
            LogNode node = head;

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

    public Operation get(int index){

        int flag = 0;
        int count = 0;
        LogNode node = head;
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

    public void saveToFile(String file_path){
        File file = new File(file_path);

        MainSystem.createDirectory(file);

        try {
            FileWriter fileWriter;
            fileWriter = new FileWriter(file.getAbsoluteFile(),false);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            LogNode node = head;

            while (node.next!=null){
                bufferedWriter.write(node.data.getOperationName()+", "+node.data.getRoll()+", "+node.data.getTime()+"\n");
                node = node.next;
            }
            bufferedWriter.write(node.data.getOperationName()+", "+node.data.getRoll()+", "+node.data.getTime()+"\n");
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
                String OperationName = arr[0].trim();
                int Roll = Integer.parseInt(arr[1].trim());
                String Time = arr[2].trim();

                Operation operation = new Operation(OperationName, Roll, Time);
                insert(operation);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
