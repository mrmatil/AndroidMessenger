package aib.projektZaliczeniowy.messenger.messagesutils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import aib.projektZaliczeniowy.messenger.MapsActivity;
import aib.projektZaliczeniowy.messenger.R;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    //variables:
    public ArrayList<messagesClass>     messages;
    public Context                      context;
    private MyViewHolder holder;
    private int position;

    public CustomAdapter(ArrayList<messagesClass> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder (@NonNull MyViewHolder holder, int position) {
        holder.name.setText(messages.get(position).getAuthor());
        holder.message.setText(messages.get(position).getMessage());

        holder.messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String longt = messages.get(position).getLognitude();
                String latit = messages.get(position).getLangitude();
                Log.i("Coordinates",latit+longt);
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("longt",longt);
                intent.putExtra("latit",latit);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView message;
        Button messageBtn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.messagesname);
            message = itemView.findViewById(R.id.messagesmessage);
            messageBtn = itemView.findViewById(R.id.messagesButton);

        }


    }




}
