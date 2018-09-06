package com.hm.minibook.controller;

import com.hm.dotnettable.DotNetTable;
import com.hm.minibook.CommonUIActions;
import com.hm.minibook.Container;

import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.TaxSer;
import com.hm.minibook.dto.TaxDTO;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ListTaxController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	TableView tableView;

	@FXML
	Button btnSearch;

	@FXML
	Button btnCancel;

	@FXML
	MenuItem miAdd;

	@FXML
	MenuItem miEdit;

	@FXML
	MenuItem miDelete;

	@FXML
	MenuItem miE2E;

	@FXML
	MenuItem miSelectAll;

	List<TaxDTO> taxs;

	DotNetTable dotNetTable;

	String[] columnNames =
	{
		"#",
		"Id",
		"Name",
		"CGST",
		"SGST",
		"IGST",
		"Note",
	};

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{

		dotNetTable = new DotNetTable(tableView, columnNames, false);

		miAdd.setOnAction(this::handleClickBtnAdd);
		miEdit.setOnAction(this::handleClickBtnEdit);
		miDelete.setOnAction(this::handleClickBtnDelete);
		miE2E.setOnAction((event) -> handleClickMiE2E());
		btnSearch.setOnAction((event) -> handleClickBtnSearch());
		btnCancel.setOnAction(this::handleClickBtnCancel);

		handleClickBtnSearch();

	}

	private void mLoadData(List<TaxDTO> taxs)
	{
		this.taxs = taxs;

		int[] ids = new int[taxs.size()];
		List<List<String>> finalList = new ArrayList<>();

		int rowNo = 1;
		for (TaxDTO tax : taxs)
		{
			
			ids[rowNo - 1] = tax.getId();
			
			List<String> l = new ArrayList<>();
			l.add(String.valueOf(rowNo));
			l.add(String.valueOf(tax.getId()));
			l.add(tax.getName());
			l.add(String.valueOf(tax.getCgst()));
			l.add(String.valueOf(tax.getSgst()));
			l.add(String.valueOf(tax.getIgst()));
			l.add(tax.getNote());
			finalList.add(l);
			rowNo++;
		}

		dotNetTable.setData(ids, finalList);

	}

	private void handleClickBtnDelete(ActionEvent event)
	{

		if (tableView.getSelectionModel().getSelectedItems().size() != 1)
		{
			StageHelper.showMessageBox("Select one row to delete");
		}
		else
		{
			if (StageHelper.showConfirmBox("Are you sure?"))
			{
				TaxSer objTaxSer = new TaxSer();

				objTaxSer.delete(dotNetTable.getSelectedId());
				handleClickBtnSearch();
				StageHelper.showMessageBox("Deleted successfully");
			}
		}

	}

	private void handleClickMiE2E()
	{
		CommonUIActions.convertToExcelEventHandler(dotNetTable);
	}

	private void handleClickBtnSearch()
	{
		this.mLoadData(new TaxSer().retrieve());
	}

	private void handleClickBtnCancel(ActionEvent event)
	{
		txtName.clear();
		handleClickBtnSearch();
	}

	private void handleClickBtnAdd(ActionEvent event)
	{

		FXMLLoader fxmll = StageHelper.getFXMLLoader(FXMLLayouts.ADD_TAX);
		AddTaxController addTaxController = fxmll.getController();
		Container.wrapperController.addPane(fxmll);
	}

	private void handleClickBtnEdit(ActionEvent event)
	{
		if (tableView.getSelectionModel().getSelectedItems().size() != 1)
		{
			StageHelper.showMessageBox("Select one row to edit");
			return;
		}

		TaxSer taxSer = new TaxSer();
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

		FXMLLoader fxmll = StageHelper.getFXMLLoader(FXMLLayouts.ADD_TAX);
		AddTaxController addTaxController = fxmll.getController();
		addTaxController.setData(taxSer.getById(dotNetTable.getSelectedId()));
		Container.wrapperController.addPane(fxmll);

	}

}
