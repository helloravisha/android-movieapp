package android.nanodegree.sportify.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nanodegree.R;
import android.nanodegree.sportify.vo.Trailers;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static android.nanodegree.R.drawable.noposter;

/**
 * 
 *Adpater class for setting the trailers.
 *
 */
public class CustomGridViewAdapter extends BaseAdapter implements View.OnClickListener{
	Context context;
	int layoutResourceId;
	ArrayList<Trailers> data = new ArrayList<Trailers>();


	public CustomGridViewAdapter(Context context, int layoutResourceId,
								 ArrayList<Trailers> data) {
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View gridView = null;
		if (row == null) {


			gridView = new View(context);


		} else {

			gridView = (View) convertView;

		}
		gridView = inflater.inflate(R.layout.rowgrid, null);
		TextView movieText = (TextView) gridView.findViewById(R.id.item_text);
		ImageView imageView = (ImageView) gridView.findViewById(R.id.item_image);
		imageView.setOnClickListener(this);
		movieText.setOnClickListener(this);
		Trailers trailers = data.get(position);
		imageView.setTag(trailers.getTitle());
		movieText.setTag(trailers.getTitle());
        int trailerCount = position+1;
		movieText.setText("Trailer: "+trailerCount);
		imageView.setImageBitmap(trailers.getImage());
		return gridView;
	}

	@Override
	public void onClick(View v) {
		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+(String)v.getTag())));
	}
}