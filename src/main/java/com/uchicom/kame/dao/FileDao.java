// (C) 2026 uchicom
package com.uchicom.kame.dao;

import com.uchicom.kame.dao.helper.DbHelper;
import com.uchicom.kame.entity.File;

/**
 * {@link File}ファイルリポジトリ.
 *
 * @author uchicom
 */
public class FileDao extends AbstractDao<File> {

  /** コンストラクタ. */
  public FileDao(DbHelper<File> helper) {
    super(helper);
  }

  public File findByApplicationIdAndPath(Long applicationId, String path) {
    var file = new File();
    return helper
        .from(file)
        .where(file.application_id)
        .is(applicationId)
        .and(file.path)
        .is(path)
        .selectFirst();
  }
}
