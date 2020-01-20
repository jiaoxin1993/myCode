package netty.heatbeat;

import io.netty.channel.ChannelHandlerContext;
import org.hyperic.sigar.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/11/1.
 */
public class HeatbeatTask implements Runnable{
    private ChannelHandlerContext ctx;

    public HeatbeatTask(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }
    public static void main(String[] args) throws SigarException, UnknownHostException {
        //who();
        String computerName = System.getenv().get("COMPUTERNAME");
        String credentials = InetAddress.getLocalHost().getHostAddress()+"_"+computerName;
        System.out.println(credentials);
        System.out.println();

    }
    public void run() {
        try {
            HeatBeatMessage msg = new HeatBeatMessage();
            msg.setIp(InetAddress.getLocalHost().getHostAddress());
            Sigar sigar = new Sigar();
            //CPU信息
            CpuPerc cpuPerc = sigar.getCpuPerc();
            Map<String,Object> cpuMsgMap = new HashMap<String, Object>();
            cpuMsgMap.put("Combined",cpuPerc.getCombined());
            cpuMsgMap.put("User",cpuPerc.getUser());
            cpuMsgMap.put("sys",cpuPerc.getSys());
            cpuMsgMap.put("wait",cpuPerc.getWait());
            cpuMsgMap.put("Idle",cpuPerc.getIdle());

            //内存信息
            Map<String,Object> memMsgMap = new HashMap<String, Object>();
            Mem mem = sigar.getMem();
            memMsgMap.put("Total",mem.getTotal());
            memMsgMap.put("Used",mem.getUsed());
            memMsgMap.put("Free",mem.getFree());

            //文件系统
            Map<String,Object> fileSysMsgMap = new HashMap<String, Object>();
            FileSystem[] list= sigar.getFileSystemList();
            fileSysMsgMap.put("FileSysCount",list.length);
            List<String> msgList = null;
            for (FileSystem fs:list) {
                msgList = new ArrayList<String>();
                msgList.add(fs.getDevName()+"总大小：  "+sigar.getFileSystemUsage(fs.getDirName()));
                msgList.add(fs.getDevName()+"剩余大小：  "+sigar.getFileSystemUsage(fs.getDirName()));
                fileSysMsgMap.put(fs.getDevName(),msgList);
            }
            msg.setCpuMsgMap(cpuMsgMap);
            msg.setMemMsgMap(memMsgMap);
            msg.setFileSysMsgMap(fileSysMsgMap);
            ctx.writeAndFlush(msg);
        } catch (Exception e) {
        	// TODO: handle exception
        }
    }
}
