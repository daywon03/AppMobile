package tp1.dam.powerhome;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListeHabitatsFragment extends Fragment {
    public ListeHabitatsFragment() {
        // Obligatoire
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_liste_habitats, container, false);
    }
}
