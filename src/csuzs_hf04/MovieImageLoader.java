package csuzs_hf04;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MovieImageLoader extends Task<Void>{

	
	
	private Button b;
	String imageLink;
	ImageView imageview;
	MovieEntry movieentry;
	public  MovieImageLoader(MovieEntry movieentry,Button b,String imageLink) {
		this.b = b;
		this.imageLink=imageLink;
		this.movieentry = movieentry;
	}
	@Override
	protected Void call() throws Exception {
		
		Image image = new Image(imageLink);
		imageview = new ImageView(image);
		movieentry.setPoster(image);
		return null;
	}
	public void succeeded(){
		
		b.setText("");
		b.setGraphic(imageview);
	}

}
