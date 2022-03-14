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
    Timer debounce = new Timer();
    Timer turret = new Timer();
    TalonFX turretSpin = new TalonFX(0);
    TalonFX turretFire1 = new TalonFX(20);
    TalonFX turretFire2 = new TalonFX(19);
    Orchestra Rick = new Orchestra();
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    public void shooterInit() {
        control = new Joystick(1);
        // Rick.loadMusic("Rocky.chrp");
        Rick.addInstrument(turretSpin);
        turret.reset();
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
        if (counter % 2 == 0) {
            table.getEntry("ledMode").setNumber(limeOn);
            visionX = table.getEntry("tx").getDouble(0);
            sensorPos = -turretSpin.getSelectedSensorPosition() * (360.0 / 4096.0) / 10.66;
            if (table.getEntry("tv").getDouble(0) > 0) {
                targetPos = sensorPos + visionX;
                if (targetPos > 170) {
                    targetPos = 170;
                }
                if (targetPos < -170) {
                    targetPos = -170;
                }
                System.out.println(targetPos);
                targetPos *= 10.66 * (4096.0 / 360.0);
                turretSpin.set(ControlMode.Position, targetPos);

            }

            visionY = table.getEntry("ty").getDouble(0);
            if (table.getEntry("ty").getDouble(0) > 4 && table.getEntry("ty").getDouble(0) < 7) {
                turret.start();
                turret.reset();
                controllerShoot = 0.5;
                // System.out.println(targetPos);

            } else {
                if (turret.get() > 2) {
                    controllerShoot = 0;
                    turret.stop();
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
        turretFire1.set(ControlMode.PercentOutput, ((controllerShoot / 100) * 60));
        turretFire2.set(ControlMode.PercentOutput, ((controllerShoot / 100) * 50));
        // System.out.println(controllerShoot);
        SmartDashboard.putNumber("targetPos", targetPos);
        SmartDashboard.putNumber("sensorPos", sensorPos);
    }
}
// 6 top
// 5 bottom
// ratiocontroll