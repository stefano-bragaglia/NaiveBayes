package preparing;

/**
 * TODO Add some meaningful class description...
 */
public enum Engine {

	GOOGLE("http://www.google.com/search?q=", "li.g>h3>a"),
	DUCK_DUCK_GO("https://duckduckgo.com/html/?q=", "div.links_main>a");

	private final String api;
	private final String filter;

	private Engine(String api, String filter) {
		this.api = api;
		this.filter = filter;
	}

	public String getApi() {
		return api;
	}

	public String getFilter() {
		return filter;
	}

}
