package com.example.superscript.ui.documentation;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.superscript.R;

public class DocumentationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private TextView documentation;

    public DocumentationFragment() {
        // Required empty public constructor
    }

    public static DocumentationFragment newInstance(String param1, String param2) {
        DocumentationFragment fragment = new DocumentationFragment();
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
        View view = inflater.inflate(R.layout.fragment_documentation, container, false);

        documentation = view.findViewById(R.id.documentation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            documentation.setText(Html.fromHtml(getString(R.string.documentation), Html.FROM_HTML_MODE_COMPACT));
        } else {
            documentation.setText(Html.fromHtml(getString(R.string.documentation)));
        }

        return view;
    }
}