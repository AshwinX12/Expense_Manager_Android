package com.expensemanager.app.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.expensemanager.app.data.repository.ReminderRepository;
import dagger.internal.DaggerGenerated;
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
public final class ReminderCheckWorker_Factory {
  private final Provider<ReminderRepository> reminderRepositoryProvider;

  private ReminderCheckWorker_Factory(Provider<ReminderRepository> reminderRepositoryProvider) {
    this.reminderRepositoryProvider = reminderRepositoryProvider;
  }

  public ReminderCheckWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, reminderRepositoryProvider.get());
  }

  public static ReminderCheckWorker_Factory create(
      Provider<ReminderRepository> reminderRepositoryProvider) {
    return new ReminderCheckWorker_Factory(reminderRepositoryProvider);
  }

  public static ReminderCheckWorker newInstance(Context context, WorkerParameters params,
      ReminderRepository reminderRepository) {
    return new ReminderCheckWorker(context, params, reminderRepository);
  }
}
