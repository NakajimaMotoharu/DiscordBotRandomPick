import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Lang {
	public static int comNum = 8, txtNum = 10, hlpNum = 10;
	public static String[] command = new String[comNum];
	public static String[] text = new String[txtNum];
	public static String[] help = new String[hlpNum];
	public static void init(String filename){
		Path path = Paths.get("Languages/" + filename + ".lang");
		if (Files.exists(path)){
			try {
				List<String> list = Files.readAllLines(path);

				for (int i = 0; i < comNum; i = i + 1){
					command[i] = list.get(i);
				}
				for (int i = 0; i < txtNum; i = i + 1){
					text[i] = list.get(i + comNum);
				}
				for (int i = 0; i < hlpNum; i = i + 1){
					help[i] = list.get(i + comNum + txtNum);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Language file not found.");
			System.out.println("Put the language file in xxx.lang and place it in the Languages directory.");
			System.exit(1);
		}
	}

	public static String getHelp(){
		String ans = "", tmp;

		for (int i = 0 ; i < hlpNum; i = i + 1){
			if (i + 1 == hlpNum){
				tmp = ans + help[i];
			} else {
				tmp = ans + help[i] + "\n";
			}
			ans = tmp;
		}

		return ans;
	}
}
