package samestart;

public class Test {

    public static void main(String[] args) {

        int num = 10;
        FireFlag fireFlag = new FireFlag();
        Thread[] racer = new Thread[num];
        for (int i = 0; i < num; i++) {
            racer[i] = new Racer(fireFlag);
            racer[i].start();
        }

        try {
            Thread.sleep(1000);
            fireFlag.fire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
