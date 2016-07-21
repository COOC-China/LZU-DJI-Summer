package edu.lzu.Wuzhijiang;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_face;

import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_face.createEigenFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;


/**
 * Created by Wuzhijiang on 2016/7/20.
 */
public class Main {

    private List<String> imageList = new ArrayList<>();
    private Map<Integer, String> LabelMap = new HashMap<>();
    protected opencv_face.BasicFaceRecognizer faceRecognizer;

    public Main() {
        readFile("E:\\Project\\Idea\\ThirdProject\\data");
        faceTrain();
        facePredict("E:\\Project\\Idea\\ThirdProject\\9.pgm");
    }

    public void faceTrain() {
        int size = imageList.size();
        opencv_core.MatVector images = new opencv_core.MatVector(size);

        Mat matLabel = new Mat(imageList.size(), 1, CV_32SC1);
        IntBuffer intBuffer = matLabel.getIntBuffer();

        int count = 0;
        for (String imagePath : imageList) {
            Mat mat = imread(imagePath, CV_LOAD_IMAGE_GRAYSCALE);
            Mat normalMat = new Mat();
            normalize(mat, normalMat, 0, 255, NORM_MINMAX, CV_8UC1, noArray());

            String[] splits = imagePath.split("/");
            String label = splits[splits.length - 2];
            int lab = Integer.parseInt(label.charAt(1) + "");

            intBuffer.put(count, lab);
            images.put(count, normalMat);
            LabelMap.put(lab, label);


            count++;
        }

        faceRecognizer = createEigenFaceRecognizer(30, 5000);
        faceRecognizer.train(images, matLabel);
    }


    public void facePredict(String path) {
        Mat mat = imread(path, CV_LOAD_IMAGE_GRAYSCALE);
        Mat matNormal = new Mat();
        normalize(mat, matNormal, 0, 255, NORM_MINMAX, CV_8UC1, noArray());
        int result = faceRecognizer.predict(matNormal);
        String trueResult = LabelMap.get(result);
        System.out.println(trueResult);
}
    public void readFile(String path){
        File file = new File(path);
        if (!file.isDirectory()){
            if (path.endsWith(".pgm") || path.endsWith(".jpg")) {
                imageList.add(path);
            }
        }else {
                String[] pa = file.list();
                for (String p : pa){
                    String newPath = path + "/"+p;
                    readFile(newPath);
                }
            }
        }

    public static void main(String []args){
        new Main();
    }
}

