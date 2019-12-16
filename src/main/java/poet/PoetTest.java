package poet;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.*;


public class PoetTest {

    public static void main(String[] args) {

//        test$N();

        test$L();
    }


    private static void testPoet() {
        /**
         * 定义方法
         */
        MethodSpec methodSpec = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String[].class, "args")
                .returns(void.class)
                .addStatement("$T.out.println($S)", System.class, "Hello, Android")
                .build();

        /**
         * 添加方法:直接通过代码块
         */
        MethodSpec methodSpec1 = MethodSpec.methodBuilder("forTest")
                .addCode("" +
                        "for(int i = 0;i<10;i++){\n" +
                        "System.out.println(i)\n" +
                        "}\n")
                .build();

        /**
         * 添加方法:通过API
         */
        MethodSpec methodSpec2 = MethodSpec.methodBuilder("forTest")
                .beginControlFlow("for(int i=0;i<10;i++)")
                .addStatement("System.out.println(i)")
                .endControlFlow()
                .build();

        /**
         * 添加方法:使用$T(代表类型), $S代表字符串, $L代表变量值(可以用来代替参数)
         */
        MethodSpec methodSpec3 = MethodSpec.methodBuilder("errorTest")
                .returns(TypeName.INT)
                .beginControlFlow("try")
                .addStatement("throw new Exception($S)", "this is a exception")
                .nextControlFlow("catch($T e)", Exception.class)
                .addStatement("throw new $T(e)", RuntimeException.class)
                .endControlFlow()
                .build();

        /**
         * 添加方法:使用$N(代表成员变量和成员方法)
         */
        MethodSpec methodSpec4 = MethodSpec.methodBuilder("nextMethod")
                .addModifiers(Modifier.PRIVATE)
                .returns(TypeName.INT)
                .addStatement("return $N()", methodSpec3)
                .build();

        /**
         * 定义类
         */
        TypeSpec typeSpec = TypeSpec.classBuilder("HelloAndroid")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(methodSpec4)
                .build();

        /**
         * 定义文件
         */
        JavaFile javaFile = JavaFile.builder("poet", typeSpec)
                .addStaticImport(Collections.class, "*")
                .build();

        /**
         * 进行写入
         */
        try {
            javaFile.writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    private static void test$N() {
        MethodSpec hexDigit = MethodSpec.methodBuilder("hexDigit")
                .addParameter(int.class, "i")
                .returns(char.class)
                .addStatement("return (char) (i < 10 ? i + '0' : i - 10 + 'a')")
                .build();

        MethodSpec byteToHex = MethodSpec.methodBuilder("byteToHex")
                .addParameter(int.class, "b")
                .returns(String.class)
                .addStatement("char[] result = new char[2]")
                .addStatement("result[0] = $N((b >>> 4) & 0xf)", hexDigit)
                .addStatement("result[1] = $N(b & 0xf)", hexDigit)
                .addStatement("return new String(result)")
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("HelloAndroid")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(byteToHex)
                .addMethod(hexDigit)
                .build();

        JavaFile javaFile = JavaFile.builder("poet", typeSpec)
                .addStaticImport(Collections.class, "*")
                .build();

        try {
            javaFile.writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void test$L() {
        /**
         * 按照顺序填充参数
         */
        CodeBlock code1 = CodeBlock.builder()
                .add("I ate $L $L", 3, "tacos")
                .build();
        System.out.println(code1.toString());

        /**
         * 指定参数顺序
         */
        CodeBlock code2 = CodeBlock.builder()
                .add("i ate $2L $1L", "tacos", 3)
                .build();
        System.out.println(code2.toString());

        /**
         * 使用map构造参数，格式:$name:L
         */
        Map<String, String> arg = new LinkedHashMap<>();
        arg.put("name", "tom");
        arg.put("age", "24");

        CodeBlock code3 = CodeBlock.builder()
                .addNamed("name is $name:L, age is $age:L", arg)
                .build();
        System.out.println(code3.toString());

        /**
         * 参数
         */
        ParameterSpec parameter1 = ParameterSpec.builder(String.class, "android")
                .addModifiers(Modifier.FINAL)
                .build();
        MethodSpec method1 = MethodSpec.methodBuilder("test")
                .addParameter(parameter1)
                .build();
        System.out.println(method1.toString());

        /**
         * 成员变量
         */
        FieldSpec field1 = FieldSpec.builder(String.class, "name")
                .addModifiers(Modifier.PRIVATE)
                .initializer("$S + $L", "this is tom, who's age is", 24)
                .build();
        System.out.println(field1.toString());

        TypeSpec interface1 = TypeSpec.interfaceBuilder("Interface1").build();
        System.out.println(interface1.toString());
        TypeSpec class1 = TypeSpec.classBuilder("Class1").build();
        System.out.println(class1.toString());
        TypeSpec enum1 = TypeSpec.enumBuilder("Enum1")
                .addEnumConstant("E1")
                .build();
        System.out.println(enum1.toString());
    }


//    public interface HandlePacket {
//        boolean handle(String packet);
//    }
//
//    public class Application {
//
//        public List<HandlePacket> packetHandler = new ArrayList<>();
//
//        public void registerPacketHandler(HandlePacket handler) {
//            if (!this.packetHandler.contains(handler) && handler != null) {
//                this.packetHandler.add(handler);
//            }
//        }
//
//        public void removePacketHandler(HandlePacket handler) {
//            this.packetHandler.remove(handler);
//        }
//
//
//        public void onReceivePacket(String packet) {
//             handlePacket(packet);
//        }
//
//        private void handlePacket(String packet) {
//            for (HandlePacket handlePacket : packetHandler) {
//                handlePacket.handle(packet);
//            }
//        }
//    }
//
//    public class BaseActivity implements HandlePacket {
//
//        public void onCreate() {
//            getApplication().registerPacketHandler(this);
//        }
//
//        @Override
//        public boolean handle(String packet) {
//            return false;
//        }
//
//        public void onDestroy() {
//            getApplication().removePacketHandler(this);
//        }
//
//    }
//
//
//    public class Activity1 extends BaseActivity {
//        //这里想重新处理,覆盖
//
//        @Override
//        public boolean handle(String packet) {
//
//            return true;
//        }
//    }


}
