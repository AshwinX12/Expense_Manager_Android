package com.expensemanager.app.di;

import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.LendingDao;
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
public final class DatabaseModule_ProvideLendingDaoFactory implements Factory<LendingDao> {
  private final Provider<AppDatabase> dbProvider;

  private DatabaseModule_ProvideLendingDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public LendingDao get() {
    return provideLendingDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideLendingDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideLendingDaoFactory(dbProvider);
  }

  public static LendingDao provideLendingDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideLendingDao(db));
  }
}
