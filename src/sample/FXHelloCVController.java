package sample;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import sample.library.Untils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXHelloCVController{
    @FXML private Button button;
    @FXML private ImageView currentFrame;

    private ScheduledExecutorService timer;
    private VideoCapture capture = new VideoCapture();
    private boolean cameraActive = false;
    private static int cameraId = 1;
    Mat matrix = null;
    private int absoluteFaceSize = 0;
    private CascadeClassifier faceCascade = new CascadeClassifier();


    public void initialize() throws Exception {
        startCamera();
    }


    public WritableImage capureSnapShot() {
        WritableImage WritableImage = null;

        // Loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        // Instantiating the VideoCapture class (camera:: 0)
        VideoCapture capture = new VideoCapture(cameraId);

        // Reading the next video frame from the camera
        Mat matrix = new Mat();
        capture.read(matrix);

        // If camera is opened
        if( capture.isOpened()) {
            // If there is next video frame
            if (capture.read(matrix)) {
                // Creating BuffredImage from the matrix
                BufferedImage image = new BufferedImage(matrix.width(),
                        matrix.height(), BufferedImage.TYPE_3BYTE_BGR);

                WritableRaster raster = image.getRaster();
                DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
                byte[] data = dataBuffer.getData();
                matrix.get(0, 0, data);
                this.matrix = matrix;

                // Creating the Writable Image
                WritableImage = SwingFXUtils.toFXImage(image, null);
            }
        }
        return WritableImage;
    }

    @FXML protected void startCamera() {
        if (!this.cameraActive) {
            // start the video capture
            String classifierPath = "C:\\Users\\MF\\IdeaProjects\\FaceRecognition&Attendance\\src\\sample\\library\\lbpcascade_frontalface.xml";
            this.faceCascade.load(classifierPath);

            if (this.capture.isOpened()) {
                this.cameraActive = true;
                Runnable frameGrabber = new Runnable() {

                    @Override
                    public void run(){
                        Mat frame = grabFrame();
                        Image imageToShow = Untils.mat2Image(frame);
                        updateImageView(currentFrame, imageToShow);
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 100, TimeUnit.MILLISECONDS);
                this.button.setText("Stop Camera");
            }
            else {
                System.err.println("Impossible to open the camera connection...");
            }
        }
        else {
            this.cameraActive = false;
            this.button.setText("Start Camera");
            this.stopAcquisition();
        }
    }



    private Mat grabFrame()
    {
        // init everything
        Mat frame = new Mat();

        // check if the capture is open
        if (this.capture.isOpened())
        {
            try {
                this.capture.read(frame);
                if (!frame.empty()) {
                    MatOfRect faces = new MatOfRect();
                    Mat grayFrame = new Mat();

                    // convert the frame in gray scale
                    Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
                    // equalize the frame histogram to improve the result
                    Imgproc.equalizeHist(grayFrame, grayFrame);

                    // compute minimum face size (20% of the frame height, in our case)
                    if (this.absoluteFaceSize == 0) {
                        int height = grayFrame.rows();
                        if (Math.round(height * 0.2f) > 0) {
                            this.absoluteFaceSize = Math.round(height * 0.2f);
                        }
                    }

                    // detect faces
                    System.out.println("hi1");
                    this.faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE, new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());

                    // each rectangle in faces is a face: draw them!
                    Rect[] facesArray = faces.toArray();
                    for (int i = 0; i < facesArray.length; i++)
                        Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 3);}

            }
            catch (Exception e)
            {
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return frame;
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    private void stopAcquisition()
    {
        if (this.timer!=null && !this.timer.isShutdown())
        {
            try
            {
                // stop the timer
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                // log any exception
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened())
        {
            // release the camera
            this.capture.release();
        }
    }

    /**
     * Update the {@link ImageView} in the JavaFX main thread
     *
     * @param view
     *            the {@link ImageView} to update
     * @param image
     *            the {@link Image} to show
     */
    private void updateImageView(ImageView view, Image image)
    {
        Untils.onFXThread(view.imageProperty(), image);
    }

    /**
     * On application close, stop the acquisition from the camera
     */
    protected void setClosed()
    {
        this.stopAcquisition();
    }

}