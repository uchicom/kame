// (C) 2026 uchicom
package com.uchicom.kame.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.uchicom.kame.entity.File;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/** {@link FileDao}のテストケース. */
@Tag("dao")
public class FileDaoTest extends AbstractDaoTest<File, FileDao> {

  public FileDaoTest() {
    super(File::new, FileDao::new);
  }

  /** {@link FileDao#findAll()}のテスト. */
  @Test
  public void findAll() throws Exception {
    test(
        () -> {
          dao.findAll();
          assertThat(getSQL()).isEqualTo("SELECT * FROM kame.file ORDER BY id");
        });
  }

  /** {@link FileDao#findByApplicationIdAndPath(Long, String)}のテスト. */
  @Test
  public void findByApplicationIdAndPath() throws Exception {
    test(
        () -> {
          dao.findByApplicationIdAndPath(1L, "/hoge/a.txt");
          assertThat(getSQL())
              .isEqualTo(
                  "SELECT * FROM kame.file WHERE application_id = 1 AND path = '/hoge/a.txt'");
        });
  }
}
