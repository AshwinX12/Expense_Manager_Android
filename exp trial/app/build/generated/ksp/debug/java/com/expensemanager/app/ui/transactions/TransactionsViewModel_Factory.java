package com.expensemanager.app.ui.transactions;

import com.expensemanager.app.data.repository.AccountRepository;
import com.expensemanager.app.data.repository.CategoryRepository;
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
public final class TransactionsViewModel_Factory implements Factory<TransactionsViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  private TransactionsViewModel_Factory(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
  }

  @Override
  public TransactionsViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), categoryRepositoryProvider.get(), accountRepositoryProvider.get());
  }

  public static TransactionsViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider) {
    return new TransactionsViewModel_Factory(transactionRepositoryProvider, categoryRepositoryProvider, accountRepositoryProvider);
  }

  public static TransactionsViewModel newInstance(TransactionRepository transactionRepository,
      CategoryRepository categoryRepository, AccountRepository accountRepository) {
    return new TransactionsViewModel(transactionRepository, categoryRepository, accountRepository);
  }
}
