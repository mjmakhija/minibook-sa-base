package com.hm.minibook.thread;

import com.hm.minibook.GV;
import com.hm.minibook.RegistrationHelper;

public class VerifyOnServerThread extends Thread
{

	RegistrationHelper registrationHelper;

	public VerifyOnServerThread()
	{
		registrationHelper = new RegistrationHelper();
	}

	@Override
	public void run()
	{

		registrationHelper.checkRegistration();

		boolean run = true;

		while (run)
		{

			registrationHelper.verifyOnServer();

			if (GV.regStatus == GV.RegStatus.REGISTERED && GV.isVerifiedOnline)
			{
				run = false;
			}
			else if (GV.regStatus == GV.RegStatus.UNREGISTERED)
			{
				run = false;
			}
			else
			{
				try
				{
					Thread.sleep(10000);
				}
				catch (InterruptedException ex)
				{
				}
			}
		}

	}

}
