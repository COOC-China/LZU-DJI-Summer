package edu.lzu.lihua;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.*;
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

public class main {

    private List<String> imageList = new ArrayList<String>();
    private Map<Integer,String> labelMap = new HashMap<>();
    private opencv_face.BasicFaceRecognizer faceRecognizer;

    public main() {

        readFile("D:\\IdeaProjects\\ThirdProject\\data");
//        for (String s : imageList) {
//            System.out.println(s);
//            String [] splits = s.split("/");
//                    System.out.println(splits[splits.length-2]);
//        }
        faceTrain();
        facePredict("D:\\IdeaProjects\\ThirdProject\\6.pgm");

    }

    public  void faceTrain(){
        int size = imageList.size();
        opencv_core.MatVector images = new opencv_core.MatVector(size);

        Mat matlabel = new Mat(imageList.size(),1,CV_32SC1);
        IntBuffer intBuffer = matlabel.getIntBuffer();

        int count = 0;
        for (String imagePath :imageList){
            Mat mat = imread(imagePath,CV_LOAD_IMAGE_GRAYSCALE);
            Mat normalMat = new Mat();
            normalize(mat,normalMat,0,255,NORM_MINMAX,CV_8UC1,noArray());

            String [] splits = imagePath.split("/");
            String label = splits[splits.length-2];
            int lab = Integer.parseInt(label.charAt(1)+"");

            intBuffer.put(count,lab);
            images.put(count,normalMat);
            labelMap.put(lab,label);

            count++;
        }

        faceRecognizer = createEigenFaceRecognizer(30,5000);
        faceRecognizer.train(images,matlabel);
    }

    public void facePredict(String path){
        Mat mat = imread(path,CV_LOAD_IMAGE_GRAYSCALE);
        Mat matNormal = new Mat();
        normalize(mat,matNormal,0,255,NORM_MINMAX,CV_8UC1,noArray());
        int reault = faceRecognizer.predict(matNormal);
        String trueresult = labelMap.get(reault);
        System.out.println(trueresult);
    }

    public void readFile(String path){
        File file = new File(path);
        if(!file.isDirectory()){
            if(path.endsWith(".pgm")||path.endsWith(".jpg")){
                imageList.add(path);
            }
        }else{
            String [] pa = file.list();
            for(String p: pa){
                String newpath = path + "/" + p;
                readFile(newpath);
            }
        }
    }

    public static void main(String [] args){
        new main();

//        Mat mat = imread("640784.jpg",CV_LOAD_IMAGE_GRAYSCALE);
//        imwrite("2.jpg",mat);
//
//        Mat img = imread("data/s1/3.pgm");
//        imshow("test",img);
//        waitKey(0);
    }
}
