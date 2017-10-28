package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.customsms.R;

/**
 * Created by zigza on 10/28/2017.
 */

public class MessageDisplayAdapter extends RecyclerView.Adapter<MessageDisplayAdapter.MessageDisplayAdapterViewHolder> {
    private


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
