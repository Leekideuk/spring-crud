package com.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.common.vo.FileVO;

public class FileUtil {
	
	// 파일 저장
	public static List<FileVO> saveAllFiles(String path, List<MultipartFile> fileList) {
		List<FileVO> fList = new ArrayList<FileVO>();
		if(fileList == null){ return fList; }
        for (MultipartFile file : fileList ) {
            if (!file.isEmpty()) {
            	// 서버에 저장할 파일 이름 생성
            	String fileName = getNewFileName(file.getOriginalFilename());
            	// 파일 저장
                saveFile(file, path, fileName);
                
                FileVO vo = new FileVO();
                vo.setFileName(file.getOriginalFilename());
                vo.setRealName(fileName);
                vo.setFileSize(file.getSize());
                vo.setPath(path);
                fList.add(vo);
                
                // 이미지 파일은 썸네일 생성
                String formatName = FilenameUtils.getExtension(fileName);
                if(MediaUtil.getMediaType(formatName) != null){
                	// 썸네일 생성
                	makeThumbnail(path, fileName);
                }
            }
        }
        return fList;
    }
	
    // 새로운 파일명 부여
    private static String getNewFileName(String fileName) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        return ft.format(new Date()) + "-" + UUID.randomUUID().toString()+ "_" + fileName;
    }
	
	// 실제 파일 저장.
    private static String saveFile(MultipartFile file, String path, String fileName){
        if (file == null || file.getName().equals("") || file.getSize() < 1) {
            return null;
        }
        // 디렉토리 생성
        makeDir(path);
        
        File file1 = new File(path, fileName);
        try {
            file.transferTo(file1);
        } catch (IllegalStateException e) {
            System.out.println("FileUtil.saveFile : " + e);
        } catch (IOException e) {
            System.out.println("FileUtil.saveFile : " + e);
        }
        return path + fileName;
    }
    
    // uploadPath 디렉토리 생성.
	private static void makeDir(String path) {
		File dir = new File(path); 
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
	
	// 썸네일 생성
    private static void makeThumbnail(String path, String fileName) {
    	try{
    		// 이미지 읽기 버퍼
    		BufferedImage sourceImg = ImageIO.read(new File(path + fileName));
    		// 100픽셀 단위의 썸네일 생성
    		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
    		// 썸네일 이름 생성(s_+원본파일명)
    		String thumbnailName = getThumbnailName(fileName);
    		// 썸네일 생성
    		File newFile = new File(path, thumbnailName);
    		String formatName = FilenameUtils.getExtension(fileName);
    		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    		// 특정 jpg 이미지 작업시 javax.imageio.IIOException: Invalid argument to native writeImage 발생.
    		// 게시판에서 이미지 다운로드 후 우분투에서는 "JPEG 파일 해석 중 오류" 발생함.  윈도우에서는 제대로 읽음.
    		// jpg -> png로 확장자 변경 시 예외 발생 안 함.
    		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
    	} catch(Exception e){
    		System.out.println("FileUtil.makeThumbnail : " + e);
    	}
    }
    
    // 썸네일 이름 생성
    public static String getThumbnailName(String fileName){
    	return "s_" + fileName;
    }
    
    // basePath + 날짜 path
    public static String getUploadPath(String basePath){
    	Calendar cal = Calendar.getInstance();
        String yearPath = File.separator + cal.get(Calendar.YEAR) + File.separator;
        String monthPath = yearPath + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1) + File.separator;
        String datePath = monthPath + new DecimalFormat("00").format(cal.get(Calendar.DATE)) + File.separator;
        
        return basePath + datePath;
    }
	
	// 파일 -> byte[]
	public static byte[] fileToByteArray(FileVO file){
    	InputStream in = null;
    	
    	try{
    		in = new FileInputStream(file.getPath()+file.getRealName());
    		return IOUtils.toByteArray(in);
    	}catch(Exception e){
    		System.out.println("FileUtil.fileToByteArray : " + e);
    	}finally{
    		try{ in.close(); } catch(IOException e){ System.out.println("FileUtil.fileToByteArray : " + e); }
    	}
    	return null;
    }
	
	// 브라우저 별 파일이름 인코딩 및 응답 헤더 설정
	public static void setResponseHeader(HttpServletRequest request, HttpServletResponse response, FileVO file) {  
		String header = request.getHeader("User-Agent");
		  try{
			  if (header.contains("MSIE") || header.contains("Trident")) {
				  String docName = URLEncoder.encode(file.getFileName(),"UTF-8").replaceAll("\\+", "%20");
				  response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");
			  } else if (header.contains("Firefox")) {
				  String docName = new String(file.getFileName().getBytes("UTF-8"), "ISO-8859-1");
				  response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
			  } else if (header.contains("Opera")) {
				  String docName = new String(file.getFileName().getBytes("UTF-8"), "ISO-8859-1");
				  response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
			  } else if (header.contains("Chrome")) {
				  String docName = new String(file.getFileName().getBytes("UTF-8"), "ISO-8859-1");
				  response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
			  }
			  response.setHeader("Content-Type", "application/octet-stream");
			  response.setHeader("Content-Transfer-Encoding", "binary;");
			  response.setHeader("Pragma", "no-cache;");
			  response.setHeader("Expires", "-1;");
			  
		  } catch (UnsupportedEncodingException e) {
			  System.out.println("FileUtil.setResponseHeader : " + e);
	     }
		  
	}
	
	// 파일 삭제
	public static void deleteFile(FileVO file){
		// 확장자가 이미지 일 경우 썸네일 삭제
		String formatName = FilenameUtils.getExtension(file.getRealName());
		MediaType mType = MediaUtil.getMediaType(formatName);
		if(mType != null){ new File(file.getPath()+ getThumbnailName(file.getRealName())).delete(); }
		
		// 원본파일 삭제
		new File(file.getPath()+file.getRealName()).delete();
	}
}
