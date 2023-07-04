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

		Path path = Paths.get("BOT_TOKEN.token");
		if (Files.exists(path)){
			try {
				List<String> list = Files.readAllLines(path);
				BOT_TOKEN = list.get(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("ボットトークンが見つかりませんでした");
			System.out.println("BOT_TOKEN.tokenにボットトークンだけを記載してルートディレクトリに配置してください");
			System.exit(1);
		}

		jda = JDABuilder.createDefault(BOT_TOKEN).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
		jda.addEventListener(new DiscordAPI());
	}
}
