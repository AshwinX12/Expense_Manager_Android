package com.expensemanager.app.data.repository;

import com.expensemanager.app.data.db.dao.ReminderDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ReminderRepository_Factory implements Factory<ReminderRepository> {
  private final Provider<ReminderDao> reminderDaoProvider;

  private ReminderRepository_Factory(Provider<ReminderDao> reminderDaoProvider) {
    this.reminderDaoProvider = reminderDaoProvider;
  }

  @Override
  public ReminderRepository get() {
    return newInstance(reminderDaoProvider.get());
  }

  public static ReminderRepository_Factory create(Provider<ReminderDao> reminderDaoProvider) {
    return new ReminderRepository_Factory(reminderDaoProvider);
  }

  public static ReminderRepository newInstance(ReminderDao reminderDao) {
    return new ReminderRepository(reminderDao);
  }
}
