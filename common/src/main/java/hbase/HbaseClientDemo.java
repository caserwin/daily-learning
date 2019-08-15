package hbase;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yidxue
 */
public class HbaseClientDemo {
    private Connection connection;

    public HbaseClientDemo() {
        this.connection = HbaseConn.getConn();
    }

    public static void main(String[] args) throws IOException {
        HbaseClientDemo hcd = new HbaseClientDemo();
        System.out.println("=======");
//        hcd.addRow("xxx:xxx", "row0003", "cf", "c2", "v2");
//        hcd.getData("xxx:xxx", "row1", "cf", "c1");
//        hcd.scanData("xxx:xxx", "row1", "rowkey0001");
//        hcd.deleRow("xxx:xxx", "row0003", "cf1", "q1");
        hcd.deleRowByRegex("xxx:xxx", "rowkey_*.");
//        hcd.scanDataByRegex("xxx:xxx", "rowkey_*.");
//        hcd.scanDataByRegex("xxx:xxx", "rowkey_");
    }


    /**
     * 插入数据
     */
    public void addRow(String tableName, String rowkey, String colFamily, String col, String val) throws IOException {
        Table table = this.connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(col), Bytes.toBytes(val));
        table.put(put);

        //批量插入
//       List<Put> putList = new ArrayList<Put>();
//        puts.add(put);
//        table.put(putList);
        table.close();
    }

    /**
     * 删除数据
     */
    public void deleRow(String tableName, String rowkey, String colFamily, String col) throws IOException {
        Table table = this.connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(Bytes.toBytes(rowkey));
        //删除指定列族
        //delete.addFamily(Bytes.toBytes(colFamily));
        //删除指定列
        //delete.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col));
        table.delete(delete);
        table.close();
    }

    /**
     * 删除数据
     * https://stackoverflow.com/questions/32598003/how-to-mass-delete-multiple-rows-in-hbase
     */
    public void deleRowByRegex(String tableName, String fuzzyStr) throws IOException {
        Table table = this.connection.getTable(TableName.valueOf(tableName));
        List<Delete> deleteList = new ArrayList<>();

        Scan scan = new Scan();
        Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(fuzzyStr));
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);

        for (Result rr : scanner) {
            Delete d = new Delete(rr.getRow());
            deleteList.add(d);
        }
        try {
            table.delete(deleteList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.close();
    }

    /**
     * 根据rowkey查找数据
     */
    public void getData(String tableName, String rowkey, String colFamily, String col) throws IOException {
        Table table = this.connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowkey));
        //获取指定列族数据
        //get.addFamily(Bytes.toBytes(colFamily));
        //获取指定列数据
        //get.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col));
        Result result = table.get(get);

        showCell(result);
        table.close();
    }

    /**
     * 格式化输出
     */
    public static void showCell(Result result) {
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            System.out.println("RowName:" + new String(CellUtil.cloneRow(cell)));
            System.out.println("Timetamp:" + cell.getTimestamp());
            System.out.println("column Family:" + new String(CellUtil.cloneFamily(cell)));
            System.out.println("row Name:" + new String(CellUtil.cloneQualifier(cell)));
            System.out.println("value:" + new String(CellUtil.cloneValue(cell)));
            System.out.println("=====================");
        }
    }

    /**
     * 批量查找数据
     */
    public void scanData(String tableName, String startRow, String stopRow) throws IOException {
        Table table = this.connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(startRow));
        scan.setStopRow(Bytes.toBytes(stopRow));
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            showCell(result);
        }
        table.close();
    }

    /**
     * 批量查找数据
     * 正则是：rowkey_*. 写法、前缀匹配是：rowkey_ 写法
     */
    public void scanDataByRegex(String tableName, String fuzzyStr) throws IOException {
        Table table = this.connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
//        Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(fuzzyStr));
        Filter filter = new PrefixFilter(fuzzyStr.getBytes());
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {
            showCell(result);
        }
        table.close();
    }
}
