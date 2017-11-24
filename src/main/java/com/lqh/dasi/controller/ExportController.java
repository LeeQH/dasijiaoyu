package com.lqh.dasi.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lqh.dasi.commen.URLConstant;
import com.lqh.dasi.service.ExportService;

import net.sf.json.JSONArray;
/**
 * 导出excel(作废，数据由前端js导出)
 * @author LiQuanhui
 * @date 2017年11月24日 下午5:27:31
 */
@Controller
@RequestMapping("/exportExcel")
@Deprecated
public class ExportController {

	@Autowired
	public ExportService es;
	
	/**
	 * 导出学生信息到excel
	 * @author LiQuanhui
	 * @date 2017年11月24日 下午2:50:43
	 * @param data
	 */
	@RequestMapping("/stuInfo.action")
	@ResponseBody
	@Deprecated
	public String exportStuInfo(@RequestBody String data){
		JSONArray jsonArray=JSONArray.fromObject(data);
		es.exportStuInfo(jsonArray);
		return "success";
	}

	
	@RequestMapping("/download.action")
	@Deprecated
	public void downloadFile(String fileName,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		System.out.println("download:"+fileName);
		if (fileName != null) {
            String path = URLConstant.CLASS_PATH+"tmpfiles/"+fileName;
            File file = new File(path);
            if (file.exists()) {
                response.setContentType("application/octet-stream");// 设置强制下载不打开
                response.setHeader("Content-disposition", "attachment; filename=" 
                        + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
	}
}
