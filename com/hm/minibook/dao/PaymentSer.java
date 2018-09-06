package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.PaymentDTO;
import com.hm.miniorm.MiniORM;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PaymentSer extends BaseSer<PaymentDTO>
{

	MiniORM myORM;
	PaymentDAO paymentDAO;

	public PaymentSer()
	{
		this.myORM = Container.myORMCompanyYear;
		paymentDAO = new PaymentDAO(myORM);

		super.setBaseDAO(paymentDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(PaymentDTO payment, StringBuilder errorMsg)
	{
		if (!checkIsValid(payment, errorMsg))
		{
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && paymentDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(PaymentDTO payment, StringBuilder errorMsg)
	{
		if (!checkIsValid(payment, errorMsg))
		{
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValid(PaymentDTO payment, StringBuilder errorMsg)
	{

		if (payment == null)
		{
			errorMsg.append("Payment dto is null");
			return false;
		}

		return true;
	}

	public BigDecimal getSum(int supplierId, Date dateFrom, Date dateTo)
	{
		return paymentDAO.getSum(supplierId, dateFrom, dateTo);
	}

	public List<PaymentDTO> retrieve(Integer supplierId, Date dateFrom, Date dateTo)
	{
		return paymentDAO.retrieve(supplierId, dateFrom, dateTo);
	}
}
