package com.hm.minibook.dto.api;

public class UpdaterDataDTO
{

	private String version;
	private String file_path;

	public String getVersion()
	{
		return version;
	}

	public String getFile_path()
	{
		return file_path;
	}

	@Override
	public String toString()
	{
		return "GetNewVersionsUpdaterDataResMo{" + "version=" + version + ", file path=" + file_path;
	}

}
