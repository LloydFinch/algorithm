import java.lang.reflect.Method;

public class VV {

    public class Person {
        private String name;
        private int age;
        private int sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }
    }

    public static void main(String[] args) {

        Method[] methods = Person.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("method:" + method);
        }
    }
}
