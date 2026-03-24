package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Auto Drive + TRUE Spin")
public class Auto extends LinearOpMode {

    private DcMotor fl, fr, bl, br;

    static final double TICKS_PER_REV = 537.7;
    static final double WHEEL_DIAMETER = 3.625;
    static final double TICKS_PER_INCH =
            TICKS_PER_REV / (Math.PI * WHEEL_DIAMETER);

    @Override
    public void runOpMode() {

        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");

        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.FORWARD);

        resetEncoders();

        waitForStart();

        // ---- DRIVE FORWARD (still time-based if you want) ----
        setAllPower(0.5);
        sleep(1000);
        stopMotors();
        sleep(500);

        // ---- TRUE SPIN (encoder controlled) ----
        spinInPlaceTicks(2000, 0.6); // adjust ticks if needed

        stopMotors();
    }

    // 🔥 TRUE CENTER SPIN
    private void spinInPlaceTicks(int ticks, double power) {

        fl.setTargetPosition(fl.getCurrentPosition() + ticks);
        bl.setTargetPosition(bl.getCurrentPosition() + ticks);
        fr.setTargetPosition(fr.getCurrentPosition() - ticks);
        br.setTargetPosition(br.getCurrentPosition() - ticks);

        setRunToPosition();

        setAllPower(power);

        while (opModeIsActive() &&
                fl.isBusy() && fr.isBusy() &&
                bl.isBusy() && br.isBusy()) {
        }

        stopMotors();
        setRunUsingEncoder();
    }

    // -------- Helpers --------
    private void resetEncoders() {
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setRunUsingEncoder();
    }

    private void setRunToPosition() {
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void setRunUsingEncoder() {
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void setAllPower(double p) {
        fl.setPower(p);
        fr.setPower(p);
        bl.setPower(p);
        br.setPower(p);
    }

    private void stopMotors() {
        setAllPower(0);
    }
}
