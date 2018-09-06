package com.hm.minibook.dao.app;

import com.hm.minibook.Container;
import com.hm.minibook.dto.app.CompanyDTO;
import com.hm.miniorm.MiniORM;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class CompanyDAO
{

	String tableName = "company";
	String sqlSelect = "SELECT * FROM %s";
	String sqlSelectByName = "SELECT * FROM %s WHERE name='%s'";
	String sqlSelectById = "SELECT * FROM %s WHERE id=%s";

	MiniORM myORM;

	public CompanyDAO()
	{
		this.myORM = Container.myORMApp;
	}

	public boolean create(CompanyDTO vObj)
	{

		try
		{
			if (myORM.insert(vObj))
			{
				myORM.getConn().commit();
				return true;
			}
			else
			{
				myORM.getConn().rollback();
				return false;
			}

		}
		catch (SQLException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}

	}

	public boolean update(CompanyDTO vObj)
	{
		try
		{
			if (myORM.update(vObj))
			{
				myORM.getConn().commit();
				return true;
			}
			else
			{
				myORM.getConn().rollback();
				return false;
			}
		}
		catch (SQLException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}

	}

	public CompanyDTO retrieveById(long id)
	{

		String sql = String.format(sqlSelectByName, String.valueOf(id));
		List<CompanyDTO> unitDTOs = myORM.get(CompanyDTO.class, sql);
		if (unitDTOs.size() > 0)
		{
			return null;
		}

		return unitDTOs.get(0);
	}

	public CompanyDTO retrieveByName(String name)
	{

		String sql = String.format(sqlSelectByName, tableName, name);
		List<CompanyDTO> unitDTOs = myORM.get(CompanyDTO.class, sql);
		if (unitDTOs.size() > 0)
		{
			return null;
		}

		return unitDTOs.get(0);
	}

	public List<CompanyDTO> retrieve()
	{

		String sql = String.format(sqlSelect, tableName);
		return myORM.get(CompanyDTO.class, sql);
	}

	public boolean delete(CompanyDTO objCompany)
	{
		boolean result;
		try
		{
			myORM.delete(objCompany);
			result = true;
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			result = false;
		}

		return result;
	}

}
