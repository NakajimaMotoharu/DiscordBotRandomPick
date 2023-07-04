import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Objects;

public class DiscordAPI extends ListenerAdapter {
	public int mode = 0;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (!e.getAuthor().equals(Main.jda.getSelfUser())) {
			String str = e.getMessage().getContentStripped();
			if (mode == 0) {
				switch (str) {
					case "ロード" -> {
						e.getChannel().sendMessage("ファイル名を入力してください").queue();
						mode = 1;
					}
					case "ピック" -> {
						if (RandomPick.file == null) {
							e.getChannel().sendMessage("ファイルが読み込まれていません").queue();
						} else {
							e.getChannel().sendMessage("ピック数を入力してください").queue();
							mode = 2;
						}
					}
					case "リスト" -> {
						String[] list = RandomPick.printAll();
						if (list == null) {
							e.getChannel().sendMessage("ファイルが読み込まれていません").queue();
						} else {
							String all = "", tmp;
							for (int i = 0; i < list.length; i = i + 1) {
								if (i + 1 == list.length) {
									tmp = all + list[i];
								} else {
									tmp = all + list[i] + "\n";
								}
								all = tmp;
							}
							e.getChannel().sendMessage(all).queue();
						}
					}
					case "ファイル名" ->
							e.getChannel().sendMessage(Objects.requireNonNullElse(RandomPick.file, "ファイルが読み込まれていません")).queue();
					case "ヘルプ" -> e.getChannel().sendMessage("""
							以下がコマンドリストになります
							ロード: ファイルを読み込みます
							ファイル一覧: 読み込めるファイルの一覧を表示します
							リスト: 読み込まれているファイルの中身を表示します
							ファイル名: 読み込まれているファイル名を表示します
							ピック: 指定数ランダムにピックします
							ヘルプ: コマンドリストを表示します
							終了: ボットを終了します""").queue();
					case "終了" -> {
						e.getChannel().sendMessage("さようなら").queue();
						System.exit(0);
					}
					case "ファイル一覧" -> {
						File dir = new File("./");
						File[] files = dir.listFiles();
						String ans = "", tmp;
						for (int i = 0; i < Objects.requireNonNull(files).length; i = i + 1) {
							if (files[i].toString().contains(".txt")) {
								if (i + 1 == files.length) {
									tmp = ans + files[i].toString().replace(".\\", "").replace(".txt", "");
								} else {
									tmp = ans + files[i].toString().replace(".\\", "").replace(".txt", "") + "\n";
								}
								ans = tmp;
							}
						}
						e.getChannel().sendMessage(ans).queue();
					}
					default -> e.getChannel().sendMessage("不明な命令です").queue();
				}
			} else if (mode == 1) {
				RandomPick.init(str);
				if (RandomPick.canUse) {
					e.getChannel().sendMessage("ファイルを読み込みました").queue();
				} else {
					e.getChannel().sendMessage("ファイルを読み込めませんでした").queue();
				}
				mode = 0;
			} else if (mode == 2) {
				if ((Integer.parseInt(str) <= RandomPick.names.length) && (Integer.parseInt(str) > 0)) {
					String[] list = RandomPick.randomPick(Integer.parseInt(str));
					String all = "", tmp;
					for (int i = 0; i < Objects.requireNonNull(list).length; i = i + 1) {
						if (i + 1 == list.length) {
							tmp = all + list[i];
						} else {
							tmp = all + list[i] + "\n";
						}
						all = tmp;
					}
					e.getChannel().sendMessage(all).queue();
				} else {
					e.getChannel().sendMessage("ピック数が不正です").queue();
				}
				mode = 0;
			}
		}
	}
}
