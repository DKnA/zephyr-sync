package lv.ctco.zephyr.beans.zapi;

public class ExecutionRequest {

    private int status;

    private String executedOn;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExecutedOn() {
        return executedOn;
    }

    public void setExecutedOn(String executedOn) {
        this.executedOn = executedOn;
    }
}