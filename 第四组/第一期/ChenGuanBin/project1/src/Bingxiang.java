/**
 * @version 1.0
 * Created by Lenovo on 2016/7/17.
 */
public class Bingxiang
{
    private boolean isOpen;

    public Bingxiang()
    {
        this.isOpen=false;
    }

    public boolean isOpen()
    {
        return isOpen;
    }

//    public void setOpen(boolean open)
//    {
//        isOpen=open;
//    }

    public void open()
    {
        if (isOpen==true)
        {
            System.out.println("Refrigerator has already opened.");
            return;
        }
        else
        {
            isOpen=true;
            System.out.println("Refrigerator opened.");
        }
    }

    public void close()
    {
        if (isOpen==false)
        {
            System.out.println("Refrigerator has already closed.");
            return;
        }
        else
        {
            isOpen=false;
            System.out.println("Refrigerator closed.");
        }
    }
}
