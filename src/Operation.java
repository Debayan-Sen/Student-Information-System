public class Operation {
    String operationName;
    int roll;
    String time;

    public Operation(String operationName, int roll, String time) {
        this.operationName = operationName;
        this.roll = roll;
        this.time = time;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
