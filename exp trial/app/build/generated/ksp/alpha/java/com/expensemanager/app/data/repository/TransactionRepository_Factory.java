package com.expensemanager.app.data.repository;

import com.expensemanager.app.data.db.dao.AccountDao;
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
public final class TransactionRepository_Factory implements Factory<TransactionRepository> {
  private final Provider<TransactionDao> transactionDaoProvider;

  private final Provider<AccountDao> accountDaoProvider;

  private TransactionRepository_Factory(Provider<TransactionDao> transactionDaoProvider,
      Provider<AccountDao> accountDaoProvider) {
    this.transactionDaoProvider = transactionDaoProvider;
    this.accountDaoProvider = accountDaoProvider;
  }

  @Override
  public TransactionRepository get() {
    return newInstance(transactionDaoProvider.get(), accountDaoProvider.get());
  }

  public static TransactionRepository_Factory create(
      Provider<TransactionDao> transactionDaoProvider, Provider<AccountDao> accountDaoProvider) {
    return new TransactionRepository_Factory(transactionDaoProvider, accountDaoProvider);
  }

  public static TransactionRepository newInstance(TransactionDao transactionDao,
      AccountDao accountDao) {
    return new TransactionRepository(transactionDao, accountDao);
  }
}
