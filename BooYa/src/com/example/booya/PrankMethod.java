package com.example.booya;

public class PrankMethod 
{
	private int n_prank_id;
	private int n_img_res_id;
	private boolean b_purchased;
	
	public PrankMethod(int prank_id,int img_res_id,int purchased)
	{
		n_prank_id = prank_id;
		n_img_res_id = img_res_id;
		b_purchased = (purchased == 1);
	}
	
	public int getPrankID()
	{
		return this.n_prank_id;
	}
	
	public int getImgResID()
	{
		return this.n_img_res_id;
	}
	public boolean getPurchased()
	{
		return this.b_purchased;
	}
	
}

