package json;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

/**
 * @author ydixue
 *
 * 嵌套解析，感觉比fastjson靠谱
 */
public class JsonPathDemo {
    public static void main(String[] args) {
        // multi json
//        String json = "{ \"message\": {\"label\": 174065352} }";

        String json = "{\n" +
                          "\t\"timestamp\": \"2018-01-14T14:53:25.939-0700\",\n" +
                          "\t\"ver\": \"2.0\",\n" +
                          "\t\"siteId\": \"924917\",\n" +
                          "\t\"confId\": \"83052446502385724\",\n" +
                          "\t\"os\": \"Windows 8\",\n" +
                          "\t\"browser\": \"Internet Explorer\",\n" +
                          "\t\"rcvTime\": \"2018-01-14T21:58:26Z\",\n" +
                          "\t\"remoteAddr\": \"64.68.120.107\",\n" +
                          "\t\"serverId\": \"mtsj1tws003.webex.com\",\n" +
                          "\t\"appType\": \"Native Client\",\n" +
                          "\t\"component\": \"Telemetry\",\n" +
                          "\t\"tsID\": \"6CEDA66F-2D6D-4C95-AF68-9502ABD82A70\",\n" +
                          "\t\"type\": \"Timing\",\n" +
                          "\t\"correlateID\": \"\",\n" +
                          "\t\"serviceType\": \"\",\n" +
                          "\t\"product\": \"WebEx\",\n" +
                          "\t\"values\": {\n" +
                          "\t\t\"category\": \"JME\",\n" +
                          "\t\t\"extVal\": {\n" +
                          "\t\t\t\"JMTReport\": {\n" +
                          "\t\t\t\t\"#conf#announcePresence\": {\n" +
                          "\t\t\t\t\t\"cost\": 152,\n" +
                          "\t\t\t\t\t\"offset\": 39347,\n" +
                          "\t\t\t\t\t\"value\": \"152\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"#conf#joinConf\": {\n" +
                          "\t\t\t\t\t\"cost\": 963,\n" +
                          "\t\t\t\t\t\"offset\": 38383,\n" +
                          "\t\t\t\t\t\"value\": \"963\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"#conf#pingSvr\": {\n" +
                          "\t\t\t\t\t\"cost\": 3298,\n" +
                          "\t\t\t\t\t\"offset\": 34952,\n" +
                          "\t\t\t\t\t\"value\": \"3298\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"AnnouncePresence\": {\n" +
                          "\t\t\t\t\t\"cost\": 152,\n" +
                          "\t\t\t\t\t\"offset\": 39347,\n" +
                          "\t\t\t\t\t\"value\": \"152\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"ApplicationStart:atmgr.exe:14180\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 26235,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"AtmgrLoadClient\": {\n" +
                          "\t\t\t\t\t\"cost\": 2316,\n" +
                          "\t\t\t\t\t\"offset\": 35569,\n" +
                          "\t\t\t\t\t\"value\": \"2316\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"AtmgrLoadUILibResource\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 35569,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"AtmgrLoadWebexMgr\": {\n" +
                          "\t\t\t\t\t\"cost\": 2130,\n" +
                          "\t\t\t\t\t\"offset\": 35569,\n" +
                          "\t\t\t\t\t\"value\": \"2130\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"AtmgrMovePingAhead\": {\n" +
                          "\t\t\t\t\t\"cost\": 1,\n" +
                          "\t\t\t\t\t\"offset\": 34953,\n" +
                          "\t\t\t\t\t\"value\": \"1\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"AtmgrStartMeeting\": {\n" +
                          "\t\t\t\t\t\"cost\": 2430,\n" +
                          "\t\t\t\t\t\"offset\": 35569,\n" +
                          "\t\t\t\t\t\"value\": \"2430\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"AtmgrWbxUI_Initialize\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 35569,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"BrowserType\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 25470,\n" +
                          "\t\t\t\t\t\"value\": \"Chrome\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"ChooseWorkFolder\": {\n" +
                          "\t\t\t\t\t\"cost\": 1,\n" +
                          "\t\t\t\t\t\"offset\": 15424,\n" +
                          "\t\t\t\t\t\"value\": \"1\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"ClientDownloadJME\": {\n" +
                          "\t\t\t\t\t\"cost\": 7768,\n" +
                          "\t\t\t\t\t\"offset\": 27184,\n" +
                          "\t\t\t\t\t\"value\": \"7768\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"ClientDownloadSize\": {\n" +
                          "\t\t\t\t\t\"cost\": 1.6242694e+07,\n" +
                          "\t\t\t\t\t\"offset\": 27184,\n" +
                          "\t\t\t\t\t\"value\": \"16242694\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"ClientJME\": {\n" +
                          "\t\t\t\t\t\"cost\": 34131,\n" +
                          "\t\t\t\t\t\"offset\": 0,\n" +
                          "\t\t\t\t\t\"value\": \"34131\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"ClientStart\": {\n" +
                          "\t\t\t\t\t\"cost\": 113,\n" +
                          "\t\t\t\t\t\"offset\": 37885,\n" +
                          "\t\t\t\t\t\"value\": \"113\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"ClientVersion\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 25470,\n" +
                          "\t\t\t\t\t\"value\": \"32.9.1.2\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"ConferenceStarted\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 39722,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"GPCJME\": {\n" +
                          "\t\t\t\t\t\"cost\": 22463,\n" +
                          "\t\t\t\t\t\"offset\": 15422,\n" +
                          "\t\t\t\t\t\"value\": \"22463\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"GoIntoPageTime\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 1175,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"GpcExtStart\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 25309,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"GpcJMT1005\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 25470,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"GpcLaunchClient\": {\n" +
                          "\t\t\t\t\t\"cost\": 40,\n" +
                          "\t\t\t\t\t\"offset\": 26195,\n" +
                          "\t\t\t\t\t\"value\": \"40\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"GpcStart\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 15422,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"InitFolder\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 15425,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"IsQuickLaunch\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 15424,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"JMECheckUpdate\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 27184,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"JMELaunchMeeting\": {\n" +
                          "\t\t\t\t\t\"cost\": 65,\n" +
                          "\t\t\t\t\t\"offset\": 26129,\n" +
                          "\t\t\t\t\t\"value\": \"65\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"JMEWriteINI\": {\n" +
                          "\t\t\t\t\t\"cost\": 8,\n" +
                          "\t\t\t\t\t\"offset\": 26130,\n" +
                          "\t\t\t\t\t\"value\": \"8\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"JoinConference\": {\n" +
                          "\t\t\t\t\t\"cost\": 963,\n" +
                          "\t\t\t\t\t\"offset\": 38383,\n" +
                          "\t\t\t\t\t\"value\": \"963\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"LaunchAsNormal\": {\n" +
                          "\t\t\t\t\t\"cost\": 56,\n" +
                          "\t\t\t\t\t\"offset\": 26138,\n" +
                          "\t\t\t\t\t\"value\": \"56\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"LaunchMethod\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 25470,\n" +
                          "\t\t\t\t\t\"value\": \"Extension\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"LoadFileList\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 27167,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"LoadINI\": {\n" +
                          "\t\t\t\t\t\"cost\": 24,\n" +
                          "\t\t\t\t\t\"offset\": 26351,\n" +
                          "\t\t\t\t\t\"value\": \"24\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"MphTimeSave\": {\n" +
                          "\t\t\t\t\t\"cost\": 2316,\n" +
                          "\t\t\t\t\t\"offset\": 34953,\n" +
                          "\t\t\t\t\t\"value\": \"2316\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"OSType\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 0,\n" +
                          "\t\t\t\t\t\"value\": \"Windows\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"PGCost\": {\n" +
                          "\t\t\t\t\t\"cost\": 211,\n" +
                          "\t\t\t\t\t\"offset\": 25470,\n" +
                          "\t\t\t\t\t\"value\": \"211\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"PGStart\": {\n" +
                          "\t\t\t\t\t\"cost\": 2018,\n" +
                          "\t\t\t\t\t\"offset\": 25468,\n" +
                          "\t\t\t\t\t\"value\": \"2018\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"PageJME\": {\n" +
                          "\t\t\t\t\t\"cost\": 1178,\n" +
                          "\t\t\t\t\t\"offset\": 0,\n" +
                          "\t\t\t\t\t\"value\": \"1178\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"ParseDocshowDuration\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 25309,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"PingConference\": {\n" +
                          "\t\t\t\t\t\"cost\": 3298,\n" +
                          "\t\t\t\t\t\"offset\": 34952,\n" +
                          "\t\t\t\t\t\"value\": \"3298\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"QLFailure\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 15424,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"ServerJME\": {\n" +
                          "\t\t\t\t\t\"cost\": 4413,\n" +
                          "\t\t\t\t\t\"offset\": 0,\n" +
                          "\t\t\t\t\t\"value\": \"4413\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"ServiceType\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 25470,\n" +
                          "\t\t\t\t\t\"value\": \"MC\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"SinceDate\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 0,\n" +
                          "\t\t\t\t\t\"value\": \"2018-01-14T14:52:46.213-0700\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"SupportJME\": {\n" +
                          "\t\t\t\t\t\"cost\": 1,\n" +
                          "\t\t\t\t\t\"offset\": 35569,\n" +
                          "\t\t\t\t\t\"value\": \"1\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"TotalJME\": {\n" +
                          "\t\t\t\t\t\"cost\": 39722,\n" +
                          "\t\t\t\t\t\"offset\": 0,\n" +
                          "\t\t\t\t\t\"value\": \"39722\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"Update\": {\n" +
                          "\t\t\t\t\t\"cost\": 7768,\n" +
                          "\t\t\t\t\t\"offset\": 27184,\n" +
                          "\t\t\t\t\t\"value\": \"7768\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"UpdateUser\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 27585,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"UserGID\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 25309,\n" +
                          "\t\t\t\t\t\"value\": \"68066653\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"UserID\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 25309,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"UserStartPoint\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 0,\n" +
                          "\t\t\t\t\t\"value\": \"2018-01-14T14:52:46.213-0700\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"UserType\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 0,\n" +
                          "\t\t\t\t\t\"value\": \"NewUser\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"VerifySign\": {\n" +
                          "\t\t\t\t\t\"cost\": 53,\n" +
                          "\t\t\t\t\t\"offset\": 23692,\n" +
                          "\t\t\t\t\t\"value\": \"53\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"Version\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 0,\n" +
                          "\t\t\t\t\t\"value\": \"1.0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"WbxCPfwServiceMgr_Initialize\": {\n" +
                          "\t\t\t\t\t\"cost\": 180,\n" +
                          "\t\t\t\t\t\"offset\": 37819,\n" +
                          "\t\t\t\t\t\"value\": \"180\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"WbxDelayInitSession\": {\n" +
                          "\t\t\t\t\t\"cost\": 99,\n" +
                          "\t\t\t\t\t\"offset\": 38082,\n" +
                          "\t\t\t\t\t\"value\": \"99\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"WbxModifyShareMenu\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 39568,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"WbxNewCWebExMgrCtl\": {\n" +
                          "\t\t\t\t\t\"cost\": 758,\n" +
                          "\t\t\t\t\t\"offset\": 36923,\n" +
                          "\t\t\t\t\t\"value\": \"758\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"WbxNewInstance\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 37682,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"WbxUIMgrInitializeUI\": {\n" +
                          "\t\t\t\t\t\"cost\": 82,\n" +
                          "\t\t\t\t\t\"offset\": 39500,\n" +
                          "\t\t\t\t\t\"value\": \"82\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"WbxUsrMgr2InitializeUI\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 37851,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"WbxVideoMgr_init\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 38082,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"docJsPluginLoad\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 1178,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"jme#AppPath\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 26376,\n" +
                          "\t\t\t\t\t\"value\": \"C:\\\\ProgramData\\\\WebEx\\\\WebEx\\\\T32_UMC\\\\atmgr.exe\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"jme#DownloadFileList\": {\n" +
                          "\t\t\t\t\t\"cost\": 788,\n" +
                          "\t\t\t\t\t\"offset\": 26378,\n" +
                          "\t\t\t\t\t\"value\": \"788\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"jme#Download[atmgr.exe][WebEx_V]16242694/7768\": {\n" +
                          "\t\t\t\t\t\"cost\": 2042,\n" +
                          "\t\t\t\t\t\"offset\": 27184,\n" +
                          "\t\t\t\t\t\"value\": \"2042\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"jme#FolderWritable\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 15425,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"jme#RunNewUser\": {\n" +
                          "\t\t\t\t\t\"cost\": 7,\n" +
                          "\t\t\t\t\t\"offset\": 25471,\n" +
                          "\t\t\t\t\t\"value\": \"7\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"jme#UpdateAtmgr\": {\n" +
                          "\t\t\t\t\t\"cost\": 657,\n" +
                          "\t\t\t\t\t\"offset\": 25471,\n" +
                          "\t\t\t\t\t\"value\": \"657\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"jme#atgpcext.dll\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 25470,\n" +
                          "\t\t\t\t\t\"value\": \"10032.9.2017.1129\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"jme#ciscowebexstart.exe\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 25470,\n" +
                          "\t\t\t\t\t\"value\": \"10032.9.2017.1129\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"jme#ciscowebexstart.exe#CreateNewProcess:#14180\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 26195,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"jme#site\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 26376,\n" +
                          "\t\t\t\t\t\"value\": \"dynatrace.webex.com\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"jme#urlRoot\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 25470,\n" +
                          "\t\t\t\t\t\"value\": \"https://dynatrace.webex.com\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"pgvDone\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 540,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"preJME#CheckBasicModules\": {\n" +
                          "\t\t\t\t\t\"cost\": 0,\n" +
                          "\t\t\t\t\t\"offset\": 15426,\n" +
                          "\t\t\t\t\t\"value\": \"0\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"preJME#StartDownload\": {\n" +
                          "\t\t\t\t\t\"cost\": 9811,\n" +
                          "\t\t\t\t\t\"offset\": 15427,\n" +
                          "\t\t\t\t\t\"value\": \"9811\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"preJME#download#atgpcdec.dll;atgpcext.dll;\": {\n" +
                          "\t\t\t\t\t\"cost\": 239,\n" +
                          "\t\t\t\t\t\"offset\": 25238,\n" +
                          "\t\t\t\t\t\"value\": \"239\"\n" +
                          "\t\t\t\t},\n" +
                          "\t\t\t\t\"wbx#initFeatureTracking\": {\n" +
                          "\t\t\t\t\t\"cost\": 118,\n" +
                          "\t\t\t\t\t\"offset\": 37699,\n" +
                          "\t\t\t\t\t\"value\": \"118\"\n" +
                          "\t\t\t\t}\n" +
                          "\t\t\t}\n" +
                          "\t\t},\n" +
                          "\t\t\"label\": \"TotalJME\",\n" +
                          "\t\t\"value\": 39722\n" +
                          "\t}\n" +
                          "}";

        DocumentContext dc = JsonPath.parse(json);
        System.out.println(dc.read("$.values.extVal.JMTReport1").toString());
    }
}
