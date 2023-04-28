package com.example.overtimesystem.helper;

import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.entity.OverTimeMaster;
import com.example.overtimesystem.repository.OverTimeDetailRepository;
import lombok.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@Getter
@Setter

public class ExportOverTimeDetail {

    private List<OverTimeDetail> overTimeDetailList;

private final OverTimeDetailRepository overTimeDetailRepository;
    private OverTimeMaster overTimeMaster;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExportOverTimeDetail(List<OverTimeDetail> overTimeDetailList, OverTimeDetailRepository overTimeDetailRepository, OverTimeMaster overTimeMaster) {
        this.overTimeDetailList = overTimeDetailList;
        this.overTimeDetailRepository = overTimeDetailRepository;
        this.overTimeMaster = overTimeMaster;
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

        employeeNameCell.setCellValue("Employee Name: " + overTimeMaster.getUser().getFullName());
        CellRangeAddress mergeRegion1 = new CellRangeAddress(3, 3, 1, 5);
        sheet.addMergedRegion(mergeRegion1);


        Row employeeDepart = sheet.createRow(4);
        Cell employeeDepartCell = employeeDepart.createCell(1);

        employeeDepartCell.setCellValue("Employee Department: " + overTimeMaster.getUser().getDepartment().getDepartmentName());
        CellRangeAddress mergeRegion2 = new CellRangeAddress(4, 4, 1, 5);
        sheet.addMergedRegion(mergeRegion2);

        List<String> projectName = overTimeDetailRepository.findAllProjectInOverTimeDetail(this.overTimeMaster.getId());
        Row projectRow = sheet.createRow(5);
        Cell projectCell=projectRow.createCell(1);

        projectCell.setCellValue("Project or description of work overtime ");
        int rowCount=5;
        int count=0;
        for (String eachProject:projectName){
            Row iterateRow=sheet.createRow(++rowCount);
            iterateRow.createCell(1).setCellValue((++count)+"."+(eachProject));
        }

        CellRangeAddress mergeRegion3 = new CellRangeAddress(5, rowCount, 1, 5);
        sheet.addMergedRegion(mergeRegion3);

        CellStyle projectCellStyle = workbook.createCellStyle();
        projectCellStyle.setAlignment(HorizontalAlignment.LEFT);
        projectCell.setCellStyle(projectCellStyle);

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        headingCellStyle.setFont(font);
        Row tableHeadRow = sheet.createRow(++rowCount);
        createCell(tableHeadRow, 1, "Date ", headingCellStyle);
        createCell(tableHeadRow, 2, "Day", headingCellStyle);
        createCell(tableHeadRow, 3, "From(Time)", headingCellStyle);
        createCell(tableHeadRow, 4, "To(Time)", headingCellStyle);
        createCell(tableHeadRow, 5, "Total Hour", headingCellStyle);


        CellStyle tableCellStyle = workbook.createCellStyle();
        font.setBold(false);
        font.setFontHeight(12);
        tableCellStyle.setFont(font);
        for (OverTimeDetail eachDetail:this.overTimeDetailList){
            Row tableData = sheet.createRow(++rowCount);
            createCell(tableData, 1, eachDetail.getDate().toString(), tableCellStyle);
            createCell(tableData, 2, eachDetail.getDate().getDayOfWeek().toString(), tableCellStyle);
            createCell(tableData, 3, eachDetail.getStartTime().toString(), tableCellStyle);
            createCell(tableData, 4, eachDetail.getEndTime().toString(), tableCellStyle);
            createCell(tableData, 5, HOURS.between(eachDetail.getStartTime(), eachDetail.getEndTime()), tableCellStyle);

        }


        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\me\\IdeaProjects\\OverTimeSystem\\data\\sheet.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();

    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
}
