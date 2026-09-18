package com.expensemanager.app.di;

import com.expensemanager.app.data.db.dao.BudgetDao;
import com.expensemanager.app.data.db.dao.TransactionDao;
import com.expensemanager.app.data.repository.BudgetRepository;
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
public final class RepositoryModule_ProvideBudgetRepositoryFactory implements Factory<BudgetRepository> {
  private final Provider<BudgetDao> budgetDaoProvider;

  private final Provider<TransactionDao> transactionDaoProvider;

  private RepositoryModule_ProvideBudgetRepositoryFactory(Provider<BudgetDao> budgetDaoProvider,
      Provider<TransactionDao> transactionDaoProvider) {
    this.budgetDaoProvider = budgetDaoProvider;
    this.transactionDaoProvider = transactionDaoProvider;
  }

  @Override
  public BudgetRepository get() {
    return provideBudgetRepository(budgetDaoProvider.get(), transactionDaoProvider.get());
  }

  public static RepositoryModule_ProvideBudgetRepositoryFactory create(
      Provider<BudgetDao> budgetDaoProvider, Provider<TransactionDao> transactionDaoProvider) {
    return new RepositoryModule_ProvideBudgetRepositoryFactory(budgetDaoProvider, transactionDaoProvider);
  }

  public static BudgetRepository provideBudgetRepository(BudgetDao budgetDao,
      TransactionDao transactionDao) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideBudgetRepository(budgetDao, transactionDao));
  }
}
