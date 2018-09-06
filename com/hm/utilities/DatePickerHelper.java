package com.hm.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

public class DatePickerHelper
{

	DatePicker datePicker;

	public DatePickerHelper(DatePicker datePicker)
	{
		this.datePicker = datePicker;

		this.datePicker.setConverter(new StringConverter<LocalDate>()
		{
			private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

			@Override
			public String toString(LocalDate localDate)
			{
				if (localDate == null)
				{
					return "";
				}
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString)
			{
				if (dateString == null || dateString.trim().isEmpty())
				{
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}
		});

		this.datePicker.getEditor().setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent event)
			{
				KeyCode code = event.getCode();

				if (code == KeyCode.DOWN || code == KeyCode.KP_DOWN)
				{
					datePicker.show();
				}
			}
		});
	}

}
