package com.qualcomm.ftcrobotcontroller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

// Ryan Jay Gray 2015-12
// Modified OpenCV ColorBlobDetector class to steer FTC robot
// and push beacon button of correct color
public class ColorBlobDetector {
    // Lower and Upper bounds for range checking in HSV color space
    private Scalar mLowerBound = new Scalar(0);
    private Scalar mUpperBound = new Scalar(0);
    // Minimum contour area in percent for contours filtering
    private static double mMinContourArea = 0.1;
    // Color radius for range checking in HSV color space
    // RJG increased color radius
    private Scalar mColorRadius = new Scalar(34,50,50,0);
    private Mat mSpectrum = new Mat();
    private List<MatOfPoint> mContours = new ArrayList<MatOfPoint>();
    private Scalar rect_COLOR = new Scalar(255, 255, 255, 255);
    public static Rect boundRect;           // RJG rectangle around largest color contour
    // RJG goDirection -1 = can't see, 0 to 1 = turn max left to max right
    public static float goDirection;

    private static Point tlPoint,brPoint;   // RJG top left of boundRect, bttom right
    // Cache
    Mat mPyrDownMat = new Mat();
    Mat mHsvMat = new Mat();
    Mat mMask = new Mat();
    Mat mDilatedMask = new Mat();
    Mat mHierarchy = new Mat();

    public void setColorRadius(Scalar radius) {
        mColorRadius = radius;
    }

    public void setHsvColor(Scalar hsvColor) {
        double minH = (hsvColor.val[0] >= mColorRadius.val[0]) ? hsvColor.val[0]-mColorRadius.val[0] : 0;
        double maxH = (hsvColor.val[0]+mColorRadius.val[0] <= 255) ? hsvColor.val[0]+mColorRadius.val[0] : 255;

        mLowerBound.val[0] = minH;
        mUpperBound.val[0] = maxH;

        mLowerBound.val[1] = hsvColor.val[1] - mColorRadius.val[1];
        mUpperBound.val[1] = hsvColor.val[1] + mColorRadius.val[1];

        mLowerBound.val[2] = hsvColor.val[2] - mColorRadius.val[2];
        mUpperBound.val[2] = hsvColor.val[2] + mColorRadius.val[2];

        mLowerBound.val[3] = 0;
        mUpperBound.val[3] = 255;


        Log.i("RJG lowercolor", String.valueOf(mLowerBound));
        Log.i("RJG uppercolor", String.valueOf(mUpperBound));

        Mat spectrumHsv = new Mat(1, (int)(maxH-minH), CvType.CV_8UC3);

        for (int j = 0; j < maxH-minH; j++) {
            byte[] tmp = {(byte)(minH+j), (byte)255, (byte)255};
            spectrumHsv.put(0, j, tmp);
        }

        Imgproc.cvtColor(spectrumHsv, mSpectrum, Imgproc.COLOR_HSV2RGB_FULL, 4);
    }

    public Mat getSpectrum() {
        return mSpectrum;
    }

    public void setMinContourArea(double area) {
        mMinContourArea = area;
    }

    public void process(Mat rgbaImage) {

//        Log.i("RJG: Image width", String.valueOf(rgbaImage.cols()));
        double theSize;
        double theWidth;

        theSize = 0;
        Imgproc.pyrDown(rgbaImage, mPyrDownMat);
        Imgproc.pyrDown(mPyrDownMat, mPyrDownMat);

        Imgproc.cvtColor(mPyrDownMat, mHsvMat, Imgproc.COLOR_RGB2HSV_FULL);
        Core.inRange(mHsvMat, mLowerBound, mUpperBound, mMask);
        Imgproc.dilate(mMask, mDilatedMask, new Mat());

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Imgproc.findContours(mDilatedMask, contours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // RJG Find max contour area so we don't chase noise
        double maxArea = 0;
        Iterator<MatOfPoint> each = contours.iterator();
        while (each.hasNext()) {
            MatOfPoint wrapper = each.next();
            double area = Imgproc.contourArea(wrapper);
            if (area > maxArea){
                maxArea = area;
                boundRect=Imgproc.boundingRect(wrapper);
                tlPoint = boundRect.tl();
                tlPoint.x *=4;
                tlPoint.y *=4;
                brPoint = boundRect.br();
                brPoint.x *=4;
                brPoint.y *=4;
                theSize = (brPoint.x - tlPoint.x)*(brPoint.y - tlPoint.y);
                Log.i("RJG", "boxsize:" + theSize);
            }
        }

        goDirection = -1;   // RJG goDirection defaults to "cant see"

        if (maxArea != 0) {
            // RJG simple calculation of directions ranging from 0 (max left) to 1 (max right)
            Imgproc.rectangle(rgbaImage, tlPoint, brPoint, rect_COLOR, 3);
            goDirection = (float)((brPoint.x + tlPoint.x)/2)/rgbaImage.cols();
        } else {
//            tlPoint.x = 0;
//            tlPoint.y = 0;
//            brPoint.x = 10;
//            brPoint.y = 10;
            goDirection = -1;
        }

        /// RJG rough stop condition at beacon (now time to bavck up)
        if(theSize > 70000) goDirection = -1;

//       Log.i("RJG dir: ", String.valueOf(goDirection));

        // Filter contours by area and resize to fit the original image size
        mContours.clear();
        each = contours.iterator();
        while (each.hasNext()) {
            MatOfPoint contour = each.next();
            if (Imgproc.contourArea(contour) > mMinContourArea*maxArea) {
                Core.multiply(contour, new Scalar(4,4), contour);
                mContours.add(contour);
            }
        }
    }

    public float getDirection() {
        return goDirection;
    }

    public Rect getBoundRect() {
        return boundRect;
    }

    public List<MatOfPoint> getContours() {
        return mContours;
    }
}
