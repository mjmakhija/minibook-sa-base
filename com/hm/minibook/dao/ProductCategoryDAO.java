package com.hm.minibook.dao;

import com.hm.minibook.dto.ProductCategoryDTO;
import com.hm.miniorm.MiniORM;

class ProductCategoryDAO extends BaseDAO<ProductCategoryDTO>
{

	String tableName = "product_category";

	private MiniORM myORM;

	public ProductCategoryDAO(MiniORM myORM)
	{
		super(myORM);
		this.myORM = myORM;
	}

	@Override
	String getTableName()
	{
		return tableName;
	}

}
