// (C) 2026 uchicom
package com.uchicom.kame.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.uchicom.kame.entity.RefreshToken;
import com.uchicom.kame.util.SecurityUtil;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/** {@link RefreshTokenDao}のテストケース. */
@Tag("dao")
public class RefreshTokenDaoTest extends AbstractDaoTest<RefreshToken, RefreshTokenDao> {

  protected RefreshTokenDaoTest() {
    super(RefreshToken::new, RefreshTokenDao::new);
  }

  /** {@link RefreshTokenDao#findAll()}のテスト. */
  @Test
  public void findAll() throws Exception {
    test(
        () -> {
          dao.findAll();
          assertThat(getSQL()).isEqualTo("SELECT * FROM kame.refresh_token ORDER BY id");
        });
  }

  /** {@link RefreshTokenDao#findByAccountId(Long)}のテスト. */
  @Test
  public void findByAccountId() throws Exception {
    test(
        () -> {
          dao.findByAccountId(1L);
          assertThat(getSQL()).isEqualTo("SELECT * FROM kame.refresh_token WHERE account_id = 1");
        });
  }

  /** {@link RefreshTokenDao#findRefreshable(byte[], LocalDateTime)}のテスト. */
  @SuppressWarnings("ArrayToString")
  @Test
  public void findRefreshable() throws Exception {
    test(
        () -> {
          var tokenHash = SecurityUtil.getHash("token", "");
          dao.findRefreshable(tokenHash, LocalDateTime.of(2026, 3, 29, 12, 0, 0));
          assertThat(getSQL())
              .startsWith(
                  "SELECT * FROM kame.refresh_token WHERE token_hash = "
                      + tokenHash
                      + " AND expire_datetime >= '2026-03-29 12:00:00' AND inactive_datetime IS NULL");
        });
  }
}
