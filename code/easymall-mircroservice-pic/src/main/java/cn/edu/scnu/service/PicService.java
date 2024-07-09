package cn.edu.scnu.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UrlPathHelper;

import com.easymall.common.utils.UploadUtil;
import com.easymall.common.vo.PicUploadResult;


@Service
public class PicService {
	@Value("${pic.pathDirPrefix}")
	private String pathDirPrefix;
	@Value("${pic.urlPrefix}")
	private String urlPreparePrefix;
	public PicUploadResult picUpload(MultipartFile pic) {
		PicUploadResult result=new PicUploadResult();
		// TODO Auto-generated method stub
		// 1)判断后缀合法
		String originName=pic.getOriginalFilename();
		//split,substring
		String extName=originName.substring(originName.lastIndexOf("."));
		//jpg,gif,png
		boolean isok=extName.matches(".(jpg|gif|png|JPG|GIF|PNG)$");
		if(!isok){
			result.setError(1);
			return result;
		}
		// 2)判断是不是木马
		try {
			BufferedImage bufImg=ImageIO.read(pic.getInputStream());
			bufImg.getWidth();
			bufImg.getHeight();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setError(1);
			return result;
		}
		// 3)创建以upload开始的路径
		String dir="/"+UploadUtil.getUploadPath(originName, "upload")+"/";
		
		// 4)创建ngnix访问的静态目录，pathDir，通过配置文件中的变量pathDirPrefix创建
		String pathDir=pathDirPrefix+dir;
		File file=new File(pathDir);
		boolean isexists=file.exists();
		if(!isexists){
			file.mkdirs();
		}
		// 5)创建urlPrefix
		String urlPrefix=urlPreparePrefix+dir;
		// 6)拼接图片名称，将图片命名uui的表示图片存储访问的名称
		String fileName=UUID.randomUUID().toString()+extName;
		// 7)上传文件到磁盘路径
		try {
			pic.transferTo(new File(pathDir+fileName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setError(1);
			return result;
		} 
		// 8)返回urlPrefix+图片名称的路径
		result.setUrl(urlPrefix+fileName);
		
		return result;
	}

}
