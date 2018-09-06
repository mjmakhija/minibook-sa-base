package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.GV;
import com.hm.minibook.dto.CurrentStockDTO;
import com.hm.minibook.dto.OpeningStockDTO;
import com.hm.minibook.dto.ProductDTO;
import com.hm.miniorm.MiniORM;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class ProductSer extends BaseSer<ProductDTO>
{

	MiniORM myORM;
	ProductDAO productDAO;

	public ProductSer()
	{
		this.myORM = Container.myORMCompanyYear;
		productDAO = new ProductDAO(myORM);

		super.setBaseDAO(productDAO);
		super.setMyORM(myORM);
	}

	public boolean create(ProductDTO product, List<OpeningStockDTO> openingStocks, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidCreate(product, errorMsg))
		{
			return false;
		}

		try
		{
			if (!productDAO.create(product))
			{
				myORM.getConn().rollback();
				return false;
			}

			OpeningStockDAO openingStockDAO = new OpeningStockDAO(myORM);
			for (OpeningStockDTO openingStock : openingStocks)
			{
				openingStock.setProductId(product.getId());
				if (!openingStockDAO.create(openingStock))
				{
					myORM.getConn().rollback();
					return false;
				}
			}

			myORM.getConn().commit();
			return true;
		}
		catch (SQLException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}
	}

	public boolean update(ProductDTO product, List<OpeningStockDTO> openingStocks, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidUpdate(product, errorMsg))
		{
			return false;
		}

		OpeningStockDAO openingStockDAO = new OpeningStockDAO(myORM);

		try
		{
			//Update product
			if (!productDAO.update(product))
			{
				myORM.getConn().rollback();
				return false;
			}

			for (OpeningStockDTO openingStock : openingStocks)
			{

				if (openingStock.getId() == 0)
				{
					openingStock.setProductId(product.getId());
					if (!openingStockDAO.create(openingStock))
					{
						myORM.getConn().rollback();
						return false;
					}
				}
				else
				{
					if (!openingStockDAO.update(openingStock))
					{
						myORM.getConn().rollback();
						return false;
					}
				}
			}

			openingStockDAO.deleteExtra(product.getId(), openingStocks);

			myORM.getConn().commit();
			return true;
		}
		catch (SQLException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}
	}
	
	public List<CurrentStockDTO> getCurrentStock()
	{
		return productDAO.getCurrentStock();
	}

	@Override
	boolean checkIsValidCreate(ProductDTO product, StringBuilder errorMsg)
	{
		if (!checkIsValid(product, errorMsg))
		{
			return false;
		}

		ProductDTO productFound = productDAO.retrieveByName(product.getName());
		if (productFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		if (GV.regStatus == GV.RegStatus.UNREGISTERED && productDAO.retrieve().size() >= 25)
		{
			errorMsg.append("You cannot make more than 25 entries in trial version");
			return false;
		}

		return true;
	}

	@Override
	boolean checkIsValidUpdate(ProductDTO product, StringBuilder errorMsg)
	{
		if (!checkIsValid(product, errorMsg))
		{
			return false;
		}

		ProductDTO productFound = productDAO.retrieveByName(product.getName());
		if (productFound != null && product.getId() != productFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	@Override
	boolean checkIsValid(ProductDTO product, StringBuilder errorMsg)
	{

		if (product == null)
		{
			errorMsg.append("Product dto is null");
			return false;
		}

		if (product.getName() == null || product.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
