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

/**
 * Created by passioninfinite on 18/3/18.
 */

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.MyViewHolder> {

    public List<Job> jobs;

    public String companyEmail;

    private final static int FOOTER = 1;

    private final static int JOB_VIEW = 0;

    private String colors[] = {"#7B241C","#4A235A","#154360","#145A32","#7D6608","#BA4A00","#212F3C","#9B0F08","#0F0E0E","#2E1332"};


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, description, prerequisite, type, salary, experience, email;

        public MyViewHolder(View view) {
            super(view);
            CardView cardView = view.findViewById(R.id.card_view_jobs);
            CardView contactView = view.findViewById(R.id.card_view_contact);

            name = view.findViewById(R.id.job_name);
            description = view.findViewById(R.id.job_description);
            prerequisite = view.findViewById(R.id.job_requisite);
            salary = view.findViewById(R.id.job_salary);
            experience = view.findViewById(R.id.job_experience);
            email = view.findViewById(R.id.email);
        }
    }

    public JobsAdapter(List<Job> jobs) {
        this.jobs = jobs;
    }


    @Override
    public int getItemViewType(int position) {
        Log.d("check_position", String.valueOf(position));
        if (position == jobs.size()) {
            return FOOTER;
        } else {
            return JOB_VIEW;
        }
    }

    @NonNull
    @Override
    public JobsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case JOB_VIEW:
                itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.company_details, parent, false);
                break;
            case FOOTER:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.contact_layout, parent, false);
                break;
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == JOB_VIEW) {
            Job job = jobs.get(position);
            companyEmail = job.getCompanyEmail();
            holder.name.setText(job.getName());
            holder.prerequisite.setText(job.getPrerequisites());
            holder.experience.setText(job.getExperience());
            holder.salary.setText(job.getSalary());
            holder.description.setText(job.getDescription());
        }

        if (viewType == FOOTER) {
            holder.email.setText(companyEmail);
        }
    }

    @Override
    public int getItemCount() {
        return jobs.size() + 1;
    }
}
