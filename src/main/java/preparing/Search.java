package preparing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * TODO Add some meaningful class description...
 */
public class Search {

	private static final String CHARSET = "UTF-8";

	private static final String[] HH = {"<h1", "<h2", "<h3", "<h4", "<h5", "<h6"};

	private static final int LIMIT = 50;

	private final String agent; // Change this to your company's name and bot

	private final String api;

	private final String filter;

	private String site = "";

	private boolean exact = false;

	private Set<String> keywords = new LinkedHashSet<>();

	public Search(Engine engine, String agent) {
		Objects.requireNonNull(engine);
		Objects.requireNonNull(agent);
		agent = agent.trim();
		if (agent.isEmpty()) {
			throw new IllegalArgumentException("'agent' is empty");
		}

		this.agent = agent;
		this.api = engine.getApi();
		this.filter = engine.getFilter();
	}

	public static String[] asBlocks(String content) {
		Objects.requireNonNull(content);

		content = content.replaceAll("<!DOCTYPE.*?>|<script.*?>.*?</script>|<style.*?>.*?</style>|<!--.*?-->/s", "");
		content = content.replaceAll("<span.*?>(.*?)</span>", "$1");
		content = content.replaceAll("<div.*?>(.*?)</div>", "$1\n");
		content = content.replaceAll("<img .*?alt=[\"']?([^\"']*)[\"']?.*?/?>", "'$1'");
		content = content.replaceAll("<a .*?href=[\"']?([^\"']*)[\"']?.*?>(.*?)</a>", "$2"); // "$2 [$1]");
		content = content.replaceAll("<(/title|/th|/tr|/td|/li|/p|/div|/h\\d|br)\\w?/?>", "\n\n");
		content = content.replaceAll("<[A-Za-z/][^<>]*>", "");
		content = content.replaceAll("&#160;|&nbsp;|\t", " ");
		content = content.replaceAll("\\}", "} ")
				.replaceAll("\\} ,", "},").replaceAll("\\} ;", "};")
				.replaceAll("\\} :", "}:").replaceAll("\\} \\.", "}.");
		content = content.replaceAll("\\]", "] ")
				.replaceAll("\\] ,", "],").replaceAll("\\] ;", "];")
				.replaceAll("\\] :", "]:").replaceAll("\\] \\.", "].");
		content = content.replaceAll("\\)", ") ")
				.replaceAll("\\) ,", "),").replaceAll("\\) ;", ");")
				.replaceAll("\\) :", "):").replaceAll("\\) \\.", ").");

		int count = 0;
		String[] lines = content.split("\n");
		for (int i = 0; i < lines.length; i++) {
			lines[i] = lines[i].trim();
			if (lines[i].length() > LIMIT) {
				count += 1;
			}
		}
		content = String.join("\n", lines);

		while (true) {
			String temp;
			if (count > LIMIT) {
				temp = content.replaceAll("\n\n\n", "\n\n").replaceAll("  ", " ");
			} else {
				temp = content.replaceAll("\n\n", "\n").replaceAll("  ", " ");
			}
			if (temp.equals(content))
				break;
			content = temp;
		}

		if (count > LIMIT) {
			return content.split("\n\n");
		} else {
			return Collections.singleton(content).toArray(new String[1]);
		}
	}

	public static String[] download(URL url) {
		Objects.requireNonNull(url);

		InputStream stream = null;
		InputStreamReader reader = null;
		BufferedReader buffered = null;
		List<String> result = new ArrayList<>();
		try {
			stream = url.openStream();
			reader = new InputStreamReader(stream);
			buffered = new BufferedReader(reader);
			for (String line = buffered.readLine(); line != null; line = buffered.readLine()) {
				result.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != buffered) {
					buffered.close();
				}
				if (null != reader) {
					reader.close();
				}
				if (null != stream) {
					stream.close();
				}
			} catch (IOException ignored) {
			}
		}
		return result.toArray(new String[result.size()]);
	}

	public static String[] incipit(URL url) {
		Objects.requireNonNull(url);

		try {
			Document document = Jsoup.parse(url, 0);
			Elements elements = document.select("p");
			if (elements.isEmpty()) {
				return null;
			}
			String result = "";
			Element paragraph = elements.get(0);
			while (null != paragraph && paragraph.tag().toString().equals("p")) {
				result += paragraph.toString();
				paragraph = paragraph.nextElementSibling();
			}
			return asBlocks(result);
		} catch (IOException e) {
			return null;
		}
	}

	private static int indexOfFirstH(String content) {
		Objects.requireNonNull(content);

		int result = Integer.MAX_VALUE;
		for (String hh : HH) {
			int section = content.indexOf(hh, 1);
			if (section > 0) {
				result = Integer.min(section, result);
			}
		}
		if (Integer.MAX_VALUE == result) {
			result = -1;
		}
		return result;
	}

	public static String[] toSections(String[] lines) {
		Objects.requireNonNull(lines);

		for (int i = 0; i < lines.length; i++)
			lines[i] = lines[i].trim();
		String content = String.join("", lines);
//		try {
//			OutputStream stream = Files.newOutputStream(Paths.get("file.txt"));
//			stream.write(content.getBytes());
//			stream.close();
//		} catch (Exception e) {
//		}

		List<String> blocks = new ArrayList<>();
		for (int pos = indexOfFirstH(content); pos > 0; pos = indexOfFirstH(content)) {
			String section = content.substring(0, pos);
			content = content.substring(pos);
			Collections.addAll(blocks, asBlocks(section));
		}
		if (!content.isEmpty()) {
			Collections.addAll(blocks, asBlocks(content));
		}
		return blocks.toArray(new String[blocks.size()]);
	}

	public Search setExact(boolean exact) {
		this.exact = exact;
		return this;
	}

	public Search add(String keyword) {
		Objects.requireNonNull(keyword);

		keyword = keyword.trim().toLowerCase();
		if (!keyword.isEmpty()) {
			if (keyword.contains(" ")) {
				keyword = String.format("\"%s\"", keyword);
			}
			keywords.add(keyword);
		}
		return this;
	}

	public Search addAll(Collection<String> keywords) {
		Objects.requireNonNull(keywords);

		for (String keyword : keywords) {
			add(keyword);
		}
		return this;
	}

	public Search addAll(String... keyword) {
		Objects.requireNonNull(keyword);

		for (String current : keyword) {
			add(current);
		}
		return this;
	}

	public Search clearWords() {
		keywords.clear();
		return this;
	}

	public Collection<URL> execute() {
		Set<URL> result = new LinkedHashSet<>();
		if (!keywords.isEmpty()) {
			String pattern = "";
			Iterator<String> iterator = keywords.iterator();
			if (iterator.hasNext()) {
				pattern = "\"" + iterator.next() + "\"";
				while (iterator.hasNext()) {
					pattern += (exact ? " + " : "") + "\"" + iterator.next() + "\"";
				}
			}
			if (!site.isEmpty()) {
				pattern += " site:" + site;
			}
			try {
				String address = api + URLEncoder.encode(pattern, CHARSET);
				Document document = Jsoup.connect(address).userAgent(agent).get();
				Elements links = document.select(filter);
				for (Element link : links) {
					String url = link.absUrl("href");
					// Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
//					url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");
					if (url.startsWith("http")) {
						result.add(new URL(url));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Search restrictTo(String site) {
		Objects.requireNonNull(site);

		this.site = site.trim();
		return this;
	}

}
