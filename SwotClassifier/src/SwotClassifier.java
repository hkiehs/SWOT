import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.classifier4J.ClassifierException;
import net.sf.classifier4J.SimpleClassifier;

public class SwotClassifier {

	public static void main(String[] args) {
		CategorisedDatum finalData = new CategorisedDatum();
		RestaurantList restaurantList = null;

		for (int i = 1; i <= 1030; i++) {
			String json = "";
			try {
				System.out.println("Reading file[" + i + "]");
				json = readFileAsString("input/output" + i + ".txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			CategorisedDatum categorisedDatum = CategorisedDatum.fromJson(json);

			finalData.datums.addAll(categorisedDatum.datums);

			try {
				String restaurantListJson = readFileAsString("input/restaurantList.txt");
				restaurantList = RestaurantList.fromJson(restaurantListJson);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		List<Datum> data = new ArrayList<Datum>(finalData.datums);

		CategorisedDatum categorisedDatum = null;
		for (String restaurantName : restaurantList.restaurants) {
			SimpleClassifier classifier = new SimpleClassifier();
			classifier.setSearchWord(restaurantName);
			categorisedDatum = new CategorisedDatum();

			for (Datum datum : data) {
				try {
					if (classifier.isMatch(datum.message)) {
						datum.restaurantName = restaurantName;
						categorisedDatum.datums.add(datum);
						finalData.datums.remove(datum);
					}
				} catch (ClassifierException e) {
					e.printStackTrace();
				}
			}

			System.out.println("Remaining Size[" + finalData.datums.size()
					+ "]");
			System.out.println("Number of matches ["
					+ categorisedDatum.datums.size()
					+ "] for restaurant name [" + restaurantName + "]");

			outputToFile(restaurantName, categorisedDatum);
			categorisedDatum = null;
		}
		outputToFile("__Remaining__Data__", finalData);
	}

	private static String readFileAsString(String filename) throws IOException {
		File file = new File(filename);
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		return new String(data, "UTF-8");
	}

	private static void outputToFile(String filename,
			CategorisedDatum categorisedDatum) {
		try {
			FileWriter writer = new FileWriter("output/" + filename + ".txt");
			writer.append(categorisedDatum.toJson());
			writer.append('\n');
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}