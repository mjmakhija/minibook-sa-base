package com.hm.minibook.dao;

import com.hm.miniorm.MiniORM;
import java.lang.reflect.ParameterizedType;
import java.util.List;

abstract class BaseDAO<T extends Object>
{

	private Class classType;
	String sqlSelect = "SELECT * FROM %s";
	String sqlSelectByName = "SELECT * FROM %s WHERE name='%s'";
	String sqlSelectById = "SELECT * FROM %s WHERE id=%s";

	private MiniORM myORM;

	public BaseDAO(MiniORM myORM)
	{
		this.myORM = myORM;
		this.classType = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public boolean create(T vObj)
	{
		return myORM.insert(vObj);
	}

	public boolean update(T vObj)
	{
		return myORM.update(vObj);
	}

	public boolean delete(T objBank)
	{
		return myORM.delete(objBank);
	}

	public <T> T retrieveById(int id)
	{

		String sql = String.format(sqlSelectById, getTableName(), String.valueOf(id));
		List<T> dtos = myORM.get(classType, sql);
		if (dtos.size() == 0)
		{
			return null;
		}

		return dtos.get(0);
	}

	public T retrieveByName(String name)
	{

		String sql = String.format(sqlSelectByName, getTableName(), name);
		List<T> dtos = myORM.get(classType, sql);
		if (dtos.size() == 0)
		{
			return null;
		}

		return dtos.get(0);
	}

	public List<T> retrieve()
	{

		String sql = String.format(sqlSelect, getTableName());
		return myORM.get(classType, sql);
	}

	abstract String getTableName();

}
