// (C) 2025 uchicom
package com.uchicom.kame.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.uchicom.kame.entity.Task;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * {@link TaskDao}のテストケース.
 *
 * @author uchicom
 */
@Tag("dao")
public class TaskDaoTest extends AbstractDaoTest<Task, TaskDao> {

  public TaskDaoTest() {
    super(Task::new, helper -> new TaskDao(helper));
  }

  /** {@link TaskDao#findAll()}のテスト. */
  @Test
  public void findAll() throws Exception {
    test(
        () -> {
          dao.findAll();
          assertThat(getSQL()).isEqualTo("SELECT * FROM kame.task ORDER BY id");
        });
  }

  /** {@link TaskDao#findByProjectId(long)}のテスト. */
  @Test
  public void findByProjectId() throws Exception {
    test(
        () -> {
          dao.findByProjectId(1L);
          assertThat(getSQL()).isEqualTo("SELECT * FROM kame.task WHERE project_id = 1");
        });
  }
}
