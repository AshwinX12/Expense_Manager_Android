package com.expensemanager.app.ui.settings;

import com.expensemanager.app.data.repository.SettingsRepository;
import com.expensemanager.app.data.repository.TransactionRepository;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private SettingsViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(settingsRepositoryProvider.get(), transactionRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider) {
    return new SettingsViewModel_Factory(settingsRepositoryProvider, transactionRepositoryProvider);
  }

  public static SettingsViewModel newInstance(SettingsRepository settingsRepository,
      TransactionRepository transactionRepository) {
    return new SettingsViewModel(settingsRepository, transactionRepository);
  }
}
