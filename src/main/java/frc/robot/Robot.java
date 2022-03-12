// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {

  Timer timer = new Timer();
  Drive tachanka = new Drive();
  Shooter shooter = new Shooter();
  Pivision vision = new Pivision();
  LimelightVision limeVision = new LimelightVision();
  Pickup pickup = new Pickup();
  Climb climb = new Climb();
  @Override
  public void robotInit() {
    vision.init();

  }

  @Override
  public void robotPeriodic() {
    vision.printVisionMessage();
  }

  @Override
  public void teleopInit() {
    tachanka.driveInit();
    climb.climbInit();
    shooter.shooterInit();
    pickup.pickupInit();
  }

  @Override
  public void teleopPeriodic() {
    tachanka.drivePeriodic();
    shooter.shooterPeriodic();
    limeVision.periodic();
    pickup.pickupPeriodic();
    //frc.robot.subsystems.navex.navexPeriodic();
   climb.climbPeriodic();
  }

  // auto
  @Override
  public void autonomousInit() {
    tachanka.driveInit();
    timer.reset();

    timer.start();
  }

  @Override
  public void autonomousPeriodic() {

    if (timer.get() < 0.5) {

      tachanka.rightTurn();
    }
    if (timer.get() > 0.5) {
      tachanka.leftTurn();
    }
    if (timer.get() > 1) {
      tachanka.stop();

    }
    tachanka.tank();
  }

}