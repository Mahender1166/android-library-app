package com.example.android_library_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminScreen extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    public static View navHeader;
    public static Menu nav_menu;
    UserRoleBasedNavigation navSetting = new UserRoleBasedNavigation();

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        NavigationView nav_view = findViewById(R.id.nav_view);
        nav_menu = nav_view.getMenu();

        if (MainActivity.loginStatus) {
            Intent loginIntent = getIntent();
            String userId = loginIntent.getStringExtra("userId");
            String userRole = loginIntent.getStringExtra("userRole");
            fAuth = FirebaseAuth.getInstance();
            navSetting.setNavigation(AdminScreen.this, userRole, userId);
        } else {
            navSetting.setNavigation(AdminScreen.this, "", "");
        }

        // nav-bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navHeader = navigationView.getHeaderView(0);
        if (MainActivity.userName != null && MainActivity.userName.length() > 0) {
            setProfileName("Hello " + MainActivity.userName + ",");
        }

        navigationView.setNavigationItemSelectedListener(AdminScreen.this);
        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ListAllBooksFragment()).addToBackStack(null).commit();
            navigationView.setCheckedItem(R.id.nav_list_all_books);

            return;
        }

    }

    public static void setProfileName(String userName) {
        TextView nameTV;
        nameTV = navHeader.findViewById(R.id.nameTV);
        nameTV.setText(userName);
        nameTV.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_book_stats:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BooksStatsListFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_list_all_books:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ListAllBooksFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_search_book:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchBookFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_login:
                handleLoginIntent();
                break;
            case R.id.nav_logout:
                handleLogoutIntent();
                break;
            case R.id.nav_register:
                handleRegisterIntent();
                break;
            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HelpMenuFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_contact_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContactMenuFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_add_book:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddBookFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_add_dept_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddDepartmentAdminFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_user_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserProfileFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_change_password:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChangePasswordFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_list_all_dept_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ListAllDrtAdminsFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_borrow_books_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BorrowBooksListFragment()).addToBackStack(null).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // login intent will be executed
    public void handleLoginIntent() {
        Intent loginIntent = new Intent(AdminScreen.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    // logout intent will be executed
    public void handleLogoutIntent() {
        MainActivity.loginStatus = false;
        MainActivity.userRole = "";
        MainActivity.user919 = "";
        MainActivity.userName = null;
        fAuth.signOut();
        navSetting.setNavigation(AdminScreen.this, "", "");
        Toast.makeText(AdminScreen.this, "Logged Out..!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AdminScreen.this, AdminScreen.class);
        startActivity(intent);
    }


    // register intent will be executed
    public void handleRegisterIntent() {
        Intent registerIntent = new Intent(AdminScreen.this, RegistrationScreen.class);
        startActivity(registerIntent);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
