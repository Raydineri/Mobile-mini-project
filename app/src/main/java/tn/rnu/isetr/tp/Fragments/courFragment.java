    package tn.rnu.isetr.tp.Fragments;

    import android.database.Cursor;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.RadioGroup;
    import android.widget.Spinner;
    import android.widget.Toast;

    import androidx.fragment.app.Fragment;

    import java.util.ArrayList;
    import java.util.List;

    import tn.rnu.isetr.tp.Database.DatabaseManager;
    import tn.rnu.isetr.tp.Entity.Teacher;
    import tn.rnu.isetr.tp.R;

    public class courFragment extends Fragment {
        private EditText Name, Hours;
        private RadioGroup Type;
        private Spinner teacherSpinner;
        private Button addCourseButton;
        private Button showButton;
        private DatabaseManager databaseManager;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ArrayList <String>teacherList = new ArrayList<>();
            databaseManager = new DatabaseManager(getContext());

            fillTeachersInSpinner(teacherList);

            View view = inflater.inflate(R.layout.fragment_cour, container, false);
            Name = view.findViewById(R.id.name);
            Hours = view.findViewById(R.id.hours);
            Type = view.findViewById(R.id.radio_group_type);
            teacherSpinner = view.findViewById(R.id.spinner_teach);
             addCourseButton = view.findViewById(R.id.button_add_course);
             teacherSpinner.setAdapter( new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, teacherList));

            addCourseButton.setOnClickListener(v -> {
                        String name = Name.getText().toString();
                        Double hours = Double.valueOf(Hours.getText().toString());
                        String type = Type.getCheckedRadioButtonId() == R.id.radio_group_type ? "Cours" : "Atelier";
                        String teacher = teacherSpinner.getSelectedItem().toString();
                        databaseManager.insertCourse(name, hours, type, teacher);
                        Toast.makeText(getContext(), "Course added successfully", Toast.LENGTH_SHORT).show();
                        Name.setText("");
                        Hours.setText("");
                        Type.clearCheck();
                    });
            List<Teacher> teachers = new ArrayList<>();
            if (getArguments() != null) {
                teachers = (List<Teacher>) getArguments().getSerializable("teachers");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, teacherList);

            showButton = view.findViewById(R.id.button_list_courses);
            showButton.setOnClickListener(v -> {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ListCourFragment())
                        .addToBackStack(null)
                        .commit();
            });
            return view;
        }
        private void fillTeachersInSpinner(ArrayList<String> teacherNames) {

            Cursor cursor = databaseManager.getAllTeachers();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));



                    teacherNames.add(name);
                } while (cursor.moveToNext());
                cursor.close();
            }
        }


    }
