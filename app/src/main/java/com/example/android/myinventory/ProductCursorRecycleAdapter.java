package com.example.android.myinventory;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myinventory.data.ProductContract;

/**
 * Created by JukUm on 6/12/2017.
 */

public class ProductCursorRecycleAdapter extends CursorRecycleAdapter<ProductCursorRecycleAdapter.ViewHolder> {

    private MainActivity activity;

    public ProductCursorRecycleAdapter(MainActivity context, Cursor cursor) {
        super(context, cursor);
        this.activity = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private TextView productPrice;
        private TextView productQuantity;
        private ImageView productPicture;
        private Button productBuy;
        private LinearLayout itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            productPrice = (TextView) itemView.findViewById(R.id.product_price);
            productQuantity = (TextView) itemView.findViewById(R.id.product_quantity);
            productBuy = (Button) itemView.findViewById(R.id.button_productBuy);
            productPicture = (ImageView) itemView.findViewById(R.id.product_image);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {

        final long id;
        final int mQuantity;

        id =cursor.getLong(cursor.getColumnIndex(ProductContract.ProductEntry._ID));
        int nameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int pictureColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_IMAGE);

        String productName = cursor.getString(nameColumnIndex);
        String productPrice = cursor.getString(priceColumnIndex);
        int quantity = cursor.getInt(quantityColumnIndex);
        String imageUriString = cursor.getString(pictureColumnIndex);
        Uri imageUri = Uri.parse(imageUriString);

        mQuantity = quantity;

        viewHolder.productName.setText(productName);
        viewHolder.productPrice.setText(productPrice);
        viewHolder.productQuantity.setText(String.valueOf(quantity));
        viewHolder.productPicture.setImageURI(imageUri);
        viewHolder.productPicture.invalidate();

        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onItemClick(id);
            }
        });

        viewHolder.productBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mQuantity >0 ) {
                    activity.onBuyButtonClick(id, mQuantity);
                } else {
                    Toast.makeText(activity, R.string.toast_noItems, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}