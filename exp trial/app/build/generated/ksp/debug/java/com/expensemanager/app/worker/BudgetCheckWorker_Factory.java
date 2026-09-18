package com.expensemanager.app.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.expensemanager.app.data.repository.BudgetRepository;
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
public final class BudgetCheckWorker_Factory {
  private final Provider<BudgetRepository> budgetRepositoryProvider;

  private BudgetCheckWorker_Factory(Provider<BudgetRepository> budgetRepositoryProvider) {
    this.budgetRepositoryProvider = budgetRepositoryProvider;
  }

  public BudgetCheckWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, budgetRepositoryProvider.get());
  }

  public static BudgetCheckWorker_Factory create(
      Provider<BudgetRepository> budgetRepositoryProvider) {
    return new BudgetCheckWorker_Factory(budgetRepositoryProvider);
  }

  public static BudgetCheckWorker newInstance(Context context, WorkerParameters params,
      BudgetRepository budgetRepository) {
    return new BudgetCheckWorker(context, params, budgetRepository);
  }
}
