package com.expensemanager.app.di;

import com.expensemanager.app.data.db.dao.GoalDao;
import com.expensemanager.app.data.repository.GoalRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RepositoryModule_ProvideGoalRepositoryFactory implements Factory<GoalRepository> {
  private final Provider<GoalDao> goalDaoProvider;

  private RepositoryModule_ProvideGoalRepositoryFactory(Provider<GoalDao> goalDaoProvider) {
    this.goalDaoProvider = goalDaoProvider;
  }

  @Override
  public GoalRepository get() {
    return provideGoalRepository(goalDaoProvider.get());
  }

  public static RepositoryModule_ProvideGoalRepositoryFactory create(
      Provider<GoalDao> goalDaoProvider) {
    return new RepositoryModule_ProvideGoalRepositoryFactory(goalDaoProvider);
  }

  public static GoalRepository provideGoalRepository(GoalDao goalDao) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideGoalRepository(goalDao));
  }
}
