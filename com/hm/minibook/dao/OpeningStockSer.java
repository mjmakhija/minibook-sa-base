package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.OpeningStockDTO;
import com.hm.miniorm.MiniORM;
import java.math.BigDecimal;
import java.util.List;

public class OpeningStockSer extends BaseSer<OpeningStockDTO>
{

	MiniORM myORM;
	OpeningStockDAO openingStockDAO;

	public OpeningStockSer()
	{
		this.myORM = Container.myORMCompanyYear;
		openingStockDAO = new OpeningStockDAO(myORM);

		super.setBaseDAO(openingStockDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(OpeningStockDTO openingStock, StringBuilder errorMsg)
	{
		if (!checkIsValid(openingStock, errorMsg))
		{
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && openingStockDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(OpeningStockDTO openingStock, StringBuilder errorMsg)
	{
		if (!checkIsValid(openingStock, errorMsg))
		{
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValid(OpeningStockDTO openingStock, StringBuilder errorMsg)
	{

		if (openingStock == null)
		{
			errorMsg.append("OpeningStock dto is null");
			return false;
		}

		return true;
	}

	public List<OpeningStockDTO> getByProductId(int productId)
	{
		return openingStockDAO.getByProductId(productId);
	}

	public BigDecimal getOpeningStockByProductId(int productId)
	{
		return openingStockDAO.getOpeningStockByProductId(productId);
	}

}
