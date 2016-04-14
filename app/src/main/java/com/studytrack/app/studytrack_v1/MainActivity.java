package com.studytrack.app.studytrack_v1;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;
import com.studytrack.app.studytrack_v1.CollegesSearch.CollegesFragment;
import com.studytrack.app.studytrack_v1.OlympsSearch.OlympsFragment;
import com.studytrack.app.studytrack_v1.UniversitySearch.SearchFragment;

public class MainActivity extends AppCompatActivity {

    //private enum Screen {UNIS, OLYMPS, COLLEGES}

    //protected HashMap<Screen, MyFragment> frags;
    FragmentManager fragmentManager;

    private Context ctx = this;
    private Toolbar toolbar;
    private Drawer drawer;
    private myFragment curFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        initToolBar();
        initFragments();
        initDrawer();

        curFrag = new SearchFragment();
        renderScreen();
    }

    private void initDrawer() {
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(false)
                .withHeader(R.layout.drawer_header)
                .withShowDrawerOnFirstLaunch(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_main_sec1_text).withIcon(FontAwesome.Icon.faw_graduation_cap),
                        new PrimaryDrawerItem().withName(R.string.drawer_main_sec2_text).withIcon(FontAwesome.Icon.faw_university),
                        new PrimaryDrawerItem().withName(R.string.drawer_main_sec3_text).withIcon(FontAwesome.Icon.faw_pencil),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_extra_sec1_text).withIcon(FontAwesome.Icon.faw_star).withSelectable(false)
                )
                .addStickyDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.drawer_footer_sec1_text).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_footer_sec2_text).withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (position) {
                            case 1:
                                curFrag = new SearchFragment();
                                renderScreen();
                                break;
                            case 2:
                                curFrag = new CollegesFragment();
                                renderScreen();
                                break;
                            case 3:
                                curFrag = new OlympsFragment();
                                renderScreen();
                                break;
                            case 5:
                                curFrag = new SearchFragment(true);
                                renderScreen();
                                break;
                            default:
                                Toast.makeText(ctx, "Coming soon", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        drawer.closeDrawer();
                        return true;
                    }
                })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerClosed(View view) {
                    }

                    @Override
                    public void onDrawerOpened(View view) {

                    }

                    @Override
                    public void onDrawerSlide(View view, float v) {

                    }
                })
                .build();

        ActionBarDrawerToggle toggle = drawer.getActionBarDrawerToggle();

        ImageView headerImg = (ImageView) drawer.getHeader().findViewById(R.id.drawer_header_img);
        Picasso.with(this).load(R.drawable.face).into(headerImg);
    }

    private void initFragments() {
        //frags = new HashMap<>();
        //frags.put(Screen.UNIS, new SearchFragment());
        //frags.put(Screen.COLLEGES, new CollegesFragment());
        //frags.put(Screen.OLYMPS, new OlympsFragment());
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeButtonEnabled(true);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    private void renderScreen() {

        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragmentManager.beginTransaction()
            .replace(R.id.main_fragment, curFrag)
            .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        }
        else if (!curFrag.onBackPressed()) {
            if (fragmentManager.getBackStackEntryCount() == 0)
                drawer.openDrawer();
            else
                super.onBackPressed();
        }
    }
}
