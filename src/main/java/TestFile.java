import java.io.*;
import java.nio.charset.Charset;
import java.util.Base64;

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
        renameFile();
    }


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
}
