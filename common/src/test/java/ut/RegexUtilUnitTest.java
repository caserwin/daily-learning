package ut;

import core.RegexDemo2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by yidxue on 2018/2/7
 */
public class RegexUtilUnitTest {

    @Test
    public void getMcsInstancdIdTest() throws Exception {
        String text = "/opt/webex/mmp/logs/wbxmcs-01_metrics_01022018_0.28919.log";
        assertEquals("wbxmcs-01", RegexDemo2.getMcsInstancdId(text));
    }

    @Test
    public void getJMATest() throws Exception {
        String text = "xxxx,jma:4.13,xxxx";
        assertEquals("4.13", RegexDemo2.getJMA(text));
    }
}
