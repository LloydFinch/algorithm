import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStream {

    public static void main(String[] args) {
//        streamFilter();
        testCollectors();
    }

    private static void streamFilter() {
        //这个是所有的学生列表
        List<Student> students = new ArrayList<>();
        for (int i = 80; i < 110; i++) {
            Student student = new Student();
            student.id = i;
            student.name = "s_name_" + i;
            student.score = i;
            students.add(student);
        }
        //这里从student中取出所有score大于90的
        List<Student> above90Students = new ArrayList<>();
        for (Student student : students) {
            if (student.score >= 90) {
                above90Students.add(student);
            }
        }

        above90Students.forEach(student -> System.out.print(student.score + ","));

        System.out.println();
        //这里使用Stream的方式处理，这个碉堡了
        //#####过滤
        //获取分数在90以上的
        above90Students = students.stream().filter(student -> student.score >= 90).collect(Collectors.toList());
        above90Students.forEach(student -> System.out.print(student.score + ","));

        //#####转换
        //获取名字列表
        List<String> names = students.stream().map(student -> student.name).collect(Collectors.toList());
        names.forEach(System.out::println); //方法引用，还能这么写，碉堡了

        //#####组合使用
        //获取分数在90以上的名字(屌、屌、屌)
        List<String> above90Names = students.stream().filter(student -> student.score > 90).map(student -> student.name).collect(Collectors.toList());
        above90Names.forEach(System.out::println);
    }

    /**
     * 收集器
     */
    private static void testCollectors() {
        Stream<String> stream = Stream.of("hello", "android", "java", "kotlin");
//        String str = stream.collect(Collectors.joining()); //组合在一起
//        System.out.println(str);

        String string = stream.collect(Collectors.joining(" ", "<", ">"));//分隔，并且添加前缀和后缀
        System.out.println(string);
    }

    /**
     * 测试类
     */
    public static class Student {
        float score;
        String name;
        int id;
    }
}
