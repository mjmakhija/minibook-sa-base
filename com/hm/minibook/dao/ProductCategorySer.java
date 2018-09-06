package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.ProductCategoryDTO;
import com.hm.miniorm.MiniORM;

public class ProductCategorySer extends BaseSer<ProductCategoryDTO>
{

	MiniORM myORM;
	ProductCategoryDAO productcategoryDAO;

	public ProductCategorySer()
	{
		this.myORM = Container.myORMCompanyYear;
		productcategoryDAO = new ProductCategoryDAO(myORM);

		super.setBaseDAO(productcategoryDAO);
		super.setMyORM(myORM);
	}

	@Override
	boolean checkIsValidCreate(ProductCategoryDTO productcategory, StringBuilder errorMsg)
	{
		if (!checkIsValid(productcategory, errorMsg))
		{
			return false;
		}

		ProductCategoryDTO productcategoryFound = productcategoryDAO.retrieveByName(productcategory.getName());
		if (productcategoryFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && productcategoryDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(ProductCategoryDTO productcategory, StringBuilder errorMsg)
	{
		if (!checkIsValid(productcategory, errorMsg))
		{
			return false;
		}

		ProductCategoryDTO productcategoryFound = productcategoryDAO.retrieveByName(productcategory.getName());
		if (productcategoryFound != null && productcategory.getId() != productcategoryFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(ProductCategoryDTO productcategory, StringBuilder errorMsg)
	{

		if (productcategory == null)
		{
			errorMsg.append("ProductCategory dto is null");
			return false;
		}

		if (productcategory.getName() == null || productcategory.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
