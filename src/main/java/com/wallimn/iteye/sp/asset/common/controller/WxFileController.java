package com.wallimn.iteye.sp.asset.common.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wallimn.iteye.sp.asset.bus.inform.model.InformReplyAttach;
import com.wallimn.iteye.sp.asset.bus.inform.service.InformService;

/**
 * 文件上传控制器的基类。
 * 
 * <br>
 * <br>时间：2018年7月27日 下午3:12:34，作者：wallimn
 */
@Controller
public class WxFileController {
	
	private static final Logger log = LoggerFactory.getLogger(WxFileController.class);

	@Value("${wx.uploadPath}")
	private String uploadPath;

	private String getFileType(String fileN) {
		if (StringUtils.isEmpty(fileN))
			return "";
		int pos = fileN.lastIndexOf(".");
		return fileN.substring(pos);
	}

	@RequestMapping(value = "/media/upload", method = RequestMethod.POST)
	@ResponseBody
	public List<InformReplyAttach> upload(HttpServletRequest request, 
			@RequestParam("file") MultipartFile[] files,
			@RequestParam("replyId") String replyId
			) {
		log.info("replyId={}",replyId);
		List<InformReplyAttach> result = new LinkedList<InformReplyAttach>();
		String fileN = null;
		// 多文件上传
		if (files != null && files.length >= 1) {
			BufferedOutputStream bw = null;
			try {
				String fileName = files[0].getOriginalFilename();
				// 判断是否有文件(实际生产中要判断是否是音频文件)
				if (StringUtils.isNoneBlank(fileName)) {
					// 创建输出文件对象
					fileN = UUID.randomUUID().toString().replace("-", "") + this.getFileType(fileName);
					File outFile = new File(uploadPath + fileN);
					// 拷贝文件到输出文件对象
					FileUtils.copyInputStreamToFile(files[0].getInputStream(), outFile);
					InformReplyAttach entity = new InformReplyAttach();
					entity.setReplyId(replyId);
					entity.setAttachUrl(fileN);
					this.informService.insertInformReplyAttach(entity);
					result.add(entity);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (bw != null) {
						bw.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}	
	
	@Autowired
	private InformService informService;
	
    @RequestMapping(value = "/media/show", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> showFile(String fileN){

        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            //return ResponseEntity.ok(resourceLoader.getResource("file:" + this.uploadPath + fileName));
            String filePath = this.uploadPath+fileN;
            FileSystemResource file = new FileSystemResource(filePath);  
            return ResponseEntity.ok().body(new InputStreamResource(file.getInputStream()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
	@RequestMapping(value = "/media/download", method = RequestMethod.GET)  
    public ResponseEntity<InputStreamResource> downloadFile( String fileN)  
            throws IOException {  
        String filePath = this.uploadPath+fileN;
        FileSystemResource file = new FileSystemResource(filePath);  
        HttpHeaders headers = new HttpHeaders();  
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileN));  
        headers.add("Pragma", "no-cache");  
        headers.add("Expires", "0");  
  
        return ResponseEntity  
                .ok()  
                .headers(headers)  
                .contentLength(file.contentLength())  
                .contentType(MediaType.parseMediaType("application/octet-stream"))  
                .body(new InputStreamResource(file.getInputStream()));  
    } 
}
