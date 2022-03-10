package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;

public class Drive {
    private DifferentialDrive tachanka;
    private Joystick joystick;

    // MotorController frontLeft = new Talon(3);
    // MotorController rearLeft = new Talon(1);

    // MotorControllerGroup leftDrive = new MotorControllerGroup(frontLeft,
    // rearLeft);

    // MotorController frontRight = new Talon(0);
    // MotorController rearRight = new Talon(2);

    // MotorControllerGroup rightDrive = new MotorControllerGroup(frontRight,
    // rearRight);

    WPI_TalonFX leftFront = new WPI_TalonFX(3);
    WPI_TalonFX leftBack = new WPI_TalonFX(2);
    WPI_TalonFX rightFront = new WPI_TalonFX(5);
    WPI_TalonFX rightBack = new WPI_TalonFX(4);
    MotorControllerGroup leftDrive = new MotorControllerGroup(leftFront, leftBack);
    MotorControllerGroup rightDrive = new MotorControllerGroup(rightFront, rightBack);
    DifferentialDrive m_drive;

    public Drive() {
        tachanka = new DifferentialDrive(leftDrive, rightDrive);
    }

    public void driveInit() {

        leftDrive.setInverted(true);
        joystick = new Joystick(0);

    }

    // Define
    double leftMotors = 0;
    double rightMotors = 0;
    static int xboxSpeed = 100;
    double controllerInvert = 0;
    double multiplier = 0;
    Timer timer = new Timer();
    double controllerTurn = 0;
    double controllerMove = 0;

    public void tank() {
       
        tachanka.tankDrive(leftMotors, rightMotors);
        // System.out.println(leftMotors + " " + rightMotors);
    }
    public void drivePeriodic() {

        if (joystick.getName().equals("Controller (XBOX 360 For Windows)")) {
            controllerMove = joystick.getRawAxis(1);
            controllerTurn = joystick.getRawAxis(4);
            if (joystick.getRawButtonPressed(6)) {
                xboxSpeed = xboxSpeed + 10;
            }
            if (joystick.getRawButtonPressed(5)) {
                xboxSpeed = xboxSpeed - 10;
            }
            if (xboxSpeed > 100) {
                xboxSpeed = 100;
            }
            if (xboxSpeed < 10) {
                xboxSpeed = 10;
            }

            controllerMove = controllerMove * xboxSpeed / 100.0;
            controllerTurn = controllerTurn * xboxSpeed / 100.0;
            // Dashboard
            SmartDashboard.putNumber("Speed", xboxSpeed);
        } else if (joystick.getName().equals("Logitech Extreme 3D")) {
            controllerMove = joystick.getY();
            controllerTurn = joystick.getZ();
            if(joystick.getRawButtonPressed(11)) {
            multiplier = (-joystick.getRawAxis(3) + 1) / 2;
            }
            controllerMove = controllerMove * multiplier;
            controllerTurn = controllerTurn * multiplier;
            SmartDashboard.putNumber("Speed", multiplier * 100);
        } else {
            System.out.println("This controller is not supported");
        }
        // Dashboard
        controllerInvert = controllerMove * -1;
        SmartDashboard.putNumber("Y", controllerInvert);
        SmartDashboard.putNumber("X", controllerTurn);
        // Removing stick drift
        if (Math.abs(controllerMove) < 0.1) {
            controllerMove = 0;
        }

        if (Math.abs(controllerTurn) < 0.1) {
            controllerTurn = 0;
        }
        // setting motors
        leftMotors = controllerMove - controllerTurn;
        rightMotors = controllerMove + controllerTurn;
        // leftMotors = leftMotors * Math.abs(joystick.getRawAxis(3));

        tank();
    }



    // auto
    public void rightTurn() {
        leftMotors = -0.745;
        rightMotors = 0.745;
    }

    public void leftTurn() {
        leftMotors = 0.745;
        rightMotors = -0.745;
    }

    public void stop() {
        leftMotors = 0;
        rightMotors = 0;
    }
}
