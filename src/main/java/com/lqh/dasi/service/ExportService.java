package com.lqh.dasi.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.lqh.dasi.commen.ExcelUtils;
import com.lqh.dasi.commen.URLConstant;

import net.sf.json.JSONArray;

/**
 * 导出excel的service(作废，数据由前端js导出)
 * @author LiQuanhui
 * @date 2017年11月24日 下午5:31:07
 */
@Service
@Deprecated
public class ExportService {
	
	/**
	 * 导出学生信息
	 * @author LiQuanhui
	 * @date 2017年11月24日 下午5:31:40
	 * @param stuInfos jsonarray的数据
	 */
	@Deprecated
	public void exportStuInfo(JSONArray data){
		JSONArray head=data.getJSONArray(0);
		JSONArray body=data.getJSONArray(1);
		String fileName=data.getString(2);
		String path=URLConstant.CLASS_PATH+"tmpfiles/";
		HSSFWorkbook workbook=ExcelUtils.expExcel(head, body);
		ExcelUtils.outFile(workbook, path+fileName);
	}
}
