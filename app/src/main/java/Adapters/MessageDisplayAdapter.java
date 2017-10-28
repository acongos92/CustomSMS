package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.customsms.R;

import Backend.MessageSender;

/**
 * Created by zigza on 10/28/2017.
 */

public class MessageDisplayAdapter extends RecyclerView.Adapter<MessageDisplayAdapter.MessageDisplayAdapterViewHolder> {
    private MessageSender messages;
    private Context mContext;

    public MessageDisplayAdapter(Context context, MessageSender sender){
        this.messages = sender;
        this.mContext = context;
    }

    @Override
    public MessageDisplayAdapter.MessageDisplayAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.rv_vh_message_content;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachImmediate = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachImmediate);
        MessageDisplayAdapter.MessageDisplayAdapterViewHolder viewHolder = new MessageDisplayAdapter.MessageDisplayAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageDisplayAdapter.MessageDisplayAdapterViewHolder holder, int position){
        if(messages.size() < position){
            return;
        }
        String senderName = messages.getSender(position);
        String message = messages.getMessage(position);
        holder.fromView.setText(String.valueOf(senderName));
        holder.bodyView.setText(String.valueOf(message));
    }

    @Override
    public int getItemCount(){return messages.size(); }

    class MessageDisplayAdapterViewHolder extends RecyclerView.ViewHolder {
        protected TextView fromView;
        protected TextView bodyView;
        public MessageDisplayAdapterViewHolder(View itemView){
            super(itemView);
            fromView = (TextView) itemView.findViewById(R.id.tv_from_sender);
            bodyView = (TextView) itemView.findViewById(R.id.tv_body_preview);

        }
    }
}
