package csuzs_hf04;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MovieApp extends Application {
	private Stage stage;
	private MenuBar menubar;
	private Menu sort_by;
	private MenuItem menu_item_popularity, menu_item_top_rated;
	private VBox root;
	private GridPane gridpane;
	private ArrayList<Button> buttons = new ArrayList<>();
	private ArrayList<MovieEntry> most_popular_movies = new ArrayList<>();
	private ArrayList<MovieEntry> top_rated_movies = new ArrayList<>();
	private ScrollPane scrollpane;
	boolean most_pop_alreadyLoaded = false;
	boolean top_rated_alreadyLoaded = false;
	boolean buttons_loaded = false;
	boolean whichsort;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.setWidth(500);
		primaryStage.setMaxWidth(500);
		root = new VBox();
		
		root.setMaxWidth(600);
		menubar = new MenuBar();
		sort_by = new Menu("Sort By");
		menu_item_popularity = new MenuItem("Popularity");
		menu_item_top_rated = new MenuItem("Rating");
		menu_item_popularity.setOnAction(e -> loadMovies(menu_item_popularity));
		menu_item_top_rated.setOnAction(e -> loadMovies(menu_item_top_rated));
		menubar.getMenus().addAll(sort_by);

		sort_by.getItems().addAll(menu_item_popularity, menu_item_top_rated);

		root.getChildren().add(menubar);
		Scene scene = new Scene(root, 400, 700);
		primaryStage.setTitle("Movieshower");
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		

		for (int i = 0; i < buttons.size(); i++) {
			int index = i;
			buttons.get(i).setOnAction(e -> openMovieProperties(whichsort, index));

		}

		primaryStage.show();
	}

	private void createnewGridPane() {
		scrollpane = new ScrollPane();
		scrollpane.resize(stage.getMaxWidth(), stage.getMaxHeight());
		gridpane = new GridPane();
		VBox.setVgrow(gridpane, Priority.ALWAYS);
		gridpane.setAlignment(Pos.BASELINE_CENTER);
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setPercentWidth(25);
		columnConstraints.setHgrow(Priority.ALWAYS);

		RowConstraints rowConstraints = new RowConstraints();
		rowConstraints.setPercentHeight(20);
		rowConstraints.setVgrow(Priority.ALWAYS);
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 4; ++j) {
				Button button = new Button();
				button.setDisable(true);
				buttons.add(button);

				button.setMaxSize((root.getWidth() / 4) - 5, root.getHeight() / 5);
				button.setPrefSize(root.getWidth() / 4 - 10, root.getHeight() / 5);
				// gridpane.setHgrow(button, Priority.ALWAYS);
				// gridpane.setVgrow(button, Priority.ALWAYS);
				gridpane.add(button, j, i);

			}
			gridpane.getRowConstraints().add(rowConstraints);
		}
		for (int i = 0; i < 4; i++) {
			gridpane.getColumnConstraints().add(columnConstraints);
		}
		scrollpane.setContent(gridpane);
		root.getChildren().add(scrollpane);
		for (int i = 0; i < buttons.size(); i++) {
			Integer index = i;
			buttons.get(i).setOnAction(e -> openMovieProperties(whichsort, index));
		}
	}

	private void loadMovies(MenuItem m) {
		if (!buttons_loaded) {
			createnewGridPane();
			buttons_loaded = true;
			setButtonText("...");

		}
		if (m == menu_item_popularity) {
			if (most_pop_alreadyLoaded == false) {
				Thread th = new Thread(new ContentDownloader(most_popular_movies, buttons, true));
				th.start();

				whichsort = true;
				most_pop_alreadyLoaded = true;
			}
			if (whichsort != true) {
				whichsort = true;
				setImagesonButtons(most_popular_movies);
			}

		} else if (m == menu_item_top_rated) {

			if (top_rated_alreadyLoaded == false) {
				Thread th = new Thread(new ContentDownloader(top_rated_movies, buttons, false));
				th.start();
				setButtonText("...");
				whichsort = false;
				top_rated_alreadyLoaded = true;

			}
			if (whichsort != false) {
				whichsort = false;
				setImagesonButtons(top_rated_movies);
			}
		}

	}

	void setButtonText(String text) {
		for (Button b : buttons) {
			b.setWrapText(true);
			b.setText(text);
		}
	}

	private void setImagesonButtons(ArrayList<MovieEntry> movies) {

		for (int i = 0; i < buttons.size(); i++) {
			ImageView iv = new ImageView();
			iv.setImage(movies.get(i).getPoster());
			buttons.get(i).setGraphic(iv);
		}

	}

	private void openMovieProperties(boolean whichsort, int index) {
		MovieEntry clickedMovie;
		if (whichsort == true) {
			clickedMovie = most_popular_movies.get(index);
		} else {
			clickedMovie = top_rated_movies.get(index);
		}

		Stage stage = new Stage();

		VBox root2 = new VBox();
		boolean inCase = clickedMovie.isLinks_downloaded();
		if (clickedMovie.isLinks_downloaded() == false) {
			Thread th = new Thread(new MovieLinkDownloader(clickedMovie, root2));
			th.start();
		}
		// title
		stage.setTitle(clickedMovie.getTitle());
		Label title = new Label(clickedMovie.getTitle());
		title.setFont(new Font("Arial", 30));
		title.setAlignment(Pos.CENTER);
		title.setWrapText(true);
		root2.getChildren().add(title);

		HBox hbox = new HBox();
		hbox.getChildren().add(new ImageView(clickedMovie.getPoster()));

		VBox nextToImage = new VBox();

		Label overview = new Label("Overview: " + clickedMovie.getOverview());
		overview.setWrapText(true);
		nextToImage.getChildren().addAll(overview, new Label("Rating: " + clickedMovie.getVote_average()),
				new Label("Release date: " + clickedMovie.getReleaseDate()));
		hbox.getChildren().add(nextToImage);
		root2.getChildren().add(hbox);
		if (inCase) {
			URLParser urlparser = new URLParser();
			for (String s : clickedMovie.getVideo_link()) {
				root2.getChildren().add(urlparser.makemakeLinkButton(s));
			}
		}
		Scene scene = new Scene(root2, 500, 500);
		stage.setScene(scene);

		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
