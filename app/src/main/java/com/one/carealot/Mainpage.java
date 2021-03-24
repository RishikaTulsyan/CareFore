package com.one.carealot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Mainpage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private FirebaseAuth mAuth;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home()).commit();


        mAuth=FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);

        navigationView = findViewById(R.id.navigationView);
        mMainNav = findViewById(R.id.bottomNavigation);
        navigationView.setNavigationItemSelectedListener(this);

        mMainFrame=findViewById(R.id.fragment_container);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch ((item.getItemId())){
                    case R.id.nav_customer:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home()).commit();
                        break;

                    case R.id.nav_NGO:
                        Toast.makeText(Mainpage.this, "You are not authorise for the page", Toast.LENGTH_SHORT).show();
                        break;


                    case R.id.nav_volunteers:
                        Toast.makeText(Mainpage.this, "You are not authorise for the page", Toast.LENGTH_SHORT).show();
                        break;



                }
                return true;
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("CareFore");
        toggle = new ActionBarDrawerToggle(Mainpage.this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null){

            Intent loginIntent = new Intent(Mainpage.this,LoginActivity.class);
            startActivity(loginIntent);
            finish();

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.Donate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Donate()).commit();
                break;


            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home()).commit();
                break;

            case R.id.FAQ:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new faq()).commit();
                break;



            case R.id.contact:
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Contact_us()).commit();
                break;

            case R.id.action_settings_btn:
                Setup();
                break;

            case R.id.acton_logout_btn:
                logout();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    private void Setup() {
        Intent SetupIntent = new Intent(Mainpage.this,SetupActivity.class);
        startActivity(SetupIntent);
    }

    private void logout() {

        mAuth.signOut();
        Intent loginIntent = new Intent(Mainpage.this,LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_menu, menu);






        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_button:
                logout();


            case R.id.cart_button:
                Intent Intentcart = new Intent(Mainpage.this,Cart.class);
                startActivity(Intentcart);


                return true;

            default:

                return false;
        }


    }
}
