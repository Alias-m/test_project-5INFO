package ashes;

import Environment.Instance.language;
import Environment.Instance;
import ashes.server.internal.server.ConnectServer;
import ashes.server.internal.server.GameServer;
import ashes.server.internal.world.containers.World;
import ashes.server.internal.world.creators.SQLManager;
import ashes.utils.JsonDatas;
import ashes.utils.Log;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static Environment.version;

public final class Emulator {
    private static transient GameServer gameServer;

    private static transient ConnectServer connectServer;

    private static boolean running;

    private static String uptime;

    @Before
    @Test
    public static void main(String[] args) throws IOException {
        long initTime = System.currentTimeMillis();
        Emulator.uptime = Emulator.getServerDateString();
        Emulator.running = true;
        Emulator.print(Emulator.getSimpleSignature(), 6);
        Emulator.print("Loading...", 4);
        Emulator.parseConfig();
        Emulator.formatPrint(4, "log_creation");
        Log.initLogs();
        Emulator.formatPrint(4, "world_creation");
        Emulator.initialisation();
        Emulator.sqlLoad();
        Emulator.formatPrint(4, "gameserver_start");
        Emulator.gameServer = GameServer.init();
        Emulator.gameServer.start();
        Emulator.formatPrint(4, "connectserver_start");
        Emulator.connectServer = ConnectServer.init();
        Emulator.connectServer.start();
        Saver saver = new Saver();
        Emulator.formatPrint(4, "started", ((System.currentTimeMillis()) - initTime));
        saver.start();
    }

    public static boolean isRunning() {
        return Emulator.running;
    }

    @Ignore
    private static void parseConfig() throws IOException {
        JsonDatas config = new JsonDatas();
        InputStream ips = new FileInputStream("config.conf");
        InputStreamReader ipsr = new InputStreamReader(ips);
        BufferedReader br = new BufferedReader(ipsr);
        String ligne;
        String configText = "";
        while ((ligne = br.readLine()) != null)
            configText += ligne;
        
        br.close();
        config.parseElements(configText);
        Instance.host = config.getItem("host");
        Instance.DBhost = config.getItem("DBhost");
        Instance.dynamicDB = config.getItem("dynamic");
        Instance.staticDB = config.getItem("static");
        Instance.user = config.getItem("user");
        Instance.pass = config.getItem("pass");
        Instance.emulatorName = config.getItem("jarName");
        Instance.game_port = Integer.parseInt(config.getItem("game_port"));
        Instance.connect_port = Integer.parseInt(config.getItem("connect_port"));
        Instance.debug = config.getItem("debug").equals("true");
        String language = config.getItem("langs");
        Emulator.parseLangs(language);
    }

    @Before
    private static void parseLangs(String language) throws IOException {
        JsonDatas lang = new JsonDatas();
        InputStream ips = new FileInputStream((("langs/" + language) + ".lang"));
        InputStreamReader ipsr = new InputStreamReader(ips);
        BufferedReader br = new BufferedReader(ipsr);
        String ligne;
        String langText = "";
        while ((ligne = br.readLine()) != null)
            langText += ligne;
        
        br.close();
        lang.parseElements(langText);
        Instance.language = lang;
    }

    private static void initialisation() {
        Emulator.formatPrint(4, "database_creation");
        SQLManager.initialisation();
    }

    private static void sqlLoad() {
        SQLManager.LOAD_WORLD();
    }

    public static String getServerDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime_1 = new Date();
        return formatter.format(currentTime_1);
    }

    public static String getServerTime() {
        Date actDate = new Date();
        return "BT" + ((actDate.getTime()) + 3600000);
    }

    public static String getServerDate() {
        Date actDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        String jour = (Integer.parseInt(dateFormat.format(actDate))) + "";
        dateFormat = new SimpleDateFormat("MM");
        String mois = (Integer.parseInt(dateFormat.format(actDate))) + "";
        dateFormat = new SimpleDateFormat("yyyy");
        String annee = ((Integer.parseInt(dateFormat.format(actDate))) - 1370) + "";
        return (((("BD" + annee) + "|") + mois) + "|") + jour;
    }

    public static void formatPrint(int i, String key, Object... params) {
        String output = String.format(language.getItem(key), params);
        Emulator.print(output, i);
    }

    public static void print(String text, int i) {
        final String[] type = new String[]{ "" , "[OK] " , "[FAIL] " , "[ERR] " , "[INFO] " , "[LOAD] " , "" };
        if ((i == 2) || (i == 3))
            System.err.println(((type[i]) + text));
        else
            if ((i != 0) || (Instance.debug))
                System.out.println(((type[i]) + text));
            
        
    }

    public static void exit() {
        Emulator.running = false;
        World.getInstance().saveWorld();
        System.exit(0);
    }

    private static String getSimpleSignature() {
        String credits = ("Emulateur Ashes pour Wakfu les gardiens 1 - version " + (Environment.version)) + "\n";
        credits += "Emulateur con\ufffdu et developp\ufffd par Alias (Globox62)\n";
        credits += "Algorithme de chiffrement des packets r\ufffdalis\ufffd par Starlight\n";
        credits += "Base de donn\ufffdes con\ufffdue par Alias\n";
        credits += "Base de donn\ufffdes remplie par Alias, Krayth et Starlight\n";
        return credits;
    }

    public static String getSignature() {
        String credits = ("Emulateur <u>Ashes</u> pour <u>Wakfu les gardiens 1</u> - version " + (Environment.version)) + "\n";
        credits += "Emulateur con\ufffdu et developp\ufffd par <u>Alias</u> (Globox62)\n";
        credits += "Algorithme de chiffrement des packets r\ufffdalis\ufffd par <u>Starlight</u>\n";
        credits += "Base de donn\ufffdes con\ufffdue par <u>Alias</u>\n";
        credits += "Base de donn\ufffdes remplie par <u>Alias</u>, <u>Krayth</u> et <u>Starlight</u>\n";
        return credits;
    }

    public static String getUptime() {
        return Emulator.uptime;
    }

    public static void reboot() throws IOException, InterruptedException {
        World.getInstance().saveWorld();
        Emulator.connectServer.close();
        Emulator.gameServer.close();
        Thread.sleep(1000);
        Emulator.running = false;
        Runtime.getRuntime().exec(("java -jar " + (Instance.emulatorName)));
        System.exit(0);
    }
}

