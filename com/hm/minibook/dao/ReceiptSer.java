package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.ReceiptDTO;
import com.hm.minibook.dto.ReceiptDTO;
import com.hm.miniorm.MiniORM;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ReceiptSer extends BaseSer<ReceiptDTO>
{

	MiniORM myORM;
	ReceiptDAO receiptDAO;

	public ReceiptSer()
	{
		this.myORM = Container.myORMCompanyYear;
		receiptDAO = new ReceiptDAO(myORM);

		super.setBaseDAO(receiptDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(ReceiptDTO receipt, StringBuilder errorMsg)
	{
		if (!checkIsValid(receipt, errorMsg))
		{
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && receiptDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(ReceiptDTO receipt, StringBuilder errorMsg)
	{
		if (!checkIsValid(receipt, errorMsg))
		{
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValid(ReceiptDTO receipt, StringBuilder errorMsg)
	{

		if (receipt == null)
		{
			errorMsg.append("Receipt dto is null");
			return false;
		}

		return true;
	}

	public BigDecimal getSum(int customerId, Date dateFrom, Date dateTo)
	{
		return receiptDAO.getSum(customerId, dateFrom, dateTo);
	}

	public List<ReceiptDTO> retrieve(Integer supplierId, Date dateFrom, Date dateTo)
	{
		return receiptDAO.retrieve(supplierId, dateFrom, dateTo);
	}

}
