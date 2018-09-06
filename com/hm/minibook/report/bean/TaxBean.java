package com.hm.minibook.report.bean;

import com.hm.minibook.dto.TaxDTO;
import com.hm.utilities.Util;
import java.math.BigDecimal;

public class TaxBean
{

	private TaxDTO tax;
	private BigDecimal taxableAmount;
	private BigDecimal cgstA;
	private BigDecimal sgstA;
	private BigDecimal igstA;

	public TaxBean(TaxDTO tax, BigDecimal taxableAmount, BigDecimal cgst, BigDecimal sgst, BigDecimal igst)
	{
		this.tax = tax;
		this.taxableAmount = taxableAmount;
		this.cgstA = cgst;
		this.sgstA = sgst;
		this.igstA = igst;
	}

	public TaxDTO getTax()
	{
		return tax;
	}

	public BigDecimal getTaxableAmount()
	{
		return taxableAmount;
	}

	public void setTaxableAmount(BigDecimal taxableAmount)
	{
		this.taxableAmount = taxableAmount;
	}

	public BigDecimal getCgst()
	{
		return cgstA;
	}

	public void setCgst(BigDecimal cgst)
	{
		this.cgstA = cgst;
	}

	public BigDecimal getSgst()
	{
		return sgstA;
	}

	public void setSgst(BigDecimal sgst)
	{
		this.sgstA = sgst;
	}

	public BigDecimal getIgst()
	{
		return igstA;
	}

	public void setIgst(BigDecimal igst)
	{
		this.igstA = igst;
	}

	public String getNameString()
	{
		return tax.getName();
	}

	public String getTaxableAmountString()
	{
		return Util.converNumberToLocalFormat(taxableAmount.doubleValue());
	}

	public String getCgstPString()
	{
		return cgstA.compareTo(BigDecimal.ZERO) == 0 ? "-" : Util.converPercentageToLocalFormat(tax.getCgst().doubleValue()) + " %";
	}

	public String getSgstPString()
	{
		return sgstA.compareTo(BigDecimal.ZERO) == 0 ? "-" : Util.converPercentageToLocalFormat(tax.getSgst().doubleValue()) + " %";
	}

	public String getIgstPString()
	{
		return igstA.compareTo(BigDecimal.ZERO) == 0 ? "-" : Util.converPercentageToLocalFormat(tax.getIgst().doubleValue()) + " %";
	}

	public String getCgstString()
	{
		return cgstA.compareTo(BigDecimal.ZERO) == 0 ? "-" : Util.converNumberToLocalFormat(cgstA.doubleValue());
	}

	public String getSgstString()
	{
		return sgstA.compareTo(BigDecimal.ZERO) == 0 ? "-" : Util.converNumberToLocalFormat(sgstA.doubleValue());
	}

	public String getIgstString()
	{
		return igstA.compareTo(BigDecimal.ZERO) == 0 ? "-" : Util.converNumberToLocalFormat(igstA.doubleValue());
	}

	public String getTotalString()
	{
		return Util.converNumberToLocalFormat(cgstA.add(sgstA).add(igstA).doubleValue());
	}

}
