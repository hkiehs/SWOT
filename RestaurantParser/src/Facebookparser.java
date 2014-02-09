import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Facebookparser {

	private static FileWriter writer = null;

	public static void main(final String[] args) {
		final WebClient webClient = new WebClient();
		HtmlPage page = null;
		try {
			page = webClient
					.getPage("file://Users/macintoshuser/Documents/Workspaces/Freelance/SWOT/Htmlunit/resturauntHtml.html");
			System.out.println("Success");
		} catch (final FailingHttpStatusCodeException e) {
			System.out.println("One");
			e.printStackTrace();
		} catch (final MalformedURLException e) {
			System.out.println("Two");
			e.printStackTrace();
		} catch (final IOException e) {
			System.out.println("Three");
			e.printStackTrace();
		} catch (final Exception e) {
			System.out.println("Four");
			e.printStackTrace();
		}

		RestaurantList restaurantList = new RestaurantList();

		// get list of all divs
		final List<?> divs = page.getByXPath("//div");
		int count = page.getByXPath("//div[@class='_zs fwb']").size();
		for (int i = 0; i < count; i++) {
			final HtmlDivision div = (HtmlDivision) page.getByXPath(
					"//div[@class='_zs fwb']").get(i);
			String name = div.getTextContent();
			restaurantList.restaurants.add(name);
			System.out.println(name);
		}
		System.out.println("Finished");

		try {
			writer = new FileWriter("RestaurantList.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			try {
				writer.append(restaurantList.toJson());
				writer.append('\n');
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}