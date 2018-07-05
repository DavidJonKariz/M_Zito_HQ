package com.example.dijonkariz.m_zito_hq;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.MyViewHolder>
{
    private Context mContext;
    private List<Option> optionList;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public int number;
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view)
        {
            super(view);
            number = 0;
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }
    public OptionsAdapter(Context mContext, List<Option> optionList)
    {
        this.mContext = mContext;
        this.optionList = optionList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        Option option = optionList.get(position);
        holder.title.setText(option.getName());
        Glide.with(mContext).load(option.getThumbnail()).into(holder.thumbnail);
        holder.number = option.getNumOfOptions();
        //setonclicklistener
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, ""+holder.number, Toast.LENGTH_SHORT).show();
                check_intent(holder.number);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return optionList.size();
    }

    public View check_intent(int i)
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (i == 13)
        {
//            Intent intent = new Intent(mContext, Record_Wrkout.class);
//            mContext.startActivity(intent);
            Toast.makeText(mContext, "Record Session", Toast.LENGTH_SHORT).show();
            return inflater.inflate(R.layout.workout_record, null, false);
        }
        else if (i == 8)
        {
            Toast.makeText(mContext, "Gym Location", Toast.LENGTH_SHORT).show();
            return inflater.inflate(R.layout.workout_record, null, false);
        }
        else if (i == 11)
        {
            Toast.makeText(mContext, "Gym Instructor", Toast.LENGTH_SHORT).show();
            return inflater.inflate(R.layout.workout_past, null, false);
        }
        else if (i == 12)
        {
            Toast.makeText(mContext, "Diet Plan", Toast.LENGTH_SHORT).show();
            return inflater.inflate(R.layout.workout_diet_plan, null, false);
        }
        else if (i == 14)
        {
            Toast.makeText(mContext, "Past Workout", Toast.LENGTH_SHORT).show();
            return inflater.inflate(R.layout.workout_past, null, false);
        }
        else if (i == 1)
        {
            Toast.makeText(mContext, "Add Workout", Toast.LENGTH_SHORT).show();
            return inflater.inflate(R.layout.workout_add, null, false);
        }
        return inflater.inflate(null, null, false);
    }
}
