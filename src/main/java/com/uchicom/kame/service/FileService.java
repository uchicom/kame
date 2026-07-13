// (C) 2026 uchicom
package com.uchicom.kame.service;

import com.uchicom.kame.dao.FileDao;
import com.uchicom.kame.dto.request.file.FileUploadDto;
import com.uchicom.kame.entity.File;
import java.util.Base64;
import java.util.regex.Pattern;

/** ファイルサービス. */
public class FileService {

  private static final Pattern DATA_URL_PATTERN =
      Pattern.compile("^data:(.*?);base64,(.*)$", Pattern.DOTALL);

  private final FileDao fileDao;

  public FileService(FileDao fileDao) {
    this.fileDao = fileDao;
  }

  public void register(long applicationId, FileUploadDto dto) {
    var file = fileDao.findByApplicationIdAndPath(applicationId, dto.path);
    if (file == null) {
      file = new File();
      file.application_id = applicationId;
      file.path = dto.path;
      file.content_type = getContentType(dto.dataUrl);
      file.data = getData(dto.dataUrl);
      fileDao.insert(file);
    } else {
      file.content_type = getContentType(dto.dataUrl);
      file.data = getData(dto.dataUrl);
      fileDao.update(file);
    }
  }

  public File get(long applicationId, String path) {
    return fileDao.findByApplicationIdAndPath(applicationId, path);
  }

  public String toDataUrl(File file) {
    return "data:" + file.content_type + ";base64," + Base64.getEncoder().encodeToString(file.data);
  }

  String getContentType(String dataUrl) {
    var matcher = DATA_URL_PATTERN.matcher(dataUrl);
    if (!matcher.matches()) {
      return null;
    }
    return matcher.group(1);
  }

  byte[] getData(String dataUrl) {
    var matcher = DATA_URL_PATTERN.matcher(dataUrl);
    var base64Data = matcher.matches() ? matcher.group(2) : dataUrl;
    return Base64.getDecoder().decode(base64Data);
  }
}
