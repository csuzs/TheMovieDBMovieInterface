package csuzs_hf04;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

public class URLParser {
	@SuppressWarnings("finally")
	public String getRawURLText(String link) {
		String rawText = "";
		URL url;
		try {
			url = new URL(link);
			URLConnection connection = url.openConnection();
			InputStream stream = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			while (br.ready()) {
				rawText += br.readLine();
			}
		} catch (MalformedURLException e) {
			// 
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			return rawText;
		}

	}

	Hyperlink makemakeLinkButton(String url) {
		Hyperlink hl = new Hyperlink("Movie Trailer:  " + url);

		hl.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		}

		);

		return hl;
	}
}
