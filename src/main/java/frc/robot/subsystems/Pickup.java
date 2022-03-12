package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class Pickup {

    private Joystick pickup;
    Timer timer = new Timer();
    Timer debounce = new Timer();
    DigitalInput limSwitch = new DigitalInput(0);
    VictorSPX elevBag = new VictorSPX(9);
    VictorSPX armBag = new VictorSPX(10);
    VictorSPX pickBag = new VictorSPX(13);
    double Speed = 0;
    double controllerArm = 0;
    double controllerElevUp = 0;
    double controllerArmBag = 0;
    int counter = 1;

    public void pickupInit() {

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
                controllerArmBag = 0.4;
            }
            if (pickup.getPOV() == 180) {
                controllerElevUp = -1;
                controllerArmBag = -0.4;
            }
            if (pickup.getPOV() != 0 && pickup.getPOV() != 180) {
                controllerElevUp = 0;
                controllerArmBag = 0;
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
                    controllerArm = -0.15;

                
                } else {
                    controllerArm = 0.3;

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
        if (limSwitch.get() == false) {
            controllerArm = 0;
    }
    }
        System.out.println(counter);
        pickBag.set(ControlMode.PercentOutput, controllerArm);
        elevBag.set(ControlMode.PercentOutput, controllerElevUp);
        armBag.set(ControlMode.PercentOutput, controllerArmBag);

    }
    }
}