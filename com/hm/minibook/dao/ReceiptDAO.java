package com.hm.minibook.dao;

import com.hm.minibook.dao.DAOHelper;
import com.hm.minibook.dto.ReceiptDTO;
import com.hm.miniorm.MiniORM;
import com.hm.miniorm.SqlHelper;
import com.hm.utilities.Util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class ReceiptDAO extends BaseDAO<ReceiptDTO>
{

	String tableName = "receipt";
	String sqlGetSum = "SELECT SUM(amount) FROM %s";

	private MiniORM myORM;

	public ReceiptDAO(MiniORM myORM)
	{
		super(myORM);
		this.myORM = myORM;
	}

	@Override
	String getTableName()
	{
		return tableName;
	}

	public BigDecimal getSum(int customerId, Date dateFrom, Date dateTo)
	{
		return DAOHelper.getSum(myORM, sqlGetSum, tableName, "customer_id", customerId, dateFrom, dateTo);
	}

	public List<ReceiptDTO> retrieve(Integer customerId, Date dateFrom, Date dateTo)
	{
		String sql = String.format(sqlSelect, tableName);
		List<String> wheres = new ArrayList<>();

		if (customerId != null)
		{
			wheres.add(tableName + ".customer_id=" + customerId);
		}
		if (dateFrom != null)
		{
			wheres.add("date(" + tableName + ".date)>=date('" + Util.getDBDateString(dateFrom) + "')");
		}
		if (dateTo != null)
		{
			wheres.add("date(" + tableName + ".date)<=date('" + Util.getDBDateString(dateTo) + "')");
		}

		sql = SqlHelper.putWheres(wheres, sql);

		return myORM.get(ReceiptDTO.class, sql);

	}

}
