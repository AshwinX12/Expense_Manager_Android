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
public final class WeeklySummaryWorker_AssistedFactory_Impl implements WeeklySummaryWorker_AssistedFactory {
  private final WeeklySummaryWorker_Factory delegateFactory;

  WeeklySummaryWorker_AssistedFactory_Impl(WeeklySummaryWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public WeeklySummaryWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<WeeklySummaryWorker_AssistedFactory> create(
      WeeklySummaryWorker_Factory delegateFactory) {
    return InstanceFactory.create(new WeeklySummaryWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<WeeklySummaryWorker_AssistedFactory> createFactoryProvider(
      WeeklySummaryWorker_Factory delegateFactory) {
    return InstanceFactory.create(new WeeklySummaryWorker_AssistedFactory_Impl(delegateFactory));
  }
}
