package com.expensemanager.app.ui.lending;

import com.expensemanager.app.data.repository.LendingRepository;
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
public final class LendingViewModel_Factory implements Factory<LendingViewModel> {
  private final Provider<LendingRepository> lendingRepositoryProvider;

  private LendingViewModel_Factory(Provider<LendingRepository> lendingRepositoryProvider) {
    this.lendingRepositoryProvider = lendingRepositoryProvider;
  }

  @Override
  public LendingViewModel get() {
    return newInstance(lendingRepositoryProvider.get());
  }

  public static LendingViewModel_Factory create(
      Provider<LendingRepository> lendingRepositoryProvider) {
    return new LendingViewModel_Factory(lendingRepositoryProvider);
  }

  public static LendingViewModel newInstance(LendingRepository lendingRepository) {
    return new LendingViewModel(lendingRepository);
  }
}
