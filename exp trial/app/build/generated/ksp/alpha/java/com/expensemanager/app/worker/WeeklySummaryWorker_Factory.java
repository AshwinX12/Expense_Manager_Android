package com.expensemanager.app.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.expensemanager.app.data.repository.TransactionRepository;
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
public final class WeeklySummaryWorker_Factory {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private WeeklySummaryWorker_Factory(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  public WeeklySummaryWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, transactionRepositoryProvider.get());
  }

  public static WeeklySummaryWorker_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new WeeklySummaryWorker_Factory(transactionRepositoryProvider);
  }

  public static WeeklySummaryWorker newInstance(Context context, WorkerParameters params,
      TransactionRepository transactionRepository) {
    return new WeeklySummaryWorker(context, params, transactionRepository);
  }
}
