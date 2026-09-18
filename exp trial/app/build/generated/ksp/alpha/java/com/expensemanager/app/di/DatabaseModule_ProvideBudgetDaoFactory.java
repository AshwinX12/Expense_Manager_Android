package com.expensemanager.app.di;

import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.BudgetDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideBudgetDaoFactory implements Factory<BudgetDao> {
  private final Provider<AppDatabase> dbProvider;

  private DatabaseModule_ProvideBudgetDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BudgetDao get() {
    return provideBudgetDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideBudgetDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideBudgetDaoFactory(dbProvider);
  }

  public static BudgetDao provideBudgetDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBudgetDao(db));
  }
}
