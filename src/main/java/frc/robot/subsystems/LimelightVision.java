package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.*;

public class LimelightVision {
    NetworkTableEntry tvEnt;
    NetworkTableEntry txEnt;
    NetworkTableEntry tyEnt;
    NetworkTableEntry taEnt;
    final double CameraAngle = 0; // radians (Set to Limelight camera angle)
    final double CameraHeight = 9.9375; // inches (Set to Limelight camera height)
    final Vector CameraPosition = new Vector(15.75, 0); // inches (Set to Limelight camera position)
    final double TargetHeight = 104; // inches (Set to height of center of target)
    final double LaunchAngle = 55 * Math.PI / 180; // radians (Set to angle of the launcher)
    final double LaunchHeight = 15; // inches (Set to height of the center of the launcher)
    final Vector LaunchPosition = new Vector(-1.5, 9); // inches (Set to position of the center of the launcher)
    public static double tx = 0;
    public static Vector TargetPosition;
    public static Boolean TargetDetected;
    public static Boolean enableCam;
    CameraServer cameraServer;
    
    public LimelightVision() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        tvEnt = table.getEntry("tv");
        txEnt = table.getEntry("tx");
        tyEnt = table.getEntry("ty");
        taEnt = table.getEntry("ta");
        TargetPosition = new Vector(0, 0);
        TargetDetected = false;
        enableCam = true;
    }

    public void periodic() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        tvEnt = table.getEntry("tv");
        txEnt = table.getEntry("tx");
        tyEnt = table.getEntry("ty");
        taEnt = table.getEntry("ta");
        double ta = taEnt.getDouble(0);
        tx = txEnt.getDouble(0) * Math.PI / 180;
        double ty = tyEnt.getDouble(0) * Math.PI / 180;
        TargetDetected = ta > 0;
        if (TargetDetected) {
            TargetPosition.setX((TargetHeight - CameraHeight) / Math.tan(CameraAngle + ty));
            TargetPosition.setY(TargetPosition.getX() * Math.tan(-tx));
        }
    }

}
