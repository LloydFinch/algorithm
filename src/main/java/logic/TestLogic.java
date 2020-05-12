package logic;

/**
 * Name: TestLogic
 * Author: lloydfinch
 * Function: if-else switch loop recursion
 * Date: 2020-05-06 16:49
 * Modify: lloydfinch 2020-05-06 16:49
 */
public class TestLogic {

    public static void main(String[] args) {

    }

    private void testSwitch() {
        String sa = "";
        int ia = 10;
        long la = 10;
        /**
         * switch不能使用long
         * 因为switch会优化为一个"跳转表"，跳转表值的存储空间一般为32位，存放不下long，而string是用的hashcode(也就是int)，是可以的
         */
        switch (sa) {

        }
    }
}