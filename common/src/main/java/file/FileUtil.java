package file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yidxue on 2018/6/13
 */
public class FileUtil {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        writeToText(list, "/Users/cisco/file/tmp1.txt");
        changeFolderPermission(new File("/Users/cisco/file/tmp1.txt"));
    }

    private static void changeFolderPermission(File dirFile) {
        Set<PosixFilePermission> perms = new HashSet<>();
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_WRITE);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);

        Path path = Paths.get(dirFile.getAbsolutePath());
        try {
            Files.setPosixFilePermissions(path, perms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToText(List<String> data, String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            for (String aData : data) {
                bw.append(aData).append("\r\n");
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToTextByStream(List<String> data, String path) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
            for (String aData : data) {
                bw.append(aData).append("\r\n");
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
