package frc.robot.subsystems;

import com.kauailabs.navx.frc.*;
import com.kauailabs.navx.frc.AHRS.BoardYawAxis;

import edu.wpi.first.wpilibj.SPI;
// import edu.wpi.first.wpilibj.I2C;
// import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

public class navex {
    static AHRS navex;
    {
        try {

            navex = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }

    }
   public static void navexPeriodic() {
    SmartDashboard.putBoolean(  "IMU_Connected",        navex.isConnected());
    SmartDashboard.putBoolean(  "IMU_IsCalibrating",    navex.isCalibrating());
    SmartDashboard.putNumber(   "IMU_Yaw",              navex.getYaw());
    SmartDashboard.putNumber(   "IMU_Pitch",            navex.getPitch());
    SmartDashboard.putNumber(   "IMU_Roll",             navex.getRoll());
    
    /* Display tilt-corrected, Magnetometer-based heading (requires             */
    /* magnetometer calibration to be useful)                                   */
    
    SmartDashboard.putNumber(   "IMU_CompassHeading",   navex.getCompassHeading());
    
    /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
    SmartDashboard.putNumber(   "IMU_FusedHeading",     navex.getFusedHeading());

    /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
    /* path for upgrading from the Kit-of-Parts gyro to the navx MXP            */
    
    SmartDashboard.putNumber(   "IMU_TotalYaw",         navex.getAngle());
    SmartDashboard.putNumber(   "IMU_YawRateDPS",       navex.getRate());

    /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
    
    SmartDashboard.putNumber(   "IMU_Accel_X",          navex.getWorldLinearAccelX());
    SmartDashboard.putNumber(   "IMU_Accel_Y",          navex.getWorldLinearAccelY());
    SmartDashboard.putBoolean(  "IMU_IsMoving",         navex.isMoving());
    SmartDashboard.putBoolean(  "IMU_IsRotating",       navex.isRotating());

    /* Display estimates of velocity/displacement.  Note that these values are  */
    /* not expected to be accurate enough for estimating robot position on a    */
    /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
    /* of these errors due to single (velocity) integration and especially      */
    /* double (displacement) integration.                                       */
    
    SmartDashboard.putNumber(   "Velocity_X",           navex.getVelocityX());
    SmartDashboard.putNumber(   "Velocity_Y",           navex.getVelocityY());
    SmartDashboard.putNumber(   "Displacement_X",       navex.getDisplacementX());
    SmartDashboard.putNumber(   "Displacement_Y",       navex.getDisplacementY());
    
    /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
    /* NOTE:  These values are not normally necessary, but are made available   */
    /* for advanced users.  Before using this data, please consider whether     */
    /* the processed data (see above) will suit your needs.                     */
    
    SmartDashboard.putNumber(   "RawGyro_X",            navex.getRawGyroX());
    SmartDashboard.putNumber(   "RawGyro_Y",            navex.getRawGyroY());
    SmartDashboard.putNumber(   "RawGyro_Z",            navex.getRawGyroZ());
    SmartDashboard.putNumber(   "RawAccel_X",           navex.getRawAccelX());
    SmartDashboard.putNumber(   "RawAccel_Y",           navex.getRawAccelY());
    SmartDashboard.putNumber(   "RawAccel_Z",           navex.getRawAccelZ());
    SmartDashboard.putNumber(   "RawMag_X",             navex.getRawMagX());
    SmartDashboard.putNumber(   "RawMag_Y",             navex.getRawMagY());
    SmartDashboard.putNumber(   "RawMag_Z",             navex.getRawMagZ());
    SmartDashboard.putNumber(   "IMU_Temp_C",           navex.getTempC());
    
    /* Omnimount Yaw Axis Information                                           */
    /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
    BoardYawAxis yaw_axis = navex.getBoardYawAxis();
    SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
    SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );
    
    /* Sensor Board Information                                                 */
    SmartDashboard.putString(   "FirmwareVersion",      navex.getFirmwareVersion());
    
    /* Quaternion Data                                                          */
    /* Quaternions are fascinating, and are the most compact representation of  */
    /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
    /* from the Quaternions.  If interested in motion processing, knowledge of  */
    /* Quaternions is highly recommended.                                       */
    SmartDashboard.putNumber(   "QuaternionW",          navex.getQuaternionW());
    SmartDashboard.putNumber(   "QuaternionX",          navex.getQuaternionX());
    SmartDashboard.putNumber(   "QuaternionY",          navex.getQuaternionY());
    SmartDashboard.putNumber(   "QuaternionZ",          navex.getQuaternionZ());
    
    /* Sensor Data Timestamp */
    SmartDashboard.putNumber(   "SensorTimestamp",		navex.getLastSensorTimestamp());
    }
}
