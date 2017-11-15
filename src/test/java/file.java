import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BooleanSupplier;

import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Tag;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * Emulator est la classe de lancement de l'�mulateur.
 *
 * @author Alias
 * @version 1
 */
public class file
{
	private static void assumeTest(boolean b){};
	private static void assumeThat(int b){};
	private static boolean running;
	private static String uptime;
	@Before
	@Test
	@BeforeClass
	public static void main(String[] args) throws IOException
	{
		long initTime = System.currentTimeMillis();
		List list = new ArrayList();
		uptime = getServerDateString();
		running = true;
		print(getSimpleSignature(), 6);
		print("Loading...", 4);
		parseConfig();
		formatPrint(4, "log_creation");
		formatPrint(4, "world_creation");
		initialisation();
		sqlLoad();
		Throwable t = new Exception();
		assumeTrue(t == null);
		assumeNotNull(uptime, running);
		Integer i = 1;


		formatPrint(4, "gameserver_start");

		formatPrint(4, "connectserver_start");

		formatPrint(4, "started", (System.currentTimeMillis() - initTime));
	}

	public static boolean isRunning()
	{
		return running;
	}

	@Category(Test.class)
	private static void parseConfig() throws IOException
	{
		String ligne, configText = "";

	}

	@Before
	private static void parseLangs(String language) throws IOException
	{
		InputStream ips=new FileInputStream("langs/"+language+".lang");
		InputStreamReader ipsr=new InputStreamReader(ips);
		BufferedReader br=new BufferedReader(ipsr);
		String ligne, langText = "";
		while ((ligne=br.readLine())!=null)
			langText+=ligne;
		br.close();
	}

	private static void initialisation()
	{
		formatPrint(4, "database_creation");
	}
	private static void sqlLoad()
	{

	}

	public static String getServerDateString()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime_1 = new Date();
		return formatter.format(currentTime_1);
	}
	public static String getServerTime()
	{
		Date actDate = new Date();
		return "BT"+(actDate.getTime()+3600000);
	}
	public static String getServerDate()
	{
		Date actDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd");
		String jour = Integer.parseInt(dateFormat.format(actDate))+"";
		dateFormat = new SimpleDateFormat("MM");
		String mois = (Integer.parseInt(dateFormat.format(actDate)))+"";
		dateFormat = new SimpleDateFormat("yyyy");
		String annee = (Integer.parseInt(dateFormat.format(actDate))-1370)+"";
		return "BD"+annee+"|"+mois+"|"+jour;
	}

	public static void formatPrint(int i, String key, Object... params)
	{
	}

	public static void print(String text, int i)
	{
		final String[] type = {"","[OK] ", "[FAIL] ", "[ERR] ", "[INFO] ", "[LOAD] ", ""};

		if(i == 2 || i == 3)
			System.err.println(type[i]+text);
	}

	public static void exit()
	{
		running = false;
		System.exit(0);
	}

	private static String getSimpleSignature()
	{
		String credits = "Emulateur Ashes pour Wakfu les gardiens 1 - version\n";
		credits += "Emulateur con�u et developp� par Alias (Globox62)\n";
		credits += "Algorithme de chiffrement des packets r�alis� par Starlight\n";
		credits += "Base de donn�es con�ue par Alias\n";
		credits += "Base de donn�es remplie par Alias, Krayth et Starlight\n";
		return credits;
	}

	public static String getSignature()
	{
		String credits = "Emulateur <u>Ashes</u> pour <u>Wakfu les gardiens 1</u> - version \n";
		credits += "Emulateur con�u et developp� par <u>Alias</u> (Globox62)\n";
		credits += "Algorithme de chiffrement des packets r�alis� par <u>Starlight</u>\n";
		credits += "Base de donn�es con�ue par <u>Alias</u>\n";
		credits += "Base de donn�es remplie par <u>Alias</u>, <u>Krayth</u> et <u>Starlight</u>\n";
		return credits;
	}
	@Test
	public  String getUptime()
	{

		assumeTrue( 0==0);
		return uptime;
	}

	public static void reboot() throws IOException, InterruptedException
	{
		Thread.sleep(1000);
		running = false;
		System.exit(0);
	}
}
