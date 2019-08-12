package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;

public class test_excel {

	@SuppressWarnings("deprecation")
	@Test
	public void test_read() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		// 确定要操作的excelc:/1.xls
		HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream("d:/question.xls")));

		// 取第0个单元表
		HSSFSheet sheet = workbook.getSheetAt(0);
		// sheet.getPhysicalNumberOfRows();求出所有行数
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			System.out.print(sheet.getPhysicalNumberOfRows());
			// 取一行操作
			HSSFRow row = sheet.getRow(i);

			// row.getPhysicalNumberOfCells();求出本行的单元格数，也就是列数
			for (int j = 0; j < 19; j++) {
				Cell cell=row.getCell(j);
				if(cell==null ){
					System.out.print("  AA");
					continue;
				}
				if(cell.getCellType()==HSSFCell.CELL_TYPE_BLANK){
					System.out.print("  BB");
					continue;
				}
				
				String cellString=cell.toString().trim();
				int length = cellString.length();
				if (length >= 2) { // 这里大于等于2是防止有些列只有一个字符，到下面会报错
					// 通过截取最后两个字符，如果等于.0 就去除最后两个字符
					if (cellString.substring(length - 2, length).equals(".0"))
						cellString = cellString.substring(0, length - 2);
				}

				System.out.print(cellString + "\t");
			}
			System.out.println();
		}
	}

}
