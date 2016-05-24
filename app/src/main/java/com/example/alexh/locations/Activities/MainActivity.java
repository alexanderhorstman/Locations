package com.example.alexh.locations.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alexh.locations.Adapters.ListPagerAdapter;
import com.example.alexh.locations.Data.User;
import com.example.alexh.locations.Managers.ListManager;
import com.example.alexh.locations.Managers.UserManager;
import com.example.alexh.locations.R;


public class MainActivity extends AppCompatActivity {

    private ViewHolder holder;
    private ArrayAdapter<String> drawerAdapter;
    private ActionBarDrawerToggle drawerToggle;
    Context context;
    ListManager listManager;
    final ListPagerAdapter listPagerAdapter = new ListPagerAdapter(getSupportFragmentManager());
    User loggedInUser;

    //fires when the app returns to this activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        holder.drawerLayout.closeDrawers();
        if(requestCode == UserManager.CREATE_USER_ACTIVITY && resultCode == RESULT_OK) {
            //refresh login status for new user
            //loginCurrentUser();
        }
        if(requestCode == UserManager.LOGIN_USER_ACTIVITY && resultCode == RESULT_OK) {
            loggedInUser = (User) data.getSerializableExtra("user");
            if(loggedInUser != null) {
                getSupportActionBar().setTitle("My Locations - " + loggedInUser.getName());
            }
            else {
                getSupportActionBar().setTitle("My Locations");
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listPagerAdapter.updatePage(0,this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);

        listManager = ListManager.getManager(this);
        UserManager userManager = UserManager.getManager(this);
        userManager.readLastUser();
        loggedInUser = userManager.getLastUser();
        if(loggedInUser == null) {
            Intent intent = new Intent(this, UserLogin.class);
            startActivityForResult(intent, UserManager.LOGIN_USER_ACTIVITY);
        }
        if(!listManager.loadSavedLists()) {
            Toast.makeText(this, "There was a problem creating data files. Shutting down.",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        holder = new ViewHolder();
        setupDrawer();
        setAppBarColor();
        setupViewPager();

        selectList(findViewById(R.id.my_list_button));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.create_location) {
            addNewLocation(new View(getBaseContext()));
            return true;
        }
        else if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void addNewLocation(View view) {
        Intent intent = new Intent(this, CreateLocation.class);
        //intent.putExtra("location type", "new location");
        startActivity(intent);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawer() {
        String[] options = { "User Settings", "App Settings", "Logout" };
        drawerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        holder.drawerList.setAdapter(drawerAdapter);

        holder.drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int item = view.getId();
                if (position == 0) {
                    //launch user settings activity
                } else if (position == 1) {
                    //launch app settings activity
                } else if (position == 2) {
                    //logout user
                    UserManager.getManager(context).saveLastUser(null);
                    Intent intent = new Intent(context, UserLogin.class);
                    startActivityForResult(intent, UserManager.LOGIN_USER_ACTIVITY);
                }
            }
        });
        setSupportActionBar(holder.toolbar);
        if(loggedInUser != null) {
            getSupportActionBar().setTitle("My Locations - " + loggedInUser.getName());
        }
        else {
            getSupportActionBar().setTitle("My Locations");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, holder.drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        holder.drawerLayout.setDrawerListener(drawerToggle);
    }

    public void selectList(View view) {
        int id = view.getId();
        int select = ContextCompat.getColor(this, R.color.colorAccent);
        int black = ContextCompat.getColor(this, android.R.color.black);
        if(id == R.id.my_list_button) {
            //set correct tab
            holder.pager.setCurrentItem(0);
            //make sure other two tabs are default colors
            holder.sharedListButton.setTextColor(black);
            //change button colors to show that the list is selected
            holder.myListButton.setTextColor(select);
        }
        else if(id == R.id.shared_list_button) {
            //set correct tab
            holder.pager.setCurrentItem(1);
            //change button colors to show that the list is selected
            //make sure other two tabs are default colors
            holder.myListButton.setTextColor(black);
            //change button colors to show that the list is selected
            holder.sharedListButton.setTextColor(select);
        }
    }

    private void setAppBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    private void setupViewPager() {
        holder.pager.setAdapter(listPagerAdapter);
        holder.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    selectList(findViewById(R.id.my_list_button));
                }
                else if (position == 1) {
                    selectList(findViewById(R.id.shared_list_button));
                }
                listPagerAdapter.updatePage(position, context);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class ViewHolder {
        protected Button myListButton;
        protected Button sharedListButton;
        protected ViewPager pager;
        protected Toolbar toolbar;
        protected ListView drawerList;
        protected DrawerLayout drawerLayout;

        public ViewHolder() {
            myListButton = (Button)findViewById(R.id.my_list_button);
            sharedListButton = (Button)findViewById(R.id.shared_list_button);
            pager = (ViewPager)findViewById(R.id.pager);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            drawerList = (ListView) findViewById(R.id.navList);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        }
    }
}
