package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    private Joystick control;
    TalonFX turretSpin = new TalonFX(0);
    TalonFX turretFire1 = new TalonFX(20);
    TalonFX turretFire2 = new TalonFX(19);
    Orchestra Rick = new Orchestra();
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    public void shooterInit() {
        control = new Joystick(1);
        // Rick.loadMusic("Rocky.chrp");
        Rick.addInstrument(turretSpin);
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

    public void shooterPeriodic() {
        SmartDashboard.setDefaultNumber("turret", 0);

        if (control.getName().equals("Controller (XBOX 360 For Windows)")) {

            if (control.getRawButtonPressed(8)) {

                counter += 1;
            }
            if (counter % 2 == 0) {

            } else {
                controllerShoot = control.getRawAxis(1);
                controllerTurn = control.getRawAxis(0);
                if (Math.abs(controllerTurn) < 0.1) {
                    controllerTurn = 0;
                }
                turretSpin.set(ControlMode.PercentOutput, controllerTurn);
            }
            System.out.println(controllerTurn);

        } else if (control.getName().equals("Logitech Extreme 3D")) {

            if (control.getRawButtonPressed(2)) {

                counter += 1;
            }
            if (counter % 2 == 0) {

            } else {
                controllerShoot = (-control.getRawAxis(3) + 1) / 2;
                controllerTurn = control.getZ();
                if (Math.abs(controllerTurn) < 0.1) {
                    controllerTurn = 0;
                }
                turretSpin.set(ControlMode.PercentOutput, controllerTurn);
            }
            System.out.println(controllerTurn);
        } else {
            controllerShoot = 0;
            controllerTurn = 0;
            System.out.println("This controller is not supported");
        }
        if (counter % 2 == 0) {
            visionX = table.getEntry("tx").getDouble(0);
            sensorPos = -turretSpin.getSelectedSensorPosition() * (360.0 / 4096.0) / 10.66;
            if (table.getEntry("tv").getDouble(0) > 0) {
                targetPos = sensorPos + visionX;
                if (targetPos > 270) {
                    targetPos = 270;
                }
                if (targetPos < -270) {
                    targetPos = -270;
                }
                targetPos *= 10.66 * (4096.0 / 360.0);
            }
            // controllerTurn = SmartDashboard.getNumber("turret", 0);
            degrees = (turretSpin.getSelectedSensorPosition(1));
            if (degrees > 13500) {
                if (controllerTurn > 0) {

                    controllerTurn = 0;

                } else if (degrees < 0) {
                    if (controllerTurn < 0) {

                        controllerTurn = 0;

                    }
                    
                }
                if (counter % 2 == 0) {
                    visionY = table.getEntry("ty").getDouble(0);
                    if (table.getEntry("tv").getDouble(0) > 0) {
                        controllerShoot = visionY / 25;
                        turretSpin.set(ControlMode.Position, targetPos);
                    }
                }

            }

        }
        //turretFire1.set(ControlMode.PercentOutput, controllerShoot/5);
        turretFire2.set(ControlMode.PercentOutput, controllerShoot);
        System.out.println(controllerShoot);
        SmartDashboard.putNumber("targetPos", targetPos);
        SmartDashboard.putNumber("sensorPos", sensorPos);
    }
}
