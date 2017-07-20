package csuzs_hf04;



import javafx.scene.image.Image;

public class MovieEntry {
	private String poster_path;
	private String title;
	private String overview;
	private String vote_average;
	private String releaseDate;
	private Image poster;
	private String id;
	private String[] video_link = new String[3];
	
	private boolean links_downloaded = false;

	
	
	public String getPoster_path() {
		return poster_path;
	}
	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Image getPoster() {
		return poster;
	}
	
	public String[] getVideo_link() {
		return video_link;
	}
	public void setVideo_link(String[] video_link) {
		this.video_link = video_link;
	}
	
	public boolean isLinks_downloaded() {
		return links_downloaded;
	}
	public void setLinks_downloaded(boolean links_downloaded) {
		this.links_downloaded = links_downloaded;
	}
	
	public MovieEntry(String poster_path,String title,String overview,String releaseDate,String vote_average2,String id){
		this.poster_path = poster_path;
		this.title = title;
		this.overview = overview;
		this.releaseDate = releaseDate;
		this.vote_average = (String)vote_average2;
		this.id =id;
		
	}
	public void setPoster(Image image){
		poster = image;
	}
	public String getVote_average() {
		return vote_average;
	}
	
	public void setVote_average(String vote_average) {
		this.vote_average = vote_average;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
