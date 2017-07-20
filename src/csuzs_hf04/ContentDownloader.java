package csuzs_hf04;

import java.util.ArrayList;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ContentDownloader extends Task<Void> {

	private ArrayList<MovieEntry> movies;
	boolean whichsort;
	private ArrayList<Button> buttons;

	public ContentDownloader(ArrayList<MovieEntry> movies, ArrayList<Button> buttons, boolean whichsort) {
		this.buttons = buttons;
		this.whichsort = whichsort;
		this.movies = movies;
	}

	@Override
	protected Void call() throws Exception {
		JSONReader jr = new JSONReader(buttons, whichsort, movies);
		jr.getsortedmoviesfromTheDB();

		return null;
	}

	protected void succeeded() {
		for (int i = 0; i < buttons.size(); i++) {
			ImageView iv = new ImageView();
			iv.setImage(movies.get(i).getPoster());
			buttons.get(i).setGraphic(iv);
		}
		for (Button b : buttons) {
			b.setText("");
		}
		System.out.println("vege");
	}

}
