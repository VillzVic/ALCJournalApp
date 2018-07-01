package com.vic.villz.journalapp.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vic.villz.journalapp.R;
import com.vic.villz.journalapp.Utils.ToastGenerator;
import com.vic.villz.journalapp.model.JournalEntry;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private CircleImageView mProfilePic;
    private TextView mUsername;
    private RecyclerView mRecyclerview;
    private DatabaseReference mDbReference;
    private AlertDialog mAlertDialog;
    private AlertDialog.Builder mBuilder;
    private final String TIME = "time";
    private final String DESCRIPTION = "description";
    private final String TITLE = "title";
    private FloatingActionButton fab;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        init();

    }

    private void init() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDbReference = FirebaseDatabase.getInstance().getReference().child("Entries");
        mDbReference.keepSynced(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mBuilder = new AlertDialog.Builder(this);

        fab = findViewById(R.id.add_journal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddJournalActivity.start(HomePageActivity.this);
            }
        });

        View header = navigationView.getHeaderView(0);
        mProfilePic = header.findViewById(R.id.user_profilepic);
        mUsername = header.findViewById(R.id.username);

        mRecyclerview = findViewById(R.id.recyclerView);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }




    @Override
    protected void onStart() {
        super.onStart();


        //on start of the activity, check if the user is logged in, if he is not, send the user back to login activity
        mCurrentUser = mAuth.getCurrentUser();
        GoogleSignInAccount account =  GoogleSignIn.getLastSignedInAccount(this);


        if (mCurrentUser == null && account == null) {
            logOutUser();
        }


        //if user is logged in then load the data
        else {
            setUpFirebaseAdapter();
        }

        //retrieve user data from signed in ACCOUNT
        if (account != null) {

            if( account.getPhotoUrl() != null) {

                Uri profileUri = account.getPhotoUrl();
                mProfilePic.setImageURI(profileUri);
            }

            mUsername.setText(account.getDisplayName());
        }


    }


    public static void start(Context context) {
        Intent starter = new Intent(context, HomePageActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.logout:
                HandleLogout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setUpFirebaseAdapter() {

        FirebaseRecyclerAdapter<JournalEntry, viewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<JournalEntry, viewHolder>
                (JournalEntry.class, R.layout.list_item_layout, viewHolder.class, mDbReference) {
            @Override
            protected void populateViewHolder(viewHolder viewHolder, final JournalEntry model, int position) {
                //first validate that the journal title is not null to avoid run time crashes
                String firstletter;
                if( model.getTitle().length() > 0) {
                    firstletter = model.getTitle().substring(0,1);
                } else {
                    firstletter = "J";
                }
                final String pushId = getRef(position).getKey();

                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color = generator.getRandomColor();

                TextDrawable drawable = TextDrawable.builder()
                        .beginConfig()
                        .textColor(Color.WHITE)
                        .fontSize(24) /* text size on pixels */
                        .bold()
                        .toUpperCase()
                        .endConfig()
                        .buildRound(firstletter,color);

                //bind the data
                viewHolder.bind(model, drawable);

                //handle the item click
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveToDetail(model);
                    }
                });

                //handle edit click action
                viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editJournalEntry(pushId, model);
                    }
                });

                //handle delete click action
                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteJournalEntry(pushId);
                    }
                });
            }
        };

        mRecyclerview.setAdapter(firebaseRecyclerAdapter);
    }

    private boolean moveToDetail(JournalEntry model) {

        final String title = model.getTitle();
        final String description = model.getDescription();
        final String time = model.getTime();

        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(DESCRIPTION, description);
        bundle.putString(TIME, time);

        DetailActivity.start(HomePageActivity.this, bundle);
        return true;
    }


    private boolean editJournalEntry(String id, JournalEntry model){
        //launch intent to

        UpdateActivity.start(HomePageActivity.this, model, id);
        return true;
    }

    private boolean deleteJournalEntry(final String id){

        //build a dialog to confirm id the user wans to delete this item
        mBuilder.setTitle("Delete");
        mBuilder.setMessage("Are you sure you want to delete this item");
        mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //if the user clicks yes, delete the entry
                mDbReference.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            ToastGenerator.GenerateToast(HomePageActivity.this, "Entry deleted", Toast.LENGTH_SHORT);
                        }
                    }
                });

                dialog.cancel();
            }
        });

        mBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //if the user clicks no, remove the dialog
                dialog.cancel();
            }
        });

        mAlertDialog = mBuilder.create();
        mAlertDialog.show();

        return true;
    }



    //send the user back to login activity and prevent the user from returning to this activity
    private void logOutUser() {
        Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }






    private void HandleLogout() {

        mBuilder.setTitle("Logout");
        mBuilder.setMessage("Are you sure you want to log out?");
        mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                logOutUser();
            }
        });
        mBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        mAlertDialog = mBuilder.create();
        mAlertDialog.show();

    }


    public static class viewHolder extends RecyclerView.ViewHolder{

        View itemView;
        AppCompatTextView title;
        AppCompatTextView time;
        ImageView textDrawable;
        AppCompatImageView edit;
        AppCompatImageView delete;


        public viewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            title = itemView.findViewById(R.id.journal_title);
            time = itemView.findViewById(R.id.joural_time);
            textDrawable = itemView.findViewById(R.id.text_drawable);
            edit = itemView.findViewById(R.id.edit_item);
            delete = itemView.findViewById(R.id.delete_item);
        }

        public void bind(JournalEntry entry, TextDrawable drawable){

            title.setText(entry.getTitle());
            time.setText(entry.getTime());
            textDrawable.setImageDrawable(drawable);

        }
    }
}
