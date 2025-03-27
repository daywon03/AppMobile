package tp1.dam.powerhome;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AboutDialogFragment extends DialogFragment {

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        // Créer un Dialog pour "À propos"
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("À propos")
                .setMessage("Cette application permet de gérer vos équipements et habitats.")
                .setPositiveButton("OK", (dialog, id) -> dismiss());
        return builder.create();
    }
}
