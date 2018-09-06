package com.hm.minibook.dao;

import com.hm.minibook.dao.DAOHelper;
import com.hm.minibook.dto.PaymentDTO;
import com.hm.miniorm.MiniORM;
import com.hm.miniorm.SqlHelper;
import com.hm.utilities.Util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class PaymentDAO extends BaseDAO<PaymentDTO>
{

	String tableName = "payment";
	String sqlGetSum = "SELECT SUM(amount) FROM %s";

	private MiniORM myORM;

	public PaymentDAO(MiniORM myORM)
	{
		super(myORM);
		this.myORM = myORM;
	}

	@Override
	String getTableName()
	{
		return tableName;
	}

	public BigDecimal getSum(int supplierId, Date dateFrom, Date dateTo)
	{
		return DAOHelper.getSum(myORM, sqlGetSum, tableName, "supplier_id", supplierId, dateFrom, dateTo);
	}

	public List<PaymentDTO> retrieve(Integer supplierId, Date dateFrom, Date dateTo)
	{
		String sql = String.format(sqlSelect, tableName);
		List<String> wheres = new ArrayList<>();

		if (supplierId != null)
		{
			wheres.add(tableName + ".supplier_id=" + supplierId);
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

		return myORM.get(PaymentDTO.class, sql);

	}
}
