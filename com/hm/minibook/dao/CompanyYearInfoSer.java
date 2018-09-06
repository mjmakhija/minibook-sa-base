package com.hm.minibook.dao;

import com.hm.minibook.Container;
import com.hm.minibook.dto.CompanyYearInfoDTO;
import com.hm.miniorm.MiniORM;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class CompanyYearInfoSer
{

	public enum CompanyYearInfoKey
	{
		CompanyName("company_name"),
		PersonName("person_name"),
		Address("address"),
		City("city"),
		StateId("state_id"),
		Pin("pin"),
		ContactNo("contact_no"),
		Email("email"),
		GSTNo("gst_no"),
		TnC("tnc"),
		BankDetails("bank_details"),
		Info("info"),
		LogoPath("logo_path"),
		LogoPath2("logo_path2");

		private final String name;

		private CompanyYearInfoKey(String s)
		{
			name = s;
		}

		public boolean equalsName(String otherName)
		{
			// (otherName == null) check is not needed because name.equals(null) returns false 
			return name.equals(otherName);
		}

		public String toString()
		{
			return this.name;
		}
	}

	List<CompanyYearInfoDTO> companyYearInfoDTOs;
	CompanyYearInfoDAO companyYearInfoDAO;
	MiniORM miniORM;

	public CompanyYearInfoSer()
	{
		this.miniORM = Container.myORMCompanyYear;
		companyYearInfoDAO = new CompanyYearInfoDAO(miniORM);
		companyYearInfoDTOs = companyYearInfoDAO.retrieve();
	}

	public String get(CompanyYearInfoKey vAppProperty)
	{
		for (CompanyYearInfoDTO companyYearInfoDTO : companyYearInfoDTOs)
		{
			if (vAppProperty.toString().equals(companyYearInfoDTO.getInfoKey()))
			{
				return String.valueOf(companyYearInfoDTO.getInfoValue());
			}
		}
		return null;
	}

	public boolean set(CompanyYearInfoKey key, String value)
	{
		for (CompanyYearInfoDTO companyYearInfoDTO : companyYearInfoDTOs)
		{
			if (key.toString().equals(companyYearInfoDTO.getInfoKey()))
			{

				companyYearInfoDTO.setInfoValue(value);
				return update(companyYearInfoDTO);

			}
		}

		return false;

	}

	public boolean update(CompanyYearInfoDTO companyYearInfoDTO)
	{

		try
		{
			if (!companyYearInfoDAO.update(companyYearInfoDTO))
			{
				miniORM.getConn().rollback();
				return false;
			}
			miniORM.getConn().commit();
			return true;
		}
		catch (SQLException ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}

	}
}
