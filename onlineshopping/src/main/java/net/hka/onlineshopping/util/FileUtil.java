package net.hka.onlineshopping.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * Web utility class used to upload file operation   
 */
public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	//private static final String ABS_PATH = "C:/My Space/My Work/workplace-j2ee/me/online-shopping/onlineshopping/src/main/webapp/assets/images/";
	private static String REAL_PATH = null;
	
	public static boolean uploadFile(HttpServletRequest request, MultipartFile file, String code) {
		// get the real server path
		REAL_PATH = request.getSession().getServletContext().getRealPath("/assets/images/");
		
		//logger.info(REAL_PATH);	
		
		// create the directories if it does not exist
		if(!new File(REAL_PATH).exists()) {
			new File(REAL_PATH).mkdirs();
		}
		
		/*if(!new File(ABS_PATH).exists()) {
			new File(ABS_PATH).mkdirs();
		}*/
		
		//transfer the file to both the location
		try {
			// server side upload
			file.transferTo(new File(REAL_PATH + code + ".jpg"));
			
			// logs the files upload operations to a physical real path on the web server
			String fileName = file.getOriginalFilename();
			String contentType = file.getContentType();
			long size = file.getSize();
			logger.info("Uploading a file: \"" + fileName + "\" type: " + contentType 
							+ " size: " + size + " to the server at ");
			
			// project directory upload
			//file.transferTo(new File(ABS_PATH + code + ".jpg"));
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		return true;
	}
}
