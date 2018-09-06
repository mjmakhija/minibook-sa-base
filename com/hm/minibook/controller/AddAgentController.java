package com.hm.minibook.controller;

import com.hm.minibook.CommonUIActions;
import com.hm.minibook.Container;
import com.hm.minibook.FXMLLayouts;
import com.hm.minibook.StageHelper;
import com.hm.minibook.dao.AgentSer;
import com.hm.minibook.dao.StateSer;
import com.hm.minibook.dto.AgentDTO;
import com.hm.minibook.dto.StateDTO;
import com.hm.minibook.Reload;
import com.hm.utilities.ComboBoxAutoComplete;
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
import javafx.scene.control.TextField;

public class AddAgentController implements Initializable
{

	@FXML
	TextField txtName;

	@FXML
	TextField txtAddress;

	@FXML
	TextField txtCity;

	@FXML
	ComboBox<String> cmbState;

	@FXML
	TextField txtPIN;

	@FXML
	TextField txtContactNo;

	@FXML
	TextField txtEmail;

	@FXML
	TextField txtNote;

	@FXML
	Button btnSave;

	@FXML
	Button btnCancel;

	FXMLLoader returnFXMLLoader = null;
	ICallerController callerController = null;
	boolean modeAdd = true;

	AgentDTO agent;
	List<StateDTO> stateDTOs;
	AgentSer agentSer;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{

		new ComboBoxAutoComplete<String>(cmbState);

		btnSave.setOnAction((event) -> handleClickBtnSave());
		btnCancel.setOnAction((event) -> handleClickBtnCancel());

		Platform.runLater(() -> txtName.requestFocus());

		myInit();
	}

	private void myInit()
	{
		modeAdd = true;

		agentSer = new AgentSer();
		stateDTOs = new StateSer().retrieve();

		List<String> stateStrings = new ArrayList<>();
		for (StateDTO stateDTO : stateDTOs)
		{
			stateStrings.add(stateDTO.getName());
		}
		CommonUIActions.fillComboBox(cmbState, stateStrings);

		agent = new AgentDTO();
	}

	public void setData(AgentDTO agent)
	{
		this.modeAdd = false;
		this.agent = agent;

		txtName.setText(agent.getName());
		txtAddress.setText(agent.getAddress());
		txtCity.setText(agent.getCity());
		if (agent.getStateId() != null)
		{
			StateDTO stateDTO = new StateSer().getById(agent.getStateId());
			cmbState.getSelectionModel().select(stateDTO.getName());
		}
		txtPIN.setText(agent.getPin());
		txtContactNo.setText(agent.getContactNo());
		txtEmail.setText(agent.getEmail());
		txtNote.setText(agent.getNote());
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
		txtAddress.clear();
		txtCity.clear();
		cmbState.getSelectionModel().select("");
		txtPIN.clear();
		txtContactNo.clear();
		txtEmail.clear();
		txtNote.clear();

		agent = new AgentDTO();

		txtName.requestFocus();
	}

	private void handleClickBtnSave()
	{

		agent.setName(txtName.getText());
		agent.setAddress(txtAddress.getText());
		agent.setCity(txtCity.getText());
		if (cmbState.getSelectionModel().getSelectedIndex() > 0)
		{
			agent.setStateId(stateDTOs.get(cmbState.getSelectionModel().getSelectedIndex() - 1).getId());
		}
		agent.setPin(txtPIN.getText());
		agent.setContactNo(txtContactNo.getText());
		agent.setEmail(txtEmail.getText());
		agent.setNote(txtNote.getText());

		StringBuilder errorMsg = new StringBuilder();
		boolean result;

		if (modeAdd)
		{
			result = (agentSer.create(agent, errorMsg));
		}
		else
		{
			result = (agentSer.update(agent, errorMsg));
		}

		if (result)
		{
			StageHelper.showMessageBox("Agent saved successfully");
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
					callerController.reload(Reload.AGENTS);
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
		Container.wrapperController.addPane(StageHelper.getFXMLLoader(FXMLLayouts.LIST_AGENT));
	}

}
