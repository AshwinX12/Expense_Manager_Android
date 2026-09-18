package com.expensemanager.app.di;

import com.expensemanager.app.data.db.dao.ReminderDao;
import com.expensemanager.app.data.repository.ReminderRepository;
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
public final class RepositoryModule_ProvideReminderRepositoryFactory implements Factory<ReminderRepository> {
  private final Provider<ReminderDao> reminderDaoProvider;

  private RepositoryModule_ProvideReminderRepositoryFactory(
      Provider<ReminderDao> reminderDaoProvider) {
    this.reminderDaoProvider = reminderDaoProvider;
  }

  @Override
  public ReminderRepository get() {
    return provideReminderRepository(reminderDaoProvider.get());
  }

  public static RepositoryModule_ProvideReminderRepositoryFactory create(
      Provider<ReminderDao> reminderDaoProvider) {
    return new RepositoryModule_ProvideReminderRepositoryFactory(reminderDaoProvider);
  }

  public static ReminderRepository provideReminderRepository(ReminderDao reminderDao) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideReminderRepository(reminderDao));
  }
}
