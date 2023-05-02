package com.example.overtimesystem.helper;

import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.entity.OverTimeMaster;
import com.example.overtimesystem.repository.OverTimeDetailRepository;
import lombok.*;
import org.apache.poi.ss.usermodel.*;

import static java.time.temporal.ChronoUnit.HOURS;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
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
        sheet.createRow(5);


        List<String> projectName = overTimeDetailRepository.findAllProjectInOverTimeDetail(this.overTimeMaster.getId());
        Row projectRow = sheet.createRow(6);
        Cell projectCell = projectRow.createCell(1);

        projectCell.setCellValue("Project or description of work overtime ");
        int rowCount = 6;
        int count = 0;
        for (String eachProject : projectName) {
            Row iterateRow = sheet.createRow(++rowCount);
            iterateRow.createCell(1).setCellValue((++count) + "." + (eachProject));
        }

        sheet.createRow(++rowCount);
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
        for (OverTimeDetail eachDetail : this.overTimeDetailList) {
            Row tableData = sheet.createRow(++rowCount);
            createCell(tableData, 1, eachDetail.getDate().toString(), tableCellStyle);
            createCell(tableData, 2, eachDetail.getDate().getDayOfWeek().toString(), tableCellStyle);
            createCell(tableData, 3, eachDetail.getStartTime().toString(), tableCellStyle);
            createCell(tableData, 4, eachDetail.getEndTime().toString(), tableCellStyle);
            createCell(tableData, 5, HOURS.between(eachDetail.getStartTime(), eachDetail.getEndTime()), tableCellStyle);

        }

        Row totalHour = sheet.createRow(++rowCount);
        Cell totalHourCell = totalHour.createCell(1);

        totalHourCell.setCellValue("Total Hour");
        CellRangeAddress mergeRegion5 = new CellRangeAddress(totalHour.getRowNum(), totalHour.getRowNum(), 1, 4);
        sheet.addMergedRegion(mergeRegion5);

        totalHourCell.setCellStyle(headingCellStyle);

        Cell totalHourCal = totalHour.createCell(5, CellType.FORMULA);
        totalHourCal.setCellValue("=SUM(F" + (totalHourCal.getRowIndex()) + ":F" + (totalHourCal.getRowIndex() - (this.overTimeDetailList.size() - 1)) + ")\n");

        sheet.createRow(++rowCount);

        Row text1 = sheet.createRow(++rowCount);
        Cell cell1 = text1.createCell(1);
        cell1.setCellValue("I certify that this is a true and correct claim of overtime incurred by me on the above dates.");
        CellRangeAddress mergeRegion6 = new CellRangeAddress(text1.getRowNum(), text1.getRowNum(), 1, 5);
        sheet.addMergedRegion(mergeRegion6);
        int currentRow = ++rowCount;
        CellRangeAddress mergeRegion7 = new CellRangeAddress(currentRow, currentRow, 1, 5);
        sheet.addMergedRegion(mergeRegion7);


        Row line = sheet.createRow(++rowCount);
        Cell cell2 = line.createCell(1);
        cell2.setCellValue("_______________________");
        CellRangeAddress mergeRegion8 = new CellRangeAddress(line.getRowNum(), line.getRowNum(), 1, 5);
        sheet.addMergedRegion(mergeRegion8);


        Row employeeSignature = sheet.createRow(++rowCount);
        Cell cellSign = employeeSignature.createCell(1);
        cellSign.setCellValue("Employee's Signature");
        Cell date=employeeSignature.createCell(4);
        date.setCellValue("Date: "+ LocalDate.now());
         currentRow = ++rowCount;
        CellRangeAddress mergeRegionGap = new CellRangeAddress(currentRow, currentRow, 1, 5);
        sheet.addMergedRegion(mergeRegionGap);



        Row text2 = sheet.createRow(++rowCount);
        Cell cell3 = text2.createCell(1);
        cell3.setCellValue("I certify that this is a true and correct claim of overtime incurred by the above employee" +
                " on the above dates. Therefore, I recommend payment for the above overtime.");
        CellRangeAddress mergeRegion9 = new CellRangeAddress(text2.getRowNum(), text2.getRowNum(), 1, 13);
        sheet.addMergedRegion(mergeRegion9);
        currentRow = ++rowCount;
        CellRangeAddress mergeRegion10 = new CellRangeAddress(currentRow, currentRow, 1, 13);
        sheet.addMergedRegion(mergeRegion10);


        Row line1 = sheet.createRow(++rowCount);
        Cell cell5 = line1.createCell(1);
        cell5.setCellValue("_______________________");
        CellRangeAddress mergeRegion11 = new CellRangeAddress(line1.getRowNum(), line1.getRowNum(), 1, 5);
        sheet.addMergedRegion(mergeRegion11);


        Row superVisorSign = sheet.createRow(++rowCount);
        Cell cellSign1 = superVisorSign.createCell(1);
        cellSign1.setCellValue("Supervisor’s signature ");
        Cell date1=superVisorSign.createCell(4);
        date1.setCellValue("Date: "+ LocalDate.now());

        currentRow=++rowCount;
        CellRangeAddress mergeRegionGap1 = new CellRangeAddress(currentRow, currentRow, 1, 5);
        sheet.addMergedRegion(mergeRegionGap1);


        Row HrApproval = sheet.createRow(++rowCount);
        Cell hrCell = HrApproval.createCell(1);
        hrCell.setCellValue("HR Approval ");


        currentRow = ++rowCount;
        CellRangeAddress mergeRegion17 = new CellRangeAddress(currentRow, currentRow, 1, 5);
        sheet.addMergedRegion(mergeRegion17);

        Row line3 = sheet.createRow(++rowCount);
        Cell cell13 = line3.createCell(1);
        cell13.setCellValue("_______________________");
        CellRangeAddress mergeRegion13 = new CellRangeAddress(line3.getRowNum(), line3.getRowNum(), 1, 5);
        sheet.addMergedRegion(mergeRegion13);


        Row hrSign = sheet.createRow(++rowCount);
        Cell cellSign14 = hrSign.createCell(1);
        cellSign14.setCellValue("HR Representative ");
        Cell date3=hrSign.createCell(4);
        date3.setCellValue("Date: "+ LocalDate.now());




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
