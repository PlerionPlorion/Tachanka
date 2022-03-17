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
    final double limeAngle = 0.401425728; // radians
    final double limeRadius = 8; // inches
    final double limeHeight = 8; // inches
    final double targetHeight = 104; // tester stick hight = 58; // inches
    Timer debounce = new Timer();
    Timer turretY = new Timer();
    Timer turretX = new Timer();
    Timer timer = new Timer();
    TalonFX turretSpin = new TalonFX(0);
    TalonFX bottomShoot = new TalonFX(20);
    TalonFX topShoot = new TalonFX(19);
    Orchestra Rick = new Orchestra();
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    double dt = 0;
    double lastTime = 0;
    double Time = 0;
    double constant = 0.05;

    public void shooterInit() {
        timer.start();
        timer.reset();
        control = new Joystick(1);
        Rick.loadMusic("Rocky.chrp");
        Rick.addInstrument(turretSpin);
        Rick.addInstrument(bottomShoot);
        Rick.addInstrument(topShoot);
        turretY.reset();
        dt = 0;
        lastTime = 0;
        Time = 0;

    }

    public double interpolate(double a, double b, double y) {
        return (1 - y) * a + y * b;
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

    public Vector getTargetPosition() {
        double tx = Math.toRadians(table.getEntry("tx").getDouble(0)) * 1.1;
        double ty = Math.toRadians(table.getEntry("ty").getDouble(0));
        double turretAngle = -(turretSpin.getSelectedSensorPosition() / 777.777) * Math.PI / 180;
        double m = ((targetHeight - limeHeight) / Math.tan(limeAngle + ty) / 2);
        Vector targetRelLimelight = new Vector(tx, m, true);
        Vector targetRelTurret = targetRelLimelight.addVector(new Vector(limeRadius, 0));
        System.out.println(targetRelTurret.getAngle() - turretAngle);
        return new Vector(targetRelTurret.getAngle() - turretAngle, targetRelTurret.getMag(), true);
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
                // table.getEntry("ledMode").setNumber(limeOff);
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
        // sensorPos = -turretSpin.getSelectedSensorPosition() / 777;
        // System.out.println(sensorPos*777);
        table.getEntry("ledMode").setNumber(limeOn);
        if (counter % 2 == 0) {
            // visionX = table.getEntry("tx").getDouble(0);
            if (table.getEntry("tv").getDouble(0) > 0) {
                turretX.start();
                turretX.reset();
                // if (Math.abs(visionX) < 5) {
                // visionX = 0;
                // }
                // targetPos = sensorPos + visionX;
                // targetPos = targetPos * 777;
                // targetPos = 70000;
                Vector newTargetposition = getTargetPosition();
                // System.out.println(targetPosition);
                // targetPos = (newTargetposition.getAngleDeg()) * 777.777;
                System.out.println(newTargetposition.getAngleDeg() * 777.777);
                targetPos = interpolate(newTargetposition.getAngleDeg() * 777.777, targetPos, Math.pow(constant, dt));
            } else {
                if (turretX.get() > 2) {
                    // targetPos = 0;
                }
            }
            Time = timer.get();
            dt = Time - lastTime;
            lastTime = Time;
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
        bottomShoot.set(ControlMode.PercentOutput, ((controllerShoot / 100) * 70));
        topShoot.set(ControlMode.PercentOutput, ((controllerShoot / 100) * 40));
        // System.out.println(controllerShoot);
        SmartDashboard.putNumber("targetPos", targetPos);
        SmartDashboard.putNumber("sensorPos", sensorPos);
    }

    public void song() {
        Rick.play();
    }
}
// 6 top
// 5 bottom
// ratiocontrol
// ty = 11 for good shooting
