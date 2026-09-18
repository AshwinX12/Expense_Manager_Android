package com.expensemanager.app.di;

import com.expensemanager.app.data.db.dao.SettingsDao;
import com.expensemanager.app.data.repository.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RepositoryModule_ProvideSettingsRepositoryFactory implements Factory<SettingsRepository> {
  private final Provider<SettingsDao> settingsDaoProvider;

  private RepositoryModule_ProvideSettingsRepositoryFactory(
      Provider<SettingsDao> settingsDaoProvider) {
    this.settingsDaoProvider = settingsDaoProvider;
  }

  @Override
  public SettingsRepository get() {
    return provideSettingsRepository(settingsDaoProvider.get());
  }

  public static RepositoryModule_ProvideSettingsRepositoryFactory create(
      Provider<SettingsDao> settingsDaoProvider) {
    return new RepositoryModule_ProvideSettingsRepositoryFactory(settingsDaoProvider);
  }

  public static SettingsRepository provideSettingsRepository(SettingsDao settingsDao) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideSettingsRepository(settingsDao));
  }
}
