package com.builderfly.parsing_app;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {


    private static final String TAG = ShopListAdapter.class.getSimpleName();
    public AppCompatActivity mContext;
    private List<AllCategoriesDataModel> allCategorieslist;
    View view;

    /**
     * @param mContext          :Context
     * @param allCategorieslist :List of All categories
     * @return
     * @throws
     * @purpose to initialize Shop List adapter.
     */
    public ShopListAdapter(AppCompatActivity mContext, List<AllCategoriesDataModel> allCategorieslist) {
        this.mContext = mContext;
        this.allCategorieslist = allCategorieslist;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_shop_item_1, parent, false);
            return new ViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_shop_item_2, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AllCategoriesDataModel allCategoriesDataModel = allCategorieslist.get(position);
        holder.mTxtShopItemCategory.setText(allCategoriesDataModel.getCategoryName());
        CommonUtils.loadImage(mContext, allCategoriesDataModel.getCategoryImage(), holder.mIvShopItemCat);

        /*RelativeLayout.LayoutParams layoutarams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (position == 0) {
            layoutarams.setMarginEnd(PixelUtils.dpToPx(mContext, 50));
            layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutarams.addRule(RelativeLayout.CENTER_VERTICAL);
            holder.mLlShopItemRoot.setLayoutParams(layoutarams);
        }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemViewType(int position) {

        if (position % 2 == 0) {
            return 1;
        } else {
            return 2;
        }
    }


    @Override
    public int getItemCount() {
        return allCategorieslist.size();
    }

    /**
     * @param
     * @return
     * @throws
     * @purpose to initialize Shopping-Item row view.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtShopItemCategory;
        private TextView mTxtShopItemQty;
        private RelativeLayout mRltShopItemRoot;
        private LinearLayout mLlShopItemRoot;
        private ImageView mIvShopItemCat;

        public ViewHolder(View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(View itemView) {
            mTxtShopItemCategory = (TextView) itemView.findViewById(R.id.txt_shop_item_category);
            mTxtShopItemQty = (TextView) itemView.findViewById(R.id.txt_shop_item_qty);
            mRltShopItemRoot = itemView.findViewById(R.id.rlt_shop_item_root);
            mLlShopItemRoot = itemView.findViewById(R.id.ll_shop_item_root);
            mIvShopItemCat = itemView.findViewById(R.id.img_shop_item_cat);
        }

    }

}
