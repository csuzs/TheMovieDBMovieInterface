package csuzs_hf04;

import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import javafx.scene.control.Button;
import org.json.simple.parser.JSONParser;

//https://www.youtube.com/watch?v=

public class JSONReader {
	static String apiKey = "?api_key=448e905e9a70a7a240912525ba61f8d8";
	static String siteName = "http://api.themoviedb.org/3/movie/";
	String popular_raw = "";
	String toprated_raw = "";
	ArrayList<Button>buttons;
	ArrayList<MovieEntry> movies;
	boolean sortBy;
	
	public JSONReader(ArrayList<Button>buttons,boolean sortBy,ArrayList<MovieEntry> movies) {
		this.sortBy = sortBy;
		this.buttons=buttons;
		this.movies=movies;
	}
	
	

	public void getsortedmoviesfromTheDB() {

		String sort;
		if (sortBy == true) {
			sort = "popular";
		} else {
			sort = "top_rated";
		}
		String rawText = new URLParser().getRawURLText(siteName + sort + apiKey);
		if (sortBy) {
			popular_raw += rawText;
		} else {
			toprated_raw += rawText;
		}
		JSONParser parser = new JSONParser();
		
		JSONObject movie;
		try {
			movie = (JSONObject) parser.parse(rawText);

			JSONArray entries = (JSONArray) movie.get("results");

			for (int i = 0; i < entries.size(); i++) {
				JSONObject entry = (JSONObject) entries.get(i);
				long id =(long) entry.get("id");
				String vote_average = String.valueOf(entry.get("vote_average"));
				
				MovieEntry e = new MovieEntry("http://image.tmdb.org/t/p/w92" + (String) entry.get("poster_path"),
						(String) entry.get("title"),
						(String) entry.get("overview"),
						(String) entry.get("release_date"),
						vote_average,
						Long.toString(id));
				Thread buttonImageSetter = new Thread(new MovieImageLoader(e, buttons.get(i), e.getPoster_path()));
				buttonImageSetter.start();
				
				
				movies.add(e);
				buttons.get(i).setDisable(false);
			}

		} catch (ParseException e1) {

			e1.printStackTrace();
		}

	}
	

}
