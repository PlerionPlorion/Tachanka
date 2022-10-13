package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;

public class Auto {
// auto
Timer timer = new Timer();
Drive autoDrive = new Drive();
Shooter autoShooter = new Shooter();
Pickup autoPickup = new Pickup();
public void autonomousInit() {
        timer.reset();
    }



public void autonomousPeriodic() {
  timer.start();

    autoDrive.leftMotors = -0.5;
    autoDrive.rightMotors = 0.5;
    System.out.println(timer.get());
  if (timer.get() > 2.5) {
    autoDrive.rightMotors = 0;
    autoDrive.leftMotors = 0;
    timer.stop();
    //timer.reset();
  } if(timer.get() > 2) {
      autoShooter.shooterLime();

  }
   if(timer.get() > 2.5) {
    autoShooter.bottomShoot.set(ControlMode.PercentOutput, 0.4);
    autoShooter.topShoot.set(ControlMode.PercentOutput, 0.2);
  } if (timer.get() > 2.6) {
    autoPickup.elevBag.set(ControlMode.PercentOutput, 1);
  }


autoDrive.tank();

}














    // AHRS navex;
    // Drive auto = new Drive();
    // Shooter autoShoot = new Shooter();
    // Pickup autoPickup = new Pickup();
    // double inches = 20000;
    // Timer timer = new Timer();
    // public void stop() {
    //     auto.leftDrive.set(0);
    //     auto.rightDrive.set(0);
        
    // }
    // public void autonomousInit(){
    //     timer.reset();
    //     navex = new AHRS(edu.wpi.first.wpilibj.SerialPort.Port.kUSB);
    //     navex.reset();
    //     auto.rightDrive.setInverted(true);
    // }
    // public void autonomousPeriodic(){
    //    

    // }
}
//5ft 8in