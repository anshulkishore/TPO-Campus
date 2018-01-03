package com.example.anshul.tpocampus.Student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.anshul.tpocampus.R;

/**
 * Created by ANSHUL KISHORE on 09-10-2017.
 */

public class display_change_password extends Fragment {

    EditText e_old_password, e_new_password, e_confirm_password;
    Button bconfirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.display_change_password,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        e_old_password = (EditText) getActivity().findViewById(R.id.eold_pwd);
        e_new_password = (EditText) getActivity().findViewById(R.id.enew_pwd);
        e_confirm_password = (EditText) getActivity().findViewById(R.id.econfirm_pwd);
        bconfirm = (Button) getActivity().findViewById(R.id.bconfirm);
    }
}
