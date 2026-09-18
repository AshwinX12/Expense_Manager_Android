package com.expensemanager.app.data.repository;

import com.expensemanager.app.data.db.dao.AccountDao;
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
public final class AccountRepository_Factory implements Factory<AccountRepository> {
  private final Provider<AccountDao> accountDaoProvider;

  private AccountRepository_Factory(Provider<AccountDao> accountDaoProvider) {
    this.accountDaoProvider = accountDaoProvider;
  }

  @Override
  public AccountRepository get() {
    return newInstance(accountDaoProvider.get());
  }

  public static AccountRepository_Factory create(Provider<AccountDao> accountDaoProvider) {
    return new AccountRepository_Factory(accountDaoProvider);
  }

  public static AccountRepository newInstance(AccountDao accountDao) {
    return new AccountRepository(accountDao);
  }
}
