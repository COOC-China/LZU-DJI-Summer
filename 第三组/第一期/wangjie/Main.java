package com.company;

public class Main {

    private final String dji  = "this summer is good!";

    public Main(){
    }

    public static void showSomethingPrivate(){
        Main ma = new Main();
        System.out.println(ma.dji);
    }

    public static void main(String[] args) {
//        String[] test = new String[]{"Hello","World!"};
//        for (String str : test){
//            System.out.print(str);
//            System.out.print(" ");
//        }
//        System.out.println();
//
//        showSomethingPrivate();

        Elephant elephant = new Elephant();
        Bingxiang Refrigerator = new Bingxiang();

        Refrigerator.open();
        Refrigerator.close();
        if (Refrigerator.isOpen()){
            elephant.goToBingXiang();
        }
        else {
            System.out.println("门没打开,放不进去");
        }
        Refrigerator.close();
    }
}
