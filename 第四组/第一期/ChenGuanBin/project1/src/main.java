/**
 * Created by Lenovo on 2016/7/17.
 */
public class main
{
//    private final String dji="this summer is good.";

    public main(){}

//    public static void showSomethingPrivate()
//    {
//        main ma=new main();
//        System.out.println(ma.dji);
//    }

    public static void main(String[] args)
    {
        Elephant elephant = new Elephant();
        Bingxiang Refrigerator = new Bingxiang();
        Refrigerator.open();
        Refrigerator.close();
        if (Refrigerator.isOpen())
        {
            elephant.goToBingXiang();
            Refrigerator.close();
        }
        else
        {
            System.out.println("Elephant says,'A bad refrigerator,Let me in!'");
        }
    }
//    {
//        String[] test=new String[]{"Hello","World"};
//        for (String str:test)
//        {
//            System.out.print(str);
//            System.out.print(" ");
//        }
//        System.out.println();
////        main ma=new main();
////        ma.showSomethingPrivate();
//        showSomethingPrivate();
//    }
}
