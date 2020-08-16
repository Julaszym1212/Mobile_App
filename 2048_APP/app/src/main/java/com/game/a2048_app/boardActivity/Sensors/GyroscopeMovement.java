package com.game.a2048_app.boardActivity.Sensors;

import android.content.Context;
import android.hardware.SensorManager;


import com.game.a2048_app.R;
import com.game.a2048_app.boardActivity.OurCustomListenerFIXMERenameME;

public class GyroscopeMovement implements Runnable {

    private final static float minGyroValue = 2f;

    private final static float resetGyroValue = 0.3f;

    private float[] mGyroscopeData;
    private float[] mAccelerometerData;
    private float[] mMagnetometerData;
    private Context context;
    private OurCustomListenerFIXMERenameME ourCustomListenerFIXMERenameME;
    private static boolean hasMoved = true;

    private final static float RESET_PITCH = 0.2f;
    private final static float RESET_ROLL = 0.2f;

    public GyroscopeMovement(Context context, float[] mGyroscopeData, float[] mAccelerometerData, float[] mMagnetometerData) {
        this.context = context;
        this.ourCustomListenerFIXMERenameME = (OurCustomListenerFIXMERenameME) context;
        this.mGyroscopeData = mGyroscopeData;
        this.mAccelerometerData = mAccelerometerData;
        this.mMagnetometerData = mMagnetometerData;
    }

    private float[] getOrientationValues() {
        float[] rotationMatrix = new float[9];
        boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix,
                null, mAccelerometerData, mMagnetometerData);
        float[] orientationValues = new float[3];
        if (rotationOK) {
            SensorManager.getOrientation(rotationMatrix, orientationValues);
        }
        return orientationValues;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        float[] orientationValues = getOrientationValues();

        float azimuth = orientationValues[0];
        float pitch = orientationValues[1];
        float roll = orientationValues[2];


        if (!hasMoved) {
            if (this.mGyroscopeData[0] > minGyroValue) {
                ourCustomListenerFIXMERenameME.callback(context.getString(R.string.MoveDown));
            } else if (this.mGyroscopeData[0] < -minGyroValue) {
                ourCustomListenerFIXMERenameME.callback(context.getString(R.string.MoveUP));
            } else if (this.mGyroscopeData[1] > minGyroValue) {
                ourCustomListenerFIXMERenameME.callback(context.getString(R.string.MoveRight));
            } else if (this.mGyroscopeData[1] < -minGyroValue) {
                ourCustomListenerFIXMERenameME.callback(context.getString(R.string.MoveLeft));
            }
            if(Math.abs(this.mGyroscopeData[0]) > minGyroValue && Math.abs(this.mGyroscopeData[1]) > minGyroValue ) {
                hasMoved = true;
            }
        }
        if (Math.abs(pitch) < RESET_PITCH && Math.abs(roll) < RESET_ROLL &&
            Math.abs(this.mGyroscopeData[0]) < resetGyroValue && Math.abs(this.mGyroscopeData[1]) < resetGyroValue) {
            hasMoved = false;
        }
    }
}
