package mcmi.config;

import org.json.JSONObject;

public class Parser {

	private JSONObject obj;
	
	public String version;
	public String update_link;
	public String modpack_version;
	public String mc_version;
	public String forge_version;
	public String forge_link;
	public String modpack_link;
	public String server_pack_link;
	
	public Parser(String data) {
		obj = new JSONObject(data);
		
		version = obj.getString("version");
		update_link = obj.getString("update_link");
		modpack_version = obj.getString("modpack_version");
		mc_version = obj.getString("mc_version");
		forge_version = obj.getString("forge_version");
		forge_link = obj.getString("forge_link");
		modpack_link = obj.getString("modpack_link");
		server_pack_link = obj.getString("server_pack_link");
		
		System.out.println("Retriving data...");
		System.out.println("----------");
		/*System.out.println(String.format("version: %s", version));
		System.out.println(String.format("update_link: %s", update_link));
		System.out.println(String.format("modpack_version: %s", modpack_version));
		System.out.println(String.format("mc_version: %s", mc_version));
		System.out.println(String.format("forge_version: %s", forge_version));
		System.out.println(String.format("forge_link: %s", forge_link));
		System.out.println(String.format("modpack_link: %s", modpack_link));
		System.out.println(String.format("server_pack_link: %s", server_pack_link));*/
		System.out.println(toString());
		System.out.println("----------");
	}
	
	@Override
	public String toString() {
		if (version == null || modpack_version == null || mc_version == null || forge_version == null || forge_link == null || modpack_link == null || server_pack_link == null) {
			return "[Parser] - No data";
		}
		return String.format("version: %s\nupdate_link: %s\nmodpack_version: %s\nmc_version: %s\nforge_version: %s\nforge_link: %s\n"
				+ "modpack_link: %s\nserver_pack_link: %s", version, update_link, modpack_version, mc_version, forge_version, forge_link, modpack_link, server_pack_link);
	}

}
