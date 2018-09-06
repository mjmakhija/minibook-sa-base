package com.hm.minibook.dao.app;

import com.hm.minibook.Container;
import com.hm.minibook.dto.app.UpdateLogDTO;
import com.hm.miniorm.MiniORM;
import java.util.List;
import java.util.logging.Level;

class UpdateLogDAO
{

	MiniORM myORM;

	public UpdateLogDAO(MiniORM myORM)
	{
		this.myORM = myORM;
	}

	public boolean create(UpdateLogDTO vObj)
	{
		boolean result;

		try
		{
			myORM.insert(vObj);
			result = true;
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			result = false;
		}

		return result;

	}

	public boolean update(UpdateLogDTO vObj)
	{
		boolean result;

		try
		{
			myORM.update(vObj);
			result = true;
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			result = false;
		}

		return result;

	}

	public List<UpdateLogDTO> retrieve()
	{
		String sql = "SELECT * FROM update_log";
		return myORM.get(UpdateLogDTO.class, sql);
	}

	public UpdateLogDTO retrieveLatestInstalledVersion()
	{
		String sql = "SELECT * FROM update_log ORDER BY id DESC";

		List<UpdateLogDTO> listUpdateLogDTOs = myORM.get(UpdateLogDTO.class, sql);

		return listUpdateLogDTOs.get(0);
	}

}
