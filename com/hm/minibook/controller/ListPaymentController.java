package com.hm.minibook.controller;

import com.hm.dotnettable.DotNetTable;
import com.hm.minibook.CommonUIActions;
import com.hm.minibook.Container;

import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.BankSer;
import com.hm.minibook.dao.CashSer;
import com.hm.minibook.dao.PaymentSer;
import com.hm.minibook.dao.SupplierSer;
import com.hm.minibook.dto.PaymentDTO;
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

public class ListPaymentController implements Initializable
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

	List<PaymentDTO> payments;

	DotNetTable dotNetTable;

	String[] columnNames =
	{
		"#",
		"Id",
		"Date",
		"Supplier",
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

	private void mLoadData(List<PaymentDTO> payments)
	{
		this.payments = payments;

		int[] ids = new int[payments.size()];
		List<List<String>> finalList = new ArrayList<>();

		int rowNo = 1;
		for (PaymentDTO payment : payments)
		{

			ids[rowNo - 1] = payment.getId();

			List<String> l = new ArrayList<>();
			l.add(String.valueOf(rowNo));
			l.add(String.valueOf(payment.getId()));
			l.add(Util.mGetLocalDateFormat(payment.getDate()));
			l.add(new SupplierSer().getById(payment.getSupplierId()).getName());
			l.add(payment.getAmount().toString());
			if (payment.getFromType() == PaymentDTO.FromType.BANK)
			{
				l.add("Bank - " + new BankSer().getById(payment.getBankId()).getName());
			}
			else
			{
				l.add("Cash - " + new CashSer().getById(payment.getCashId()).getName());
			}
			l.add(payment.getNote());
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
				PaymentSer objPaymentSer = new PaymentSer();

				objPaymentSer.delete(dotNetTable.getSelectedId());
				handleClickBtnSearch();
				StageHelper.showMessageBox("Deleted successfully");
			}
		}

	}

	private void handleClickBtnSearch()
	{
		this.mLoadData(new PaymentSer().retrieve());
	}

	private void handleClickBtnCancel(ActionEvent event)
	{
		txtName.clear();
		handleClickBtnSearch();
	}

	private void handleClickBtnAdd(ActionEvent event)
	{

		FXMLLoader fxmll = StageHelper.getFXMLLoader(FXMLLayouts.ADD_PAYMENT);
		AddPaymentController addPaymentController = fxmll.getController();
		Container.wrapperController.addPane(fxmll);
	}

	private void handleClickBtnEdit(ActionEvent event)
	{
		if (tableView.getSelectionModel().getSelectedItems().size() != 1)
		{
			StageHelper.showMessageBox("Select one row to edit");
			return;
		}

		PaymentSer paymentSer = new PaymentSer();

		FXMLLoader fxmll = StageHelper.getFXMLLoader(FXMLLayouts.ADD_PAYMENT);
		AddPaymentController addPaymentController = fxmll.getController();
		addPaymentController.setData(paymentSer.getById(dotNetTable.getSelectedId()));
		Container.wrapperController.addPane(fxmll);

	}

	private void handleClickMiE2E()
	{
		CommonUIActions.convertToExcelEventHandler(dotNetTable);
	}

}
