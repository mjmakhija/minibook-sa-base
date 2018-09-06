package com.hm.minibook.dao;

import com.hm.minibook.dto.OpeningStockDTO;
import com.hm.miniorm.MiniORM;
import java.math.BigDecimal;
import java.util.List;

class OpeningStockDAO extends BaseDAO<OpeningStockDTO>
{

	String tableName = "opening_stock";
	String sqlGetByProductId = "SELECT * FROM %s WHERE product_id = %s";
	String sqlDeleteExtras = "DELETE FROM %s WHERE product_id = %s AND id NOT IN (%s)";
	String sqlGetOpeningStockByProductId = "SELECT SUM(quantity) FROM %s WHERE product_id = %s";

	private MiniORM myORM;

	public OpeningStockDAO(MiniORM myORM)
	{
		super(myORM);
		this.myORM = myORM;
	}

	@Override
	String getTableName()
	{
		return tableName;
	}

	public List<OpeningStockDTO> getByProductId(int productId)
	{
		String sql = String.format(sqlGetByProductId, tableName, String.valueOf(productId));
		return myORM.get(OpeningStockDTO.class, sql);
	}

	public boolean deleteExtra(int productId, List<OpeningStockDTO> openingStocks)
	{

		int[] nos = new int[openingStocks.size()];
		for (int i = 0; i < openingStocks.size(); i++)
		{
			nos[i] = openingStocks.get(i).getId();
		}

		String sql = String.format(sqlDeleteExtras, tableName, productId, DAOHelper.getCSV(nos));
		return myORM.execute(sql);

	}

	public BigDecimal getOpeningStockByProductId(int productId)
	{
		String sql = String.format(sqlGetOpeningStockByProductId, tableName, String.valueOf(productId));

		List<BigDecimal> values = myORM.get(BigDecimal.class, sql);

		if (values == null || values.get(0) == null)
		{
			return BigDecimal.ZERO;
		}

		return values.get(0);
	}

}
