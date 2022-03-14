package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climb {
    TalonFX climbMotor = new TalonFX(1);
    Timer timer = new Timer();
    private Joystick climb;
    double controllerClimb = 0;
    double degrees = 0;
    double inches = 37;
    double autoClimb = 0;
    boolean climbB = false;
    public void climbInit() {
        climb = new Joystick(1);
        timer.reset();
    }

    public void climbUp() {
        autoClimb = 0.3;
    }

    public void climbDown() {
        autoClimb = -0.3;
    }

    public void climbPeriodic() {

        if (climb.getName().equals("Controller (XBOX 360 For Windows)")) {
            controllerClimb = climb.getRawAxis(5);
        } else if (climb.getName().equals("Logitech Extreme 3D")) {
            controllerClimb = climb.getRawAxis(1);
            controllerClimb *= -1;
            if (climb.getRawButtonPressed(3)) {
                climbB = true;


            }
            if(climb.getRawButtonReleased(3)) {
                climbB = false;
                timer.stop();
             
            }
        } else {
            System.out.println("This controller is not supported");
        }
        if(climbB == true) {
            timer.start();
            if (timer.get() < 4) {
                autoClimb = 0.3;
                
            } 
            if (timer.get() > 4) {
                autoClimb = -0.3;
            } if (timer.get() > 8) {
                autoClimb = 0.3;
            } if (timer.get() > 12) {
                autoClimb = -0.3;
            } if (timer.get() > 16) {
                autoClimb = 0.3;
            } if (timer.get() > 20) {
                autoClimb = -0.3;
            }
            climbMotor.set(ControlMode.PercentOutput, autoClimb);
        } else {
            climbMotor.set(ControlMode.PercentOutput, controllerClimb);
        }
        //System.out.println(timer.get());
        degrees = (climbMotor.getSelectedSensorPosition(1));
        if (degrees > 70000) {
            if (controllerClimb > 0) {

                controllerClimb = 0;

            } else if (degrees < -65000) {
                if (controllerClimb < 0) {

                    controllerClimb = 0;

                }
            }

        }

        inches = ((climbMotor.getSelectedSensorPosition(1) / 5925) + 37);
        SmartDashboard.putNumber("Climber Height", inches);

       
    }
}