package csuzs_hf04;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.concurrent.Task;
import javafx.scene.layout.Pane;

public class MovieLinkDownloader extends Task<Void>{
	private MovieEntry movieEntry;
	Pane root;
	public MovieLinkDownloader(MovieEntry movieentry,Pane root) {
		this.movieEntry = movieentry;
		this.root = root;
	}
	@Override
	protected Void call() throws Exception {
		movieEntry.setLinks_downloaded(true);
		String link = "http://api.themoviedb.org/3/movie/"
		+movieEntry.getId()
		+"/videos?api_key=448e905e9a70a7a240912525ba61f8d8";
		String rawText = new URLParser().getRawURLText(link);
		
		JSONParser parser = new JSONParser();

		JSONObject movieLinks;
		
			movieLinks = (JSONObject) parser.parse(rawText);
			JSONArray entries = (JSONArray) movieLinks.get("results");
			if(entries.size()<3){
			
				for(int i = 0; i < entries.size();i++){
					JSONObject entry = (JSONObject) entries.get(i);
					movieEntry.getVideo_link()[i] = "https://www.youtube.com/watch?v="+String.valueOf(entry.get("key")) ;
					
				}
			}else{
				for(int i = 0; i < 3;i++){
					JSONObject entry = (JSONObject) entries.get(i);
					movieEntry.getVideo_link()[i] = "https://www.youtube.com/watch?v="+String.valueOf(entry.get("key"));
					
				}
			}
			
		return null;
	}
	public void succeeded(){
		URLParser urlparser = new URLParser();
		for(String s : movieEntry.getVideo_link()){
			if(s!=null)root.getChildren().add(urlparser.makemakeLinkButton(s));
		}
	}
	

}
