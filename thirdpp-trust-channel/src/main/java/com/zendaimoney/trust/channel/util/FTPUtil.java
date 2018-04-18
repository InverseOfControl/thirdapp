package com.zendaimoney.trust.channel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * FTP文件上传、下载操作工具
 * @author mencius
 *
 */
public class FTPUtil {
	
	private static final LogPrn logger = new LogPrn(FTPUtil.class);
	
    private FTPClient ftpClient;    
    public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;    
    public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;    
    
    /**
     * 工具构造器
     * @param host
     * @param port
     * @param user
     * @param password
     * @param remotePath
     * @throws SocketException
     * @throws IOException
     */
    public boolean connectServer(String host,String port,String user,String password,String remotePath) throws SocketException,    
            IOException {       
        return connectServer(host, Integer.parseInt(port), user, password, remotePath);    
    }    
        
        
    public boolean connectServer(String server, int port, String user,    
            String password, String path) throws SocketException, IOException {    
        ftpClient = new FTPClient();    
        //ftpClient.connect(server, port);    
        //ftpClient.login(user, password);
        try {
			int reply;
			ftpClient.connect(server, port);

			// 连接后检测返回码来校验连接是否成功
			reply = ftpClient.getReplyCode();

			if (FTPReply.isPositiveCompletion(reply)) {
				if (ftpClient.login(user, password)) {
					// 设置为passive模式
					ftpClient.enterLocalPassiveMode();
					//ftpClient.enterLocalActiveMode();
					//ftpClient.enterRemotePassiveMode();
					if (path.length() != 0) {    
			            ftpClient.changeWorkingDirectory(path);    
			        }
					logger.info("Connected to " + server + " replyCode: " + ftpClient.getReplyCode());
					logger.info("ftp登陆成功");
					return true;
				}
				logger.error("ftp登陆失败");
			} else {
				logger.error("ftp连接失败");
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException f) {
				}
			}
		}
        return false;
    }    
 
    
    public void setFileType(int fileType) throws IOException {    
        ftpClient.setFileType(fileType);    
    }    
    
    public void closeServer() throws IOException {    
        if (ftpClient.isConnected()) {    
            ftpClient.disconnect();    
        }    
    }    
        
    public boolean changeDirectory(String path) throws IOException {    
        return ftpClient.changeWorkingDirectory(path);    
    }    
    public boolean createDirectory(String pathName) throws IOException {    
        return ftpClient.makeDirectory(pathName);    
    }    
    public boolean removeDirectory(String path) throws IOException {    
        return ftpClient.removeDirectory(path);    
    }    
          
    public boolean removeDirectory(String path, boolean isAll)    
            throws IOException {    
            
        if (!isAll) {    
            return removeDirectory(path);    
        }    
    
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);    
        if (ftpFileArr == null || ftpFileArr.length == 0) {    
            return removeDirectory(path);    
        }    
        //     
        for (FTPFile ftpFile : ftpFileArr) {    
            String name = ftpFile.getName();    
            if (ftpFile.isDirectory()) {    
            	System.out.println("* [sD]Delete subPath ["+path + "/" + name+"]");                 
                removeDirectory(path + "/" + name, true);    
            } else if (ftpFile.isFile()) {    
            	System.out.println("* [sF]Delete file ["+path + "/" + name+"]");                            
                deleteFile(path + "/" + name);    
            } else if (ftpFile.isSymbolicLink()) {    
    
            } else if (ftpFile.isUnknown()) {    
    
            }    
        }    
        return ftpClient.removeDirectory(path);    
    }    
        
    // Check the path is exist; exist return true, else false.    
    public boolean existDirectory(String path) throws IOException {    
        boolean flag = false;    
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);

        for (FTPFile ftpFile : ftpFileArr) {    
            if (ftpFile.isDirectory()    
                    && ftpFile.getName().equalsIgnoreCase(path)) {    
                flag = true;    
                break;    
            }    
        }    
        return flag;    
    }    
    
 // Check the path is exist; exist return true, else false.    
    public boolean existPathDirectory(String path) throws IOException {    
        boolean flag = false;    
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
			return true;
		}
        return flag;    
    }  
    
    public List<String> getFileList(String path) throws IOException {      
        FTPFile[] ftpFiles= ftpClient.listFiles(path);    
            
        List<String> retList = new ArrayList<String>();    
        if (ftpFiles == null || ftpFiles.length == 0) {    
            return retList;    
        }    
        for (FTPFile ftpFile : ftpFiles) {    
            if (ftpFile.isFile()) {    
                retList.add(ftpFile.getName());    
            }    
        }    
        return retList;    
    }    
    
    public boolean deleteFile(String pathName) throws IOException {    
        return ftpClient.deleteFile(pathName);    
    }    
    
    
    public boolean uploadFile(String fileName, String newName)    
            throws IOException {    
        boolean flag = false;    
        InputStream iStream = null;    
        try {    
            iStream = new FileInputStream(fileName);    
            flag = ftpClient.storeFile(newName, iStream);    
        } catch (IOException e) {    
            flag = false;    
            return flag;    
        } finally {    
            if (iStream != null) {    
                iStream.close();    
            }    
        }    
        return flag;    
    }    
    
    public boolean uploadFile(String fileName) throws IOException {    
        return uploadFile(fileName, fileName);    
    }    
    
    public boolean uploadFile(InputStream iStream, String newName)    
            throws IOException {    
        boolean flag = false;    
        try {    
            flag = ftpClient.storeFile(newName, iStream);    
        } catch (IOException e) {    
            flag = false;    
            return flag;    
        } finally {    
            if (iStream != null) {    
                iStream.close();    
            }    
        }    
        return flag;    
    }    
    
    public boolean download(String remoteFileName, String localFileName)    
            throws IOException {    
        boolean flag = false;    
        File outfile = new File(localFileName);    
        OutputStream oStream = null;    
        try {    
            oStream = new FileOutputStream(outfile);    
            flag = ftpClient.retrieveFile(remoteFileName, oStream);    
        } catch (IOException e) {    
            flag = false;    
            return flag;    
        } finally {  
        	if(oStream!=null){
        	    oStream.close();    
        	}
        }    
        return flag;    
    }    
        
    public InputStream downFile(String sourceFileName) throws IOException {    
        return ftpClient.retrieveFileStream(sourceFileName);    
    }
    
    public static void main(String[] args) {
		FTPUtil ftpUtil = new FTPUtil();
		
		try {
			ftpUtil.connectServer("10.90.6.67", "21", "8888021654", "U36Ji#wJ", "");
			
			System.out.println(ftpUtil.ftpClient.isConnected());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
