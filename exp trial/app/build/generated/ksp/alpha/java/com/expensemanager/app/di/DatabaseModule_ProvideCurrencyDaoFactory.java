package com.expensemanager.app.di;

import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.CurrencyDao;
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
public final class DatabaseModule_ProvideCurrencyDaoFactory implements Factory<CurrencyDao> {
  private final Provider<AppDatabase> dbProvider;

  private DatabaseModule_ProvideCurrencyDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CurrencyDao get() {
    return provideCurrencyDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCurrencyDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideCurrencyDaoFactory(dbProvider);
  }

  public static CurrencyDao provideCurrencyDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCurrencyDao(db));
  }
}
