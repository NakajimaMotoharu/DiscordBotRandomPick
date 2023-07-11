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
		if (str.equals(Lang.command[0])) {
			outMessage(msg, Lang.text[0]);
			mode = 1;
		} else if (str.equals(Lang.command[1])) {
			if (RandomPick.file == null) {
				outMessage(msg, Lang.text[1]);
			} else {
				outMessage(msg, Lang.text[2]);
				mode = 2;
			}
		} else if (str.equals(Lang.command[2])) {
			String[] list = RandomPick.printAll();
			if (list == null) {
				outMessage(msg, Lang.text[3]);
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
		} else if (str.equals(Lang.command[3])) {
			outMessage(msg, Objects.requireNonNullElse(RandomPick.file, Lang.text[4]));
		} else if (str.equals(Lang.command[4])) {
			outMessage(msg, Lang.getHelp());
		} else if (str.equals(Lang.command[5])) {
			outMessage(msg, Lang.text[5]);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			System.exit(0);
		} else if (str.equals(Lang.command[6])) {
			File dir = new File("Text");
			File[] files = dir.listFiles();
			String ans = "", tmp;
			for (int i = 0; i < Objects.requireNonNull(files).length; i = i + 1) {
				if (files[i].toString().contains(".txt")) {
					if (i + 1 == files.length) {
						tmp = ans + files[i].toString().replace("Text", "").replace(".txt", "");
					} else {
						tmp = ans + files[i].toString().replace("Text", "").replace(".txt", "") + "\n";
					}
					ans = tmp;
				}
			}
			outMessage(msg, ans);
		} else if (str.equals(Lang.command[7])) {
			String[] list = RandomPick.randomPick(1);
			outMessage(msg, Objects.requireNonNull(list)[0]);
			mode = 0;
		}
	}

	public void fileInputMode(String str, Message msg) {
		RandomPick.init(str);
		if (RandomPick.canUse) {
			outMessage(msg, Lang.text[6]);
		} else {
			outMessage(msg, Lang.text[7]);
		}
		mode = 0;
	}

	public void integerInputMode(String str, Message msg) {
		if (!str.matches("[+-]?\\d*(\\.\\d+)?")) {
			outMessage(msg, Lang.text[8]);
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
			outMessage(msg, Lang.text[9]);
		}
		mode = 0;
	}

	public void outMessage(Message e, String str) {
		e.replyFormat(str).queue();
	}
}
