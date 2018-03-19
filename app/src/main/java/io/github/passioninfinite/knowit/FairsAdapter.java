package io.github.passioninfinite.knowit;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by passioninfinite on 18/3/18.
 */

public class FairsAdapter extends RecyclerView.Adapter<FairsAdapter.MyViewHolder>{


    private List<Fair> fairList;

    private String colors[] = {"#7B241C","#4A235A","#154360","#145A32","#7D6608","#BA4A00","#212F3C","#9B0F08","#0F0E0E","#2E1332"};

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, location, start_time, end_time, date;

        public MyViewHolder(View view) {
            super(view);
            CardView cardView = (CardView)view.findViewById(R.id.cardView);
            Random random = new Random();
            String color = colors[random.nextInt(colors.length)];
            cardView.setCardBackgroundColor(Color.parseColor(color));
            name = (TextView) view.findViewById(R.id.title);
            location = (TextView) view.findViewById(R.id.location_name);
            start_time = (TextView) view.findViewById(R.id.start_time);
            end_time = (TextView) view.findViewById(R.id.end_time);
            date = (TextView) view.findViewById(R.id.date);
        }
    }

    public FairsAdapter(List<Fair> fairList) {
        this.fairList = fairList;
    }

    @NonNull
    @Override
    public FairsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FairsAdapter.MyViewHolder holder, int position) {
        Fair fair = fairList.get(position);
        holder.name.setText(fair.getName());
        holder.location.setText(fair.getLocation());
        holder.start_time.setText(fair.getStartTime());
        holder.end_time.setText(fair.getEndTime());
        holder.date.setText(fair.getDate());
    }

    @Override
    public int getItemCount() {
        Log.d("count_check", String.valueOf(fairList.size()));
        return fairList.size();
    }
}
