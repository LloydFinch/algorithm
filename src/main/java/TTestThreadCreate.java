public class TTestThreadCreate {

    public static void main(String[] args) {


        new Thread() {
            @Override
            public void run() {
                super.run();
            }
        }.start();


        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }
}
