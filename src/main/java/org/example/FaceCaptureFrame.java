package org.example;

import javax.swing.*;
import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;

public class FaceCaptureFrame extends JFrame {

    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public FaceCaptureFrame() {
        setTitle("Face Capture");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton captureBtn = new JButton("Capture Face");
        captureBtn.addActionListener(e -> captureFace());
        add(captureBtn);

        setVisible(true);
    }

    private void captureFace() {
        VideoCapture camera = new VideoCapture(0); // Open default camera
        if (!camera.isOpened()) {
            JOptionPane.showMessageDialog(this, "Camera not detected!");
            return;
        }

        Mat frame = new Mat();
        camera.read(frame); // Capture frame

        // Convert to grayscale
        Mat gray = new Mat();
        Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

        // Load Haar Cascade for face detection
        CascadeClassifier faceDetector = new CascadeClassifier("resources/haarcascade_frontalface_default.xml");
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(gray, faceDetections);

        if (faceDetections.toArray().length > 0) {
            Imgcodecs.imwrite("captured_face.png", frame); // Save captured face
            JOptionPane.showMessageDialog(this, "Face captured successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "No face detected. Try again.");
        }

        camera.release();
    }
}