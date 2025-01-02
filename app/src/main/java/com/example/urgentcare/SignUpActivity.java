package com.example.urgentcare;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword;
    private Button signupButton;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialisation des vues
        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        // Gestion du clic sur le bouton d'inscription
        signupButton.setOnClickListener(v -> signUp());

        // Redirection vers l'écran de connexion
        loginRedirectText.setOnClickListener(v -> navigateTo(LoginActivity.class));
    }

    private void signUp() {
        String email = signupEmail.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();

        if (email.isEmpty()) {
            signupEmail.setError("Veuillez entrer un email");
            signupEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Email invalide");
            signupEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            signupPassword.setError("Veuillez entrer un mot de passe");
            signupPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            signupPassword.setError("Le mot de passe doit contenir au moins 6 caractères");
            signupPassword.requestFocus();
            return;
        }

        // Création de l'utilisateur
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                        navigateTo(MainActivity.class);
                    } else {
                        String errorMessage = task.getException() != null ?
                                task.getException().getMessage() : "Erreur inconnue";
                        Toast.makeText(SignUpActivity.this, "Échec de l'inscription: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Méthode de navigation
    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(SignUpActivity.this, targetActivity);
        startActivity(intent);
        finish();
    }
}
