// (C) 2026 uchicom
package com.uchicom.pj.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.uchicom.pj.entity.Application;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/** {@link ApplicationDao}のテストケース. */
@Tag("dao")
public class ApplicationDaoTest extends AbstractDaoTest<Application, ApplicationDao> {

  public ApplicationDaoTest() {
    super(Application::new, ApplicationDao::new);
  }

  /** {@link ApplicationDao#findAll()}のテスト. */
  @Test
  public void findAll() throws Exception {
    test(
        () -> {
          dao.findAll();
          assertThat(getSQL()).isEqualTo("SELECT * FROM kame.application ORDER BY id");
        });
  }

  /** {@link ApplicationDao#findByApiKey(String)}のテスト. */
  @Test
  public void findByApiKey() throws Exception {
    test(
        () -> {
          dao.findByApiKey("app-key");
          assertThat(getSQL())
              .isEqualTo("SELECT * FROM kame.application WHERE api_key = 'app-key'");
        });
  }
}
