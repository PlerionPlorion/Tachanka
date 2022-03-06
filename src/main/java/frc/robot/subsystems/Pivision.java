package frc.robot.subsystems;

import edu.wpi.first.networktables.*;

public class Pivision {
    NetworkTableInstance table;
    NetworkTable visionTable;
    NetworkTableEntry visionMessage;

    public void init() {
        table = NetworkTableInstance.getDefault();
        table.startClient("10.28.39.80");
        visionTable = table.getTable("Vision");
        visionMessage = visionTable.getEntry("Message");
    }

    public void printVisionMessage() {
        ConnectionInfo[] conns = table.getConnections();
        for (int i = 0; i < conns.length; ++i) {
            //System.out.println(i + ": " + conns[i].remote_ip);
        }
       // System.out.println(visionMessage.getString("Error"));
    }
}
