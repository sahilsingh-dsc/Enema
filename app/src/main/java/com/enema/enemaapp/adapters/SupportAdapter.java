package com.enema.enemaapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.SupportData;

import java.util.List;

public class SupportAdapter extends RecyclerView.Adapter<SupportAdapter.SupportViewHolder> {

    List<SupportData> supportDataList;
    Context context;

    public SupportAdapter(List<SupportData> supportDataList, Context context) {
        this.supportDataList = supportDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public SupportAdapter.SupportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.support_item_list, viewGroup, false);

        return new SupportViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final SupportAdapter.SupportViewHolder supportViewHolder, int i) {

        final String[] faq = {"0"};

        SupportData supportData = supportDataList.get(i);

        supportViewHolder.txtFAQ.setText(supportData.getSupport_question());
        supportViewHolder.txtFAQAnswer.setText(supportData.getSupport_answer());
        supportViewHolder.txtFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (faq[0].equals("0")){
                    supportViewHolder.txtFAQAnswer.setVisibility(View.VISIBLE);
                    faq[0] = "1";
                }else {
                    supportViewHolder.txtFAQAnswer.setVisibility(View.GONE);
                    faq[0] = "0";
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return supportDataList.size();
    }

    public class SupportViewHolder extends RecyclerView.ViewHolder {

        TextView txtFAQ, txtFAQAnswer;

        public SupportViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFAQ = (TextView) itemView.findViewById(R.id.txtFAQ);
            txtFAQAnswer = (TextView) itemView.findViewById(R.id.txtFAQAnswer);

        }
    }
}
