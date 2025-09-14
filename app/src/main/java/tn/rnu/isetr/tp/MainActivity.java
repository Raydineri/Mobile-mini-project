package tn.rnu.isetr.tp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import tn.rnu.isetr.tp.Adapters.CoursAdapter;
import tn.rnu.isetr.tp.Adapters.TeachAdapter;
import tn.rnu.isetr.tp.Database.DatabaseHelper;
import tn.rnu.isetr.tp.Database.DatabaseManager;
import tn.rnu.isetr.tp.Entity.Teacher;
import tn.rnu.isetr.tp.Fragments.AboutFragment;
import tn.rnu.isetr.tp.Fragments.ListCourFragment;
import tn.rnu.isetr.tp.Fragments.ProfileFragment;
import tn.rnu.isetr.tp.Fragments.TaskFragment;
import tn.rnu.isetr.tp.Fragments.courFragment;
import tn.rnu.isetr.tp.Fragments.homeFragment;
import tn.rnu.isetr.tp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyDialogFragment.TeacherDialogListener {
    ActivityMainBinding bind;
    private List<Teacher> teacherList;


    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        View view = bind.getRoot();
        setContentView(view);
        setTitle("My App");


        teacherList = new ArrayList<>();
// Initialiser la Toolbar
        setSupportActionBar(bind.toolbar);
// Gestion des clics dans le Navigation Drawer
        bind.navView.setNavigationItemSelectedListener(this);
// Initialiser le DrawerLayout
// Ajouter un Listener pour ouvrir/fermer
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, bind.drawerLayout, bind.toolbar, R.string.open_drawer,
                R.string.close_drawer);
        bind.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace
                    (R.id.fragment_container, new AboutFragment()).commit();
            bind.navView.setCheckedItem(R.id.nav_home);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            bind.toolbar.setTitle("Enseignants");
            enseig = true;
            about = false;
            invalidateOptionsMenu();
            homeFragment fragment = new homeFragment();
            fragment.setTeacherList(teacherList);
            getSupportFragmentManager().beginTransaction().replace
                    (R.id.fragment_container, fragment).commit();
        } else if (item.getItemId() == R.id.nav_about) {
            bind.toolbar.setTitle("About");
            enseig = false;
            about = true;
            invalidateOptionsMenu();
            getSupportFragmentManager().beginTransaction().replace
                    (R.id.fragment_container, new AboutFragment()).commit();
        } else if (item.getItemId()==R.id.nav_task) {
            bind.toolbar.setTitle("Tasks");
            enseig = false;
            about = false;
            Cour = false;
            profile = false;
            Task = true;
            invalidateOptionsMenu();
            getSupportFragmentManager().beginTransaction().replace
                    (R.id.fragment_container, new TaskFragment()).commit();
            
        } else if (item.getItemId() == R.id.nav_profile) {
            bind.toolbar.setTitle("Profile");
            enseig = false;
            about = false;
            Cour = false;
            profile = true;
            invalidateOptionsMenu();

            getSupportFragmentManager().beginTransaction().replace
                    (R.id.fragment_container, new ProfileFragment()).commit();
        } else if (item.getItemId() == R.id.nav_logout) {
            Log.i("tag", "exit");
            Toast.makeText(this, "Exit", Toast.LENGTH_LONG).show();
            finish();
        }
        else if (item.getItemId() == R.id.nav_cours) {
        bind.toolbar.setTitle("Cours");
        enseig = false;
        about = false;
        Cour = true;
        invalidateOptionsMenu();

        getSupportFragmentManager().beginTransaction().replace
                (R.id.fragment_container, new courFragment()).commit();

    } else if (item.getItemId() == R.id.nav_logout) {
            Log.i("tag", "exit");
            Toast.makeText(this, "Exit", Toast.LENGTH_LONG).show();
            finish();
        }

        bind.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed(){
        if(bind.drawerLayout.isDrawerOpen(GravityCompat.START)){
            bind.drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }}

    @Override
    public boolean onCreateOptionsMenu(Menu menus) {
        getMenuInflater().inflate(R.menu.menu_main_about_xml, menus);
        this.menu=menus;
        return super.onCreateOptionsMenu(menu);
    }
    boolean enseig=false;
    boolean about=false;
    boolean Cour=false;
    boolean profile=false;
    boolean Task=false;
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (enseig) {
            getMenuInflater().inflate(R.menu.menu_main_enseig_xml, menu);
            // Inflate menu for option 1
        } else if(about) {
            getMenuInflater().inflate(R.menu.menu_main_about_xml, menu);
        } else if (Cour) {
            getMenuInflater().inflate(R.menu.menu_cour_edit, menu);

        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.a_z) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof homeFragment) {
                homeFragment homeFrag = (homeFragment) currentFragment;
                TeachAdapter adapter = homeFrag.getAdapter();
                adapter.sortByName(teacherList); // Sort A-Z
            } else {
                Log.e("FragmentError", "Current fragment is not homeFragment for sorting.");
            }
            return true;
        } else if (itemId == R.id.z_a) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof homeFragment) {
                homeFragment homeFrag = (homeFragment) currentFragment;
                TeachAdapter adapter = homeFrag.getAdapter();
                adapter.reverseByName(teacherList); // Sort Z-A
            } else {
                Log.e("FragmentError", "Current fragment is not homeFragment for sorting.");
            }
            return true;
        } else if (itemId == R.id.add) {
            showAddingDialog(); // Show the add dialog
            return true;
        } else if (itemId == R.id.AjoutMenu) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new courFragment())
                    .commit(); // Replace with courFragment
            return true;
        } else if (itemId == R.id.ListerMenu) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ListCourFragment())
                    .commit(); // Replace with ListCourFragment
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onTeacherAdded(String name, String email) {
        Teacher newTeacher = new Teacher(name, email);
        teacherList.add(newTeacher);
        homeFragment fragment = (homeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            TeachAdapter adapter = fragment.getAdapter();
            adapter.notifyItemInserted(teacherList.size() - 1);
        }
    }
    private void showAddingDialog() {
        MyDialogFragment dialog = new MyDialogFragment();
        dialog.setTeacherDialogListener(this);
        dialog.show(getSupportFragmentManager(), "my_dialog");
    }










}