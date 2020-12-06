package com.sonmob.qlsvroom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sonmob.qlsvroom.R;
import com.sonmob.qlsvroom.model.Lop;

import java.util.List;

public class LopAdapter extends RecyclerView.Adapter<LopAdapter.LopViewHolder> {
    private List<Lop> mListLop;
    private IClickItemLop iClickItemLop;

    public void setDataLop(List<Lop> list) {
        this.mListLop = list;
        notifyDataSetChanged();
    }

    public interface IClickItemLop {
        void updateLop(Lop lop);
        void deleteLop(Lop lop);
    }

    public LopAdapter(IClickItemLop iClickItemLop) {
        this.iClickItemLop = iClickItemLop;
    }

    @NonNull
    @Override
    public LopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_lop, parent,false);
        return new LopViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull LopViewHolder holder, int position) {
        final Lop lop = mListLop.get(position);
        if (lop == null){
            return;
        }
        holder.tvTenlop.setText(lop.getTenlop());
        holder.tvMalop.setText(lop.getMalop());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemLop.updateLop(lop);
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemLop.deleteLop(lop);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListLop != null){
            return mListLop.size();
        }
        return 0;
    }

    public static class LopViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTenlop;
        private final TextView tvMalop;
        private ImageView ivEdit;
        private ImageView ivDelete;
        public LopViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenlop = itemView.findViewById(R.id.tv_tenlop);
            tvMalop = itemView.findViewById(R.id.tv_malop);
            ivEdit = itemView.findViewById(R.id.btn_edit);
            ivDelete = itemView.findViewById(R.id.btn_delete);
        }
    }


}
