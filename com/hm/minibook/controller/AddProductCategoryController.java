package com.hm.minibook.controller;

import com.hm.minibook.Container;

import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.ProductCategorySer;
import com.hm.minibook.dto.ProductCategoryDTO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddProductCategoryController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	TextField txtNote;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	ProductCategoryDTO productCategory = new ProductCategoryDTO();
	boolean modeAdd = true;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());

		Platform.runLater(() -> txtName.requestFocus());
	}

	public void setData(ProductCategoryDTO productCategory)
	{
		this.productCategory = productCategory;
		this.modeAdd = false;
		txtName.setText(productCategory.getName());
		txtNote.setText(productCategory.getNote());
	}

	private boolean checkIsValid()
	{
		return true;
	}

	private void clearBoxes()
	{
		txtName.clear();
		txtNote.clear();

		productCategory = new ProductCategoryDTO();

		txtName.requestFocus();
	}

	private void handleClickBtnSave()
	{
		if (!checkIsValid())
		{
			return;
		}

		productCategory.setName(txtName.getText());
		productCategory.setNote(txtNote.getText());

		ProductCategorySer productCategorySer = new ProductCategorySer();
		StringBuilder errorMsg = new StringBuilder();
		boolean result;

		if (modeAdd)
		{
			result = (productCategorySer.create(productCategory, errorMsg));
		}
		else
		{
			result = (productCategorySer.update(productCategory, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("ProductCategory saved successfully");
		}
		else
		{
			StageHelper.showMessageBox(errorMsg.toString());
			return;
		}

		if (modeAdd)
		{
			clearBoxes();
		}
		else
		{
			this.showListForm();
		}

	}

	private void handleClickBtnCancel()
	{
		this.showListForm();
	}

	private void showListForm()
	{
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_PRODUCT_CATEGORY));
	}

}
