package com.expensemanager.app.di;

import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.SplitExpenseDao;
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
public final class DatabaseModule_ProvideSplitExpenseDaoFactory implements Factory<SplitExpenseDao> {
  private final Provider<AppDatabase> dbProvider;

  private DatabaseModule_ProvideSplitExpenseDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SplitExpenseDao get() {
    return provideSplitExpenseDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSplitExpenseDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideSplitExpenseDaoFactory(dbProvider);
  }

  public static SplitExpenseDao provideSplitExpenseDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSplitExpenseDao(db));
  }
}
