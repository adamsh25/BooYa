package com.example.booya;

import android.R.integer;
import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class PrankMethod implements Parcelable
{
	private int n_prank_id;
	private int n_img_res_id;
	private boolean b_purchased;
	
	public PrankMethod(int prank_id,int img_res_id,int purchased)
	{
		setN_prank_id(prank_id);
		n_img_res_id = img_res_id;
		b_purchased = (purchased == 1);
	}
	
	public int getPrankID()
	{
		return this.getN_prank_id();
	}
	
	public int getImgResID()
	{
		return this.n_img_res_id;
	}
	public boolean getPurchased()
	{
		return this.b_purchased;
	}
	// Parcel stuff
	
	@SuppressLint("UseValueOf")
	public PrankMethod(Parcel in)
	{
		int[] data = new int[3];
		in.readIntArray(data);
		setN_prank_id(data[0]);
		n_img_res_id = data[1];
		b_purchased = (data[2] == 1);
	}
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		  dest.writeIntArray(new int[] {this.getN_prank_id(),
                  this.n_img_res_id,
                  (this.b_purchased? 1 : 0)});	
		  
			}

    public int getN_prank_id() {
		return n_prank_id;
	}

	public void setN_prank_id(int n_prank_id) {
		this.n_prank_id = n_prank_id;
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() 
    {
        public PrankMethod createFromParcel(Parcel in) 
        {
            return new PrankMethod(in); 
        }
        public PrankMethod[] newArray(int size) 
        {
            return new PrankMethod[size];
        }
    };
}
