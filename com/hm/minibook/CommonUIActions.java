package com.hm.minibook;

import com.hm.dotnettable.DotNetTable;
import com.hm.utilities.ComboBoxAutoComplete;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;

public class CommonUIActions
{

	public static void fillComboBox(ComboBox<String> comboBox, List<String> items)
	{
		comboBox.getItems().clear();
		comboBox.getItems().add("[SELECT]");
		items.forEach((item) -> comboBox.getItems().add(item));
		comboBox.getSelectionModel().select(0);
		//new AutoCompleteComboBoxListener<String>(comboBox);
		new ComboBoxAutoComplete<>(comboBox);
	}

	public static void convertToExcelEventHandler(DotNetTable table)
	{

		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel File", "xlsx");
		fileChooser.getExtensionFilters().add(extFilter);

		_convertToExcelEventHandler(table, fileChooser);

	}

	private static void _convertToExcelEventHandler(DotNetTable table, FileChooser fileChooser)
	{

		File file = fileChooser.showSaveDialog(Container.stageHelper.stage);
		file = new File(file.getAbsolutePath() + ".xlsx");

		if (file.exists())
		{

			if (StageHelper.showConfirmBox("File already exists. Do you want to overwrite it?"))
			{
				if (!file.delete())
				{
					StageHelper.showMessageBox("Can't delete file. Please try again");
					_convertToExcelEventHandler(table, fileChooser);
					return;
				}
			}
			else
			{
				_convertToExcelEventHandler(table, fileChooser);
				return;
			}
		}

		try
		{
			table.createExcel(file.getPath());
			StageHelper.showMessageBox("File created successfully");
		}
		catch (Exception ex)
		{
			Container.logger.log(Level.SEVERE, ex.getMessage(), ex);
		}

	}

	public static File imageFileChooser()
	{

		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);

		return fileChooser.showOpenDialog(Container.stageHelper.stage);

	}

}
