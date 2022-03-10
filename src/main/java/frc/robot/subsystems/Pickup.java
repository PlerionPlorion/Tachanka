package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class Pickup {

    private Joystick pickup;
   DigitalInput limSwitch = new DigitalInput(0);
    VictorSPX elevBag = new VictorSPX(9);
    VictorSPX armBag = new VictorSPX(10);
    VictorSPX pickBag = new VictorSPX(11);
    double Speed = 0;
    double controllerArm = -0.5;
    double controllerElevUp = 0;
    double controllerArmBag = 0;
    int counter = 0;
    
    public void pickupInit() {
        
        pickup = new Joystick(1);

    }

    public void pickupPeriodic() {
      //  System.out.println(limSwitch.get());
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
            } if (pickup.getPOV() == 180) {
                controllerElevUp = -Speed;
            }

            if (pickup.getRawButtonPressed(1)) {
                controllerArm *= -1;

            }
        } else if (pickup.getName().equals("Logitech Extreme 3D")) {
            if (pickup.getPOV() == 0) {
                controllerElevUp = 1;
                controllerArmBag = 0.4;
            } if (pickup.getPOV() == 180) {
                controllerElevUp = -1;
                controllerArmBag = -0.4;
            }
            if(pickup.getPOV() != 0 && pickup.getPOV() != 180) {
                controllerElevUp = 0;
                controllerArmBag = 0;
            }
            if (pickup.getRawButtonPressed(1)) {
                controllerArm *= -1;

            }
        }
        // if(limSwitch.get() == true) {
        //     controllerElevUp = Math.min(controllerElevUp, 0);
        // }
        // if(limSwitch.get() == false) {
        //     controllerElevUp = Math.max(controllerElevUp, -1);
        // }
       // pickBag.set(ControlMode.PercentOutput, controllerArm);
    elevBag.set(ControlMode.PercentOutput, controllerElevUp);
   armBag.set(ControlMode.PercentOutput, controllerArmBag);
            

    }
}