/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hm.utilities;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import java.util.stream.Stream;
import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

/**
 *
 * Uses a combobox tooltip as the suggestion for auto complete and updates the
 * combo box itens accordingly <br />
 * It does not work with space, space and escape cause the combobox to hide and
 * clean the filter ... Send me a PR if you want it to work with all characters
 * -> It should be a custom controller - I KNOW!
 *
 * @author wsiqueir
 *
 * @param <T>
 */
public class ComboBoxAutoComplete<T>
{

	private ComboBox<T> cmb;
	String filter = "";
	//private ObservableList<T> originalItems;
	T selectedItem;

	public ComboBoxAutoComplete(ComboBox<T> cmb)
	{
		this.cmb = cmb;
		//originalItems = FXCollections.observableArrayList(cmb.getItems());

		Platform.runLater(() ->
		{
			ComboBoxListViewSkin cblvs = (ComboBoxListViewSkin) cmb.getSkin();

			cblvs.getListView().setOnKeyPressed((event) ->
			{
				if (event.getCode() == KeyCode.SPACE)
				{
					handleOnKeyPressed(event);

				}
			});

			cmb.getEditor().setOnKeyPressed((event) ->
			{
				if (event.getCode() == KeyCode.SPACE)
				{
					event.consume();
				}
			});
		});

		cmb.setOnKeyPressed(this::handleOnKeyPressed);
		cmb.setOnHidden(this::handleOnHiding);

	}

	public void handleOnKeyPressed(KeyEvent e)
	{
		if (cmb.getTooltip() == null)
		{
			cmb.setTooltip(new Tooltip());
		}

		//ObservableList<T> filteredList = FXCollections.observableArrayList();
		KeyCode code = e.getCode();

		if (code == KeyCode.SPACE)
		{
			e.consume();
		}

		if (code == KeyCode.DOWN || code == KeyCode.KP_DOWN)
		{
			cmb.show();
		}
		if (code.isLetterKey() || code.isDigitKey() || code == KeyCode.SPACE)
		{
			filter += e.getText();
		}
		if (code == KeyCode.BACK_SPACE && filter.length() > 0)
		{
			filter = filter.substring(0, filter.length() - 1);
			//cmb.getItems().setAll(originalItems);
		}
		if (code == KeyCode.ESCAPE)
		{
			filter = "";
		}
		if (filter.length() == 0)
		{
			//filteredList = originalItems;
			cmb.getTooltip().hide();
		}
		else
		{
			String txtUsr = filter.toString().toLowerCase();

			if (!cmb.getTooltip().isActivated() || !cmb.getTooltip().isShowing())
			{
				Point2D p = cmb.localToScene(0.0, 0.0);

				/*
				System.out.println("P" + p.toString());
				System.out.println("p.getX()" + String.valueOf(p.getX()));
				System.out.println("p.getY()" + String.valueOf(p.getY()));

				System.out.println("cmb.getScene().getX()" + String.valueOf(cmb.getScene().getX()));
				System.out.println("cmb.getScene().getY()" + String.valueOf(cmb.getScene().getY()));

				System.out.println("cmb.getScene().getWindow().getX()" + String.valueOf(cmb.getScene().getWindow().getX()));
				System.out.println("cmb.getScene().getWindow().getY()" + String.valueOf(cmb.getScene().getWindow().getY()));

				System.out.println("--------------------------------------");
				 */
				//cmb.getTooltip().show(cmb,
				//		p.getX() + cmb.getScene().getX() + cmb.getScene().getWindow().getX(),
				//		p.getY() + cmb.getScene().getY() + cmb.getScene().getWindow().getY() - 25);
			}

			Window stage = cmb.getScene().getWindow();
			//double posX = stage.getX() + cmb.getBoundsInParent().getMinX();
			//double posY = stage.getY() + cmb.getBoundsInParent().getMinY();

			double posX = stage.getX() + cmb.localToScene(cmb.getBoundsInLocal()).getMinX();
			double posY = stage.getY() + cmb.localToScene(cmb.getBoundsInLocal()).getMinY();

			//cmb.getTooltip().show(stage);
			cmb.getTooltip().hide();
			cmb.getTooltip().setText(txtUsr);
			cmb.getTooltip().show(stage, posX, posY);
			for (T item : cmb.getItems())
			{
				if (item.toString().toLowerCase().startsWith(txtUsr))
				{
					cmb.getSelectionModel().select(item);
					selectedItem = item;
					break;
				}
			}

			if (!cmb.isShowing())
			{
				cmb.show();
			}

			ListView<T> lv = ((ComboBoxListViewSkin) cmb.getSkin()).getListView();
			lv.scrollTo(selectedItem);
		}
		//cmb.getItems().setAll(filteredList);
	}

	public void handleOnHiding(Event e)
	{
		filter = "";
		if (cmb.getTooltip() != null)
		{
			cmb.getTooltip().hide();
			cmb.setTooltip(null);
		}
		//cmb.setTooltip(null);
		//T s = cmb.getSelectionModel().getSelectedItem();
		//cmb.getItems().setAll(originalItems);
		//cmb.getSelectionModel().select(s);
	}

}
