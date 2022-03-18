package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class Pickup {

    private Joystick pickup;
    Robot robot = new Robot();
    Timer timer = new Timer();
    Timer debounce = new Timer();
    VictorSPX elevBag = new VictorSPX(9);
    VictorSPX pickBag = new VictorSPX(13);
    WPI_VictorSPX bottomRight = new WPI_VictorSPX(10);
    WPI_VictorSPX bottomLeft = new WPI_VictorSPX(15);
    WPI_VictorSPX topRight = new WPI_VictorSPX(16);
    WPI_VictorSPX topLeft = new WPI_VictorSPX(17);
    MotorControllerGroup bottomBags = new MotorControllerGroup(bottomLeft, bottomRight);
    MotorControllerGroup topBags = new MotorControllerGroup(topLeft, topRight);
    double Speed = 0;
    double controllerArm = 0;
    double controllerElevUp = 0;
    double controllerBottom = 0;
    double controllerTop = 0;
    int counter = 1;
    int bagIntakecounter = 1;
    int bagDropcounter = 1;
    public void pickupInit() {
        counter = 1;
        pickup = new Joystick(1);

    }

    public void pickupPeriodic() {
        // System.out.println(limSwitch.get());

        if (pickup.getName().equals("Controller (XBOX 360 For Windows)")) {
            if (pickup.getRawButtonPressed(6)) {
                Speed = Speed + 10;
            }
            if (pickup.getRawButtonPressed(5)) {
                Speed = Speed - 10;
            }
            if (Speed > 100) {
                Speed = 100;
            }
            if (Speed < 10) {
                Speed = 10;
            }
            if (pickup.getPOV() == 0) {
                controllerElevUp = Speed;
            }
            if (pickup.getPOV() == 180) {
                controllerElevUp = -Speed;
            }

            if (pickup.getRawButtonPressed(1)) {
                controllerArm *= -1;

            }
        } else if (pickup.getName().equals("Logitech Extreme 3D")) {
            if (pickup.getPOV() == 0) {
                controllerElevUp = 1;
                
            }
            if (pickup.getPOV() == 180) {
                controllerElevUp = -1;
                
            }
            if (pickup.getPOV() != 0 && pickup.getPOV() != 180) {
                controllerElevUp = 0;
               
            }
            if (pickup.getRawButton(1)) {
                counter += 1;
                debounce.start();
                if(debounce.get() < 0.4){
                    counter -= 1;
                }else {
                    debounce.stop();
                    debounce.reset();
                }
                if(counter % 2 == 0) {


                    timer.start();
                    controllerArm = -0.4;

                
                } else {
                    controllerArm = 0.5;

                }
                if(timer.get() > 1) {
                    controllerArm = 0.25;
                }
                if (timer.get() > 2) {
                    controllerArm = 0;
                    timer.stop();
                    timer.reset();
                }
        }
        if(controllerArm < 0) {
        if (timer.get() > 1.5) {
            controllerArm = 0;
            timer.stop();
            timer.reset();
        }
    }
    if(controllerArm > 0) {
        if (robot.limSwitch.get() == false) {
            controllerArm = 0;
    }
    if(pickup.getRawButtonPressed(3)){
        bagIntakecounter += 1;

    }
    if(counter % 2 == 0) {
        bottomBags.set(0.5);
        topBags.set(-0.5);
    } else {
        bottomBags.set(0);
        topBags.set(0);
        }
        if(pickup.getRawButtonPressed(5)){
            bagDropcounter += 1;
    
        }
        if(counter % 2 == 0) {
            bottomBags.set(0.5);
            topBags.set(0.5);
        } else {
            bottomBags.set(0);
            topBags.set(0);
            }
    }
    
        //System.out.println(controllerArm);
        pickBag.set(ControlMode.PercentOutput, controllerArm);
        elevBag.set(ControlMode.PercentOutput, controllerElevUp);
        

    }
    }
}
