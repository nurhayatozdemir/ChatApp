package com.example.nurhayat.chatapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//-------------------Adaptör Sınıfı-----------------------------
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private List<String> chatMessages;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {  //Row ları bağladık

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_list_row,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) { //Hangi stringlerin görüneceğini söyler
    String chatMessage = chatMessages.get(position);  //private List<String> chatMessages; den alıcacak hangi pozisyondaki hangi mesaj
    holder.chatMessage.setText(chatMessage);


    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    public RecyclerViewAdapter(List<String> chatMessages) {
        this.chatMessages = chatMessages;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder { //oluştrudugumuz textViewleri bağlama

        public TextView chatMessage;

        public MyViewHolder(View itemView) {
            super(itemView);

            chatMessage= itemView.findViewById(R.id.recycler_view_text_view);
        }
    }

}
