package com.example.houses;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.MyHolder> {
    private Context context;
    private ArrayList<HouseModel> houseList;
    private LayoutInflater layoutInflater;
    private final SelectListenerInterface selectListenerInterface;

    public HouseAdapter(Context context, ArrayList<HouseModel> houseList, SelectListenerInterface selectListenerInterface) {
        this.context = context;
        this.houseList = houseList;
        layoutInflater = LayoutInflater.from(context);
        this.selectListenerInterface = selectListenerInterface;
    }

    @NonNull
    @Override
    public HouseAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.house_item, parent, false);

        return new MyHolder(view, selectListenerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseAdapter.MyHolder holder, int position) {
        holder.image.setImageResource(houseList.get(position).getLocalImage());
        holder.city.setText(houseList.get(position).getCity());
        holder.price.setText(String.valueOf(houseList.get(position).getPrice()));
        holder.bedrooms.setText(String.valueOf(houseList.get(position).getBedrooms()));
        holder.bathrooms.setText(String.valueOf(houseList.get(position).getBathrooms()));
        holder.size.setText(String.valueOf(houseList.get(position).getSize()));
        holder.zip.setText(houseList.get(position).getZip());
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        int id;
        ImageView image;
        TextView price, bedrooms, bathrooms, size, zip, city, description, latitude, longitude, createdDate;
        public CardView cardView;

        public MyHolder(@NonNull View itemView, SelectListenerInterface selectListenerInterface) {
            super(itemView);

            image = itemView.findViewById(R.id.imageHouse);
            price = itemView.findViewById(R.id.textViewPrice);
            description = itemView.findViewById(R.id.textViewDescription);
            bedrooms = itemView.findViewById(R.id.textViewBedroomsNumber);
            bathrooms = itemView.findViewById(R.id.textViewBathroomsNumber);
            size = itemView.findViewById(R.id.textViewHouseSize);
            zip = itemView.findViewById(R.id.zipCode);
            city = itemView.findViewById(R.id.city);
            cardView = itemView.findViewById(R.id.houseCardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectListenerInterface != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            selectListenerInterface.onItemClicked(position);
                        }
                    }
                }
            });
        }
    }
}
