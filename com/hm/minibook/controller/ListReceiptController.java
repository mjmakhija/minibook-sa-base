package com.hm.minibook.controller;

import com.hm.dotnettable.DotNetTable;
import com.hm.minibook.CommonUIActions;
import com.hm.minibook.Container;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.BankSer;
import com.hm.minibook.dao.CashSer;
import com.hm.minibook.dao.CustomerSer;
import com.hm.minibook.dao.ReceiptSer;
import com.hm.minibook.dto.ReceiptDTO;
import com.hm.utilities.Util;
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

public class ListReceiptController implements Initializable
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

	List<ReceiptDTO> receipts;

	DotNetTable dotNetTable;

	String[] columnNames =
	{
		"#",
		"Id",
		"Date",
		"Customer",
		"Amount",
		"Description",
		"Note"
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

	private void mLoadData(List<ReceiptDTO> receipts)
	{
		this.receipts = receipts;

		int[] ids = new int[receipts.size()];
		List<List<String>> finalList = new ArrayList<>();

		int rowNo = 1;
		for (ReceiptDTO receipt : receipts)
		{

			ids[rowNo - 1] = receipt.getId();

			List<String> l = new ArrayList<>();
			l.add(String.valueOf(rowNo));
			l.add(String.valueOf(receipt.getId()));
			l.add(Util.mGetLocalDateFormat(receipt.getDate()));
			l.add(new CustomerSer().getById(receipt.getCustomerId()).getName());
			l.add(receipt.getAmount().toString());
			if (receipt.getToTypeId() == 1)
			{
				l.add("Bank - " + new BankSer().getById(receipt.getBankId()).getName());
			}
			else
			{
				l.add("Cash - " + new CashSer().getById(receipt.getCashId()).getName());
			}
			l.add(receipt.getNote());
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
				ReceiptSer objReceiptSer = new ReceiptSer();

				objReceiptSer.delete(dotNetTable.getSelectedId());
				handleClickBtnSearch();
				StageHelper.showMessageBox("Deleted successfully");
			}
		}

	}

	private void handleClickBtnSearch()
	{
		this.mLoadData(new ReceiptSer().retrieve());
	}

	private void handleClickBtnCancel(ActionEvent event)
	{
		txtName.clear();
		handleClickBtnSearch();
	}

	private void handleClickBtnAdd(ActionEvent event)
	{

		FXMLLoader fxmll = StageHelper.getFXMLLoader(FXMLLayouts.ADD_RECEIPT);
		AddReceiptController addReceiptController = fxmll.getController();
		Container.wrapperController.addPane(fxmll);
	}

	private void handleClickBtnEdit(ActionEvent event)
	{
		if (tableView.getSelectionModel().getSelectedItems().size() != 1)
		{
			StageHelper.showMessageBox("Select one row to edit");
			return;
		}

		ReceiptSer receiptSer = new ReceiptSer();

		FXMLLoader fxmll = StageHelper.getFXMLLoader(FXMLLayouts.ADD_RECEIPT);
		AddReceiptController addReceiptController = fxmll.getController();
		addReceiptController.setData(receiptSer.getById(dotNetTable.getSelectedId()));
		Container.wrapperController.addPane(fxmll);

	}

	private void handleClickMiE2E()
	{
		CommonUIActions.convertToExcelEventHandler(dotNetTable);
	}

}
