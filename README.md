  You can use like this to export excel   
   
   @RequestMapping("/test")
    @ExportExcel(name = "user.xls")
    public List<SummaryExcel> ExportExcel(){
        List<SummaryExcel> list = new ArrayList<>();
        SummaryExcel summaryExcel = new SummaryExcel();
        summaryExcel.setSum(1.0);
        summaryExcel.setDate("2020");
        summaryExcel.setType("good");
        list.add(summaryExcel);
        return list;
    }
  
  the Dto like this use easy poi to expoer
  
  
@ExcelTarget("summaryexcel")
@Data
public class SummaryExcel {

    @Excel(name = "日期", orderNum = "1")
    private String date;//当天日期

    @Excel(name = "能源类型", orderNum = "2")
    private String type;         // 能源类型

    @Excel(name = "能源用量", orderNum = "3")
    private Double sum;          // 用量
}
