package com.ngochien.myapplication.Fragment.Admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ngochien.myapplication.Activity.DatLichActivity;
import com.ngochien.myapplication.Adapter.AdminStoreAdapter;
import com.ngochien.myapplication.Adapter.StoreAdapter;
import com.ngochien.myapplication.Model.StoreData;
import com.ngochien.myapplication.databinding.FragmentQuanLyStoresBinding;
import com.ngochien.myapplication.databinding.FragmentShopBinding;

import java.util.ArrayList;

public class QuanLyStoreFragment extends Fragment {
    private FragmentQuanLyStoresBinding binding;
    ArrayList<StoreData> list;
    AdminStoreAdapter adapter;
    FirebaseFirestore firestore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuanLyStoresBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();

        adapter=new AdminStoreAdapter(getActivity(),list);
        adapter.listener = new AdminStoreAdapter.AdminStoreListener() {
            @Override
            public void onClickBook(StoreData item) {

            }
        };

        binding.rvStore.setAdapter(adapter);
        getData();
    }

    private void getData(){
        list.clear();
        CollectionReference productRefs = firestore.collection("Stores");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String id           = document.getId();
                        String address = document.getString("address");
                        String detailAddress = document.getString("detailAddress");
                        String avt = document.getString("avt");
                        String sdt = document.getString("sdt");
                        StoreData data = new StoreData(id,detailAddress,address,sdt,avt);
                        list.add(data);
                    }
                    adapter.notifyDataSetChanged();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","Error");
            }
        });
    }
}
