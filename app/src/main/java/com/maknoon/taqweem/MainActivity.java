package com.maknoon.taqweem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity
{
	SharedPreferences mPrefs;

	int page = 0;
	static String pagesFolder = "auh";

	PagerAdapter pagerAdapter;
	ViewPager2 mViewPager;

	static final String EXTRA_page = "com.maknoon.quran.page";
	static final String EXTRA_pagesFolder = "com.maknoon.quran.pagesFolder";

	MenuItem rakMenuItem, dxbMenuItem, auhMenuItem, shjMenuItem, ajmMenuItem, uaqMenuItem, fujMenuItem;

	// This flag should be set to true to enable VectorDrawable support for API < 21
	static
	{
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
	}

	@Override
	protected void attachBaseContext(Context base)
	{
		super.attachBaseContext(ContextWrapper.wrap(base, new Locale("ar")));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL); // force RTL

		mPrefs = getSharedPreferences("setting", Context.MODE_PRIVATE);

		if (savedInstanceState != null) // when recreate() this activity if language is change. savedInstanceState will be saved so we need to force the direction
			page = savedInstanceState.getInt(EXTRA_page);
		else
			page = mPrefs.getInt(EXTRA_page, 0);

		pagesFolder = mPrefs.getString(EXTRA_pagesFolder, "auh");

		final ActionBar ab = getSupportActionBar();
		if (ab != null)
			ab.setTitle(R.string.app_name);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pagerAdapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle());
		mViewPager = findViewById(R.id.viewpager);
		mViewPager.setAdapter(pagerAdapter);

		/*
		mViewPager.setOnClickListener(
				new View.OnClickListener()
				{
					public void onClick(View v)
					{
					}
				}
		);
		*/
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		final int id = item.getItemId();

		/*
		if (id == R.id.menu_hijri)
		{
			final IslamicCalendar start = new IslamicCalendar(new ULocale("@calendar=islamic-umalqura"));
			start.set(1445, 1, 1, 0 ,0 ,0);

			final IslamicCalendar end = new IslamicCalendar(new ULocale("@calendar=islamic-umalqura"));
			end.set(1445, 12, 29 + 1, 0, 0, 0); // 1 day more to include the same day

			final ArrayList<CalendarConstraints.DateValidator> listValidators = new ArrayList<>();
			listValidators.add(DateValidatorPointBackward.before(end.getTimeInMillis())); // disable future year days
			listValidators.add(DateValidatorPointForward.from(start.getTimeInMillis())); // disable previous year days
			final CalendarConstraints.DateValidator validators = CompositeDateValidator.allOf(listValidators);

			final MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
			builder.setCalendarConstraints(new CalendarConstraints.Builder()
					.setStart(start.getTimeInMillis()) // start month in UTC
					.setEnd(end.getTimeInMillis()) // end month in UTC
					.setValidator(validators)
					.build()
			);

			final MaterialDatePicker<Long> picker = builder.build();
			picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>()
			{
				@Override
				public void onPositiveButtonClick(Long selection)
				{
					final Calendar date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
					date.setTimeInMillis(selection); // 'selection' in UTC timezone, it is truncated by default but in UTC

					//IslamicCalendar islamicCalendar;
					final HijrahDate islamicDate = HijrahDate.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
					//islamicDate = HijrahDate.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.ofOffset("UTC", ZoneOffset.UTC)));

					final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("D", Locale.ENGLISH);
					page = Integer.parseInt(islamicDate.format(dtf)) - 1;
					mViewPager.setCurrentItem(page, false);
				}
			});
			picker.show(getSupportFragmentManager(), picker.toString());

			return true;
		}
		*/

		if (id == R.id.menu_gregorian)
		{
			final Calendar start = Calendar.getInstance(); // TimeZone.getTimeZone("UTC") will not work since the calender is displayed based on the local but get the input based on UTC
			start.set(2024, 6, 7, 0 ,0 ,0); // TODO: change with every year

			final Calendar end = Calendar.getInstance();
			end.set(2025, 5, 25 + 1, 0, 0, 0); // 1 day more to include the same day

			final ArrayList<CalendarConstraints.DateValidator> listValidators = new ArrayList<>();
			listValidators.add(DateValidatorPointBackward.before(end.getTimeInMillis())); // disable future year days
			listValidators.add(DateValidatorPointForward.from(start.getTimeInMillis())); // disable previous year days
			final CalendarConstraints.DateValidator validators = CompositeDateValidator.allOf(listValidators);

			final MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
			builder.setCalendarConstraints(new CalendarConstraints.Builder()
					.setStart(start.getTimeInMillis()) // start month in UTC
					.setEnd(end.getTimeInMillis()) // end month in UTC
					.setValidator(validators)
					.build()
			);

			final MaterialDatePicker<Long> picker = builder.build();
			picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>()
			{
				@Override
				public void onPositiveButtonClick(Long selection)
				{
					final Calendar date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
					date.setTimeInMillis(selection); // 'selection' in UTC timezone, it is truncated by default but in UTC

					//IslamicCalendar islamicCalendar;
					final HijrahDate islamicDate = HijrahDate.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
					//islamicDate = HijrahDate.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.ofOffset("UTC", ZoneOffset.UTC)));

					final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("D", Locale.ENGLISH);
					page = Integer.parseInt(islamicDate.format(dtf)) - 1;
					mViewPager.setCurrentItem(page, false);
				}
			});
			picker.show(getSupportFragmentManager(), picker.toString());

			return true;
		}

		if (id == R.id.menu_rak)
		{
			pagesFolder = "rak";

			final SharedPreferences.Editor mEditor = mPrefs.edit();
			mEditor.putString(EXTRA_pagesFolder, pagesFolder).apply();

			refresh();
			return true;
		}

		if (id == R.id.menu_auh)
		{
			pagesFolder = "auh";

			final SharedPreferences.Editor mEditor = mPrefs.edit();
			mEditor.putString(EXTRA_pagesFolder, pagesFolder).apply();

			refresh();
			return true;
		}

		if (id == R.id.menu_dxb)
		{
			pagesFolder = "dxb";

			final SharedPreferences.Editor mEditor = mPrefs.edit();
			mEditor.putString(EXTRA_pagesFolder, pagesFolder).apply();

			refresh();
			return true;
		}

		if (id == R.id.menu_shj)
		{
			pagesFolder = "shj";

			final SharedPreferences.Editor mEditor = mPrefs.edit();
			mEditor.putString(EXTRA_pagesFolder, pagesFolder).apply();

			refresh();
			return true;
		}

		if (id == R.id.menu_ajm)
		{
			pagesFolder = "ajm";

			final SharedPreferences.Editor mEditor = mPrefs.edit();
			mEditor.putString(EXTRA_pagesFolder, pagesFolder).apply();

			refresh();
			return true;
		}

		if (id == R.id.menu_uaq)
		{
			pagesFolder = "uaq";

			final SharedPreferences.Editor mEditor = mPrefs.edit();
			mEditor.putString(EXTRA_pagesFolder, pagesFolder).apply();

			refresh();
			return true;
		}

		if (id == R.id.menu_fuj)
		{
			pagesFolder = "fuj";

			final SharedPreferences.Editor mEditor = mPrefs.edit();
			mEditor.putString(EXTRA_pagesFolder, pagesFolder).apply();

			refresh();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	void refresh()
	{
		mViewPager.setAdapter(null);
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setCurrentItem(page, false);
	}

	@Override
	public boolean onCreateOptionsMenu(@NonNull Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		rakMenuItem = menu.findItem(R.id.menu_rak);
		auhMenuItem = menu.findItem(R.id.menu_auh);
		dxbMenuItem = menu.findItem(R.id.menu_dxb);
		shjMenuItem = menu.findItem(R.id.menu_shj);
		ajmMenuItem = menu.findItem(R.id.menu_ajm);
		uaqMenuItem = menu.findItem(R.id.menu_uaq);
		fujMenuItem = menu.findItem(R.id.menu_fuj);

		menu.findItem(R.id.menu_hijri).setVisible(false);
		fujMenuItem.setVisible(false);
		ajmMenuItem.setVisible(false);
		dxbMenuItem.setVisible(false);

		return super.onCreateOptionsMenu(menu);
	}

	static class PagerAdapter extends FragmentStateAdapter
	{
		PagerAdapter(FragmentManager fm, @NonNull Lifecycle lc)
		{
			super(fm, lc);
		}

		@NonNull
		@Override
		public Fragment createFragment(int position)
		{
			return PageFragment.newInstance(position);
		}

		@Override
		public int getItemCount()
		{
			return 354;
		}
	}

	@Override
	protected void onSaveInstanceState(@NonNull Bundle savedInstanceState)
	{
		savedInstanceState.putInt(EXTRA_page, page);
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume()
	{
		final HijrahDate hd1 = HijrahDate.now();
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("D", Locale.ENGLISH);
		page = Integer.parseInt(hd1.format(dtf)) - 1;
		mViewPager.setCurrentItem(page, false);

		super.onResume();
	}
}