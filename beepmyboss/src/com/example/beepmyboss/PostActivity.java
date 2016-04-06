package com.example.beepmyboss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.androidhive.R;
import com.example.beepmyboss.helper.AlertDialogManager;
import com.example.beepmyboss.helper.ConnectionDetector;
import com.example.beepmyboss.helper.JSONParser;

public class PostActivity extends ListActivity {
	// Connection detector
	ConnectionDetector cd;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> albumsList;

	// albums JSONArray
	JSONArray albums = null;

	// albums JSON url
	private static final String URL_ALBUMS = "http://100.65.46.95/hello/test.php";

	// ALL JSON node names
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "username";
	private static final String TAG_SONGS_COUNT = "like_count";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		
		cd = new ConnectionDetector(getApplicationContext());
		 
        // Check for internet connection
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(PostActivity.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

		// Hashmap for ListView
		albumsList = new ArrayList<HashMap<String, String>>();

		// Loading Albums JSON in Background Thread
		new LoadAlbums().execute();
		
		// get listview
		ListView lv = getListView();
		
		/**
		 * Listview item click listener
		 * TrackListActivity will be lauched by passing album id
		 * */
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// on selecting a single album
				// TrackListActivity will be launched to show tracks inside the album
				Intent i = new Intent(getApplicationContext(), TrackListActivity.class);
				
				// send album id to tracklist activity to get list of songs under that album
				String album_id = ((TextView) view.findViewById(R.id.album_id)).getText().toString();
				i.putExtra("album_id", album_id);				
				
				startActivity(i);
			}
		});		
	}

	/**
	 * Background Async Task to Load all Albums by making http request
	 * */
	class LoadAlbums extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(PostActivity.this);
			pDialog.setMessage("Listing Albums ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Albums JSON
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			String json = jsonParser.makeHttpRequest(URL_ALBUMS, "GET",
					params);

			// Check your log cat for JSON reponse
			Log.d("Albums JSON: ", "> " + json);

			try {				
				albums = new JSONArray(json);
				
				if (albums != null) {
					// looping through All albums
					for (int i = 0; i < albums.length(); i++) {
						JSONObject c = albums.getJSONObject(i);

						// Storing each json item values in variable
						String id = c.getString(TAG_ID);
						String name = c.getString(TAG_NAME);
						String songs_count = c.getString(TAG_SONGS_COUNT);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_NAME, name);
						map.put(TAG_SONGS_COUNT, songs_count);

						// adding HashList to ArrayList
						albumsList.add(map);
					}
				}else{
					Log.d("Albums: ", "null");
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all albums
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							PostActivity.this, albumsList,
							R.layout.list_row, new String[] { TAG_ID,
									TAG_NAME, TAG_SONGS_COUNT }, new int[] {
									R.id.title, R.id.artist, R.id.duration });
					
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}