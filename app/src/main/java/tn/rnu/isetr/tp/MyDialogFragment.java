package tn.rnu.isetr.tp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import tn.rnu.isetr.tp.Database.DatabaseManager;

public class MyDialogFragment extends DialogFragment {
    DatabaseManager databaseManager;
    public interface TeacherDialogListener {
        void onTeacherAdded(String name, String email);
    }
    private TeacherDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        databaseManager = new DatabaseManager(getContext());

        AlertDialog.Builder builder = new
                AlertDialog.Builder(requireContext());
        final View dialogView =
                this.getLayoutInflater().inflate(R.layout.layout_add_dialog, null, false);
        builder.setView(dialogView);
        final EditText nom = (EditText) dialogView.findViewById(R.id.edit_text);
        final EditText email = (EditText) dialogView.findViewById(R.id.email);
        return builder.setTitle("Ajouter Nouveau Enseignant\"")
                .setMessage("Donner nom enseignant")
                .setPositiveButton("Valider",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String Name = nom.getText().toString();
                                String Email = email.getText().toString();
                                if (listener != null) {
                                    listener.onTeacherAdded(Name,Email);
                                    databaseManager.addTeacher(Name,Email);

                                }
                            }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .create();
    }


    public void setTeacherDialogListener(TeacherDialogListener listener) {
        this.listener = listener;
    }
}