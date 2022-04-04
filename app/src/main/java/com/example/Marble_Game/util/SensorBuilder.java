package com.example.Marble_Game.util;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.Marble_Game.UI.SensorUpdate;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by User on 2017/1/12.
 */

public class SensorBuilder implements SensorEventListener {
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;
    private float[] angle=new float[3];
    private SensorManager sensorManager;
    public SensorUpdate SUP=null;
    public SensorBuilder(Context context){
        sensorManager = (SensorManager)context.getSystemService(SENSOR_SERVICE);
    }

    public void Onstart(){
        android.hardware.Sensor gyroscope = sensorManager
                .getDefaultSensor(android.hardware.Sensor.TYPE_GYROSCOPE
                );
        sensorManager.registerListener(this, gyroscope,
                SensorManager.SENSOR_DELAY_GAME);
    }

    public void Onstop(){
        sensorManager.unregisterListener(this);
    }

    private final float[] deltaRotationVector = new float[4];
    private final float[] rotationCurrent =new float[3];
    private final float EPSILON=0.1f;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // 根据陀螺仪采样数据计算出此次时间间隔的偏移量后，它将与当前旋转向量相乘。
        if (timestamp != 0) {
            final float dT =  timestamp * NS2S;
            // 未规格化的旋转向量坐标值，。
            float axisX = sensorEvent.values[0];
            float axisY = sensorEvent.values[1];
            float axisZ = sensorEvent.values[2];

            SUP.Update(new float[]{axisX,axisY,axisZ});
        }
        timestamp=sensorEvent.timestamp-timestamp;
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {

    }

}
