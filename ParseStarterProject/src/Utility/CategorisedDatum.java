package Utility;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class CategorisedDatum {
	public List<Datum> datums;

	public CategorisedDatum() {
		datums = new ArrayList<Datum>();
	}

	public static CategorisedDatum fromJson(String json) {
		return new Gson().fromJson(json, CategorisedDatum.class);
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
}