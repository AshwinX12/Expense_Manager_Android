package com.expensemanager.app.ui.reports;

import com.expensemanager.app.data.repository.BudgetRepository;
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
public final class ReportsViewModel_Factory implements Factory<ReportsViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<BudgetRepository> budgetRepositoryProvider;

  private ReportsViewModel_Factory(Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<BudgetRepository> budgetRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.budgetRepositoryProvider = budgetRepositoryProvider;
  }

  @Override
  public ReportsViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), categoryRepositoryProvider.get(), budgetRepositoryProvider.get());
  }

  public static ReportsViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<BudgetRepository> budgetRepositoryProvider) {
    return new ReportsViewModel_Factory(transactionRepositoryProvider, categoryRepositoryProvider, budgetRepositoryProvider);
  }

  public static ReportsViewModel newInstance(TransactionRepository transactionRepository,
      CategoryRepository categoryRepository, BudgetRepository budgetRepository) {
    return new ReportsViewModel(transactionRepository, categoryRepository, budgetRepository);
  }
}
