package com.expensemanager.app.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ReminderCheckWorker_AssistedFactory_Impl implements ReminderCheckWorker_AssistedFactory {
  private final ReminderCheckWorker_Factory delegateFactory;

  ReminderCheckWorker_AssistedFactory_Impl(ReminderCheckWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public ReminderCheckWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<ReminderCheckWorker_AssistedFactory> create(
      ReminderCheckWorker_Factory delegateFactory) {
    return InstanceFactory.create(new ReminderCheckWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<ReminderCheckWorker_AssistedFactory> createFactoryProvider(
      ReminderCheckWorker_Factory delegateFactory) {
    return InstanceFactory.create(new ReminderCheckWorker_AssistedFactory_Impl(delegateFactory));
  }
}
