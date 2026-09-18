package com.expensemanager.app.di;

import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.GoalDao;
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
public final class DatabaseModule_ProvideGoalDaoFactory implements Factory<GoalDao> {
  private final Provider<AppDatabase> dbProvider;

  private DatabaseModule_ProvideGoalDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public GoalDao get() {
    return provideGoalDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideGoalDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideGoalDaoFactory(dbProvider);
  }

  public static GoalDao provideGoalDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideGoalDao(db));
  }
}
