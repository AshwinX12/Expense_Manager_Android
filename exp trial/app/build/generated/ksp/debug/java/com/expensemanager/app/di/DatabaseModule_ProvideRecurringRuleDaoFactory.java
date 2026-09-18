package com.expensemanager.app.di;

import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.RecurringRuleDao;
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
public final class DatabaseModule_ProvideRecurringRuleDaoFactory implements Factory<RecurringRuleDao> {
  private final Provider<AppDatabase> dbProvider;

  private DatabaseModule_ProvideRecurringRuleDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public RecurringRuleDao get() {
    return provideRecurringRuleDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideRecurringRuleDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideRecurringRuleDaoFactory(dbProvider);
  }

  public static RecurringRuleDao provideRecurringRuleDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRecurringRuleDao(db));
  }
}
