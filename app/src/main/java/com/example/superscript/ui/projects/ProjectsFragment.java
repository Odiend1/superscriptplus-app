package com.example.superscript.ui.projects;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.superscript.CodeEditor;
import com.example.superscript.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProjectsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectsFragment.
     */
    // TODO: Rename and change types and number of parameters

    private Button toEditor;
    private Button deleteProject;
    private HashMap<String, Button> projectButtons = new HashMap<String, Button>();
    private Boolean deleting = false;

    public static ProjectsFragment newInstance(String param1, String param2) {
        ProjectsFragment fragment = new ProjectsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        toEditor = view.findViewById(R.id.to_editor);
        toEditor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openCodeEditor(v,"new", true);
            }
        });

        File dir = getActivity().getFilesDir();
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(".ssp");
            }
        });
        ScrollView scrollView = view.findViewById(R.id.scroll_view);
        LinearLayout projectList = scrollView.findViewById(R.id.project_list);
        for(File file : files){
            Button project = new Button(getContext());
            projectButtons.put(file.getName(), project);
            project.setText(file.getName().substring(0, file.getName().length() - 4));
            project.setAllCaps(false);
            projectList.addView(project);
            project.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v){
                   if(!deleting) {
                       openCodeEditor(v, file.getName().substring(0, file.getName().length() - 4), false);
                   }
                   else{
                       new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_alert)
                               .setTitle("Delete Project \"" + file.getName().substring(0, file.getName().length() - 4) + "\"").setMessage(" Are you sure you want to delete this project? This cannot be undone.")
                               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       File projectFile = new File(getActivity().getFilesDir(), file.getName());
                                       if(projectFile.delete()){
                                           getActivity().recreate();
                                           Toast.makeText(getContext(), "Project was successfully deleted.", Toast.LENGTH_SHORT).show();
                                       }
                                       else{
                                           Toast.makeText(getContext(), "An error occurred while deleting the project.", Toast.LENGTH_SHORT).show();
                                       }
                                       deleting = false;
                                       getView().setBackgroundColor(getResources().getColor(R.color.background_grey));
                                   }
                               }).setNegativeButton("No", null).show();
                   }
               }
            });
        }

        deleteProject = view.findViewById(R.id.delete_project);
        deleteProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!deleting){
                    deleting = true;
                    getView().setBackgroundColor(getResources().getColor(R.color.crimson));
                }
                else{
                    deleting = false;
                    getView().setBackgroundColor(getResources().getColor(R.color.background_grey));
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        File dir = getActivity().getFilesDir();
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(".ssp");
            }
        });
        ScrollView scrollView = getView().findViewById(R.id.scroll_view);
        LinearLayout projectList = scrollView.findViewById(R.id.project_list);
        for(File file : files){
            boolean buttonExists = false;
            for (int i = 0; i < projectList.getChildCount(); i++) {
                View childView = projectList.getChildAt(i);
                if (childView instanceof Button) {
                    Button button = (Button) childView;
                    if (button.getText().toString().equals(file.getName().substring(0, file.getName().length() - 4))) {
                        buttonExists = true;
                        break;
                    }
                }
            }
            if(!buttonExists) {
                Button project = new Button(getContext());
                projectButtons.put(file.getName(), project);
                project.setText(file.getName().substring(0, file.getName().length() - 4));
                project.setAllCaps(false);
                projectList.addView(project);
                project.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Delete Project \"" + file.getName().substring(0, file.getName().length() - 4) + "\"").setMessage(" Are you sure you want to delete this project? This cannot be undone.")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        File projectFile = new File(getActivity().getFilesDir(), file.getName());
                                        if(projectFile.delete()){
                                            Toast.makeText(getContext(), "Project was successfully deleted.", Toast.LENGTH_SHORT).show();
                                            getActivity().recreate();
                                        }
                                        else{
                                            Toast.makeText(getContext(), "An error occurred while deleting the project.", Toast.LENGTH_SHORT).show();
                                        }
                                        deleting = false;
                                        getView().setBackgroundColor(getResources().getColor(R.color.background_grey));
                                    }
                                }).setNegativeButton("No", null).show();
                    }
                });
            }
        }
    }

    public void openCodeEditor(View v, String name, Boolean isNew){
        if(isNew) {
            // Create the AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Enter Project Name");

            // Inflate the layout for the AlertDialog
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_layout, null);
            EditText editText = view.findViewById(R.id.editText);
            builder.setView(view);

            // Set the positive button for the AlertDialog
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String userInput = editText.getText().toString();
                    String pattern = "^[a-zA-Z0-9\\s._-]+$"; // replace with your desired pattern
                    if (userInput.matches(pattern) && !userInput.trim().isEmpty()) {
                        Toast.makeText(v.getContext(), "Project created successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), CodeEditor.class);
                        intent.putExtra("name", userInput);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(v.getContext(), "Invalid project name.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Set the negative button for the AlertDialog
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Cancel the dialog and return to the activity
                }
            });

            // Create and show the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
            name = editText.getText().toString();
        }
        else {
            Intent intent = new Intent(getActivity(), CodeEditor.class);
            intent.putExtra("name", name);
            startActivity(intent);
        }
    }
}