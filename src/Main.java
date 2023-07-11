import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
	public static JDA jda = null;

	public static void main(String[] args) {
		String BOT_TOKEN = null;

		Path path = Paths.get("settings.conf");
		if (Files.exists(path)){
			try {
				List<String> list = Files.readAllLines(path);
				BOT_TOKEN = list.get(0);
				Lang.init(list.get(1));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Config file not found.");
			System.out.println("Put the bot token and language file name in settings.conf and place it in the root directory.");
			System.exit(1);
		}

		jda = JDABuilder.createDefault(BOT_TOKEN).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
		jda.addEventListener(new DiscordAPI());
	}
}
