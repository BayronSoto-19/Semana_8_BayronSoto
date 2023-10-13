package com.example.semana_8_bayronsoto;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {



    Button Descargar;
    ImageView imagen;

    Button mover;

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    /*private SensorManager sensorManager;
    private Sensor sensor;
    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    sensor = sensorManager.getDefaultSensor(
    Sensor.TYPE_ROTATION_VECTOR);
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Descargar = findViewById(R.id.btnDescargar);
        imagen = findViewById(R.id.imagen);
        mover = findViewById(R.id.MoverImagen);


        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        final Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        SensorEventListener vectorSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float[] rotationMatrix = new float[9];
                float[] orientationValues = new float[3];

                SensorManager.getRotationMatrixFromVector(rotationMatrix, sensorEvent.values);
                SensorManager.getOrientation(rotationMatrix, orientationValues);
                float angleZ = (float) Math.toDegrees(orientationValues[2]);

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };


        Descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = loadImageFromNetwork("https://th.bing.com/th/id/R.182ac155ed47fc768b8f62d78e6b4373?rik=iaSt5asVJOR8yg&pid=ImgRaw&r=0");
                        imagen.post(new Runnable() {
                            @Override
                            public void run() {
                                imagen.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private Bitmap loadImageFromNetwork (String imageURL){
        try {
            URL url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }


}