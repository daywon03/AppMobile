package tp1.dam.powerhome;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText emailReset, tempPasswordEdit, newPasswordEdit;
    private static final String UPDATE_URL = "http://10.0.2.2:8888/powerhome_server/updatePassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailReset = findViewById(R.id.emailReset);
        tempPasswordEdit = findViewById(R.id.tempPassword);
        newPasswordEdit = findViewById(R.id.newPassword);

        // Récupérer le mot de passe temporaire depuis l'intent et l'afficher
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String tempPassword = extras.getString("tempPassword");
            if (tempPassword != null) {
                tempPasswordEdit.setText(tempPassword);
            }
        }

        findViewById(R.id.btnResetPassword).setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        String email = emailReset.getText().toString().trim();
        String tempPassword = tempPasswordEdit.getText().toString().trim();
        String newPassword = newPasswordEdit.getText().toString().trim();

        if (email.isEmpty() || tempPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("tempPassword", tempPassword);
        json.addProperty("newPassword", newPassword);

        Ion.with(this)
                .load("POST", UPDATE_URL)
                .setJsonObjectBody(json)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(ResetPasswordActivity.this, "Erreur réseau : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.d("RESET_RESPONSE", "Result: " + result);
                        try {
                            JSONObject response = new JSONObject(result);
                            if (response.has("message")) {
                                Toast.makeText(ResetPasswordActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(ResetPasswordActivity.this, "Erreur : " + response.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(ResetPasswordActivity.this, "Erreur lors du traitement de la réponse", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
