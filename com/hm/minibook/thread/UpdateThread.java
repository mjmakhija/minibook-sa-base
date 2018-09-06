package com.hm.minibook.thread;

import com.hm.miniorm.MiniORM;

public class UpdateThread extends Thread
{

	MiniORM myORM;

	public UpdateThread(MiniORM myORM)
	{
		this.myORM = myORM;
	}

	@Override
	public void run()
	{
		/*

		UpdateLogSer updateLogSer;
		updateLogSer = new UpdateLogSer(myORM);

		boolean run = true;

		while (run)
		{

			try
			{
				if (Util.mIsNetConnected())
				{
					UpdateResDTO updateResDTO = new APIHelper(
						"", "", "", GV.APP_TYPE_ID, "", updateLogSer.getLatestInstalledVersion().getVersion(), "")
						.getNewUpdates();

					if (updateResDTO.getData().size() > 0)
					{

						updateLogSer = new UpdateLogSer(myORM);

						for (UpdateDataDTO updateDataDTO : updateResDTO.getData())
						{
							UpdateLogDTO updateLogDTO = new UpdateLogDTO();
							updateLogDTO.setVersion(updateDataDTO.getVersion());
							updateLogDTO.setPath(updateDataDTO.getFile_path());
							updateLogDTO.setStatusId(1);
							updateLogSer.createOrUpdate(updateLogDTO);
						}
					}

					run = false;

				}
				else
				{
					Thread.sleep(10000);
				}
			}
			catch (Exception e)
			{
				Container.logger.log(Level.SEVERE, e.getMessage(), e);
				run = false;
			}

		}
		*/

	}

}
