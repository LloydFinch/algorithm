package javax.test;

import javax.bean.ParseBean;
import javax.bean.UserBean;

public class Scene {

    @ParseBean(value = 1)
    private UserBean userBean;

    public static void main(String[] args) {
        System.out.println("enter scene");
    }
}
