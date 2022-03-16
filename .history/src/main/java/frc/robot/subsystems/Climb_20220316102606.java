package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climb {
    TalonFX climbMotor = new TalonFX(1);
    PWM servo = new PWM(9);
    private Joystick climb;
    double controllerClimb = 0;
    double degrees = 0;
    double inches = 37;
    
    public void climbInit() {
        climb = new Joystick(1);
    }



    public void climbPeriodic() {

        if (climb.getName().equals("Controller (XBOX 360 For Windows)")) {
            controllerClimb = climb.getRawAxis(5);
        } else if (climb.getName().equals("Logitech Extreme 3D")) {
            controllerClimb = climb.getRawAxis(1);
            controllerClimb *= -1;

        } else {
            System.out.println("This controller is not supported");
        }
        if(climb.getRawButtonPressed(6)){
            
            climbMotor.set(ControlMode.PercentOutput, controllerClimb);
        } else {
            controllerClimb = 0;
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