package com.primeton.liuzhichao.demo.utils;
/**
 * excel导入导出工具类
 * @author ASUS
 *
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.primeton.liuzhichao.demo.entity.Org;
import com.primeton.liuzhichao.demo.entity.User;


public class PoiUtils {

	/**
	 * 
	 * 
	 * @param userAndOrg 被导出的数据
	 * @param headerName 被导出的表头名
	 * @return
	 */
	/**
	 * excel导出
	 * @param list  被导出的数据内容
	 * @param headerName   excel表的表头
	 * @param CellWidth    cxcel单元格的宽度
	 * @return
	 */
	public static ResponseEntity<byte[]> exportExcel(List<List<String>> list, String[] headerName,int[] CellWidth) {
		HttpHeaders headers = null;
		ByteArrayOutputStream baos = null;
		// chain构建excel文档
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 创建文档摘要
		workbook.createInformationProperties();
		// 获取文档信息并配置
		DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
		dsi.setCategory("员工信息"); // 文档类别
		dsi.setManager("吴彦祖"); // 文档管理员
		dsi.setCompany("普元信息"); // 设置组织机构
		// 获取摘要信息并配置
		SummaryInformation sif = workbook.getSummaryInformation();
		sif.setSubject("员工信息表"); // 设置文档主题
		sif.setTitle("员工信息"); // 设置文档标题
		sif.setAuthor("普元信息科技"); // 设置文档作者
		sif.setComments("备注暂无"); // 设置文档备注

		// 创建excel表单
		HSSFSheet sheet = workbook.createSheet("普元信息员工信息表");

		// 设置单元格的行高和列宽
		for (int i = 0; i < CellWidth.length; i++) {
			sheet.setColumnWidth(i, CellWidth[i] * 256);
		}

		// 创建标题的字体样式
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		HSSFFont titlefont = workbook.createFont();
		titlefont.setFontName("宋体");
		titlefont.setFontHeightInPoints((short) 16); // 字体大小
		titlefont.setBold(true);// 字体加粗
		titleStyle.setFont(titlefont); // 将字体设置到样式中
		titleStyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
		titleStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		titleStyle.setBorderLeft(BorderStyle.DASH_DOT_DOT);// 左边框
		titleStyle.setBorderTop(BorderStyle.THIN); // 上边框
		titleStyle.setBorderRight(BorderStyle.THIN); // 右边框

		// 创建表头的显示样式
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		HSSFFont headerFont = workbook.createFont();
		headerFont.setFontName("宋体");
		headerFont.setFontHeightInPoints((short) 10); // 字体大小
		headerFont.setBold(true);// 字体加粗
		titleStyle.setFont(headerFont); // 将字体设置到样式中
		headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index); // 设置表头颜色
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
		headerStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		headerStyle.setBorderLeft(BorderStyle.DASH_DOT_DOT);// 左边框
		headerStyle.setBorderTop(BorderStyle.THIN); // 上边框
		headerStyle.setBorderRight(BorderStyle.THIN); // 右边框

		// 创建内容的显示样式
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
		contentStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		contentStyle.setBorderLeft(BorderStyle.DASH_DOT_DOT);// 左边框
		contentStyle.setBorderTop(BorderStyle.THIN); // 上边框
		contentStyle.setBorderRight(BorderStyle.THIN); // 右边框

		// 创建显示日期的格式
		HSSFCellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

		// 创建excel表的标题
		for (int i = 0; i < 2; i++) {
			HSSFRow headerRow = sheet.createRow(i);
			for (int j = 0; j < headerName.length; j++) {
				HSSFCell cell = headerRow.createCell(j);
				if (i == 0 && j == 0) {
					cell.setCellValue("员工信息表");
				}
				cell.setCellStyle(titleStyle);
			}
		}
		// 参数：起始行号，终止行号,起始列号，终止列号，
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, headerName.length - 1));

		// 自动获取下一行row对象
		HSSFRow headerRow = sheet.createRow(sheet.getPhysicalNumberOfRows());
		
		// 创建excel的表头
		for (int i = 0; i < headerName.length; i++) {
			HSSFCell cell = headerRow.createCell(i);
			cell.setCellValue(headerName[i]);
			cell.setCellStyle(headerStyle);
		}

		// 填充excel数据
		for (int i = 0; i < list.size(); i++) {
			HSSFRow contentRow = sheet.createRow(sheet.getPhysicalNumberOfRows());
			for (int j = 0; j < list.get(i).size(); j++) {
				HSSFCell cell = contentRow.createCell(j);
				String cellValue = "";
				if(null != list.get(i).get(j)) {
					cellValue = list.get(i).get(j).toString();
				}
				cell.setCellValue(cellValue);
				cell.setCellStyle(contentStyle);
			}
		}

		try {
			headers = new HttpHeaders();
			headers.setContentDispositionFormData("attachment", new String("员工表.xls".getBytes("UTF-8"), "iso-8859-1"));
			//把响应设置为二进制流
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			baos = new ByteArrayOutputStream();
			//将excel对象写入字节流中
			workbook.write(baos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//将文件以二进制的形式返回，需要传入3个参数,分别是:请求体、请求头和状态码
		return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
	}
	
	/**
	 * excel导入
	 * @param file   excel文件
	 * @return  excel对应的实体类
	 */
	public static List<User> importEmp(MultipartFile file){
		
		List<User> userList = new ArrayList<User>();
		
		try {
			//解析上传到的excel文件，创建workbook对象
			HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
			//获取所有的sheet
			int numberOfSheet = workbook.getNumberOfSheets();
			for(int i=0; i<numberOfSheet; i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);  //获取sheet对象
				int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();  //获取sheet中的excel行数
				User user = null;
				Org org = null;
				for(int j=0; j<physicalNumberOfRows; j++) {
					 if (j == 0 || j == 1 || j == 2) {
				            continue;//标题行和表头行
				        }
					 HSSFRow row = sheet.getRow(j); //获取excel行对象
					 if(null == row) {
						 continue; //没有数据
					 }
					 int physicalNumberOfCells = row.getPhysicalNumberOfCells(); //获取row（行对象）中的列数
					 user = new User();
					 org = new Org();
					 for(int k=0; k<physicalNumberOfCells; k++) {
						 HSSFCell cell = row.getCell(k);   //获取excel的单元格对象
						//getCellTypeEnum()返回单元格的类型描述
						//（NUMERIC数值型，STRING字符串型，,FORMULA（公式型，,BLANK（空值），BOOLEAN（布尔型），ERROR（错误））
						 switch (cell.getCellTypeEnum()) {
							case STRING:
								String cellValue = cell.getStringCellValue();
								if(null == cellValue) {
									cellValue = "";
								}
								switch (k) {
									case 0:
										user.setUserId(cellValue);
										break;
									case 1:
										user.setUserName(cellValue);
										break;
									case 2:
										user.setJob(cellValue);;
										break;
									case 3:
										org.setOrgName(cellValue);
										break;
									case 4:
										org.setOrgLoc(cellValue);
										break;
									default:
										break;
								}
								break;
							case NUMERIC:
								double doubleValue = cell.getNumericCellValue();
								switch (k) {
								case 0:
									user.setUserId(String.valueOf(doubleValue));
									break;
								default:
									break;
								}
								break;
							default:
								break;
						}
					 }
					 userList.add(user);
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return userList;
	}

}
