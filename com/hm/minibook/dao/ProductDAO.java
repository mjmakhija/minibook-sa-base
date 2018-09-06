package com.hm.minibook.dao;

import com.hm.minibook.dto.CurrentStockDTO;
import com.hm.minibook.dto.ProductDTO;
import com.hm.miniorm.MiniORM;
import java.util.List;

class ProductDAO extends BaseDAO<ProductDTO>
{

	String tableName = "product";
	String sqlGetCurrentStock
			= " SELECT"
			+ "	*,"
			+ "	("
			+ "		(SELECT IFNULL(SUM(quantity),0) FROM opening_stock WHERE opening_stock.product_id =  product.id) "
			+ "		+ (SELECT IFNULL(SUM(qty),0) FROM purchase_item WHERE purchase_item.product_id = product.id)"
			+ "		- (SELECT IFNULL(SUM(qty),0) FROM sales_item WHERE sales_item.product_id =  product.id)"
			+ "	) as `stock`"
			+ " FROM"
			+ "	product";

	private MiniORM myORM;

	public ProductDAO(MiniORM myORM)
	{
		super(myORM);
		this.myORM = myORM;
	}

	@Override
	String getTableName()
	{
		return tableName;
	}

	public List<CurrentStockDTO> getCurrentStock()
	{
		return myORM.get(CurrentStockDTO.class, sqlGetCurrentStock);
	}

}
