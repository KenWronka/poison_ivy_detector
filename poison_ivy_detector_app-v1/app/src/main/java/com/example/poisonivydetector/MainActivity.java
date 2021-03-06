package com.example.poisonivydetector;

//Initial app based off: https://github.com/BCJuan/SimpleClassificationApp

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.poisonivydetector.Classifier;
import com.example.poisonivydetector.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String [] REQUIRED_PERMISSIONS = new String []{Manifest.permission.CAMERA};

    PreviewView mPreviewView;
    TextView tvResults;
    Classifier classifier;
    ProgressBar detectionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreviewView = findViewById(R.id.viewFinder);
        tvResults = findViewById(R.id.tvResults);
        detectionBar = findViewById(R.id.detectionBar);

        classifier = new Classifier(this);

        if (allPermissionsGranted()){
            startCamera();
        }
        else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS);
        }
    }

    private void startCamera(){
        ListenableFuture<ProcessCameraProvider>
                cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                            bindPreview(cameraProvider);
                        } catch (ExecutionException | InterruptedException e) { }
                    }
                },
                ActivityCompat.getMainExecutor(this)
        );
    }

    void bindPreview(ProcessCameraProvider cameraProvider) {
        ImageCapture.Builder builder = new ImageCapture.Builder();
        ImageCapture imageCapture = builder.build();

        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(mPreviewView.createSurfaceProvider());

        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(
                CameraSelector.LENS_FACING_BACK).build();
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(ActivityCompat.getMainExecutor(this),
                new ImageAnalysis.Analyzer() {
                    @Override
                    public void analyze(@NonNull ImageProxy image) {
                        float[] result_array = classifier.classify(image);
                        float result_num = result_array[classifier.POSITIVE_INDEX];
//                        double result_num = (1 / (1 + Math.exp(-result_logit)));
                        String result = String.format("Poison ivy likelihood: %.2f%s", result_num*100, "%");
                        tvResults.setText(result);
                        detectionBar.setProgress((int) Math.round(result_num * 100));
                        image.close();
                    }
                });
        Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector,
                preview, imageAnalysis, imageCapture);
    }

    private boolean allPermissionsGranted(){
        for(String permission: REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(
                    this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS){
            if(allPermissionsGranted()){
                startCamera();
            }
            else{
                Toast.makeText(this, "Please, give access to camera",
                        Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }
}