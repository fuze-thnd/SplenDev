package shared;

import java.io.Serializable;

public class NetworkMessage implements Serializable {
    private String command;
    private Object data;

    // Constructor แบบมีข้อมูลแนบ
    public NetworkMessage(String command, Object data) {
        this.command = command;
        this.data = data;
    }

    // Constructor แบบไม่มีข้อมูลแนบ
    public NetworkMessage(String command) {
        this.command = command;
        this.data = null;
    }

    public String getCommand() {
        return command;
    }
    public Object getData() {
        return data;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
