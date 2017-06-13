package com.example.android.myinventory;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;


/**
 * Created by JukUm on 6/12/2017.
 */

public abstract class CursorRecycleAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Cursor mCursor;
    private MainActivity activity;
    private boolean mDataValidation;
    private int mColumnId;
    private DataSetObserver mDataSetObserver;

    public CursorRecycleAdapter(MainActivity context, Cursor cursor) {
        this.activity = context;
        this.mCursor = cursor;
        this.mDataValidation = mCursor != null;
        mColumnId = mDataValidation ? mCursor.getColumnIndex("_id") : -1;
        mDataSetObserver = new NotifyingDataSetObserver();
        if (mCursor != null) {
            mCursor.registerDataSetObserver(mDataSetObserver);
        }
    }

    public Cursor getCursor() {return mCursor;}

    @Override
    public int getItemCount() {
        if (mDataValidation && mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        if (mDataValidation && mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor.getLong(mColumnId);
        }
        return 0;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (!mDataValidation) {
            throw new IllegalStateException("Called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("Invalid position " + position);
        }

        onBindViewHolder(holder, mCursor);

    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        final Cursor oldCursor = mCursor;
        if (oldCursor != null && mDataSetObserver != null) {
            oldCursor.unregisterDataSetObserver(mDataSetObserver);
        }
        mCursor = newCursor;
        if (mCursor != null) {
            if (mDataSetObserver != null) {
                mCursor.registerDataSetObserver(mDataSetObserver);
            }
            mColumnId = newCursor.getColumnIndexOrThrow("_id");
            mDataValidation = true;
            notifyDataSetChanged();
        } else {
            mColumnId = -1;
            mDataValidation = false;
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    private class NotifyingDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            mDataValidation = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            mDataValidation = false;
            notifyDataSetChanged();
        }
    }
}