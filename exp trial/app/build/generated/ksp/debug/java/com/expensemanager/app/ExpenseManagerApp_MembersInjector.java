package com.expensemanager.app;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class ExpenseManagerApp_MembersInjector implements MembersInjector<ExpenseManagerApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  private ExpenseManagerApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  @Override
  public void injectMembers(ExpenseManagerApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  public static MembersInjector<ExpenseManagerApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new ExpenseManagerApp_MembersInjector(workerFactoryProvider);
  }

  @InjectedFieldSignature("com.expensemanager.app.ExpenseManagerApp.workerFactory")
  public static void injectWorkerFactory(ExpenseManagerApp instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
