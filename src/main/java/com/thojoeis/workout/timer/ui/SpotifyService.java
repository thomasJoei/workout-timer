package com.thojoeis.workout.timer.ui;

import java.net.HttpURLConnection;
import java.net.URL;

public class SpotifyService {

    private final static String SPOTIFY_BASE_URL = "https://api.spotify.com/v1";
    private final static String VOLUME_ENDPOINT = "/me/player/volume";

    public static void setVolume(String accessToken, int volumePercent) {
        try {
            String volumeParam = "?volume_percent=" + volumePercent;
            URL obj = new URL(SPOTIFY_BASE_URL + VOLUME_ENDPOINT + volumeParam);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Authorization", "Bearer " + accessToken);

            con.getResponseCode();
        } catch (Exception ex) {

        }
    }
}
