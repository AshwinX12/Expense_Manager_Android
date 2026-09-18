package com.expensemanager.app.di;

import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.SubcategoryDao;
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
public final class DatabaseModule_ProvideSubcategoryDaoFactory implements Factory<SubcategoryDao> {
  private final Provider<AppDatabase> dbProvider;

  private DatabaseModule_ProvideSubcategoryDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SubcategoryDao get() {
    return provideSubcategoryDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSubcategoryDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideSubcategoryDaoFactory(dbProvider);
  }

  public static SubcategoryDao provideSubcategoryDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSubcategoryDao(db));
  }
}
