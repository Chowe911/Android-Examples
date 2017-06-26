package one.chowe.stepcounter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.format.DateUtils;
import android.widget.TextView;

/**
 * Created by cc on 2017/6/26.
 */

public class StepCounter implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Sensor mSensor2;
    public TextView textview;
    long timestamp;
    int step=0;

    StepCounter(Context context, TextView textView)
    {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensor2 = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,mSensor2,SensorManager.SENSOR_DELAY_UI);
        textview=textView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_STEP_DETECTOR)
        {
            timestamp = event.timestamp / 1000000;
            textview.setText("自 "+DateUtils.getRelativeTimeSpanString(timestamp)+" 以来\n一共走了 "+step+" 步");
        }

        if(event.sensor.getType()==Sensor.TYPE_STEP_COUNTER)
        {
            step=(int)event.values[0];
            textview.setText("一共走了 "+step+" 步");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
