package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.math.BigDecimal;

@Table(name = "opening_stock")
public class OpeningStockDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "product_id")
	private int productId;

	@Column(name = "quantity")
	private BigDecimal quantity;

	@Column(name = "rate")
	private BigDecimal rate;

	@Column(name = "amount")
	private BigDecimal amount;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getProductId()
	{
		return productId;
	}

	public void setProductId(int productId)
	{
		this.productId = productId;
	}

	public BigDecimal getQuantity()
	{
		return quantity;
	}

	public void setQuantity(BigDecimal quantity)
	{
		this.quantity = quantity;
	}

	public BigDecimal getRate()
	{
		return rate;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

}
