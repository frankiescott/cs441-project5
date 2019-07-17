package edu.binghamton.project5;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<String> mData;
    private LayoutInflater mInflater;
    private ListChanged responder; //used to talk to main activity when data changes

    RecyclerAdapter(Context context, List<String> data, ListChanged responder) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.responder = responder;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String s = mData.get(position);
        holder.myTextView.setText(s);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.recyclecell);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public void delete(int position) {
        mData.remove(position);
        responder.calculateTotal();
        notifyItemRemoved(position);
    }
}