package com.expensemanager.app.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.expensemanager.app.data.repository.RecurringTransactionRepository;
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
public final class RecurringTransactionWorker_Factory {
  private final Provider<RecurringTransactionRepository> recurringRepositoryProvider;

  private RecurringTransactionWorker_Factory(
      Provider<RecurringTransactionRepository> recurringRepositoryProvider) {
    this.recurringRepositoryProvider = recurringRepositoryProvider;
  }

  public RecurringTransactionWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, recurringRepositoryProvider.get());
  }

  public static RecurringTransactionWorker_Factory create(
      Provider<RecurringTransactionRepository> recurringRepositoryProvider) {
    return new RecurringTransactionWorker_Factory(recurringRepositoryProvider);
  }

  public static RecurringTransactionWorker newInstance(Context context, WorkerParameters params,
      RecurringTransactionRepository recurringRepository) {
    return new RecurringTransactionWorker(context, params, recurringRepository);
  }
}
