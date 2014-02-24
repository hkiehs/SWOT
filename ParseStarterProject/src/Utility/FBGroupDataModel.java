package utility;
import java.util.List;
import com.google.gson.Gson;

public class FBGroupDataModel {
	public List<Datum> data;
	public Paging3 paging;

	public class Paging3 {
		public String previous;
		public String next;
	}

	public static FBGroupDataModel fromJson(String json) {
		return new Gson().fromJson(json, FBGroupDataModel.class);
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
}