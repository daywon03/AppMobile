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
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateAccountActivity extends AppCompatActivity {
    private Spinner spinnerCountryCode;
    private EditText editTextFirstname, editTextLastname, editTextPassword, editTextEmail, editTextPhoneNumber;
    private Button btnRegister;
    private static final String REGISTER_URL = "http://10.0.2.2:8888/powerhome_server/register.php";

    String[] spinner_country_code = {"+33", "+44", "+1", "+49", "+91"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Liaison des vues
        spinnerCountryCode = findViewById(R.id.spinner_country_code);
        editTextFirstname = findViewById(R.id.username);  // Champs renommé en firstname
        editTextLastname = findViewById(R.id.nom);        // Champs renommé en lastname
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextPhoneNumber = findViewById(R.id.phone_number);
        btnRegister = findViewById(R.id.login);

        // Adapter du Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner_country_code);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountryCode.setAdapter(adapter);

        // Listener du bouton
        btnRegister.setOnClickListener(v -> {
            if (validateInputs()) {
                registerUser();
            }
        });
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // ✅ Correction : Validation des entrées utilisateur
    private boolean validateInputs() {
        String firstname = editTextFirstname.getText().toString().trim();
        String lastname = editTextLastname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        String nameRegex = "^[A-Za-zÀ-ÖØ-öø-ÿ]{2,25}$";
        String emailRegex = android.util.Patterns.EMAIL_ADDRESS.pattern();
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (!Pattern.matches(nameRegex, firstname)) {
            editTextFirstname.setError("Le prénom doit être alphabétique (2-25 lettres)");
            return false;
        }
        if (!Pattern.matches(nameRegex, lastname)) {
            editTextLastname.setError("Le nom doit être alphabétique (2-25 lettres)");
            return false;
        }
        if (!Pattern.matches(emailRegex, email)) {
            editTextEmail.setError("Email invalide");
            return false;
        }
        if (!Pattern.matches(passwordRegex, password)) {
            editTextPassword.setError("Le mot de passe doit contenir au moins 8 caractères, une lettre, un chiffre et un symbole");
            return false;
        }
        if (phoneNumber.isEmpty()) {
            editTextPhoneNumber.setError("Numéro de téléphone requis");
            return false;
        }
        return true;
    }

    // ✅ Correction : Gestion correcte de la réponse serveur
    private void registerUser() {
        String firstname = editTextFirstname.getText().toString().trim();
        String lastname = editTextLastname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = spinnerCountryCode.getSelectedItem().toString() + editTextPhoneNumber.getText().toString().trim();
        String address = ""; // Adresse vide pour l’instant

        JsonObject json = new JsonObject();
        json.addProperty("firstname", firstname);
        json.addProperty("lastname", lastname);
        json.addProperty("email", email);
        json.addProperty("password", password);
        json.addProperty("phone", phone);
        json.addProperty("address", address);

        Ion.with(this)
                .load("POST", REGISTER_URL)
                .setJsonObjectBody(json)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(CreateAccountActivity.this, "Erreur réseau : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            JSONObject response = new JSONObject(result);
                            if (response.has("message")) {
                                Toast.makeText(CreateAccountActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                goToLogin(null);
                            } else {
                                Toast.makeText(CreateAccountActivity.this, "Erreur création : " + response.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException ex) {
                            Toast.makeText(CreateAccountActivity.this, "Erreur JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
