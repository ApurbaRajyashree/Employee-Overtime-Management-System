package com.example.overtimesystem.helper;

import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.entity.OverTimeMaster;
import jakarta.servlet.ServletOutputStream;
import lombok.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportOverTimeDetail {

    private List<OverTimeDetail> overTimeDetailList;

    private OverTimeMaster overTimeMaster;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExportOverTimeDetail(List<OverTimeDetail> overTimeDetailList,OverTimeMaster overTimeMaster) {
        this.overTimeDetailList = overTimeDetailList;
        this.overTimeMaster=overTimeMaster;
        workbook = new XSSFWorkbook();
    }

    public void OtdSheet() throws IOException {
        this.sheet = workbook.createSheet("OTD");

        Row headingRow = sheet.createRow(1);
        Cell headingCell1 = headingRow.createCell(1);

        headingCell1.setCellValue("OVERTIME APPROVAL SHEET");
        CellRangeAddress mergeRegion = new CellRangeAddress(1, 2, 1, 5);
        sheet.addMergedRegion(mergeRegion);

        CellStyle headingCellStyle = workbook.createCellStyle();
        headingCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headingCell1.setCellStyle(headingCellStyle);


        Row employeeName = sheet.createRow(3);
        Cell employeeNameCell = employeeName.createCell(1);

        employeeNameCell.setCellValue("Employee Name: "+overTimeMaster.getUser().getFullName());
        CellRangeAddress mergeRegion1 = new CellRangeAddress(3, 3, 1, 5);
        sheet.addMergedRegion(mergeRegion1);


        Row employeeDepart = sheet.createRow(4);
        Cell employeeDepartCell = employeeDepart.createCell(1);

        employeeDepartCell.setCellValue("Employee Department: "+overTimeMaster.getUser().getDepartment().getDepartmentName());
        CellRangeAddress mergeRegion2 = new CellRangeAddress(4, 4, 1, 5);
        sheet.addMergedRegion(mergeRegion2);



        FileOutputStream fileOutputStream=new FileOutputStream("C:\\Users\\me\\IdeaProjects\\OverTimeSystem\\data\\sheet.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();

    }
}
