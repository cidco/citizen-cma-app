package com.cma.pgrssystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.widget.ListView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.cma.pgrssystem.adapter.DrawerMenuAdapter;
import com.cma.pgrssystem.bean.DrawerMenuBean;
import com.cma.pgrssystem.fragment.ComplaintParentFragment;
import com.cma.pgrssystem.fragment.HistoryFragment;
import com.cma.pgrssystem.fragment.ProfileFragment;
import com.cma.pgrssystem.fragment.StatusFragment;
import com.cma.pgrssystem.listener.AsyncTaskCallBack;
import com.cma.pgrssystem.listener.OnDrawerMenuListener;
import com.cma.pgrssystem.util.PgrsConstants;
import com.cma.pgrssystem.util.PgrsHelper;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class HomeActivity extends BaseActivity implements AsyncTaskCallBack,
		OnDrawerMenuListener {

	private Fragment contentFragment;
	private DrawerMenuBean selectedDrawerMenuBean;
	private boolean isBackPressed = false;
	private String currentFragmentTag;
	private FragmentTransaction fragmentTransaction;
	private static List<DrawerMenuBean> drawerList;
	private ListView drawerListView = null;
	private static final String TAG = HomeActivity.class.getName();
	private SherlockActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private ActionBar mActionBar;
	private Uri outputFileUri = null;
	
	public void invokeSelectPhotosAction() {

		// Determine Uri of camera image to save.
		final File root = new File(Environment.getExternalStorageDirectory()
				+ File.separator + PgrsConstants.DIRECTORY_CAMERA_IMAGE
				+ File.separator);
		root.mkdirs();
		final String fname = PgrsHelper.getInstance()
				.getUniqueImageFilename();
		final File sdImageMainDirectory = new File(root, fname);
		outputFileUri = Uri.fromFile(sdImageMainDirectory);

		// Camera.
		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(
				captureIntent, 0);
		for (ResolveInfo res : listCam) {
			final String packageName = res.activityInfo.packageName;
			final Intent intent = new Intent(captureIntent);
			intent.setComponent(new ComponentName(res.activityInfo.packageName,
					res.activityInfo.name));
			intent.setPackage(packageName);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			cameraIntents.add(intent);
		}

		// Filesystem.
		Intent galleryIntent = null;
		if (Build.VERSION.SDK_INT < 19) {
			galleryIntent = new Intent();
			galleryIntent.setType("image/*");
			galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

		} else {

			galleryIntent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}

		// Set Common Flags to Gallery Intent
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
			galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
		}
		galleryIntent.putExtra(Intent.CATEGORY_OPENABLE, true);

		// Chooser of filesystem options.
		final Intent chooserIntent = Intent.createChooser(galleryIntent,
				PgrsConstants.INTENT_TITLE_PICK_PHOTOS);

		// Add the camera options.
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
				cameraIntents.toArray(new Parcelable[] {}));

		startActivityForResult(chooserIntent,
				PgrsConstants.REQUEST_CODE_PICK_PICTURES_FOR_COMPLAINTS);
	}


	@Override
	protected void onDestroy() {
		Crouton.cancelAllCroutons();
		super.onDestroy();
	}

	public void setCurrentFragmentTag(String pFragmentTag) {
		currentFragmentTag = pFragmentTag;
	}

	private void setActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}

	public void switchContent(final Fragment fragment, String fragmentName) {
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, fragment, fragmentName);
		fragmentTransaction
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fragmentTransaction.commit();

	}

	@Override
	public void onBackPressed() {
		if (!isBackPressed) {
			isBackPressed = true;
			Crouton.showText(this, "Press back again to exit", Style.INFO);

		} else {
			moveTaskToBack(true);
			isBackPressed = false;
			super.onBackPressed();
		}

	}

	public List<DrawerMenuBean> getDrawerMenuList() {

		if (drawerList != null) {
			return drawerList;
		}
		drawerList = new ArrayList<DrawerMenuBean>();

		// 1. Status
		DrawerMenuBean streams = new DrawerMenuBean(
				PgrsConstants.SLIDING_MENU_ID_STATUS,
				PgrsConstants.SLIDING_MENU_LABEL_STATUS);
		drawerList.add(streams);

		// 2. Complaint
		DrawerMenuBean notifications = new DrawerMenuBean(
				PgrsConstants.SLIDING_MENU_ID_COMPLAINT,
				PgrsConstants.SLIDING_MENU_LABEL_COMPLAINT);
		drawerList.add(notifications);

		// 3. History
		DrawerMenuBean fanpages = new DrawerMenuBean(
				PgrsConstants.SLIDING_MENU_ID_HISTORY,
				PgrsConstants.SLIDING_MENU_LABEL_HISTORY);
		drawerList.add(fanpages);

		// 4. Profile
		DrawerMenuBean aspects = new DrawerMenuBean(
				PgrsConstants.SLIDING_MENU_ID_PROFILE,
				PgrsConstants.SLIDING_MENU_LABEL_PROFILE);
		drawerList.add(aspects);

		return drawerList;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	// The click listener for ListView in the navigation drawer
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// Close drawer
			mDrawerLayout.closeDrawer(drawerListView);
			handleDrawerMenuClick(getDrawerMenuList().get(position));
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {

			if (mDrawerLayout.isDrawerOpen(drawerListView)) {
				mDrawerLayout.closeDrawer(drawerListView);
			} else {
				mDrawerLayout.openDrawer(drawerListView);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	public void setActionBartTitle(int pSelectedMenuId) {
		switch (pSelectedMenuId) {
		case PgrsConstants.SLIDING_MENU_ID_STATUS:
			getSupportActionBar().setTitle(
					PgrsConstants.SLIDING_MENU_LABEL_STATUS);
			break;
		case PgrsConstants.SLIDING_MENU_ID_COMPLAINT:
			getSupportActionBar().setTitle(
					PgrsConstants.SLIDING_MENU_LABEL_COMPLAINT);
			break;
		case PgrsConstants.SLIDING_MENU_ID_HISTORY:
			getSupportActionBar().setTitle(
					PgrsConstants.SLIDING_MENU_LABEL_HISTORY);
			break;

		case PgrsConstants.SLIDING_MENU_ID_PROFILE:
			getSupportActionBar().setTitle(
					PgrsConstants.SLIDING_MENU_LABEL_PROFILE);
			break;

		}

	}

	private void getStatusFragment() {
		setActionBartTitle(PgrsConstants.SLIDING_MENU_ID_STATUS);
		setCurrentFragmentTag(PgrsConstants.SLIDING_MENU_LABEL_STATUS);
		contentFragment = getSupportFragmentManager().findFragmentByTag(
				PgrsConstants.SLIDING_MENU_LABEL_STATUS);
		if (contentFragment == null)
			contentFragment = StatusFragment.newInstance();
		switchContent(contentFragment, PgrsConstants.SLIDING_MENU_LABEL_STATUS);
	}

	/**
	 * Method is public as it needs to be invoked from Status fragment
	 */
	public void getComplaintParentFragment() {
		setActionBartTitle(PgrsConstants.SLIDING_MENU_ID_COMPLAINT);
		setCurrentFragmentTag(PgrsConstants.SLIDING_MENU_LABEL_COMPLAINT);
		contentFragment = getSupportFragmentManager().findFragmentByTag(
				PgrsConstants.SLIDING_MENU_LABEL_COMPLAINT);
		if (contentFragment == null)
			contentFragment = new ComplaintParentFragment();
		switchContent(contentFragment,
				PgrsConstants.SLIDING_MENU_LABEL_COMPLAINT);
	}

	private void getHistoryFragment() {
		setActionBartTitle(PgrsConstants.SLIDING_MENU_ID_HISTORY);
		setCurrentFragmentTag(PgrsConstants.SLIDING_MENU_LABEL_HISTORY);
		contentFragment = getSupportFragmentManager().findFragmentByTag(
				PgrsConstants.SLIDING_MENU_LABEL_HISTORY);
		if (contentFragment == null)
			contentFragment = new HistoryFragment();
		switchContent(contentFragment, PgrsConstants.SLIDING_MENU_LABEL_HISTORY);
	}

	private void getProfileFragment() {
		setActionBartTitle(PgrsConstants.SLIDING_MENU_ID_PROFILE);
		setCurrentFragmentTag(PgrsConstants.SLIDING_MENU_LABEL_PROFILE);
		contentFragment = getSupportFragmentManager().findFragmentByTag(
				PgrsConstants.SLIDING_MENU_LABEL_PROFILE);
		if (contentFragment == null)
			contentFragment = new ProfileFragment();
		switchContent(contentFragment, PgrsConstants.SLIDING_MENU_LABEL_PROFILE);
	}
	
	

	@Override
	public void handleDrawerMenuClick(DrawerMenuBean pMenuBean) {
		isBackPressed = false;

		// Update selected sliding menu bean reference
		selectedDrawerMenuBean = pMenuBean;

		// Reset Progress Bar upon Menu Click
		setProgressBarIndeterminateVisibility(false);

		// Set Action bar
		setActionBar();

		switch (selectedDrawerMenuBean.getId()) {
		case PgrsConstants.SLIDING_MENU_ID_STATUS:
			getStatusFragment();
			break;

		case PgrsConstants.SLIDING_MENU_ID_COMPLAINT:
			getComplaintParentFragment();
			break;

		case PgrsConstants.SLIDING_MENU_ID_HISTORY:
			getHistoryFragment();
			break;

		case PgrsConstants.SLIDING_MENU_ID_PROFILE:
			getProfileFragment();
			break;

		}

	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			currentFragmentTag = savedInstanceState
					.getString(PgrsConstants.CURRENT_FRAGMENT_TAG);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putString(PgrsConstants.CURRENT_FRAGMENT_TAG,
				currentFragmentTag);
		getSupportFragmentManager().putFragment(outState, currentFragmentTag,
				contentFragment);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_home);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// ActionBarDrawerToggle provides convenient helpers for tying together
		// the
		// prescribed interactions between a top-level sliding drawer and the
		// action bar.
		mDrawerToggle = new SherlockActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer_light, R.string.drawer_open,
				R.string.drawer_close);

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		drawerListView = (ListView) findViewById(R.id.left_drawer);
		drawerListView.setAdapter(new DrawerMenuAdapter(this,
				getDrawerMenuList()));
		drawerListView.setOnItemClickListener(new DrawerItemClickListener());

		// set the Above View Fragment
		if (savedInstanceState != null) {
			currentFragmentTag = savedInstanceState
					.getString(PgrsConstants.CURRENT_FRAGMENT_TAG);
			contentFragment = getSupportFragmentManager().getFragment(
					savedInstanceState, currentFragmentTag);
		}
		if (contentFragment == null) {
			currentFragmentTag = PgrsConstants.SLIDING_MENU_LABEL_STATUS;
			contentFragment = StatusFragment.newInstance();
			setActionBartTitle(PgrsConstants.SLIDING_MENU_ID_STATUS);

		}

		setProgressBarIndeterminateVisibility(false);

		setActionBar();

		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, contentFragment,
				currentFragmentTag);

		fragmentTransaction.commit();

	}

	@Override
	public void onDrawerOpened(View drawerView) {
		mDrawerToggle.onDrawerOpened(drawerView);
	}

	@Override
	public void onDrawerClosed(View drawerView) {
		mDrawerToggle.onDrawerClosed(drawerView);
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
	}

	@Override
	public void onDrawerStateChanged(int newState) {
		mDrawerToggle.onDrawerStateChanged(newState);
	}

	@Override
	public void onPreExecute() {
		setSupportProgressBarIndeterminateVisibility(true);

	}

	@Override
	public void onProgressUpdate(int percent) {

	}

	@Override
	public void onCancelled() {
		setSupportProgressBarIndeterminateVisibility(false);

	}

	@Override
	public void onPostExecute() {
		setSupportProgressBarIndeterminateVisibility(false);
	}

}
