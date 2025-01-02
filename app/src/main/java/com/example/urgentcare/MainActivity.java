package com.example.urgentcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button logoutButton;
    private FirebaseAuth auth;
    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();
        // Si l'utilisateur est déjà connecté, redirigez-le vers UploadActivity
        if (auth.getCurrentUser() != null) {
            navigateTo(UploadActivity.class);
        }

        // Déconnexion
        //logoutButton = findViewById(R.id.logout);  // Assurez-vous que logoutButton est correctement lié
        logoutButton.setOnClickListener(v -> {
            auth.signOut();
            navigateTo(LoginActivity.class); // Redirige vers LoginActivity après la déconnexion
        });
    }

    // Méthode de navigation pour éviter la répétition du code
    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(MainActivity.this, targetActivity);
        startActivity(intent);
        finish(); // Assurez-vous que l'ancienne activité est terminée pour ne pas revenir en arrière
    }
}
