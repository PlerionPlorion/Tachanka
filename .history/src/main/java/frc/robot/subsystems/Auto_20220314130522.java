package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {
    AHRS navex;
    Drive auto = new Drive();
    public void stop() {
        auto.leftMotors = 0;
        auto.rightMotors = 0;
    }
    public void autonomousInit(){
        Timer timer = new Timer();
        navex = new AHRS(SPI.Port.kMXP);
    }
    public void autonomousPeriodic(){
        SmartDashboard.putNumber("Compass", navex.getAngle());

    }
}
