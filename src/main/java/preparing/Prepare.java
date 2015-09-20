package preparing;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import example.Language;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import preparing.languages.Deutsch;
import preparing.languages.Espanol;
import preparing.languages.Francoise;
import preparing.languages.Italiano;

/**
 * TODO Add some meaningful class description...
 */
public class Prepare {

	private static final String LINK = "http://tools.wmflabs.org/magnustools/randomarticle" +
			".php?lang=en&project=wikipedia&categories=Featured+articles&d=0";

	public static void main(String[] args) throws IOException {
		for (Language language : Language.values()) {
			System.out.println("Current language: " + language);
			String id = language.toString().toLowerCase();
			List<String> list = new ArrayList<>(
					language == Language.DE ? Deutsch.LINKS :
							language == Language.EN ? Collections.emptyList() :
									language == Language.ES ? Espanol.LINKS :
											language == Language.FR ? Francoise.LINKS : Italiano.LINKS
			);
			Collections.shuffle(list);
			for (int i = 0; i < 350; i++) {
				String link;
				if (language == Language.EN) {
					URL url = new URL(LINK);
					String reply = String.join("", Search.download(url));
					int start = reply.indexOf("url=") + "url=".length();
					int end = reply.indexOf("'>", start);
					link = "https:" + reply.substring(start, end);
				} else {
					link = list.get(i);
				}
				Document document = Jsoup.parse(new URL(link), 0);
				Elements elements = document.select("body");
				Element element = elements.get(0);
				String[] lines;
				lines = Search.asBlocks(element.html());
				lines = String.join("     ", lines).trim().split("     ");
				String content = String.join("\n", lines);
				String name = link.substring(5 + link.indexOf("wiki/")).trim()
						.replaceAll(" ", "_").replaceAll("///", "_").replaceAll("-", "_")
						.replaceAll("__", "_").replaceAll("__", "_").replaceAll("__", "_");
				Path path = Paths.get(
						i < 10 ? String.format("./data/training/%s/%s.txt", id, name) :
								i < 300 ? String.format("./data/control/%s/%s.txt", id, name) :
										String.format("./data/random/%s_%s.txt", id, name)
				);
				Files.createDirectories(path.getParent());
				Files.write(path, content.getBytes());
				if ((1 + i) % 50 == 0) {
					System.out.println((1 + i) + " articles saved!");
				}
			}
			System.out.println();
		}
		System.out.println("Done.");
	}

}
