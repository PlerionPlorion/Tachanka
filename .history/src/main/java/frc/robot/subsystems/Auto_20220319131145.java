package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Timer;

public class Auto {
// auto
Timer timer = new Timer();
Drive autoDrive = new Drive();
public void autonomousInit() {
        timer.reset();
        
    }



public void autonomousPeriodic() {
  timer.start();

    autoDrive.leftMotors = -0.5;
    autoDrive.rightMotors = 0.5;
    System.out.println(timer.get());
  if (timer.get() > 1.2) {
    autoDrive.rightMotors = 0;
    autoDrive.leftMotors = 0;
    timer.stop();
    //timer.reset();
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