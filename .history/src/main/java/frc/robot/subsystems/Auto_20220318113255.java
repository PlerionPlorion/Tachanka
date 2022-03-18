package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Auto {
    AHRS navex;
    Drive auto = new Drive();
    Shooter autoShoot = new Shooter();
    double inches = 20000;
    public void stop() {
        auto.leftDrive.set(0);
        auto.rightDrive.set(0);
        
    }
    public void autonomousInit(){
        Timer timer = new Timer();
        navex = new AHRS(edu.wpi.first.wpilibj.SerialPort.Port.kUSB);
        navex.reset();
        auto.rightDrive.setInverted(true);
    }
    public void autonomousPeriodic(){
        if(auto.leftFront.getSelectedSensorPosition(0) > (inches * -60) ) {
            auto.leftDrive.set(-0.5);
            auto.rightDrive.set(-0.5);
        } else {
            stop(); 
            autoShoot.bottomShoot.set(ControlMode.PercentOutput, 0.6);
            autoShoot.topShoot.set(ControlMode.PercentOutput, 0.4);
        } 
        SmartDashboard.putNumber("Compass", navex.getAngle());

    }
}
//5ft 8in