import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Model{

	private static List<HotKey> keys;
	private static List<HotKey> cmds;
	private static double defaultDif = 3;
	private static double maxDif = 3;


	public enum Type{ CMD,HOTKEY,TYPING,CUSTOM }

		// UBUNTU("HotKeys/Ubuntu.json"),
		// NETBEANS("HotKeys/Netbeans.json"),
		// TERMINATOR("HotKeys/Terminator.json"),
		// ULTIMATE("HotKeys/Ultimate.json"),
		// WINDOWS("HotKeys/Windows.json"),
	public static class Json {
		// GOOGLEDOCS("HotKeys/GoogleDocs.json"),
		// SUBLIME("HotKeys/Sublime.json"),
		// VIVALDI("HotKeys/Vivaldi.json"),

		// VIM("Cmds/Vim.json"),
		// VIMIUM("Cmds/Vimium.json");

		private String path;
		Json(String path)
		{
			this.path = path;	
		}

		public String getPath() { return path; }
		public String toString() 
		{ 
			String name = path.substring(path.indexOf("/")+1);
			return name.substring(0,name.indexOf(".")); }

	}

	// public static Json getJsonByName(String name)
	// {
	// 	for (Json json : Json.values()) {
	// 		if(json.toString().equals(name))
	// 			return json;
	// 	}
	// 	return null;
	// }

	public static List<Json> getJsons(Type type)
	{		
		List<Json> results = new ArrayList<>();
		File[] files;

		File[] cmdFiles =  new File("./Cmds").listFiles();
		File[] hotkeysFiles =  new File("./HotKeys").listFiles();
		File[] typingsFiles =  new File("./Typing").listFiles();
		File[] customsFiles =  new File("./Custom").listFiles();

		switch (type) {
			case CMD:
				files = cmdFiles;
				break;
			case HOTKEY:
				files = hotkeysFiles;
				break;
			case TYPING:
				files = typingsFiles;
				break;
			default:
				files = customsFiles;
		}

		for (File file : files) {
			String filename = file.getParentFile().getName()+"/"+file.getName();
			System.out.println(filename);
			results.add(new Json(filename));	
		}
		return results;
	}

	private static void wakeUp(Json file) { wakeUp(file,defaultDif); }

	private static void wakeUp(Json file, double difficulty)
	{
		getJsons(Type.CMD);
		keys = new ArrayList<>();
		cmds = new ArrayList<>();
		String content;
		try{ content = new Scanner(new File(file.getPath())).useDelimiter("\\Z").next(); } catch(Exception e){ return; }
		content = content.substring(content.indexOf("lvl")+3);
		String[] list = content.split("lvl");
		int scale = (int) Math.floor(list.length*difficulty/maxDif);
			scale = Math.max(scale,1);
			System.out.println(scale);
		for(int j=0; j<list.length && j<scale; j++) {

			String lvl = list[j];
			int i,e;
			String type,combo,desc,find,title;

			// Title
			find = ":\"";
			lvl = lvl.substring(lvl.indexOf(find) + find.length());
			title = lvl.substring(0,lvl.indexOf("\""));

			// Type
			find = "type\":\"";
			lvl = lvl.substring(lvl.indexOf(find) + find.length());
			type = lvl.substring(0,lvl.indexOf("\""));

			// Cmds and Keys
			find = "combo\":\"";
			while( (i=lvl.indexOf(find)) != -1)
			{
				i += find.length();
				lvl = lvl.substring(i);
				e = lvl.indexOf("\"");
				combo = lvl.substring(0,e);

				find = "desc\":\"";
				i = lvl.indexOf(find)+find.length();
				lvl = lvl.substring(i);
				e = lvl.indexOf("\"");
				desc = lvl.substring(0,e);

				if( type.equals("cmds") ){
					cmds.add(new HotKey(combo,desc));
				}
				else{ //if(type.equals("hotkey"))
					keys.add(new HotKey(combo,desc));
				}

				find = "combo\":\"";
			}
		}
	}

	public static List<HotKey> getKeys() { return keys; }

	public static List<HotKey> getKeys(Json file)
	{
		wakeUp(file);
		return keys;
	}

	public static List<HotKey> getKeys(Json file, double difficulty)
	{
		wakeUp(file,difficulty);
		return keys;
	}

	public static List<HotKey> getCmds() { return cmds; }

	public static List<HotKey> getCmds(Json file)
	{
		wakeUp(file);
		return cmds;
	}

	public static List<HotKey> getCmds(Json file, double difficulty)
	{
		wakeUp(file,difficulty);
		return keys;
	}
}