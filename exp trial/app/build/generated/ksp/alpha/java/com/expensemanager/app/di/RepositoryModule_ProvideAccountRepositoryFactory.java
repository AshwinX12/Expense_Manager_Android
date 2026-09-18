package com.expensemanager.app.di;

import com.expensemanager.app.data.db.dao.AccountDao;
import com.expensemanager.app.data.repository.AccountRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RepositoryModule_ProvideAccountRepositoryFactory implements Factory<AccountRepository> {
  private final Provider<AccountDao> accountDaoProvider;

  private RepositoryModule_ProvideAccountRepositoryFactory(
      Provider<AccountDao> accountDaoProvider) {
    this.accountDaoProvider = accountDaoProvider;
  }

  @Override
  public AccountRepository get() {
    return provideAccountRepository(accountDaoProvider.get());
  }

  public static RepositoryModule_ProvideAccountRepositoryFactory create(
      Provider<AccountDao> accountDaoProvider) {
    return new RepositoryModule_ProvideAccountRepositoryFactory(accountDaoProvider);
  }

  public static AccountRepository provideAccountRepository(AccountDao accountDao) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideAccountRepository(accountDao));
  }
}
