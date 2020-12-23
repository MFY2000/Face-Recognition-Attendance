package sample;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.library.Untils;

public class FaceDetectionController {
	// FXML buttons
	@FXML
	private Button cameraButton;
	// the FXML area for showing the current frame
	@FXML
	private ImageView originalFrame;
	private ScheduledExecutorService timer;
	private VideoCapture capture;
	private boolean cameraActive;
	private CascadeClassifier faceCascade;
	private int absoluteFaceSize;

	public void initialize() throws Exception {
		this.capture = new VideoCapture();
		this.faceCascade = new CascadeClassifier();
		this.absoluteFaceSize = 0;
		
		// set a fixed width for the frame
		originalFrame.setFitWidth(600);
		// preserve image ratio
		originalFrame.setPreserveRatio(true);
		checkboxSelection();
	}
	
	/**
	 * The action triggered by pushing the button on the GUI
	 */
	@FXML
	protected void startCamera() {
		if (!this.cameraActive) {
			try {
				this.capture.open(2);
			}catch (Exception ex){
				System.err.println("Impossible");
			}

			// is the video stream available?
			if (this.capture.isOpened()) {
				this.cameraActive = true;
				
				// grab a frame every 33 ms (30 frames/sec)
				Runnable frameGrabber = new Runnable() {
					
					@Override
					public void run() {
						// effectively grab and process a single frame
						Mat frame = grabFrame();
						// convert and show the frame
						Image imageToShow = Untils.mat2Image(frame);
						updateImageView(originalFrame, imageToShow);
					}
				};
				
				this.timer = Executors.newSingleThreadScheduledExecutor();
				this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
				
				// update the button content
				this.cameraButton.setText("Stop Camera");
			}
			else
			{
				// log the error
				System.err.println("Failed to open the camera connection...");
			}
		}
		else {
			this.cameraActive = false;
			this.cameraButton.setText("Start Camera");

			this.stopAcquisition();
		}
	}

	private Mat grabFrame() {
		Mat frame = new Mat();
		
		// check if the capture is open
		if (this.capture.isOpened()) {
			try {
				// read the current frame
				this.capture.read(frame);
				if (!frame.empty()) {
					this.detectAndDisplay(frame);

				}
				
			}
			catch (Exception e) {
				System.err.println("Exception during the image elaboration: " + e);
			}
		}
		return frame;
	}


	private void ComparingImages () {

	}


	private void detectAndDisplay(Mat frame) {
		MatOfRect faces = new MatOfRect();
		Mat grayFrame = new Mat();
		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(grayFrame, grayFrame);

		if (this.absoluteFaceSize == 0) {
			int height = grayFrame.rows();
			if (Math.round(height * 0.2f) > 0) {
				this.absoluteFaceSize = Math.round(height * 0.2f);
			}
		}

		this.faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE, new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());

		Rect[] facesArray = faces.toArray();
		System.out.println("Arrasy: "+ Arrays.toString(facesArray));
		for (int i = 0; i < facesArray.length; i++)
			Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(255, 0,255), 3);
		if (facesArray.length != 0){
			stopAcquisition();
		}
	}
	


	

	private void checkboxSelection() {
		String temp = "C:\\Users\\MF\\IdeaProjects\\FaceRecognition&Attendance\\src\\sample\\library\\resource\\haarcascades\\haarcascade_frontalface_alt.xml";
		this.faceCascade =  new CascadeClassifier(temp);

		this.cameraButton.setDisable(false);
		startCamera();
	}

	private void stopAcquisition() {
		if (this.timer!=null && !this.timer.isShutdown()) {
			try {
				this.timer.shutdown();
				this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e) {
				System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
			}
		}
		
		if (this.capture.isOpened()) {
			this.capture.release();
		}
	}

	private void updateImageView(ImageView view, Image image)
	{
		Untils.onFXThread(view.imageProperty(), image);
	}
	public void setClosed()
	{
		this.stopAcquisition();
	}
	
}
