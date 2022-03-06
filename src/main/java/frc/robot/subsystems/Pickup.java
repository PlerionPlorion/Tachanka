package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pickup {
    private CANSparkMax armNeo;
    private Joystick pickup;
    private SparkMaxPIDController pidArmcontroller;
    private RelativeEncoder encoder;
    private SparkMaxLimitSwitch forwardLimit;
    private SparkMaxLimitSwitch reverseLimit;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;
    VictorSPX elevBag = new VictorSPX(9);
    VictorSPX armBag = new VictorSPX(10);
    double setPoint = 0;
    double Speed = Drive.xboxSpeed;
    double controllerArm = -1;
    double controllerUp = 0;
    int counter = 0;

    public void pickupInit() {

        pickup = new Joystick(1);
        armNeo = new CANSparkMax(6, MotorType.kBrushless);

        forwardLimit = armNeo.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyClosed);
        reverseLimit = armNeo.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyClosed);
        forwardLimit.enableLimitSwitch(true);
        reverseLimit.enableLimitSwitch(true);
        pidArmcontroller = armNeo.getPIDController();

        kP = 6e-5;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0.000015;
        kMaxOutput = 1;
        kMinOutput = -1;
        maxRPM = 5700;
        encoder = armNeo.getEncoder();
        pidArmcontroller.setP(kP);
        pidArmcontroller.setI(kI);
        pidArmcontroller.setD(kD);
        pidArmcontroller.setIZone(kIz);
        pidArmcontroller.setFF(kFF);
        pidArmcontroller.setOutputRange(kMinOutput, kMaxOutput);
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);

    }

    public void pickupPeriodic() {

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
                controllerUp = Speed;
            } else if (pickup.getPOV() == 180) {
                controllerUp = -Speed;
            }

            if (pickup.getRawButtonPressed(1)) {
                controllerArm *= -1;

            }
        } else if (pickup.getName().equals("Logitech Extreme 3D")) {
            Speed = (-pickup.getRawAxis(3) + 1) / 2;
            if (pickup.getPOV() == 0) {
                controllerUp = Speed;
            } else if (pickup.getPOV() == 180) {
                controllerUp = -Speed;
            }
            if (pickup.getRawButtonPressed(1)) {
                controllerArm *= -1;

            }
        }
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);
        if ((p != kP)) {
            pidArmcontroller.setP(p);
            kP = p;
        }
        if ((i != kI)) {
            pidArmcontroller.setI(i);
            kI = i;
        }
        if ((d != kD)) {
            pidArmcontroller.setD(d);
            kD = d;
        }
        if ((iz != kIz)) {
            pidArmcontroller.setIZone(iz);
            kIz = iz;
        }
        if ((ff != kFF)) {
            pidArmcontroller.setFF(ff);
            kFF = ff;
        }
        if ((max != kMaxOutput) || (min != kMinOutput)) {
            pidArmcontroller.setOutputRange(min, max);
            kMinOutput = min;
            kMaxOutput = max;

        }

        pidArmcontroller.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
        elevBag.set(ControlMode.Velocity, controllerUp);
        armBag.set(ControlMode.Velocity, controllerUp);
        SmartDashboard.putNumber("SetPoint", setPoint);
        SmartDashboard.putNumber("ProcessVariable", encoder.getVelocity());
    }
}