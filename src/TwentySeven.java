import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TwentySeven {

	public static void main(String[] args) {

		try {
			List<String> stopWords = Files.lines(Paths.get("stop-words.txt"))
					.map(line -> line.split(",")).flatMap(Arrays::stream).collect(Collectors.toList());

			Files.lines(Paths.get(args[0]))
					.flatMap(line -> Arrays.stream(line.split("[\\s,;:?._!--]+"))).map(s -> s.toLowerCase())
					.collect(Collectors.toList()).parallelStream()
					.filter(d -> stopWords.parallelStream()
							.allMatch(f -> !d.equals(f) && !d.equals("") && !d.equals("s")))
					.collect(Collectors.toMap(w -> w, w -> 1, Integer::sum)).entrySet().stream()
					.sorted((element1, element2) -> Integer.compare(element2.getValue(), element1.getValue())).limit(25)
					.forEach(element -> System.out.println(element.getKey() + "-" + element.getValue()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
