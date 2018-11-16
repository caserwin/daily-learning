package util.tool;

import org.apache.commons.io.IOUtils;
import java.io.*;

/**
 * @author yidxue
 */
public class SerializeUtil {

    /**
     * 把对象序列化成，比特数组
     */
    public static byte[] serialize(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            //将对象写入到字节数组中进行序列化
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把比特数组，反序列化成对象
     */
    public static Object deSerialize(byte[] bytes) {
        //将二进制数组导入字节数据流中
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            //将字节数组流转化为对象
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把比特数组写到文件中保存
     */
    public static void writeToTextByByte(String path, byte[] buffer) {
        try {
            FileOutputStream os = new FileOutputStream(new File(path));
            os.write(buffer, 0, buffer.length);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件读取比特数组
     */
    public static byte[] readFromTextByByte(String path) {
        try {
            FileInputStream is = new FileInputStream(path);
            return IOUtils.toByteArray(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 直接序列化到存储文件
     */
    public static void serializeToFile(Object object, String path) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(new File(path));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件读取，反序列化分成类
     */
    public static Object deSerializeFromFile(String path) {
        Object obj = null;
        try {
            FileInputStream fi = new FileInputStream(new File(path));
            ObjectInputStream oi = new ObjectInputStream(fi);
            obj = oi.readObject();
            oi.close();
            fi.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
