package frc.robot.subsystems;



public class Auto {
    Drive auto = new Drive();
    public void stop() {
        auto.leftMotors = 0;
        auto.rightMotors = 0;
    }
    
}
