package com.hm.minibook.dto;

import com.hm.miniorm.Column;
import com.hm.miniorm.Table;
import java.math.BigDecimal;

@Table(name = "product")
public class CurrentStockDTO
{

	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "unit_id")
	private int unitId;

	@Column(name = "hsn")
	private Integer hsn;

	@Column(name = "purchase_price")
	private BigDecimal purchasePrice;

	@Column(name = "sales_price")
	private BigDecimal salesPrice;

	@Column(name = "mrp")
	private BigDecimal mrp;

	@Column(name = "tax_id")
	private int taxId;

	@Column(name = "category_id")
	private Integer productCategoryId;

	@Column(name = "note")
	private String note;

	@Column(name = "stock")
	private BigDecimal stock;

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getUnitId()
	{
		return unitId;
	}

	public void setUnitId(int unitId)
	{
		this.unitId = unitId;
	}

	public Integer getHsn()
	{
		return hsn;
	}

	public void setHsn(Integer hsn)
	{
		this.hsn = hsn;
	}

	public BigDecimal getPurchasePrice()
	{
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice)
	{
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSalesPrice()
	{
		return salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice)
	{
		this.salesPrice = salesPrice;
	}

	public BigDecimal getMrp()
	{
		return mrp;
	}

	public void setMrp(BigDecimal mrp)
	{
		this.mrp = mrp;
	}

	public int getTaxId()
	{
		return taxId;
	}

	public void setTaxId(int taxId)
	{
		this.taxId = taxId;
	}

	public Integer getProductCategoryId()
	{
		return productCategoryId;
	}

	public void setProductCategoryId(Integer productCategoryId)
	{
		this.productCategoryId = productCategoryId;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public BigDecimal getStock()
	{
		return stock;
	}

	public void setStock(BigDecimal stock)
	{
		this.stock = stock;
	}

}
