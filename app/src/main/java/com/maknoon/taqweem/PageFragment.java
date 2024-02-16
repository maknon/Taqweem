package com.maknoon.taqweem;

import static com.maknoon.taqweem.MainActivity.EXTRA_page;
import static com.maknoon.taqweem.MainActivity.pagesFolder;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;

public class PageFragment extends Fragment
{
	private int page;

	static PageFragment newInstance(int page)
	{
		final PageFragment fragmentFirst = new PageFragment();
		final Bundle args = new Bundle();
		args.putInt(EXTRA_page, page);
		fragmentFirst.setArguments(args);
		return fragmentFirst;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		assert getArguments() != null;
		page = getArguments().getInt(EXTRA_page, 0);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		final View view = inflater.inflate(R.layout.page_fragment, container, false);
		final AppCompatImageView pageView = view.findViewById(R.id.page);

		/*
		pageView.setOnClickListener(
				new View.OnClickListener()
				{
					public void onClick(View v)
					{
					}
				}
		);
		*/

		try
		{
			final AssetManager am = getResources().getAssets();
			final InputStream in = am.open(pagesFolder + "/" + page + ".webp");
			final Drawable pg = Drawable.createFromStream(in, null);
			//final Drawable pg = new BitmapDrawable(getResources(), BitmapFactory.decodeStream(in));
			pageView.setImageDrawable(pg);
			//pageView.setImageBitmap(BitmapFactory.decodeStream(in));
			//if(!pagesFolder.equals("shj"))
				//pageView.setBackgroundColor(Color.rgb(255, 255, 242)); // "#FFFFF2"
			in.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}

		return view;
	}
}