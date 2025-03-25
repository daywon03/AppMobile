package tp1.dam.powerhome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateAccountActivity extends AppCompatActivity {
    Spinner spinnerCountryCode;
    //initialisation des EditText et Button
    private EditText editTextUsername, editTextNom, editTextPassword;
    private Button btnLogin;
    String[] spinner_country_code = {"+33", "+44", "+1", "+49", "+91"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        spinnerCountryCode = findViewById(R.id.spinner_country_code);
        editTextUsername = findViewById(R.id.username);
        editTextNom = findViewById(R.id.nom);
        editTextPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner_country_code);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountryCode.setAdapter(adapter);

        //Listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    Toast.makeText(CreateAccountActivity.this, "Validation réussie !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Redirige vers la page Login
    public void goToLogin (View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean validateInputs() {
        String username = editTextUsername.getText().toString().trim();
        String nom = editTextNom.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Regex
        String nameRegex = "^[A-Za-zÀ-ÖØ-öø-ÿ]{2,25}$";
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        // Validation
        if (!Pattern.matches(nameRegex, username)) {
            editTextUsername.setError("Le prénom doit être alphabétique (2-25 lettres)");
            return false;
        }

        if (!Pattern.matches(nameRegex, nom)) {
            editTextNom.setError("Le nom doit être alphabétique (2-25 lettres)");
            return false;
        }

        if (!Pattern.matches(passwordRegex, password)) {
            editTextPassword.setError("Le mot de passe doit contenir au moins 8 caractères, une lettre, un chiffre et un symbole");
            return false;
        }

        return true;
    }

}