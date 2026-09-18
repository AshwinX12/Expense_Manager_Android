package com.expensemanager.app.di;

import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.AccountDao;
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
public final class DatabaseModule_ProvideAccountDaoFactory implements Factory<AccountDao> {
  private final Provider<AppDatabase> dbProvider;

  private DatabaseModule_ProvideAccountDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AccountDao get() {
    return provideAccountDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideAccountDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideAccountDaoFactory(dbProvider);
  }

  public static AccountDao provideAccountDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAccountDao(db));
  }
}
