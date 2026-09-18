package com.expensemanager.app.di;

import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.QuickAddShortcutDao;
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
public final class DatabaseModule_ProvideQuickAddShortcutDaoFactory implements Factory<QuickAddShortcutDao> {
  private final Provider<AppDatabase> dbProvider;

  private DatabaseModule_ProvideQuickAddShortcutDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public QuickAddShortcutDao get() {
    return provideQuickAddShortcutDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideQuickAddShortcutDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideQuickAddShortcutDaoFactory(dbProvider);
  }

  public static QuickAddShortcutDao provideQuickAddShortcutDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideQuickAddShortcutDao(db));
  }
}
