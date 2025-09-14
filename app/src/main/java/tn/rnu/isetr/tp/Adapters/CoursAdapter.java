package tn.rnu.isetr.tp.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tn.rnu.isetr.tp.Database.DatabaseManager;
import tn.rnu.isetr.tp.Entity.Teacher;
import tn.rnu.isetr.tp.Entity.cour;
import tn.rnu.isetr.tp.R;

public class CoursAdapter extends RecyclerView.Adapter<CoursAdapter.CoursViewHolder> {
    private final List<cour> coursList;
    private int selectedPosition;
    private final Context context;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public CoursAdapter(List<cour> coursList, Context context) {
        this.coursList = coursList;
        this.context = context;
    }

    @NonNull
    @Override
    public CoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cours, parent, false);
        return new CoursViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursViewHolder holder, int position) {
        DatabaseManager databaseManager = new DatabaseManager(context);
        cour cours = coursList.get(position);
        holder.textNom.setText(cours.getNom());
        holder.textType.setText(cours.getType());
        holder.editNbHeures.setText(String.valueOf(cours.getHeure()));

        holder.buttonDelete.setOnClickListener(v -> {
            Log.e("Mytag", "onBindViewHolder: " + cours.getID());
            Toast.makeText(context, "Course deleted successfully" + cours.getID(), Toast.LENGTH_SHORT).show();
            databaseManager.deleteCourse(cours.getNom());
            removeItem(position);
        });

        List<String> TeacherNames = new ArrayList<>();
        Cursor Cursor = databaseManager.getAllTeachers();
        if (Cursor != null && Cursor.moveToFirst()) {
            do {
                String name = Cursor.getString(Cursor.getColumnIndexOrThrow("name"));
                TeacherNames.add(name);
            } while (Cursor.moveToNext());
            Cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, TeacherNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holder.spinnerEnseignant.setAdapter(adapter);
        holder.spinnerEnseignant.setSelection(adapter.getPosition(cours.getEnseignant()));

        holder.buttonEdit.setOnClickListener(v -> {
            updateCourse(holder, position);
        });

        holder.itemView.setOnLongClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return coursList.size();
    }

    public static class CoursViewHolder extends RecyclerView.ViewHolder {
        TextView textType, textNom;
        EditText editNbHeures;
        Spinner spinnerEnseignant;
        Button buttonEdit, buttonDelete;

        public CoursViewHolder(@NonNull View itemView) {
            super(itemView);
            textType = itemView.findViewById(R.id.textType);
            textNom = itemView.findViewById(R.id.textNom);
            editNbHeures = itemView.findViewById(R.id.editNbHeures);
            spinnerEnseignant = itemView.findViewById(R.id.spinnerEnseignant);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    public void removeItem(int position) {
        coursList.remove(position);
        notifyDataSetChanged();
    }

    private void updateCourse(CoursViewHolder holder, int position) {
        DatabaseManager databaseManager = new DatabaseManager(context);
        cour cours = coursList.get(position);

        String newNom = holder.textNom.getText().toString();
        String newType = holder.textType.getText().toString();
        int newHeure = Integer.parseInt(holder.editNbHeures.getText().toString());
        String newEnseignant = holder.spinnerEnseignant.getSelectedItem().toString();

        cours.setNom(newNom);
        cours.setType(newType);
        cours.setHeure(newHeure);
        cours.setEnseignant(newEnseignant);

        databaseManager.updateCourse(cours);

        notifyItemChanged(position);
        Toast.makeText(context, "Course updated successfully", Toast.LENGTH_SHORT).show();
    }
}

