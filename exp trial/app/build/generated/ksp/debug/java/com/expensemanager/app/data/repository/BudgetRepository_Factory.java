package com.expensemanager.app.data.repository;

import com.expensemanager.app.data.db.dao.BudgetDao;
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
public final class BudgetRepository_Factory implements Factory<BudgetRepository> {
  private final Provider<BudgetDao> budgetDaoProvider;

  private final Provider<TransactionDao> transactionDaoProvider;

  private BudgetRepository_Factory(Provider<BudgetDao> budgetDaoProvider,
      Provider<TransactionDao> transactionDaoProvider) {
    this.budgetDaoProvider = budgetDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
  }

  @Override
  public BudgetRepository get() {
    return newInstance(budgetDaoProvider.get(), transactionDaoProvider.get());
  }

  public static BudgetRepository_Factory create(Provider<BudgetDao> budgetDaoProvider,
      Provider<TransactionDao> transactionDaoProvider) {
    return new BudgetRepository_Factory(budgetDaoProvider, transactionDaoProvider);
  }

  public static BudgetRepository newInstance(BudgetDao budgetDao, TransactionDao transactionDao) {
    return new BudgetRepository(budgetDao, transactionDao);
  }
}
