package com.expensemanager.app.di;

import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.TransactionDao;
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
public final class DatabaseModule_ProvideTransactionDaoFactory implements Factory<TransactionDao> {
  private final Provider<AppDatabase> dbProvider;

  private DatabaseModule_ProvideTransactionDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TransactionDao get() {
    return provideTransactionDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTransactionDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideTransactionDaoFactory(dbProvider);
  }

  public static TransactionDao provideTransactionDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTransactionDao(db));
  }
}
