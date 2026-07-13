// (C) 2026 uchicom
package com.uchicom.kame.dto.response.file;

public class FileDto {

  public String path;

  public String dataUrl;

  public FileDto(String path, String dataUrl) {
    this.path = path;
    this.dataUrl = dataUrl;
  }
}
