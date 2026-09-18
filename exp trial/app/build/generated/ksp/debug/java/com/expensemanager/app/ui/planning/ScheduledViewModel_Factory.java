package com.expensemanager.app.ui.planning;

import com.expensemanager.app.data.repository.AccountRepository;
import com.expensemanager.app.data.repository.CategoryRepository;
import com.expensemanager.app.data.repository.RecurringTransactionRepository;
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
public final class ScheduledViewModel_Factory implements Factory<ScheduledViewModel> {
  private final Provider<RecurringTransactionRepository> recurringTransactionRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  private ScheduledViewModel_Factory(
      Provider<RecurringTransactionRepository> recurringTransactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    this.recurringTransactionRepositoryProvider = recurringTransactionRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
  }

  @Override
  public ScheduledViewModel get() {
    return newInstance(recurringTransactionRepositoryProvider.get(), categoryRepositoryProvider.get(), accountRepositoryProvider.get());
  }

  public static ScheduledViewModel_Factory create(
      Provider<RecurringTransactionRepository> recurringTransactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    return new ScheduledViewModel_Factory(recurringTransactionRepositoryProvider, categoryRepositoryProvider, accountRepositoryProvider);
  }

  public static ScheduledViewModel newInstance(
      RecurringTransactionRepository recurringTransactionRepository,
      CategoryRepository categoryRepository, AccountRepository accountRepository) {
    return new ScheduledViewModel(recurringTransactionRepository, categoryRepository, accountRepository);
  }
}
