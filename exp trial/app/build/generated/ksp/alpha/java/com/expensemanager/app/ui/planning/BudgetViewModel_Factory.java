package com.expensemanager.app.ui.planning;

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
public final class BudgetViewModel_Factory implements Factory<BudgetViewModel> {
  private final Provider<BudgetRepository> budgetRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private BudgetViewModel_Factory(Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.budgetRepositoryProvider = budgetRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public BudgetViewModel get() {
    return newInstance(budgetRepositoryProvider.get(), categoryRepositoryProvider.get(), transactionRepositoryProvider.get());
  }

  public static BudgetViewModel_Factory create(Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new BudgetViewModel_Factory(budgetRepositoryProvider, categoryRepositoryProvider, transactionRepositoryProvider);
  }

  public static BudgetViewModel newInstance(BudgetRepository budgetRepository,
      CategoryRepository categoryRepository, TransactionRepository transactionRepository) {
    return new BudgetViewModel(budgetRepository, categoryRepository, transactionRepository);
  }
}
