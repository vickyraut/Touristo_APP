package com.polytechnic.touristo_app;

import android.view.ViewGroup;

public abstract class DrawerItem<T extends DrawerAdapter.ViewHolder> {

    protected boolean ischecked;
    public abstract T createViewHoler(ViewGroup parent);
    public abstract void bindViewHolder(T holder);

    public DrawerItem<T>setChecked(boolean ischecked){
        this.ischecked = ischecked;
        return this;
    }

    public boolean isChecked() {
        return ischecked;
    }

    public boolean isSelectable(){
        return true;
    }
}
