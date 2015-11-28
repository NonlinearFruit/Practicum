import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

class Model{

	private static List<HotKey> keys;
	private static List<HotKey> cmds;

	public enum Type{ CMD,HOTKEY,TYPING,CUSTOM }

		// UBUNTU("HotKeys/Ubuntu.json"),
		// NETBEANS("HotKeys/Netbeans.json"),
		// TERMINATOR("HotKeys/Terminator.json"),
		// ULTIMATE("HotKeys/Ultimate.json"),
		// WINDOWS("HotKeys/Windows.json"),
	public enum Json {
		GOOGLEDOCS("HotKeys/GoogleDocs.json"),
		SUBLIME("HotKeys/Sublime.json"),
		VIVALDI("HotKeys/Vivaldi.json"),

		VIM("Cmds/Vim.json");

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

	public static Json getJsonByName(String name)
	{
		for (Json json : Json.values()) {
			if(json.toString().equals(name))
				return json;
		}
		return null;
	}

	public static List<Json> getJsons(Type type)
	{		
		List<Json> results;
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
			System.out.println(file.getName());
		}
		return null;
	}

	private static void wakeUp(Json file)
	{
		getJsons(Type.CMD);
		keys = new ArrayList<>();
		cmds = new ArrayList<>();
		String content;
		try{ content = new Scanner(new File(file.getPath())).useDelimiter("\\Z").next(); } catch(Exception e){ return; }
		content = content.substring(content.indexOf("lvl")+3);
		String[] list = content.split("lvl");

		for (String lvl: list) {

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

	public static List<HotKey> getCmds() { return cmds; }

	public static List<HotKey> getCmds(Json file)
	{
		wakeUp(file);
		return cmds;
	}
}