package com.example.android.threadsample;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VideoFragment extends Fragment {

	private ListView mListView;
	
	String[] values = new String[] { "video1", 
            "video2"
           };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.video_list, container, false);
		
		mListView = (ListView) view.findViewById(R.id.menu_listview);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
	              android.R.layout.simple_list_item_1, android.R.id.text1, values);
		
		mListView.setAdapter(adapter);
		
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
               int position, long id) {
	            // ListView Clicked item value
	            String  itemValue    = (String) mListView.getItemAtPosition(position);
	             
	            String url = itemValue;
	             
	            Intent intent = new Intent(VideoFragment.this.getActivity(), VideoActivity.class);
	     	    intent.putExtra(Constants.VIDEO_LOCAL, 4);
	     	    intent.putExtra(Constants.VIDEO_URL, url);
	     	    startActivity(intent);
            }

		}); 
		return view;
	}
	
	/*
     * This callback is invoked when the Fragment is being destroyed.
     */
    @Override
    public void onDestroyView() {
        
        // Sets variables to null, to avoid memory leaks
        mListView = null;

        // Always call the super method last
        super.onDestroyView();
    }
}
