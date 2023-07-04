import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Objects;

public class DiscordAPI extends ListenerAdapter {
	public int mode = 0;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getAuthor().equals(Main.jda.getSelfUser())) {
			return;
		}

		String str = e.getMessage().getContentStripped();
		Message msg = e.getMessage();

		switch (mode) {
			case 0 -> defaultMode(str, msg);
			case 1 -> fileInputMode(str, msg);
			case 2 -> integerInputMode(str, msg);
		}

	}

	public void defaultMode(String str, Message msg) {
		switch (str) {
			case "!ロード" -> {
				outMessage(msg, "ファイル名を入力してください");
				mode = 1;
			}
			case "!ピック" -> {
				if (RandomPick.file == null) {
					outMessage(msg, "ファイルが読み込まれていません");
				} else {
					outMessage(msg, "ピック数を入力してください");
					mode = 2;
				}
			}
			case "!リスト" -> {
				String[] list = RandomPick.printAll();
				if (list == null) {
					outMessage(msg, "ファイルが読み込まれていません");
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
					outMessage(msg, all);
				}
			}
			case "!ファイル名" -> outMessage(msg, Objects.requireNonNullElse(RandomPick.file, "ファイルが読み込まれていません"));
			case "!ヘルプ" -> outMessage(msg, """
					以下がコマンドリストになります
					"!コマンド"で実行です
					ロード: ファイルを読み込みます
					ファイル一覧: 読み込めるファイルの一覧を表示します
					リスト: 読み込まれているファイルの中身を表示します
					ファイル名: 読み込まれているファイル名を表示します
					ピック: 指定数ランダムにピックします
					ヘルプ: コマンドリストを表示します
					終了: ボットを終了します""");
			case "!終了" -> {
				outMessage(msg, "さようなら");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
			case "!ファイル一覧" -> {
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
				outMessage(msg, ans);
			}
		}
	}

	public void fileInputMode(String str, Message msg) {
		RandomPick.init(str);
		if (RandomPick.canUse) {
			outMessage(msg, "ファイルを読み込みました");
		} else {
			outMessage(msg, "ファイルを読み込めませんでした");
		}
		mode = 0;
	}

	public void integerInputMode(String str, Message msg) {
		if (!str.matches("[+-]?\\d*(\\.\\d+)?")) {
			outMessage(msg, "数値ではありません");
		} else if ((Integer.parseInt(str) <= RandomPick.names.length) && (Integer.parseInt(str) > 0)) {
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
			outMessage(msg, all);
		} else {
			outMessage(msg, "ピック数が不正です");
		}
		mode = 0;
	}

	public void outMessage(Message e, String str) {
		e.replyFormat(str).queue();
	}
}
