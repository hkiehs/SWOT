import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sf.classifier4J.ClassifierException;
import net.sf.classifier4J.SimpleClassifier;

public class SwotClassifier {

	public static void main(String[] args) {
		CategorisedDatum finalData = new CategorisedDatum();
		RestaurantList restaurantList;
		
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

		CategorisedDatum categorisedDatum = new CategorisedDatum();
		while (finalData.datums.size() != 0) {
			System.out.println("Message[" + finalData.datums.get(0).message + "]");
			SimpleClassifier classifier = new SimpleClassifier();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter restaurant name : ");
			String restaurantName = "";
			try {
				restaurantName = br.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			classifier.setSearchWord(restaurantName);

			for (Datum datum : finalData.datums) {
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
			System.out.println("Array Size[" + finalData.datums.size() + "]");
			System.out.println("Number of matches ["
					+ categorisedDatum.datums.size()
					+ "] for restaurant name [" + restaurantName + "]");
		}

		outputToFile(categorisedDatum);
	}
	// public static void main(String[] args) {
	// SimpleClassifier classifier = new SimpleClassifier();
	// String restaurantName = "java";
	// classifier.setSearchWord(restaurantName);
	//
	// CategorisedDatum finalData = new CategorisedDatum();
	// for (int i = 1; i <= 1030; i++) {
	// String json = "";
	// try {
	// System.out.println("Reading file[" + i + "]");
	// json = readFileAsString("output" + i + ".txt");
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// CategorisedDatum categorisedDatum = CategorisedDatum.fromJson(json);
	// for (Datum datum : categorisedDatum.datums) {
	// try {
	// if (classifier.isMatch(datum.message)) {
	// datum.restaurantName = restaurantName;
	// finalData.datums.add(datum);
	// }
	// } catch (ClassifierException e) {
	// e.printStackTrace();
	// }
	// }
	// outputToFile(finalData);
	// System.out.println("Number of matches [" + finalData.datums.size()
	// + "] for restaurant name [" + restaurantName + "]");
	// finalData.datums.clear();
	// }
	// }

	private static String readFileAsString(String filename) throws IOException {
		File file = new File(filename);
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		return new String(data, "UTF-8");
	}

	private static void outputToFile(CategorisedDatum categorisedDatum) {
		try {
			FileWriter writer = new FileWriter("categorisedOutput.txt");
			writer.append(categorisedDatum.toJson());
			writer.append('\n');
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}