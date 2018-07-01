package jdbc;

import jdbc.bean.PersonRecord;
import jdbc.hive.HiveDAO;
import java.util.ArrayList;

/**
 * Created by yidxue on 2018/6/30
 */
public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("args error!");
            return;
        }
        String table = args[0];
        String path = args[1];

        ArrayList<PersonRecord> records = new ArrayList<>();
        records.add(new PersonRecord(1, "AA", 17, "male"));
        records.add(new PersonRecord(1, "BB", 19, "female"));
        records.add(new PersonRecord(1, "CC", 23, "male"));

        HiveDAO hiveDAO = new HiveDAO();
        // create table
        hiveDAO.create(table, PersonRecord.class);

        // load data
        hiveDAO.loadToHive(records, table, path);
    }
}
