package io.github.passioninfinite.knowit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Random;

/**
 * Created by passioninfinite on 18/3/18.
 */

public class FairDetailAdapter extends RecyclerView.Adapter<FairDetailAdapter.MyViewHolder> {

    public List<Company> companies;

    private String colors[] = {"#7B241C","#4A235A","#154360","#145A32","#7D6608","#BA4A00","#212F3C","#9B0F08","#0F0E0E","#2E1332"};


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView companyName, companyBranch;

        public ImageView companyImage;

        public MyViewHolder(View view) {
            super(view);
            CardView cardView = (CardView)view.findViewById(R.id.card_view_companies);
            Random random = new Random();
            String color = colors[random.nextInt(colors.length)];
            cardView.setCardBackgroundColor(Color.parseColor(color));

            companyName = view.findViewById(R.id.company_name);
            companyBranch = view.findViewById(R.id.company_branch);
            companyImage = view.findViewById(R.id.company_image);
        }
    }

    public FairDetailAdapter(List<Company> companies) {
        this.companies = companies;
    }


    @NonNull
    @Override
    public FairDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Log.d("check_object", String.valueOf(position));
        Company company = companies.get(position);
        holder.companyName.setText(company.getName());
        holder.companyBranch.setText(company.getLocation());

        if (!company.getImageUrl().isEmpty()) {
            new DownloadImageTask(holder.companyImage)
                    .execute(company.getImageUrl());
        }
    }

    @Override
    public int getItemCount() {
        Log.d("check_object", String.valueOf(companies.size()));
        return companies.size();
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
