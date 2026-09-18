package com.expensemanager.app.ui.home;

import com.expensemanager.app.data.db.dao.QuickAddShortcutDao;
import com.expensemanager.app.data.repository.AccountRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  private final Provider<BudgetRepository> budgetRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<QuickAddShortcutDao> quickAddShortcutDaoProvider;

  private HomeViewModel_Factory(Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<QuickAddShortcutDao> quickAddShortcutDaoProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
    this.budgetRepositoryProvider = budgetRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.quickAddShortcutDaoProvider = quickAddShortcutDaoProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), accountRepositoryProvider.get(), budgetRepositoryProvider.get(), categoryRepositoryProvider.get(), quickAddShortcutDaoProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<QuickAddShortcutDao> quickAddShortcutDaoProvider) {
    return new HomeViewModel_Factory(transactionRepositoryProvider, accountRepositoryProvider, budgetRepositoryProvider, categoryRepositoryProvider, quickAddShortcutDaoProvider);
  }

  public static HomeViewModel newInstance(TransactionRepository transactionRepository,
      AccountRepository accountRepository, BudgetRepository budgetRepository,
      CategoryRepository categoryRepository, QuickAddShortcutDao quickAddShortcutDao) {
    return new HomeViewModel(transactionRepository, accountRepository, budgetRepository, categoryRepository, quickAddShortcutDao);
  }
}
