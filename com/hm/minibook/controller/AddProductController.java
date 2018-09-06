package com.hm.minibook.controller;

import com.hm.dotnettable.DotNetTable;
import com.hm.minibook.CommonUIActions;
import com.hm.minibook.Container;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.ProductCategorySer;
import com.hm.minibook.dao.ProductSer;
import com.hm.minibook.dao.TaxSer;
import com.hm.minibook.dao.UnitSer;
import com.hm.minibook.dto.ProductCategoryDTO;
import com.hm.minibook.dto.ProductDTO;
import com.hm.minibook.dto.TaxDTO;
import com.hm.minibook.dto.UnitDTO;
import com.hm.minibook.Reload;
import com.hm.minibook.dao.OpeningStockSer;
import com.hm.minibook.dto.OpeningStockDTO;
import com.hm.utilities.ComboBoxAutoComplete;
import com.hm.utilities.Util;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddProductController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	ComboBox<String> cmbUnit;

	@FXML
	TextField txtHSNCode;

	@FXML
	TextField txtPurchasePrice;

	@FXML
	TextField txtSalePrice;

	@FXML
	TextField txtMRP;

	@FXML
	ComboBox<String> cmbTax;

	@FXML
	ComboBox<String> cmbProductCategory;

	@FXML
	TextField txtNote;

	@FXML
	TextField txtQuantity;

	@FXML
	TextField txtRate;

	@FXML
	TableView tvOpeningStock;

	@FXML
	Button btnSaveOS;

	@FXML
	Button btnEditOS;

	@FXML
	Button btnDeleteOS;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	FXMLLoader returnFXMLLoader = null;
	ICallerController callerController = null;
	boolean modeAdd = true;

	ProductDTO product;
	List<ProductCategoryDTO> productCategorys;
	List<UnitDTO> units;
	List<TaxDTO> taxs;
	ProductSer productSer;
	OpeningStockSer openingStockSer;

	List<OpeningStockDTO> openingStockDTOs = new ArrayList<>();

	String[] columnNamesOpeningStock =
	{
		"#",
		"Quantity",
		"Rate",
		"Amount"
	};

	DotNetTable dntOpeningStock;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		new ComboBoxAutoComplete<String>(cmbUnit);

		btnSaveOS.setOnAction((event) -> handleClickBtnSaveOS());
		//btnEdit.setOnAction((event) -> handleClickBtnEdit());
		btnDeleteOS.setOnAction((event) -> handleClickBtnDeleteOS());

		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());

		Platform.runLater(() -> txtName.requestFocus());

		myInit();
	}

	private void myInit()
	{
		modeAdd = true;

		productSer = new ProductSer();
		openingStockSer = new OpeningStockSer();

		productCategorys = new ProductCategorySer().retrieve();
		units = new UnitSer().retrieve();
		taxs = new TaxSer().retrieve();

		List<String> l1 = new ArrayList<>();
		for (ProductCategoryDTO productCategory : productCategorys)
		{
			l1.add(productCategory.getName());
		}
		CommonUIActions.fillComboBox(cmbProductCategory, l1);

		List<String> l2 = new ArrayList<>();
		for (UnitDTO unit : units)
		{
			l2.add(unit.getName());
		}
		CommonUIActions.fillComboBox(cmbUnit, l2);

		List<String> l3 = new ArrayList<>();
		for (TaxDTO tax : taxs)
		{
			l3.add(tax.getName());
		}
		CommonUIActions.fillComboBox(cmbTax, l3);

		product = new ProductDTO();

		dntOpeningStock = new DotNetTable(tvOpeningStock, columnNamesOpeningStock, false);
	}

	public void setData(ProductDTO product)
	{
		this.modeAdd = false;
		this.product = product;

		txtName.setText(product.getName());
		UnitDTO unit = new UnitSer().getById(product.getUnitId());
		cmbUnit.getSelectionModel().select(unit.getName());
		if (product.getHsn() != null)
		{
			txtHSNCode.setText(String.valueOf(product.getHsn()));
		}
		if (product.getPurchasePrice() != null)
		{
			txtPurchasePrice.setText(String.valueOf(product.getPurchasePrice()));
		}
		if (product.getSalesPrice() != null)
		{
			txtSalePrice.setText(String.valueOf(product.getSalesPrice()));
		}
		if (product.getMrp() != null)
		{
			txtMRP.setText(String.valueOf(product.getMrp()));
		}
		TaxDTO tax = new TaxSer().getById(product.getTaxId());
		cmbTax.getSelectionModel().select(tax.getName());
		if (product.getProductCategoryId() != null)
		{
			ProductCategoryDTO productCategory = new ProductCategorySer().getById(product.getProductCategoryId());
			cmbProductCategory.getSelectionModel().select(productCategory.getName());
		}
		txtNote.setText(product.getNote());

		openingStockDTOs = openingStockSer.getByProductId(product.getId());
		loadOpeningStockInTable();
	}

	public void setReturnFXMLLoader(FXMLLoader returnFXMLLoader)
	{
		this.returnFXMLLoader = returnFXMLLoader;
	}

	public void setCallerController(ICallerController callerController)
	{
		this.callerController = callerController;
	}

	private void clearBoxes()
	{
		txtName.clear();
		cmbUnit.getSelectionModel().select(0);
		txtHSNCode.clear();
		txtPurchasePrice.clear();
		txtSalePrice.clear();
		txtMRP.clear();
		cmbTax.getSelectionModel().select(0);
		cmbProductCategory.getSelectionModel().select(0);
		txtNote.clear();

		clearOpeningStockBoxes();
		tvOpeningStock.getItems().clear();
		openingStockDTOs.clear();

		product = new ProductDTO();

		txtName.requestFocus();
	}

	private void clearOpeningStockBoxes()
	{
		txtQuantity.clear();
		txtRate.clear();

		txtQuantity.requestFocus();
	}

	private void handleClickBtnSave()
	{

		StringBuilder errorMsg = new StringBuilder();
		if (!checkIsValid(errorMsg))
		{
			StageHelper.showMessageBox(errorMsg.toString());
			return;
		}

		product.setName(txtName.getText());
		product.setUnitId(units.get(cmbUnit.getSelectionModel().getSelectedIndex() - 1).getId());
		if (!txtHSNCode.getText().equals(""))
		{
			product.setHsn(Integer.parseInt(txtHSNCode.getText()));
		}
		if (!txtPurchasePrice.getText().equals(""))
		{
			product.setPurchasePrice(BigDecimal.valueOf(Double.valueOf(txtPurchasePrice.getText())));
		}
		if (!txtSalePrice.getText().equals(""))
		{
			product.setSalesPrice(BigDecimal.valueOf(Double.valueOf(txtSalePrice.getText())));
		}
		if (!txtMRP.getText().equals(""))
		{
			product.setMrp(BigDecimal.valueOf(Double.valueOf(txtMRP.getText())));
		}
		product.setTaxId(taxs.get(cmbTax.getSelectionModel().getSelectedIndex() - 1).getId());
		if (cmbProductCategory.getSelectionModel().getSelectedIndex() > 0)
		{
			product.setProductCategoryId(productCategorys.get(cmbProductCategory.getSelectionModel().getSelectedIndex() - 1).getId());
		}
		product.setNote(txtNote.getText());

		boolean result;

		if (modeAdd)
		{
			result = (productSer.create(product, openingStockDTOs, errorMsg));
		}
		else
		{
			result = (productSer.update(product, openingStockDTOs, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("Product saved successfully");
		}
		else
		{
			StageHelper.showMessageBox(errorMsg.toString());
			return;
		}

		if (modeAdd)
		{
			if (returnFXMLLoader == null)
			{
				clearBoxes();
			}
			else
			{
				if (callerController != null)
				{
					callerController.reload(Reload.PRODUCTS);
				}
				Container.wrapperController.addPane(returnFXMLLoader);
			}
		}
		else
		{
			this.showListForm();
		}

	}

	private void handleClickBtnCancel()
	{
		if (modeAdd && returnFXMLLoader != null)
		{
			Container.wrapperController.addPane(returnFXMLLoader);
		}
		else
		{
			this.showListForm();
		}
	}

	private void showListForm()
	{
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_PRODUCT));
	}

	private boolean checkIsValid(StringBuilder errorMsg)
	{
		if (txtName.getText() == null || txtName.getText().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		if (cmbUnit.getSelectionModel().getSelectedIndex() == 0)
		{
			errorMsg.append("Unit is required");
			return false;
		}

		if (txtHSNCode.getText() != null
				&& !txtHSNCode.getText().equals("")
				&& !Util.isNumeric(txtHSNCode.getText()))
		{
			errorMsg.append("HSN is invalid");
			return false;
		}

		if (txtSalePrice.getText() != null
				&& !txtSalePrice.getText().equals("")
				&& !Util.isNumeric(txtSalePrice.getText()))
		{
			errorMsg.append("Sale price is invalid");
			return false;
		}

		if (txtMRP.getText() != null
				&& !txtMRP.getText().equals("")
				&& !Util.isNumeric(txtMRP.getText()))
		{
			errorMsg.append("MRP is invalid");
			return false;
		}

		if (cmbTax.getSelectionModel().getSelectedIndex() == 0)
		{
			errorMsg.append("Tax is required");
			return false;
		}

		return true;
	}

	private void handleClickBtnSaveOS()
	{
		StringBuilder errorMsg = new StringBuilder();

		if (!checkIsValidOpeningStock(errorMsg))
		{
			StageHelper.showMessageBox(errorMsg.toString());
			return;
		}

		OpeningStockDTO openingStockDTO = new OpeningStockDTO();
		openingStockDTO.setQuantity(BigDecimal.valueOf(Double.parseDouble(txtQuantity.getText())));
		openingStockDTO.setRate(BigDecimal.valueOf(Double.parseDouble(txtRate.getText())));
		openingStockDTO.setAmount(openingStockDTO.getQuantity().multiply(openingStockDTO.getRate()));

		openingStockDTOs.add(openingStockDTO);
		loadOpeningStockInTable();

		clearOpeningStockBoxes();

	}

	private void handleClickBtnDeleteOS()
	{

		if (tvOpeningStock.getSelectionModel().getSelectedItems().size() != 1)
		{
			StageHelper.showMessageBox("Select one row to delete");
		}
		else
		{
			if (StageHelper.showConfirmBox("Are you sure?"))
			{
				openingStockDTOs.remove(tvOpeningStock.getSelectionModel().getSelectedIndex());
				loadOpeningStockInTable();
			}
		}
	}

	private boolean checkIsValidOpeningStock(StringBuilder errorMsg)
	{
		if (txtQuantity.getText() == null || txtQuantity.getText().equals("") || !Util.isDouble(txtQuantity.getText()))
		{
			errorMsg.append("Invalid quantity");
			return false;
		}

		if (txtRate.getText() == null || txtRate.getText().equals("") || !Util.isDouble(txtRate.getText()))
		{
			errorMsg.append("Invalid rate");
			return false;
		}

		return true;
	}

	private void loadOpeningStockInTable()
	{

		List<List<String>> finalList = new ArrayList<>();

		int rowNo = 1;
		for (OpeningStockDTO openingStockDTO : openingStockDTOs)
		{

			List<String> l = new ArrayList<>();
			l.add(String.valueOf(rowNo));
			l.add(openingStockDTO.getQuantity().toString());
			l.add(openingStockDTO.getRate().toString());
			l.add(openingStockDTO.getAmount().toString());
			finalList.add(l);
			rowNo++;
		}

		dntOpeningStock.setData(finalList);
	}
}
