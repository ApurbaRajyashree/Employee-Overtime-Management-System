//package com.example.overtimesystem.helper;
//
//import com.example.overtimesystem.entity.OverTimeDetail;
//import lombok.*;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.util.List;
//
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@RequiredArgsConstructor
//public class ExportOverTimeDetail  {
//
//    private List<OverTimeDetail> overTimeDetailList;
//    private XSSFWorkbook workbook;
//    private XSSFSheet sheet;
//
//    public ExportOverTimeDetail(List<OverTimeDetail> overTimeDetailList) {
//        this.overTimeDetailList = overTimeDetailList;
//        workbook=new XSSFWorkbook();
//    }
//
// public  void OtdSheet(){
//        this.sheet=workbook.createSheet("OTD");
//
//        Row headingRow=sheet.createRow(0);
//        Cell headingCell1=headingRow.createCell(0);
//
//        headingCell1.setCellValue("OVERTIME APPROVAL SHEET");
//     CellRangeAddress mergeRegion=new CellRangeAddress()
//
// }
//}
