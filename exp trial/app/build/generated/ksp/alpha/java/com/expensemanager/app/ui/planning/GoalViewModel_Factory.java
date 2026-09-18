package com.expensemanager.app.ui.planning;

import com.expensemanager.app.data.repository.AccountRepository;
import com.expensemanager.app.data.repository.GoalRepository;
import com.expensemanager.app.data.repository.TransactionRepository;
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
public final class GoalViewModel_Factory implements Factory<GoalViewModel> {
  private final Provider<GoalRepository> goalRepositoryProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private GoalViewModel_Factory(Provider<GoalRepository> goalRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.goalRepositoryProvider = goalRepositoryProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public GoalViewModel get() {
    return newInstance(goalRepositoryProvider.get(), accountRepositoryProvider.get(), transactionRepositoryProvider.get());
  }

  public static GoalViewModel_Factory create(Provider<GoalRepository> goalRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new GoalViewModel_Factory(goalRepositoryProvider, accountRepositoryProvider, transactionRepositoryProvider);
  }

  public static GoalViewModel newInstance(GoalRepository goalRepository,
      AccountRepository accountRepository, TransactionRepository transactionRepository) {
    return new GoalViewModel(goalRepository, accountRepository, transactionRepository);
  }
}
