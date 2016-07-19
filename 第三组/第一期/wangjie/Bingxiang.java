package com.company;

/**
 * @version 1.0
 * @e-mail shenzb12@lzu.edu.cn
 * Created by shenzebang on 16/7/17.
 */
public class Bingxiang {

    private boolean isOpen;

    public Bingxiang() {
        this.isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void open() {
        if (isOpen == true) {
            System.out.println("冰箱门已经打开了,不能再开啦!");
            return;
        } else {
            isOpen = true;
            System.out.println("好的,我自己把门打开了");
        }
    }

    public void close() {
        if (isOpen == false) {
            System.out.println("冰箱门已经管好了,不能再关了!");
            return;
        } else {
            isOpen = false;
            System.out.println("我自己把门关上了");
        }
    }
}
