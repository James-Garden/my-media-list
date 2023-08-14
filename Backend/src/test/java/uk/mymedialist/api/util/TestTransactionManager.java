package uk.mymedialist.api.util;

import static org.assertj.core.api.Fail.fail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class TestTransactionManager {

  private final PlatformTransactionManager transactionManager;

  @Autowired
  public TestTransactionManager(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  public void runInTransaction(Runnable runnable) {
    var status = transactionManager.getTransaction(null);

    try {
      runnable.run();
      transactionManager.commit(status);
    } catch (Exception e) {
      transactionManager.rollback(status);
      fail("Failed to run in transaction", e);
    }
  }
}
