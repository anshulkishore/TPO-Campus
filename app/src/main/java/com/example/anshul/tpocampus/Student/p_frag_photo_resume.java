package com.example.anshul.tpocampus.Student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.anshul.tpocampus.R;

/**
 * Created by ANSHUL KISHORE on 11-10-2017.
 */

public class p_frag_photo_resume extends Fragment{

    Button download_resume;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.p_frag_photo_resume, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        download_resume = (Button) getActivity().findViewById(R.id.download_resume);
    }
}

