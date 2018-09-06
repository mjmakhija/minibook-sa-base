package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.miniorm.MiniORM;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public abstract class BaseSer<T extends Object>
{

	private MiniORM myORM;
	private BaseDAO<T> baseDAO;

	public void setBaseDAO(BaseDAO<T> baseDAO)
	{
		this.baseDAO = baseDAO;
	}

	public void setMyORM(MiniORM myORM)
	{
		this.myORM = myORM;
	}

	public List<T> retrieve()
	{
		return baseDAO.retrieve();
	}

	public T retrieveByName(String vName)
	{
		return baseDAO.retrieveByName(vName);
	}

	public boolean create(T agent, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidCreate(agent, errorMsg))
		{
			return false;
		}

		try
		{
			if (!baseDAO.create(agent))
			{
				myORM.getConn().rollback();
				return false;
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

	public boolean update(T agent, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidUpdate(agent, errorMsg))
		{
			return false;
		}

		try
		{
			if (!baseDAO.update(agent))
			{
				myORM.getConn().rollback();
				return false;
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

	public boolean delete(T agent)
	{

		try
		{
			if (!baseDAO.delete(agent))
			{
				myORM.getConn().rollback();
				return false;
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

	public boolean delete(int id)
	{
		return delete(getById(id));
	}

	public T getById(int id)
	{
		return baseDAO.retrieveById(id);
	}

	abstract boolean checkIsValidCreate(T agent, StringBuilder errorMsg);

	abstract boolean checkIsValidUpdate(T agent, StringBuilder errorMsg);

	abstract boolean checkIsValid(T agent, StringBuilder errorMsg);

}
