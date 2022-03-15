package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    private Joystick control;
    final int limeOff = 1;
    final int limeOn = 3;
    final double limeAngle = 0.401425728;
    final double limeRadius = 8;
    final double limeHeight = 8;
    final double targetHeight = 12;
    Timer debounce = new Timer();
    Timer turretY = new Timer();
    Timer turretX = new Timer();
    TalonFX turretSpin = new TalonFX(0);
    TalonFX bottomShoot = new TalonFX(20);
    TalonFX topShoot = new TalonFX(19);
    Orchestra Rick = new Orchestra();
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    public void shooterInit() {
        control = new Joystick(1);
        // Rick.loadMusic("Rocky.chrp");
        Rick.addInstrument(turretSpin);
        turretY.reset();
    }

    // Define
    double controllerTurn = 0;
    double controllerShoot = 0;
    double limelightTurn = 0;
    int counter = 1;
    double visionX = 0;
    double visionY = 0;
    double targetPos = 0;
    double sensorPos = 0;
    double degrees = 0;
    public void limeDisable() {
        table.getEntry("ledMode").setNumber(limeOff);
    }
    public Vector getTargetPosition () {
        double tx = table.getEntry("tx").getDouble(0);
        double ty = table.getEntry("ty").getDouble(0);
        double m = (targetHeight - limeHeight) / Math.tan(limeAngle+ty);
        Vector relTargetPosition = new Vector(tx, m, true);
        double limelightAngle = -turretSpin.getSelectedSensorPosition() / 777/ Math.PI;
        Vector limelightPosition = new Vector();
    }
    public void shooterPeriodic() {
        degrees = (turretSpin.getSelectedSensorPosition(1));
        if (degrees > 70000) {
            if (controllerTurn > 0) {

                controllerTurn = 0;
            }
        } else if (degrees < -65000) {
            if (controllerTurn < 0) {

                controllerTurn = 0;

            }
        }
        SmartDashboard.setDefaultNumber("turret", 0);

        if (control.getName().equals("Controller (XBOX 360 For Windows)")) {

            if (control.getRawButtonPressed(8)) {

                counter += 1;
            }
            if (counter % 2 == 0) {

            } else {

                controllerShoot = control.getRawAxis(1);
                controllerTurn = control.getRawAxis(0);
                if (controllerTurn < 0.6) {
                    controllerTurn = 0;
                }
                turretSpin.set(ControlMode.PercentOutput, controllerTurn);
            }
            // System.out.println(controllerTurn);

        } else if (control.getName().equals("Logitech Extreme 3D")) {

            if (control.getRawButtonPressed(2)) {

                counter += 1;

            }
            if (counter % 2 == 0) {

            } else {
                table.getEntry("ledMode").setNumber(limeOff);
                if (control.getRawButton(12)) {
                    controllerShoot = 0.5;
                } else {
                    controllerShoot = 0.0;
                }
                controllerTurn = control.getZ();
                if (Math.abs(controllerTurn) < 0.5) {
                    controllerTurn = 0;
                }
                turretSpin.set(ControlMode.PercentOutput, controllerTurn);
            }
            // System.out.println(controllerTurn);
        } else {
            controllerShoot = 0;
            controllerTurn = 0;
            System.out.println("This controller is not supported");
        }
        sensorPos = -turretSpin.getSelectedSensorPosition() / 777;
        System.out.println(sensorPos*777);
        if (counter % 2 == 0) {
            table.getEntry("ledMode").setNumber(limeOn);
            visionX = table.getEntry("tx").getDouble(0);
            if (table.getEntry("tv").getDouble(0) > 0) {
                turretX.start();
                turretX.reset();
                if (Math.abs(visionX) < 5) {
                    visionX = 0;
                }
                targetPos = sensorPos + visionX;
                targetPos = targetPos * 777;
                targetPos = 70000;
            } else {
                if(turretX.get() > 2) {
                    targetPos = 0;
                }
            }
            turretSpin.set(ControlMode.Position, targetPos);

            visionY = table.getEntry("ty").getDouble(0);
            if (table.getEntry("ty").getDouble(0) > 10 && table.getEntry("ty").getDouble(0) < 12) {
                turretY.start();
                turretY.reset();
                controllerShoot = 0.5;
                // System.out.println(targetPos);

            } else {
                if (turretY.get() > 2) {
                    controllerShoot = 0;
                    turretY.stop();
                }
            }

        }
        if (degrees > 70000) {
            if (targetPos > 90) {

                controllerTurn = targetPos = 90;

            } else if (degrees < -65000) {
                if (targetPos < -90) {

                    controllerTurn = -90;

                }
            }
        }

        // System.out.println(counter);
        bottomShoot.set(ControlMode.PercentOutput, ((controllerShoot / 100) * 60));
        topShoot.set(ControlMode.PercentOutput, ((controllerShoot / 100) * 40));
        // System.out.println(controllerShoot);
        SmartDashboard.putNumber("targetPos", targetPos);
        SmartDashboard.putNumber("sensorPos", sensorPos);
    }
}
// 6 top
// 5 bottom
// ratiocontrol
// ty = 11 for good shooting
