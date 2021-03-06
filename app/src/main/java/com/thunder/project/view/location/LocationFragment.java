/*-----------------------------------------------------------------------------
 - Developed by Felix Saraiva                                                 -
 - Last modified 28/10/19 11:20                                               -
 - Copyright (c) 2019. All rights reserved                                    -
 -----------------------------------------------------------------------------*/
package com.thunder.project.view.location;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thunder.project.R;
import com.thunder.project.Utils;
import com.thunder.project.adapter.RecyclerViewPlaceByLocation;
import com.thunder.project.model.Places;
import com.squareup.picasso.Picasso;
import com.thunder.project.view.detail.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.thunder.project.view.home.HomeActivity.EXTRA_DETAIL;

public class LocationFragment extends Fragment implements LocationView {

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imageLocation)
    ImageView imageLocation;
    @BindView(R.id.imageLocationBg)
    ImageView imageLocationBg;
    @BindView(R.id.textLocation)
    TextView textLocation;

    AlertDialog.Builder descDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Creates Countries Info
        if(getArguments() !=null){
            textLocation.setText(getArguments().getString("EXTRA_DATA_DESC"));
            Picasso.get().load(getArguments().getString("EXTRA_DATA_IMAGE")).into(imageLocation);
            Picasso.get().load(getArguments().getString("EXTRA_DATA_IMAGE")).into(imageLocationBg);

            descDialog = new AlertDialog.Builder(getActivity()).setTitle(getArguments().getString("EXTRA_DATA_NAME")).setMessage(getArguments().getString("EXTRA_DATA_DESC"));

            LocationPresenter presenter = new LocationPresenter(this);
            presenter.getPlaceByLocation(getArguments().getString("EXTRA_DATA_NAME"));
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setPlaces(List<Places.Place> places) {
        RecyclerViewPlaceByLocation adapter = new RecyclerViewPlaceByLocation(getActivity(),places);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setClipToPadding(false);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener((view, position) -> {
            TextView placeName = view.findViewById(R.id.placeName);
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(EXTRA_DETAIL, placeName.getText().toString());
            startActivity(intent);
        });
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getActivity(), "Error ", message);
    }

    @OnClick(R.id.cardLocation)
    public void onClick(){
        descDialog.setPositiveButton("CLOSE", ((dialog, which) -> dialog.dismiss()));
        descDialog.show();
    }
}