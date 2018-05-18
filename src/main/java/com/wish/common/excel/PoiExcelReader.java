package com.wish.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class PoiExcelReader
{
	private Workbook workBook;

	private FormulaEvaluator formulaEvaluator;

	private Sheet sheet;

	private Row row;

	private Cell cell;

	private int rows;

	private int columns;

	private int tmp = 0;

	public PoiExcelReader(File file) throws Exception
	{
		try (InputStream is = new FileInputStream(file))
		{
			workBook = WorkbookFactory.create(is);
			formulaEvaluator = workBook.getCreationHelper().createFormulaEvaluator();
		}
		catch (Exception e)
		{
		}
	}

	public PoiExcelReader(InputStream is) throws Exception
	{
		workBook = WorkbookFactory.create(is);
		formulaEvaluator = workBook.getCreationHelper().createFormulaEvaluator();
	}

	public PoiExcelReader(String strFilePath) throws Exception
	{
		this(new File(strFilePath));
	}

	public void selSheet(int sheetIndex)
	{
		sheet = workBook.getSheetAt(sheetIndex);
		computeRowsCols();
	}

	public void selSheet(String strSheetName)
	{
		sheet = workBook.getSheet(strSheetName);
		computeRowsCols();
	}

	public String getOneCell(int y, int x)
	{
		String strOneCell = "";
		if (y < columns && x < rows)
		{
			row = sheet.getRow(x);
			if (row != null)
			{
				cell = row.getCell(y);
				if (cell != null)
				{
					strOneCell = getValue(cell);
				}
			}
		}
		return strOneCell;
	}

	public String getOneCell(int y, int x, boolean exclueCenterWhite)
	{
		String value = getOneCell(y, x);
		if (exclueCenterWhite)
		{
			return value.replaceAll("[\u3000\\s]+", "");
		}
		return value;
	}

	public int getCurRows()
	{
		return rows;
	}

	public int getCurColumns()
	{
		return columns;
	}

	private String getValue(Cell cell)
	{
		String retString;
		int key = cell.getCellType();
		switch (key)
		{
		case Cell.CELL_TYPE_STRING:
			retString = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			cell.setCellType(Cell.CELL_TYPE_STRING);
			retString = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			retString = String.valueOf(cell.getBooleanCellValue());
			break;

		case Cell.CELL_TYPE_FORMULA:
			CellValue evaluateValue = formulaEvaluator.evaluate(cell);
			switch (evaluateValue.getCellType())
			{
			case Cell.CELL_TYPE_STRING:
				retString = evaluateValue.getStringValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				double d = new BigDecimal(evaluateValue.getNumberValue()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (d == (long) d)
				{
					retString = Long.toString((long) d);
				}
				else
				{
					retString = Double.toString(d);
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				retString = String.valueOf(evaluateValue.getBooleanValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				retString = evaluateValue.getStringValue();
				break;
			case Cell.CELL_TYPE_ERROR:
				retString = String.valueOf(cell.getErrorCellValue());
				break;
			default:
				retString = evaluateValue.getStringValue();
				break;
			}
			break;
		case Cell.CELL_TYPE_BLANK:
			retString = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_ERROR:
			retString = String.valueOf(cell.getErrorCellValue());
			break;
		default:
			retString = cell.getStringCellValue();
			break;
		}
		return retString.trim();
	}

	private void computeRowsCols()
	{
		if (sheet == null)
			return;

		rows = sheet.getLastRowNum() + 1;
		for (int i = 0; i < 10 || i < rows; i++)
		{
			row = sheet.getRow(i);
			if (row != null)
			{
				tmp = sheet.getRow(i).getLastCellNum() + 1;
				if (tmp > columns)
					columns = tmp;
			}
		}
		rows = getRealExcelRows();
	}

	private int getRealExcelRows()
	{
		for (int x = rows - 1; x >= 0; x--)// 行
		{
			for (int y = 0; y < columns; y++)// 列
			{
				row = sheet.getRow(x);
				if (row != null)
				{
					cell = row.getCell(y);
					if (cell != null)
					{
						String content = getValue(cell).replaceAll("[\u3000\\s]+", "");
						if (!content.trim().equals(""))
						{
							return x + 1;
						}
					}
				}
			}
		}
		return 0;
	}
}
