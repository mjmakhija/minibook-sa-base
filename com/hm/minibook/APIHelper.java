package com.hm.minibook;

import com.hm.minibook.dto.api.RegisterResDTO;
import com.hm.minibook.dto.api.UpdateResDTO;
import com.hm.minibook.dto.api.UpdaterResDTO;
import com.hm.minibook.service.api.RegisterSer;
import com.hm.minibook.service.api.UpdateSer;
import com.hm.minibook.service.api.UpdaterSer;
import com.hm.minibook.service.api.VerifySer;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHelper
{

	private Retrofit retrofit;

	String key1;
	String key2;
	String key3;
	String key4;
	String name;
	String version;
	String updaterVersion;

	int verifyTimeOutCount = 0;

	public APIHelper(String key1, String key2, String key3, String key4, String name, String version, String updaterVersion)
	{
		this.key1 = key1;
		this.key2 = key2;
		this.key3 = key3;
		this.key4 = key4;
		this.name = name;
		this.version = version;
		this.updaterVersion = updaterVersion;

		retrofit = new Retrofit.Builder()
				.baseUrl(GV.API_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
	}

	public UpdateResDTO getNewUpdates()
	{
		try
		{

			return retrofit.create(UpdateSer.class)
					.getNewUpdates(key1, key2, key3, key4, name, version, updaterVersion)
					.execute()
					.body();

		}
		catch (Exception e)
		{
			Container.logger.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}

	}

	public UpdaterResDTO getNewUpdater()
	{
		try
		{

			return retrofit.create(UpdaterSer.class)
					.getNewUpdater(key1, key2, key3, key4, name, version, updaterVersion)
					.execute()
					.body();

		}
		catch (Exception e)
		{
			Container.logger.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}

	public RegisterResDTO register()
	{
		try
		{

			return retrofit.create(RegisterSer.class)
					.register(key1, key2, key3, key4, name, version, updaterVersion)
					.execute()
					.body();

		}
		catch (Exception e)
		{
			Container.logger.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}

	public RegisterResDTO verify()
	{
		try
		{

			return retrofit.create(VerifySer.class)
					.verify(key1, key2, key3, key4, name, version, updaterVersion)
					.execute()
					.body();

		}
		catch (SocketTimeoutException e)
		{
			verifyTimeOutCount++;
			if (verifyTimeOutCount < 3)
			{
				return verify();
			}
			else
			{
				Container.logger.log(Level.SEVERE, e.getMessage(), e);
				return null;
			}
		}
		catch (Exception e)
		{
			Container.logger.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}

}
