import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

public class MainUI {
    private JPanel rootPanel;
    private JTabbedPane tabPane;
    private JTextField textField_roll;
    private JTextField textField_firstName;
    private JTextField textField_lastName;
    private JTextField textField_marks;
    private JTextField textField_grade;
    private JTable table_Students;
    private JTextField textField_deleteRoll;
    private JButton deleteButton;
    private JComboBox search_comboBox;
    private JPanel searchPanel_Roll;
    private JTable table_searchResults;
    private JTextField enterKeyTextField;
    private JButton searchButton;
    private JButton button_addRecord;
    private JTextField textField_editRoll;
    private JTextField textField_editFN;
    private JTextField textField_editMarks;
    private JTextField textField_editLN;
    private JTextField textField_editGrade;
    private JButton updateRecordButton;
    private JTable table_edit;
    private JButton saveButton_add;
    private JButton saveButton_edit;
    private JButton clearAddFieldsButton;
    private JButton clearFieldsButton;
    private JTextField textField_deleteFN;
    private JTextField textField_deleteMarks;
    private JTextField textField_deleteLN;
    private JTextField textField_deleteGrade;
    private JTable table_delete;
    private JButton saveButton_delete;
    private JButton clear_deleteFieldsButton;
    private JTable table_log;
    private JTable table_bin;
    private JButton restoreRecordButton;

    private Student selectedStudent;
    private int selectedIndex;
    private String lastRoll;


    public MainUI() {
        LinkedList list = new LinkedList();
        list.readFromFile(MainSystem.student_file_path);
        createTableStudents(list);

        textField_roll.setText(String.valueOf(MainSystem.getLatestRoll(MainSystem.studentCount_file_path)+1));

        LogLinkedList logList = new LogLinkedList();
        logList.readFromFile(MainSystem.log_file_path);
        createTableLogs(logList);

        BinStack binStack = new BinStack();
        binStack.readFromFile(MainSystem.bin_file_path);
        createTableBin(binStack);

        loadTableValuesCentre();

        saveButton_add.setEnabled(false);
        saveButton_edit.setEnabled(false);
        saveButton_delete.setEnabled(false);


        //search tab
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LinkedList searchList;
                switch (search_comboBox.getSelectedItem().toString().toLowerCase()){
                    case "roll":
                        try {
                            int searchKey = Integer.parseInt(enterKeyTextField.getText());
                            if (searchKey != 0) {
                                try {
                                    searchList = list.search(search_comboBox.getSelectedItem().toString().toLowerCase(), String.valueOf(searchKey));
                                    createTableSearch(searchList);
                                } catch (Exception exception) {
                                    infoBox("No Data Found!!", "Search for Roll");
                                }
                            } else {
                                infoBox("Enter Valid Roll", "Error Search!!");
                            }
                        }catch (Exception exception){
                            infoBox("Enter Valid Roll", "Error Search!!");
                        }
                        break;
                    case "firstname":
                        try{
                            String searchKey2 = enterKeyTextField.getText().toLowerCase();
                            try{
                                searchList = list.search(search_comboBox.getSelectedItem().toString().toLowerCase(), searchKey2);
                                createTableSearch(searchList);
                            }catch (Exception exception){
                                infoBox("No Result found!!","Search for Firstname");
                            }
                        }catch (Exception exception){
                            infoBox("Enter a valid key to search","Search Bar");
                        }
                        break;
                    case "lastname":
                        try{
                            String searchKey3 = enterKeyTextField.getText().toLowerCase();
                            try {
                                searchList = list.search(search_comboBox.getSelectedItem().toString().toLowerCase(), searchKey3);
                                createTableSearch(searchList);
                            }catch (Exception exception){
                                infoBox("No Record Found!!", "Search for Lastname");
                            }
                        }catch (Exception exception){
                            infoBox("Enter a valid key to search","Search Bar");
                        }
                        break;
                }
            }

        });
        button_addRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.insert(new Student(
                        Integer.parseInt(textField_roll.getText()),
                        textField_firstName.getText(),
                        textField_lastName.getText(),
                        Integer.parseInt(textField_marks.getText()),
                        textField_grade.getText()
                        )
                );
                logList.insertAtStart(new Operation("Record Added", Integer.parseInt(textField_roll.getText()), LocalDate.now() +" "+ LocalTime.now().toString().substring(0,8)));
                createTableStudents(list);
                createTableLogs(logList);
                lastRoll = textField_roll.getText();
                textField_roll.setText(String.valueOf(Integer.parseInt(lastRoll)+1));
                saveButton_add.setEnabled(true);
            }
        });
        saveButton_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.saveToFile(MainSystem.student_file_path);
                logList.saveToFile(MainSystem.log_file_path);
                saveButton_add.setEnabled(false);
                MainSystem.settLatestRoll(MainSystem.studentCount_file_path,lastRoll);
                resetAddFields();
            }
        });
        updateRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedStudent.setRoll(Integer.parseInt(textField_editRoll.getText()));
                selectedStudent.setFirstName(textField_editFN.getText());
                selectedStudent.setLastName(textField_editLN.getText());
                selectedStudent.setMarks(Integer.parseInt(textField_editMarks.getText()));
                selectedStudent.setGrade(textField_editGrade.getText());
                list.set(selectedIndex, selectedStudent);
                logList.insertAtStart(new Operation("Record Updated", Integer.parseInt(textField_editRoll.getText()),LocalDate.now() +" "+ LocalTime.now().toString().substring(0, 8)));
                createTableStudents(list);
                createTableLogs(logList);
                saveButton_edit.setEnabled(true);
            }
        });
        saveButton_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.saveToFile(MainSystem.student_file_path);
                logList.saveToFile(MainSystem.log_file_path);
                saveButton_edit.setEnabled(false);
                resetEditFields();
            }
        });
        table_edit.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!table_edit.getSelectionModel().isSelectionEmpty()){
                    selectedIndex = table_edit.convertRowIndexToModel(table_edit.getSelectedRow());
                    selectedStudent = list.get(selectedIndex);
                    if(selectedStudent != null){
                        textField_editRoll.setText(String.valueOf(selectedStudent.getRoll()));
                        textField_editFN.setText(selectedStudent.getFirstName());
                        textField_editLN.setText(selectedStudent.getLastName());
                        textField_editMarks.setText(String.valueOf(selectedStudent.getMarks()));
                        textField_editGrade.setText(selectedStudent.getGrade());
                        updateRecordButton.setEnabled(true);
                        saveButton_edit.setEnabled(false);
                    }
                }
            }
        });
        table_delete.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!table_delete.getSelectionModel().isSelectionEmpty()){
                    selectedIndex = table_delete.convertRowIndexToModel(table_delete.getSelectedRow());
                    selectedStudent = list.get(selectedIndex);
                    if(selectedStudent != null){
                        textField_deleteRoll.setText(String.valueOf(selectedStudent.getRoll()));
                        textField_deleteFN.setText(selectedStudent.getFirstName());
                        textField_deleteLN.setText(selectedStudent.getLastName());
                        textField_deleteMarks.setText(String.valueOf(selectedStudent.getMarks()));
                        textField_deleteGrade.setText(selectedStudent.getGrade());
                        deleteButton.setEnabled(true);
                    }
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student deletedStudent;
                deletedStudent = list.deleteAt(selectedIndex);
                logList.insertAtStart(new Operation("Record Deleted", deletedStudent.getRoll(), LocalDate.now() +" "+ LocalTime.now().toString().substring(0, 8)));
                binStack.push(deletedStudent);
                createTableStudents(list);
                createTableLogs(logList);
                createTableBin(binStack);
                saveButton_delete.setEnabled(true);
            }
        });
        clearAddFieldsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetAddFields();
            }
        });
        clearFieldsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetEditFields();
            }
        });
        clear_deleteFieldsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetDeleteFields();
            }
        });
        saveButton_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.saveToFile(MainSystem.student_file_path);
                logList.saveToFile(MainSystem.log_file_path);
                binStack.saveToFile(MainSystem.bin_file_path);
                saveButton_delete.setEnabled(false);
                resetDeleteFields();
            }
        });
        restoreRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (binStack.size()!=0){
                    Student restoreStudent;
                    restoreStudent = binStack.pop();
                    list.insert(restoreStudent);
                    logList.insertAtStart(new Operation("Record Restored", restoreStudent.getRoll(), LocalDate.now() +" "+ LocalTime.now().toString().substring(0, 8)));
                    createTableBin(binStack);
                    createTableStudents(list);
                    binStack.saveToFile(MainSystem.bin_file_path);
                    list.saveToFile(MainSystem.student_file_path);
                }else{
                    infoBox("No Records to Restore","Bin");
                }
            }
        });
    }

    private void createTableBin(BinStack binStack) {
        BinTableModel binTableModel = new BinTableModel(binStack);
        table_bin.setModel(binTableModel);
    }

    private void loadTableValuesCentre() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table_Students.setDefaultRenderer(String.class, centerRenderer);
        table_Students.setDefaultRenderer(Integer.class, centerRenderer);
        table_delete.setDefaultRenderer(String.class, centerRenderer);
        table_delete.setDefaultRenderer(Integer.class, centerRenderer);
        table_searchResults.setDefaultRenderer(String.class, centerRenderer);
        table_searchResults.setDefaultRenderer(Integer.class, centerRenderer);
        table_edit.setDefaultRenderer(String.class, centerRenderer);
        table_edit.setDefaultRenderer(Integer.class, centerRenderer);
        table_log.setDefaultRenderer(String.class, centerRenderer);
        table_log.setDefaultRenderer(Integer.class, centerRenderer);
        table_bin.setDefaultRenderer(String.class, centerRenderer);
        table_bin.setDefaultRenderer(Integer.class, centerRenderer);
    }

    private void createTableSearch(LinkedList searchList) {
        StudentTableModel studentTableModelSearch = new StudentTableModel(searchList);
        table_searchResults.setModel(studentTableModelSearch);
        table_searchResults.setAutoCreateRowSorter(true);
    }

    private void resetDeleteFields() {
        textField_deleteRoll.setText("");
        textField_deleteFN.setText("");
        textField_deleteLN.setText("");
        textField_deleteMarks.setText("");
        textField_deleteGrade.setText("");
        selectedStudent = null;
        selectedIndex = -1;
    }

    private void resetEditFields() {
        textField_editRoll.setText("");
        textField_editFN.setText("");
        textField_editLN.setText("");
        textField_editMarks.setText("");
        textField_editGrade.setText("");
        selectedStudent = null;
        selectedIndex = -1;
    }

    private void resetAddFields() {
        textField_firstName.setText("");
        textField_lastName.setText("");
        textField_marks.setText("");
        textField_grade.setText("");
    }

    private void createTableStudents(LinkedList list) {
        StudentTableModel studentTableModel = new StudentTableModel(list);
        table_Students.setModel(studentTableModel);
        table_Students.setAutoCreateRowSorter(true);
        table_edit.setModel(studentTableModel);
        table_edit.setAutoCreateRowSorter(true);
        table_delete.setModel(studentTableModel);
        table_delete.setAutoCreateRowSorter(true);
    }

    private void createTableLogs(LogLinkedList logList) {
        LogTableModel logTableModel = new LogTableModel(logList);
        table_log.setModel(logTableModel);
    }

    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainUI");
        frame.setContentPane(new MainUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600,400);
        frame.setVisible(true);
    }

    private static class StudentTableModel extends AbstractTableModel {

        private final String[] COLUMNS = {"Roll", "First Name", "Last Name", "Marks", "Grade"};
        private LinkedList list;

        public StudentTableModel(LinkedList list) {
            this.list = list;
        }

        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            Object obj = null;
            switch(columnIndex){
                case 0 :
                    obj = list.get(rowIndex).getRoll();
                    break;
                case 1:
                    obj = list.get(rowIndex).getFirstName();
                    break;
                case 2:
                    obj = list.get(rowIndex).getLastName();
                    break;
                case 3:
                    obj = list.get(rowIndex).getMarks();
                    break;
                case 4:
                    obj = list.get(rowIndex).getGrade();
                    break;
                default:
                    obj = "-";
            }
            return obj;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if(getValueAt(0, columnIndex) != null){
                return getValueAt(0, columnIndex).getClass();
            }else{
                return Object.class;
            }
        }
    }

    private static class LogTableModel extends AbstractTableModel {

        private final String[] COLUMNS = {"Operation Performed", "Roll", "Date & Time"};
        private LogLinkedList logList;

        public LogTableModel(LogLinkedList logList) {
            this.logList = logList;
        }

        @Override
        public int getRowCount() {
            return logList.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            Object obj = null;
            switch(columnIndex){
                case 0 :
                    obj = logList.get(rowIndex).getOperationName();
                    break;
                case 1:
                    obj = logList.get(rowIndex).getRoll();
                    break;
                case 2:
                    obj = logList.get(rowIndex).getTime();
                    break;
                default:
                    obj = "-";
            }
            return obj;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if(getValueAt(0, columnIndex) != null){
                return getValueAt(0, columnIndex).getClass();
            }else{
                return Object.class;
            }
        }
    }

    private static class BinTableModel extends AbstractTableModel {

        private final String[] COLUMNS = {"Roll", "First Name", "Last Name", "Marks", "Grade"};
        private BinStack binStack;

        public BinTableModel(BinStack binStack) {
            this.binStack = binStack;
        }

        @Override
        public int getRowCount() {
            return binStack.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            Object obj;
            switch(columnIndex){
                case 0 :
                    obj = binStack.get(rowIndex).getRoll();
                    break;
                case 1:
                    obj = binStack.get(rowIndex).getFirstName();
                    break;
                case 2:
                    obj = binStack.get(rowIndex).getLastName();
                    break;
                case 3 :
                    obj = binStack.get(rowIndex).getMarks();
                    break;
                case 4:
                    obj = binStack.get(rowIndex).getGrade();
                    break;
                default:
                    obj = "-";
            }
            return obj;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if(getValueAt(0, columnIndex) != null){
                return getValueAt(0, columnIndex).getClass();
            }else{
                return Object.class;
            }
        }
    }
}
