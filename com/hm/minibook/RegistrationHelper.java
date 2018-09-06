package com.hm.minibook;

import com.hm.minibook.dao.app.AppInfoSer;
import com.hm.minibook.dao.app.UpdateLogSer;
import com.hm.minibook.dto.api.RegisterResDTO;
import com.hm.utilities.Util;
import java.io.File;
import java.util.UUID;
import java.util.logging.Level;

public class RegistrationHelper
{

	AppInfoSer appInfoSer;

	public RegistrationHelper()
	{

		appInfoSer = new AppInfoSer();
	}

	public void checkRegistration()
	{
		// 3. Instance key is null
		if (this.isInstanceKeyNull()) //Instance key null = new installation
		{
			this.mGroup5to8();
		}
		else // 4. Instance key is not null	
		{
			// 4.1. Verification file exists for given instance key
			if (this.isInstanceFileInDir())
			{
				if (this.isAppRegisteredLocally())
				{
					if (this.isInternetAvailable())
					{
						if (this.isAppRegisteredOnServer())
						{
							GV.isVerifiedOnline = true;
							this.updateGV();
						}
						else
						{
							this.mGroup7to8();
						}
					}
					else
					{
						this.updateGV();
					}
				}
				else
				{
					this.updateGV();
				}
			}
			else
			{
				this.mGroup5to8();
			}
		}
	}

	// 2. Get instance key
	private String getInstanceKey()
	{
		return appInfoSer.get(AppInfoSer.AppInfoKey.InstanceKey);
	}

	// 3. Instance key is null
	private boolean isInstanceKeyNull()
	{
		return (this.getInstanceKey() == null || this.getInstanceKey().equals(""));
	}

	// 4.1 Verification file exists for given instance key
	private boolean isInstanceFileInDir()
	{
		File f = new File(GV.APP_DATA_DIR + "/" + this.getInstanceKey() + ".acso.sys");
		return (f.exists() && !f.isDirectory());
	}

	// 4.1.3 App is registered locally
	private boolean isAppRegisteredLocally()
	{
		return appInfoSer.get(AppInfoSer.AppInfoKey.StatusId).equals("2");
	}

	// 4.1.3.2 Internet is available
	private boolean isInternetAvailable()
	{
		return Util.mIsNetConnected();
	}

	// 4.1.3.2.2. App is registered on server
	private boolean isAppRegisteredOnServer()
	{

		String vAgentKey = appInfoSer.get(AppInfoSer.AppInfoKey.AgentKey);
		String vClientKey = appInfoSer.get(AppInfoSer.AppInfoKey.ClientKey);
		String vInstanceKey = appInfoSer.get(AppInfoSer.AppInfoKey.InstanceKey);
		String vName = appInfoSer.get(AppInfoSer.AppInfoKey.Name);
		String version = new UpdateLogSer().getLatestInstalledVersion().getVersion();
		String updaterVersion = appInfoSer.get(AppInfoSer.AppInfoKey.UpdaterVersion);

		RegisterResDTO registerResDTO = new APIHelper(
				vAgentKey,
				vClientKey,
				vInstanceKey,
				GV.APP_TYPE_ID,
				vName,
				version,
				updaterVersion
		).verify();

		if (registerResDTO == null)
		{
			return true;
		}
		return registerResDTO.isDone();
	}

	// 5. Generate instance key
	private void generateAndSaveInstanceKey()
	{
		String vInstanceKey = UUID.randomUUID().toString().replace("-", "");
		appInfoSer.set(AppInfoSer.AppInfoKey.InstanceKey, vInstanceKey);
	}

	// 6. Generate verification file
	private boolean generateVerificationFile()
	{
		String vFilePath = GV.APP_DATA_DIR + "/" + this.getInstanceKey() + ".acso.sys";

		try
		{
			File file = new File(vFilePath);
			return (file.createNewFile());
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}
	}

	// 7. Set app status to un registered
	private void setAppStatusUnregistered()
	{
		appInfoSer.set(AppInfoSer.AppInfoKey.StatusId, "1");
	}

	// 8.Proceed
	private void updateGV()
	{
		if (appInfoSer.get(AppInfoSer.AppInfoKey.StatusId).equals("1"))
		{
			GV.regStatus = GV.RegStatus.UNREGISTERED;
		}
		else if (appInfoSer.get(AppInfoSer.AppInfoKey.StatusId).equals("2"))
		{
			GV.regStatus = GV.RegStatus.REGISTERED;
		}
	}

	private void mGroup5to8()
	{
		this.generateAndSaveInstanceKey();
		this.generateVerificationFile();
		this.mGroup7to8();
	}

	private void mGroup7to8()
	{
		this.setAppStatusUnregistered();
		this.updateGV();
	}

	public void verifyOnServer()
	{
		if (this.isInternetAvailable())
		{
			if (this.isAppRegisteredOnServer())
			{
				GV.isVerifiedOnline = true;
				this.updateGV();
			}
			else
			{
				this.mGroup7to8();
			}
		}
	}
}
