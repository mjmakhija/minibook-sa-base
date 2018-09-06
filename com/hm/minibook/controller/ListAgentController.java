package com.hm.minibook.controller;

import com.hm.dotnettable.DotNetTable;
import com.hm.minibook.CommonUIActions;
import com.hm.minibook.Container;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.AgentSer;
import com.hm.minibook.dao.StateSer;
import com.hm.minibook.dto.AgentDTO;
import com.hm.minibook.dto.StateDTO;
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

public class ListAgentController implements Initializable
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

	List<AgentDTO> agents;

	DotNetTable dotNetTable;

	String[] columnNames =
	{
		"#",
		"Id",
		"Name",
		"Address",
		"City",
		"State",
		"PIN",
		"Contact No",
		"Email",
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

	private void mLoadData(List<AgentDTO> agents)
	{
		this.agents = agents;

		int[] ids = new int[agents.size()];
		List<List<String>> finalList = new ArrayList<>();

		int rowNo = 1;
		for (AgentDTO agent : agents)
		{
			ids[rowNo - 1] = agent.getId();

			List<String> l = new ArrayList<>();
			l.add(String.valueOf(rowNo));
			l.add(String.valueOf(agent.getId()));
			l.add(agent.getName());
			l.add(agent.getAddress());
			l.add(agent.getCity());
			if (agent.getStateId() == null)
			{
				l.add("");
			}
			else
			{
				StateDTO stateDTO = new StateSer().getById(agent.getStateId());
				l.add(stateDTO.getName());
			}
			l.add(agent.getPin());
			l.add(agent.getContactNo());
			l.add(agent.getEmail());
			l.add(agent.getNote());
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
				AgentSer objAgentSer = new AgentSer();

				objAgentSer.delete(dotNetTable.getSelectedId());
				handleClickBtnSearch();
				StageHelper.showMessageBox("Deleted successfully");
			}
		}

	}

	private void handleClickBtnSearch()
	{
		this.mLoadData(new AgentSer().retrieve());
	}

	private void handleClickBtnCancel(ActionEvent event)
	{
		txtName.clear();
		handleClickBtnSearch();
	}

	private void handleClickBtnAdd(ActionEvent event)
	{

		FXMLLoader fxmll = StageHelper.getFXMLLoader(FXMLLayouts.ADD_AGENT);
		AddAgentController addAgentController = fxmll.getController();
		Container.wrapperController.addPane(fxmll);
	}

	private void handleClickBtnEdit(ActionEvent event)
	{
		if (tableView.getSelectionModel().getSelectedItems().size() != 1)
		{
			StageHelper.showMessageBox("Select one row to edit");
			return;
		}

		AgentSer agentSer = new AgentSer();

		FXMLLoader fxmll = StageHelper.getFXMLLoader(FXMLLayouts.ADD_AGENT);
		AddAgentController addAgentController = fxmll.getController();
		addAgentController.setData(agentSer.getById(dotNetTable.getSelectedId()));
		Container.wrapperController.addPane(fxmll);

	}

	private void handleClickMiE2E()
	{
		CommonUIActions.convertToExcelEventHandler(dotNetTable);
	}

}
