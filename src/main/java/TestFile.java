
import javax.imageio.IIOException;
import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;
import java.util.zip.*;

public class TestFile {

    static String fileName = ".log.txt";

    public static void main(String[] args) {

//        testFileStream();
        //测试一下安全性
//        for (int i = 1; i <= 1000; i++) {
//            logEncrytByDes();
//        }

        //logEncrytByDes();
//        decryptLog();
//        renameFile();

//        testDataStream();

//        testInputStreamReader();

//        try {
//            //如果file不存在会创建一个，创建一个实实在在的file
//            FileOutputStream outputStream = new FileOutputStream("fileout.txt");
//
//            //如果文件不存在会报异常
//            FileInputStream inputStream = new FileInputStream("filein.txt");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        testFile();

//        testHighLevelFile();

//        testRandomAccessFile();

        //testAndroidLog();

        testRenameFile();
    }

    /**
     * 测试一下log
     */
    public static void testAndroidLog() {
        String name = "LOG.txt";
        try {
            File file = new File(name);
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (newFile) {
                    println("create new file: " + name);
                }
                file.setReadable(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testRenameFile() {
        String name = "LOG1.txt";
        try {
            File file = new File(name);
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (newFile) {
                    file.setReadable(false);
                    println("create new file: " + name);
                }
            }

            String name1 = "1LOG.txt";
            File tempFile = new File(name1);
            if (!tempFile.exists()) {
                boolean newFile = tempFile.createNewFile();
                if (newFile) {
                    println("create new file: " + name1);
                    tempFile.setReadable(false);
                    boolean rename = tempFile.renameTo(file);
                    if (rename) {
                        //需要关心的是，第一：旧文件还在不在(不在了) 2新文件是否可见(不可见) 3原有文件属性不存在，都是新的
                        println("rename file success");
                        file = tempFile;
                    }
                }
            } else {
                boolean rename = tempFile.renameTo(file);
                if (rename) {
                    println("rename file success");
                    file = tempFile;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //二进制文件字节流

    /**
     * FileInputStream/FileOutputStream
     */
    public static void testFileStream(String data) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName, true);
            byte[] bytes = data.getBytes(Charset.forName("UTF-8"));
            fileOutputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileName);
            byte[] datas = new byte[1024];
            fileInputStream.read(datas);
            String result = new String(datas);
            System.out.println("read result from file is " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * DataInputStream/DataOutputStream
     * 装饰类:支持基本类型的读写
     */
    public static void testDataStream() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("data.dat");
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            //这里写入123
//            dataOutputStream.writeDouble(100d);
            dataOutputStream.writeInt(123);
//            dataOutputStream.writeBoolean(true);
//            dataOutputStream.writeUTF("name");
            dataOutputStream.close();
            fileOutputStream.close();


            FileInputStream fileInputStream = new FileInputStream("data.dat");
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
//            double score = dataInputStream.readDouble();
//            int age = dataInputStream.readInt();
//            boolean isPass = dataInputStream.readBoolean();
//            String name = dataInputStream.readUTF();

            dataInputStream.close();
            fileInputStream.close();

            //这里测试一下FileOutStream写入的效果
            FileOutputStream fileOutputStream1 = new FileOutputStream("data1.txt");
            //这里也写入123
            fileOutputStream1.write(Integer.toString(123).getBytes("UTF-8"));
            fileOutputStream1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * BufferedInputStream/BufferedOutputStream
     * 装饰类，内部有个byte缓冲区，默认大小为8192，可以通过构造修改
     */
    public static void testBufferedStream() {
        try {

            //包装一般的文件流，带缓冲效果
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("hello.txt"));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("hello.txt"));

            //包装数据流，带缓冲，还可以写一般数据类型
            BufferedInputStream bufferedInputStream1 = new BufferedInputStream(new DataInputStream(new FileInputStream("world.txt")));
            BufferedOutputStream bufferedOutputStream1 = new BufferedOutputStream(new DataOutputStream(new FileOutputStream("world.txt")));
        } catch (Exception e) {

        }
    }


    //文本文件字符流

    /**
     * InputStreamReader/OutputStreamWriter
     */
    public static void testInputStreamReader() {
        Charset charset = Charset.forName("UTF-8");
        Charset.defaultCharset(); //获取系统的默认编码

        try {
            //以utf-8的形式写入文件
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("text.txt"), charset);
            writer.write("hello java");
            writer.close();


            InputStreamReader reader = new InputStreamReader(new FileInputStream("text.txt"), charset);
            char[] chars = new char[1024];//假如足够
            int read = reader.read(chars);
            System.out.println(new String(chars));//后面几个字符都是空格
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FileReader/FileWriter
     * 类似于FileInputStream/FileOutputStream,也只指定是覆盖还是追加
     */
    public static void testFileReader() {
        try {
            FileWriter writer = new FileWriter("writer.txt", true);//true表示追加，false表示覆盖
            writer.write("hello");
            writer.close();

            FileReader reader = new FileReader("writer.txt");
            char[] chars = new char[1024];
            int length = reader.read(chars);
            String data = new String(chars);
            System.out.println("读取的数据:" + data);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * CharArrayReader/CharArrayWriter
     * 类似于ByteArrayInputStream/ByteArrayOutputStream
     * 只不过操作对象又byte数组变成了char数据
     */
    public static void testCharArrayReader() {
        try {
            CharArrayWriter writer = new CharArrayWriter();
            writer.write(1);

            char[] chars = "hello".toCharArray();
            writer.write(chars, 0, 1024);
            System.out.println("写入的数据:" + writer.toString());

            CharArrayReader reader = new CharArrayReader(chars);
            char[] chars1 = new char[1024];
            int length = reader.read(chars1, 0, 1024);
            System.out.println("读取的数据:" + new String(chars1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * StringReader/StringWriter
     * 跟CharReader/CharWriter没区别，就是构造由char[]变为String了
     */
    public static void testStringReader() {
        try {
            StringWriter writer = new StringWriter();
            writer.write("hello");

            StringReader reader = new StringReader("hello");
            char[] chars = new char[1024];
            reader.read(chars, 0, 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * BufferedReader/BufferedWriter
     * 类似于BufferedInputStream/BufferedOutputStream
     * 也是一个装饰类，内部也有一个缓冲数组，不过类型由byte的变为char
     * 了，默认大小也是8192
     */
    public static void testBufferedReader() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("name.txt"));
            writer.newLine();//换行
            BufferedReader reader = new BufferedReader(new FileReader("name.txt"));
            reader.readLine();//表示读入一行数据，按行读取可以使用Scanner简化
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * PrintWriter
     * 专门用来打印的，有一大堆的print()重载方法
     * ,print()用来写入数据
     * 而且有很多的构造方法，支持传入各种参数
     */
    public static void testPrintWriter() {
        try {
            PrintWriter printWriter = new PrintWriter("hello.txt");
            printWriter.print("hello");
            printWriter.println();//打印一个换行
            printWriter.close();

            //以Writer为参数，内部不会有BufferedWriter了
            PrintWriter printWriter1 = new PrintWriter(new StringWriter());

            PrintStream printStream = new PrintStream("name");
            printStream.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Scanner
     * 可以理解为PrintWriter的逆操作，
     * 有一大堆的next()函数,用来读取数据
     */
    public static void testScanner() {
        try {
            Scanner scanner = new Scanner(System.in);//System.in就是一个InputStream对象
            scanner.useDelimiter(",");//使用逗号作为分隔符
            scanner.nextFloat();//读取一个float类型
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里的key使用fuck741fuck250
     */
    public static void logEncrytByDes() {
        String key = "fuck741fuck250";
        String data = "RoomSocketEngine: d\\\":false,\\\"speaking\\\":false,\\\"last_ping_time\\\":1551150753385,\\\"connect_info\\\":{\\\"app\\\":\\\"werewolf\\\",\\\"sv\\\":\\\"30\\\",\\\"pt\\\":\\\"android\\\",\\\"lg\\\":\\\"zh-CN\\\",\\\"v\\\":\\\"201902250\\\",\\\"did\\\":\\\"ffffffff-cfe7-87bb-9fe5-ba9800000000\\\",\\\"em\\\":\\\"0\\\",\\\"dvt\\\":\\\"GT-N7108\\\",\\\"em_true\\\":\\\"\\\"},\\\"connect_id\\\":\\\"9INz_MiSZ0\\\",\\\"avatar_box\\\":{},\\\"message_box\\\":{},\\\"last_msg_time\\\":1551150753385,\\\"privacy\\\":{\\\"state\\\":0}},\\\"2\\\":{\\\"id\\\":\\\"5b9a0323bc6f122fe5ec5fdd\\\",\\\"uid\\\":35000780,\\\"name\\\":\\\"黑夜步行者 很长很长很长\\\",\\\"avatar\\\":\\\"http://werewolf-image.xiaobanhui.com/Fq-5h88sWHbZisJlkNd6khRQqMQI?imageView2%2F0%2Fw%2F150%2Fh%2F150%2Fq%2F75%7Cimageslim\\\",\\\"vipInfo\\\":{\\\"active\\\":false,\\\"appType\\\":\\\"werewolf\\\",\\\"broadcast_count\\\":0,\\\"broadcast_msg\\\":\\\"官方授予\\\"},\\\"status\\\":2,\\\"prepared\\\":false,\\\"is_master\\\":false,\\\"enter_time\\\":1551150748645,\\\"position\\\":2,\\\"is_leaved\\\":false,\\\"is_disconnected\\\":false,\\\"is_escaped\\\":false,\\\"speaking\\\":false,\\\"last_ping_time\\\":1551150753512,\\\"connect_info\\\":{\\\"app\\\":\\\"werewolf\\\",\\\"sv\\\":\\\"30\\\",\\\"pt\\\":\\\"android\\\",\\\"lg\\\":\\\"zh-CN\\\",\\\"v\\\":\\\"201902250\\\",\\\"did\\\":\\\"00000000-0da8-83c2-0000-000000000000\\\",\\\"em\\\":\\\"0\\\",\\\"dvt\\\":\\\"MI MAX 3\\\",\\\"em_true\\\":\\\"\\\"},\\\"connect_id\\\":\\\"6OBK7E1BkW\\\",\\\"avatar_box\\\":{},\\\"message_box\\\":{},\\\"last_msg_time\\\":1551150753512,\\\"privacy\\\":{\\\"state\\\":0}}},\\\"observers\\\":{},\\\"exported\\\":false,\\\"can_cut_speaker\\\":true,\\\"startTime\\\":1551150748642,\\\"valResetRoomCount\\\":0,\\\"likeCount\\\":0,\\\"likeCountDur\\\":0,\\\"popVal\\\":0,\\\"popValDur\\\":0,\\\"isFull\\\":false,\\\"owner_type\\\":\\\"user\\\",\\\"owner_id\\\":\\\"599ec207e91aeae068fc73ef\\\",\\\"image\\\":\\\"http://werewolf-image.xiaobanhui.com/FoJE5xnZ8rHVQT1_uR7V8RBZvTR4\\\",\\\"name\\\":\\\"超级赛亚人\\\",\\\"init_time\\\":1551150748642,\\\"broadcast_data\\\":{\\\"broadcast_record\\\":true,\\\"live_broadcast_id\\\":\\\"\\\",\\\"broadcast_lyric_url\\\":\\\"\\\",\\\"music_id\\\":\\\"\\\",\\\"is_live_broadcast\\\":false},\\\"roles\\\":{}},\\\"game_info\\\":{\\\"words\\\":{}},\\\"ktv\\\":{\\\"room_id\\\":\\\"081719\\\",\\\"songs\\\":[],\\\"auto_sing\\\":false},\\\"merry\\\":{\\\"propose_duration\\\":30000,\\\"witnesses\\\":[],\\\"wedding_duration\\\":1\n" +
                "02-26 11:12:39.247  3318  6732 I RoomSocketEngine: 20000,\\\"now\\\":1551150759589},\\\"enter_time\\\":1551150759589,\\\"pk\\\":{\\\"voted_info\\\":[]},\\\"msg_id\\\":3641475958941},\\\"msg_time\\\":1551150759589}\"]";
        byte[] encrypt = EncryptUtils.encrypt(data.getBytes(Charset.forName("UTF-8")), key);
        String result = new String(Base64.getEncoder().encode(encrypt), Charset.forName("UTF-8"));
        testFileStream(result);

        try {
            System.out.println("加密后的内容:" + result);
            byte[] decrypt = EncryptUtils.decrypt(Base64.getDecoder().decode(result), key);
            String originData = new String(decrypt, Charset.forName("UTF-8"));
            System.out.println("解密后的内容:" + originData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void renameFile() {
        File file = new File(fileName);
        if (file.exists()) {
            file.renameTo(new File("kklog.txt"));
        }
    }

    /**
     * 这里将密文还原为明文
     */
    public static void decryptLog() {
        String key = "fuck741fuck250";
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            byte[] bytes = readStream(fileInputStream);
            byte[] decrypt = EncryptUtils.decrypt(Base64.getDecoder().decode(bytes), key);
            String originData = new String(decrypt, Charset.forName("UTF-8"));
            System.out.println("解密后的内容，准备写入file:" + originData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] readStream(InputStream in) {
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        if (in != null) {

            byte[] buff = new byte[128];
            baos = new ByteArrayOutputStream();
            int length = 0;
            try {
                while ((length = in.read(buff)) != -1) {
                    baos.write(buff, 0, length);
                }
                data = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //对比一下FileOutputStream
//        FileOutputStream fileOutputStream = new FileOutputStream("");
//        fileOutputStream.write(data);
        //这里要怎么生成一个String???,也没有直接生成file的方法
        return data;
    }


    /**
     * 文件/目录相关操作
     */
    public static void testFile() {
        try {
            //这一行并不会真正创建一个文件，只是创建了一个描述文件的对象
            File file = new File("file.txt");
            if (!file.exists()) {
                System.out.println("文件不存在");
                //这一行才是真正的创建一个file，如果文件已经存在则不会创建
                boolean newFile = file.createNewFile();
                System.out.println("crate new file:" + newFile);
            }

            //通过以下权限可以提高文件的安全性
            boolean readable = file.setReadable(true);//设置为可读
            boolean writable = file.setWritable(true);//设置为可写
            boolean executable = file.setExecutable(true);//设置为可执行

            System.out.println("readable:" + readable);
            System.out.println("writable:" + writable);
            System.out.println("executable:" + executable);


            //创建临时文件,添加前缀和后缀名字,以及目录
            //后缀默认为.temp,目录默认为系统目录
            File directory = new File("temp");
            if (!directory.exists()) {
                //创建目录，如果目录已存在，则直接返回false
                //创建目录，如果父目录不存在，则创建失败
                boolean mkdir = directory.mkdir();
                //创建目录，如果父目录不存在，则会创建父目录
                boolean mkdirs = directory.mkdirs();
            }
            File tempFile = File.createTempFile("prefix", "suffix", directory);
            System.out.println("创建临时文件:" + tempFile.getName());

            //重命名file
            boolean rename = file.renameTo(new File("666.txt"));

            //删除文件。如果file是目录，则除非目录为空，否则无法删除
            boolean delete = file.delete();
            //将file添加到待删列表，虚拟机退出的时候执行实际删除
            file.deleteOnExit();


            //这些都是返回直接子目录或文件，不会返回子目录下的
            String[] list = file.list();//listXXX系列函数，还可以添加过滤器
            //这里的pathname表示自己，file或者目录
            File[] files = file.listFiles(pathname -> "hello".equals(pathname.getName()));
            String[] list1 = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    //dir表示父目录，name表示文件名或子目录
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 高级文件操作
     */
    public static void testHighLevelFile() {
//        testGZIP();

        testZIP();
    }

    /**
     * GZIP的压缩和解压缩，使用简单，但是只能压缩一个文件
     */
    public static void testGZIP() {
        try {
            //压缩文件gzip======================================================================================
            //step1 先创建一个文件作为要压缩的文件
            File file = new File("test_gzip.txt");
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (newFile) {
                    println("create new file: " + file.getName());
                }
            }

            //step2 写入一些数据到file
            PrintWriter printWriter = new PrintWriter(file);
            //多打印一些数据，压缩后的文件不一定比之前的文件小，只有文件够大的时候，压缩效果才明显
            for (int i = 0; i < 1000; i++) {
                printWriter.print("this is first line");
                printWriter.println();
                printWriter.print("hello java");
                printWriter.println();
            }

            printWriter.close();

            //step3 创建压缩后的文件
            File gzipFile = new File("test_gzip.gz");
            if (!gzipFile.exists()) {
                boolean newFile = gzipFile.createNewFile();
                if (newFile) {
                    println("create new gzip file: " + gzipFile.getName());
                }
            }

            //step4 使用gzip文件创建GZIPOutputStream,用来将数据写入到gzip文件
            //这里包装一个缓冲流，提高写入数据的效率
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(gzipFile)));

            //step5 使用要压缩的文件创建一个读取流，用来读取数据向gzip文件写入
            //这里包装一个缓冲流，提高读取效率
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));

            //step6 读取要压缩的文件的数据，并写入到压缩后的文件中
            int len = -1;
            //因为gzip文件只能小，不能大，所以
            long length = file.length();
            byte[] buf = new byte[1024];
            while ((len = bufferedInputStream.read(buf)) > 0) {
                gzipOutputStream.write(buf, 0, len);
            }

            //step7 完事，关闭两个流
            gzipOutputStream.close();
            bufferedInputStream.close();
            println("compress finished, please look for the .gz file");
            //压缩文件gzip======================================================================================


            //解压文件gzip======================================================================================
            //这里对上面的gzip文件进行解压，得到新文件
            //step1 创建要解压的文件名，一般都是将压缩后的文件名字的后缀去掉，这里不这样做，新起一个名字，防止混淆
//            String newFileName = gzipFile.getName().substring(, )
            String newFileName = "gzip_upcompress_file.txt";
            File newFile = new File(newFileName);
            if (!newFile.exists()) {
                boolean fileNewFile = newFile.createNewFile();
                if (fileNewFile) {
                    println("create new file: " + newFile.getName());
                }
            }

            //step2 使用gzip文件创建输入GZIPInputStream，用来读取gzip文件
            //这里封装一个缓冲流，用来提高读取效果
            GZIPInputStream gzipInputStream = new GZIPInputStream(new BufferedInputStream(new FileInputStream(gzipFile)));

            //step3 创建输出流，用来写如数据到解压后的文件
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));

            //step4 从gzip文件中读取数据并写入解压后的文件中
            int len1 = -1;
            byte[] buf1 = new byte[1024];
            while ((len1 = gzipInputStream.read(buf1)) > 0) {
                bufferedOutputStream.write(buf1, 0, len1);
            }

            //step5 完事了，关闭两个流
            bufferedOutputStream.close();
            gzipInputStream.close();
            //解压文件gzip======================================================================================
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ZIP的压缩和解压缩，使用麻烦，但是可以包含多个文件
     */
    public static void testZIP() {
        try {
            //将一个目录/文件压缩为zip======================================================================================
            //step1 创建一个目录，用来测试

            // 创建一级目录
            File dir1 = new File("dir1/");
            if (!dir1.exists()) {
                boolean mkdir = dir1.mkdir();
                if (mkdir) {
                    println("create a new dir: " + dir1.getName());

                    //在一级目录下创建一个二级文件
                    File file1 = new File(dir1, "file1.txt");
                    if (!file1.exists()) {
                        boolean newFile = file1.createNewFile();
                        if (newFile) {
                            println("create new file: " + file1.getName());
                        }
                    }

                    //在一级目录下创建一个二级目录
                    File dir2 = new File(dir1, "dir2/");
                    if (!dir2.exists()) {
                        boolean mkdir1 = dir2.mkdir();
                        if (mkdir1) {
                            println("create a new dir: " + dir2.getName());
                            //在二级目录下创建一个三级文件
                            File file2 = new File(dir2, "file2.txt");
                            if (!file2.exists()) {
                                boolean newFile = file2.createNewFile();
                                if (newFile) {
                                    println("create new file: " + file2.getName());
                                }
                            }
                        }
                    }

                }
            }

            //step2 开始对上面的目录进行zip压缩
            //先创建压缩后的文件,名字后缀写成.zip
            File zipFile = new File("zip_file.zip");
            if (!zipFile.exists()) {
                boolean newFile = zipFile.createNewFile();
                if (newFile) {
                    println("create a new file: " + zipFile.getName());
                }
            }

            //step3 使用上面的zip文件创建输出流，用来写文件到zip
            //这里使用缓冲流，提高写入效率
            ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));

            //step4 将数据写入zip文件，这是个循环/递归过程
            //会不断根据子目录/文件创建输入流，进行读取-写入操作
            zipFile(zipOutputStream, dir1);

            //step5 完事了，关闭流
            zipOutputStream.close();
            println("compress file finished, please check the zip file");
            //将一个目录/文件压缩为zip======================================================================================


            //将一个zip解压为文件/目录======================================================================================
            //step1 使用上述的zip文件创建一个ZipInputStream
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));

            //step2 开始解压，遍历检索
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            while (nextEntry != null) {
                String name = nextEntry.getName();
                if (nextEntry.isDirectory()) {
                    //表示是目录
                    //那就只创建目录，不需要进行数据的读取
                    File dir = new File(name);
                    if (!dir.exists()) {
                        boolean mkdirs = dir.mkdirs();
                        if (mkdirs) {
                            println("create a new dir: " + name);
                        }
                    }
                } else {
                    //表示是文件，进行写入操作
                    //创建文件
                    File file = new File(name);
                    if (!file.exists()) {
                        boolean newFile = file.createNewFile();
                        if (newFile) {
                            println("create a new file: " + name);
                        }
                    }
                    //写入数据
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                    int len = -1;
                    byte[] buff = new byte[1024];
                    while ((len = zipInputStream.read(buff)) > 0) {
                        bufferedOutputStream.write(buff, 0, len);
                    }
                }

                //只要nextEntry为null，说明已经没有条目了
                nextEntry = zipInputStream.getNextEntry();
            }

            //将一个zip解压为文件/目录======================================================================================
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用zip方法压缩文件，因为需要兼容目录，这里采用递归的形式进行
     */
    public static void zipFile(ZipOutputStream zipOutputStream, File dir) throws Exception {
        if (dir == null) {
            return;
        }
        if (dir.isFile()) {
            //是个文件，直接压缩就行
            //这个表示要写入一个文件/目录，每次都必须添加该方法，如果以'/'结尾，会认为是一个目录，否则认为是一个文件
            //getPath()的话，只会返回相对路径名字，建议使用
            //getCanonicalPath()会返回完全路径名，泄漏私人信息，不建议使用
            zipOutputStream.putNextEntry(new ZipEntry(dir.getPath()));
//            zipOutputStream.putNextEntry(new ZipEntry(dir.getCanonicalPath()));
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(dir));
            int len = -1;
            byte[] buff = new byte[1024];
            while ((len = inputStream.read(buff)) > 0) {
                zipOutputStream.write(buff, 0, len);
            }
            inputStream.close();
        } else {
            //是个目录，需要遍历一个一个进行检索
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    zipFile(zipOutputStream, file);
                }
            }
        }
    }


    //===================================================================================================================

    /**
     * 随机读取文件 RandomAccessFile，可以当作java的SharedPreference来使用
     */
    public static void testRandomAccessFile() {
        //r:读
        //rw:读写
        //rws:读写，并且文件内容和元数据数据更新都同步到设备上
        //rwd:读写，文件内容的更新同步到设备，元数据不需要

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("randomfile.txt", "rw");

            long length = randomAccessFile.length();//获取当前文件的长度，单位是字节
            //设置文件长度，如果小于原来的长度，则会截取；如果大于则会扩展，扩展部分内容为为定义，这是一个native方法
            randomAccessFile.setLength(10086L);

            //这两个方法都是没有编码概念，假定一个字节代表一个字符，对中文不成立，尽量少使用
            randomAccessFile.writeBytes("hello");
            randomAccessFile.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 内存映射文件
     */
    public static void testFileChannel() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("testFileChannel.txt", "rw");
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 10);
            //这个map有一堆对文件位置操作的方法，暂时没什么卵用
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 序列化 ObjectOutputStream/ObjectInputStream
     * 序列化是通过反射实现的
     */
    public static void testObjectStream() {
        try {
            //transient 修饰的属性，在序列化的时候会忽视
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("hello")));
            objectOutputStream.writeObject(new Date()); //可以直接写对象

            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream("hello")));
            Date date = (Date) objectInputStream.readObject();//可以直接读对象
            println(date.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //===================================================================================================================
    private static void print(String content) {
        System.out.print(content);
    }

    private static void println(String content) {
        System.out.println(content);
    }
}
