package utility;
import com.google.gson.Gson;

public class Restaurant {
	public String name;
	public String address;
	public String type;
	public String timing;
	public String phoneNumber;
	public double latitude;
	public double longitude;
	public int rating;
	public int reviews;
	public int likes;
	public int range;
	public String highights;

	public String toJson() {
		return new Gson().toJson(this);
	}
}