package com.mcm.menuandnotification;

import java.io.File;
import java.io.FileInputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcm.R;
import com.mcm.SplashActivity;

public class Rotas extends Fragment{
	
	Context context;
	int clientID;
	String folderName;
	ImageView notifi_fragement_background;
	TextView tv_text_one,tv_text_two,tv_text_three,tv_title;
	
	LinearLayout layout_one, layout_two, layout_three;
	public Rotas (Context context,int clientID, String folderName)
	{
		this.clientID = clientID;
		this.folderName = folderName;
		this.context = context;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub 
		View rootView = inflater.inflate(R.layout.fragement_rotas, container, false);
        init(rootView);
        setBackground("EventsBG.png", folderName,notifi_fragement_background);
        return rootView;
	}
	
	private void init(View rootView) 
	{
		notifi_fragement_background = (ImageView)rootView.findViewById(R.id.fg_detail_imageview);
	}
	
	public Bitmap setBackground(String filename, String folder, ImageView imageView) {
		Bitmap thumbnail = null;
		try {
			FileInputStream fi = new FileInputStream(new File(
					context.getFilesDir(), "/Images/" + folder + "/"
							+ "ThemeImages/" + filename));
			thumbnail = BitmapFactory.decodeStream(fi);
			Drawable d = new BitmapDrawable(getResources(), thumbnail);
			imageView.setImageBitmap(thumbnail);
		} catch (Exception ex) {
			Log.e("getThumbnail() on internal storage", ex.getMessage());
		}
		return thumbnail;
	}

}
