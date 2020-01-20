package netty.heatbeat;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by admin on 2019/11/1.
 */
public class HeatBeatMessage implements Serializable {
    private static final long serialVersionUID = 3986194663147989511L;
    private String ip;
    private Map<String,Object> cpuMsgMap;
    private Map<String,Object> memMsgMap;//内存信息
    private Map<String,Object> fileSysMsgMap;//磁盘信息

    @Override
    public String toString() {
        return "HeatBeatMessage{" +
                "ip='" + ip + '\'' +
                ", cpuMsgMap=" + cpuMsgMap +
                ", memMsgMap=" + memMsgMap +
                ", fileSysMsgMap=" + fileSysMsgMap +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Map<String, Object> getCpuMsgMap() {
        return cpuMsgMap;
    }

    public void setCpuMsgMap(Map<String, Object> cpuMsgMap) {
        this.cpuMsgMap = cpuMsgMap;
    }

    public Map<String, Object> getMemMsgMap() {
        return memMsgMap;
    }

    public void setMemMsgMap(Map<String, Object> memMsgMap) {
        this.memMsgMap = memMsgMap;
    }

    public Map<String, Object> getFileSysMsgMap() {
        return fileSysMsgMap;
    }

    public void setFileSysMsgMap(Map<String, Object> fileSysMsgMap) {
        this.fileSysMsgMap = fileSysMsgMap;
    }
}
