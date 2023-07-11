import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class RandomPick {
	public static String file = null;
	public static String[] names = null;
	public static boolean canUse = false;

	public static void init(String filename){
		Path path = Paths.get("Text/" + filename + ".txt");
		file = null;
		names = null;
		canUse = false;

		if (!Files.exists(path)){
			return;
		}

		try {
			List<String>list = Files.readAllLines(path);
			names = list.toArray(new String[0]);
			file = filename;
			canUse = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String[] randomPick(int n){
		if (!canUse){
			return null;
		}

		String[] list = new String[n];
		int[] address = new int[n];

		Arrays.fill(address, -1);

		for (int i = 0; i < n; i = i + 1){
			int tmp;
			while (true){
				tmp = (int) (Math.random() * names.length);
				boolean frag = true;
				for (int j = 0; j < i; j = j + 1){
					if (address[j] == tmp) {
						frag = false;
						break;
					}
				}
				if (frag){
					address[i] = tmp;
					break;
				}
			}
		}

		for (int i = 0; i < n; i = i + 1){
			list[i] = names[address[i]];
		}

		return list;
	}

	public static String[] printAll(){
		if (!canUse){
			return null;
		}

		return names;
	}
}
