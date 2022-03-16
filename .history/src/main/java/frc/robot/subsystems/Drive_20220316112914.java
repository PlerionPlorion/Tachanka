package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.music.Orchestra;
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
    Orchestra Rick = new Orchestra();

    public Drive() {
        tachanka = new DifferentialDrive(leftDrive, rightDrive);
    }

    public void driveInit() {
        timer.start();
        leftDrive.setInverted(true);
        joystick = new Joystick(0);
        lastTime = 0;
        Rick.addInstrument(leftFront);
        Rick.addInstrument(leftBack);
        Rick.addInstrument(rightFront);
        Rick.addInstrument(rightBack);
    
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
    double constant = 0.05;
    double currentSpeed = 0;
    double dt = 0;
    double lastTime = 0;
    double Time = 0;
    public void tank() {

        tachanka.tankDrive(leftMotors, rightMotors);
        // System.out.println(leftMotors + " " + rightMotors);
    }
    public double interpolate(double a, double b, double y) {
        return (1-y)*a+y*b;
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
       Time = timer.get();
       dt = Time - lastTime;
       lastTime = Time;
        // setting motors
        currentSpeed = interpolate(controllerMove, currentSpeed, Math.pow(constant, dt));
        leftMotors = currentSpeed - (controllerTurn/2);
        rightMotors = currentSpeed + (controllerTurn/2);
        // leftMotors = leftMotors * Math.abs(joystick.getRawAxis(3));

        tank();
    }
    public void song() {
        Rick.loadMusic("Rocky.chrp");
        Rick.play();
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
    public void straight(){
        leftMotors = -0.5;
        rightMotors = -0.5;
    }
    public void stop() {
        leftMotors = 0;
        rightMotors = 0;
    }
}
