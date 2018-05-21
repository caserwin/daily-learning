package cassandra.demo;

import cassandra.conn.CassConnSimple;
import com.datastax.driver.core.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author yiding
 */
public class Demo2 {

    /**
     * 按行读取文件
     */
    public static ArrayList<RowBean> readFileByLine(String filePath) throws IOException {
        ArrayList<RowBean> lineList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] strs = line.trim().split("\\s+");
            RowBean rowBean = new RowBean();
            rowBean.setSiteurl(strs[1]);
            rowBean.setSiteid(strs[0]);
            lineList.add(rowBean);
        }
        br.close();

        return lineList;
    }

    public static HashSet<String> readFileByLineSet(String filePath) throws IOException {
        HashSet<String> set = new HashSet<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] strs = line.trim().split("\\s+");
            set.add(strs[1]);
        }
        br.close();

        return set;
    }


    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        ArrayList<RowBean> arrRowBean = readFileByLine("common/data/stapdb-prod.txt");
        Session sess = CassConnSimple.getConn();

//        int count =1;
//        BatchStatement batch =new BatchStatement();
//        for (RowBean rowBean : arrRowBean) {
//            SimpleStatement statement = new SimpleStatement("INSERT INTO ks_global_pda.siteurltositeidtable (siteurl, siteid) VALUES (?,?)", rowBean.getSiteurl(), rowBean.getSiteid());
//            batch.add(statement);
//            System.out.println("now is "+count++);
//        }
//        sess.execute(batch);

        int count = 1;
        for (RowBean rowBean : arrRowBean) {
            System.out.println(count++);
            sess.execute("INSERT INTO ks_global_pda.siteurltositeidtable (siteurl, siteid) VALUES (?,?)", rowBean.getSiteurl(), rowBean.getSiteid());
        }

        System.out.println((System.currentTimeMillis() - start) / 1000 + "s");
    }
}
