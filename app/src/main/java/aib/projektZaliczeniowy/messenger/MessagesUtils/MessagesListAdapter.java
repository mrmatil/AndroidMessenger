package aib.projektZaliczeniowy.messenger.MessagesUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import aib.projektZaliczeniowy.messenger.R;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.ViewHolder> {

    private MessagesListData[] data;

    public MessagesListAdapter(MessagesListData[] data){
        this.data=data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item,parent,false);
        ViewHolder viewholder = new ViewHolder(listItem);
        return viewholder;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        final MessagesListData myData = data[position];

    }



}
