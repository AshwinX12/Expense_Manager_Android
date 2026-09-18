package com.expensemanager.app.data.repository;

import com.expensemanager.app.data.db.dao.AccountDao;
import com.expensemanager.app.data.db.dao.LendingDao;
import com.expensemanager.app.data.db.dao.TransactionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class LendingRepository_Factory implements Factory<LendingRepository> {
  private final Provider<LendingDao> lendingDaoProvider;

  private final Provider<TransactionDao> transactionDaoProvider;

  private final Provider<AccountDao> accountDaoProvider;

  private LendingRepository_Factory(Provider<LendingDao> lendingDaoProvider,
      Provider<TransactionDao> transactionDaoProvider, Provider<AccountDao> accountDaoProvider) {
    this.lendingDaoProvider = lendingDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
    this.accountDaoProvider = accountDaoProvider;
  }

  @Override
  public LendingRepository get() {
    return newInstance(lendingDaoProvider.get(), transactionDaoProvider.get(), accountDaoProvider.get());
  }

  public static LendingRepository_Factory create(Provider<LendingDao> lendingDaoProvider,
      Provider<TransactionDao> transactionDaoProvider, Provider<AccountDao> accountDaoProvider) {
    return new LendingRepository_Factory(lendingDaoProvider, transactionDaoProvider, accountDaoProvider);
  }

  public static LendingRepository newInstance(LendingDao lendingDao, TransactionDao transactionDao,
      AccountDao accountDao) {
    return new LendingRepository(lendingDao, transactionDao, accountDao);
  }
}
