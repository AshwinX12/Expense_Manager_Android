package com.expensemanager.app.di;

import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.SavedFilterDao;
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
public final class DatabaseModule_ProvideSavedFilterDaoFactory implements Factory<SavedFilterDao> {
  private final Provider<AppDatabase> dbProvider;

  private DatabaseModule_ProvideSavedFilterDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SavedFilterDao get() {
    return provideSavedFilterDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSavedFilterDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideSavedFilterDaoFactory(dbProvider);
  }

  public static SavedFilterDao provideSavedFilterDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSavedFilterDao(db));
  }
}
