package com.example.houses.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.houses.HouseAdapter;
import com.example.houses.HouseDetailsActivity;
import com.example.houses.HouseModel;
import com.example.houses.R;
import com.example.houses.SelectListenerInterface;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment implements SelectListenerInterface {
    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<HouseModel> housesList = new ArrayList<>();
    ArrayList<HouseModel> searchList;
    View noResultsLayout;
    int[] houseImages = {
            R.drawable.house1, R.drawable.house2,
            R.drawable.house3, R.drawable.house4,
            R.drawable.house5, R.drawable.house6,
            R.drawable.house7, R.drawable.house8,
            R.drawable.house9, R.drawable.house10,
    };

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchView.setIconified(false);
        searchView.clearFocus();
        noResultsLayout = view.findViewById(R.id.noResultsFound);
        noResultsLayout.setVisibility(View.GONE);

        this.getDataFromBE();
        this.listenSearch();

        return view;
    }

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        housesList.clear();
    }

    private void listenSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchValue) {
                searchListFunctionality(searchValue);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchValue) {
                searchListFunctionality(searchValue);

                return false;
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void searchListFunctionality(String searchValue) {
        searchList = new ArrayList<>();

        if (searchValue.length() > 0) {
            for (int i = 0; i < housesList.size(); i++) {
                if (housesList.get(i).getCity().toUpperCase().contains(searchValue.toUpperCase())
                        || housesList.get(i).getZip().toUpperCase().contains(searchValue.toUpperCase())) {
                    addModelToList(housesList, searchList, i);
                }
            }

            if (searchList.size() > 0) {
                showRecycleList();
            } else {
                showNoResultsView();
            }

            initList(searchList);
        } else {
            showRecycleList();
            initList(housesList);
        }
    }

    private void getDataFromBE() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://intern.d-tt.nl/api/house")
                .method("GET", null)
                .header("Access-Key", "98bww4ezuzfePCYFxJEWyszbUXc7dxRx")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String stringResponse = response.body().string();

                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<HouseModel>>() {}.getType();
                    ArrayList<HouseModel> housesListResponse = gson.fromJson(stringResponse, type);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("STATE", stringResponse);
                            setDataList(housesListResponse);
                        }
                    });
                }
            }
        });
    }

    private void setDataList(ArrayList<HouseModel> housesListResponse) {
        for (int i = 0; i < housesListResponse.size(); i++) {
            addModelToList(housesListResponse, housesList, i);
        }

        initList(housesList);
    }

    private void addModelToList(ArrayList<HouseModel> listFrom, ArrayList<HouseModel> listTo, int index) {
        HouseModel houseModel = new HouseModel();
        houseModel.setId(listFrom.get(index).getId());
        houseModel.setLocalImage(houseImages[index]);
        houseModel.setPrice(listFrom.get(index).getPrice());
        houseModel.setDescription(listFrom.get(index).getDescription());
        houseModel.setBedrooms(listFrom.get(index).getBedrooms());
        houseModel.setBathrooms(listFrom.get(index).getBathrooms());
        houseModel.setSize(listFrom.get(index).getSize());
        houseModel.setZip(listFrom.get(index).getZip());
        houseModel.setCity(listFrom.get(index).getCity());
        houseModel.setLatitude(listFrom.get(index).getLatitude());
        houseModel.setLongitude(listFrom.get(index).getLongitude());
        houseModel.setCreatedDate(listFrom.get(index).getCreatedDate());
        listTo.add(houseModel);

        // Ascending Sort by price for the ArrayList ( listTo ) of HouseModel objects
        Collections.sort(listTo, new Comparator< HouseModel >() {
            @Override public int compare(HouseModel houseModel1, HouseModel houseModel2) {
                return houseModel1.getPrice()- houseModel2.getPrice();
            }
        });
    }

    private void showRecycleList() {
        noResultsLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showNoResultsView() {
        noResultsLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void initList(ArrayList<HouseModel> listForInit) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        HouseAdapter houseAdapter = new HouseAdapter(getContext(), listForInit, this);
        recyclerView.setAdapter(houseAdapter);
    }

    @Override
    public void onItemClicked(int position) {
        String priceWithDecimal = new DecimalFormat("000,000").format(housesList.get(position).getPrice());

        Intent intent = new Intent(getActivity(), HouseDetailsActivity.class);
        intent.putExtra("PRICE", String.valueOf(priceWithDecimal));
        intent.putExtra("IMAGE", String.valueOf(housesList.get(position).getLocalImage()));
        intent.putExtra("DESCRIPTION", housesList.get(position).getDescription());
        intent.putExtra("BEDROOMS", String.valueOf(housesList.get(position).getBedrooms()));
        intent.putExtra("BATHROOMS", String.valueOf(housesList.get(position).getBathrooms()));
        intent.putExtra("SIZE", String.valueOf(housesList.get(position).getSize()));

        startActivity(intent);
    }
}