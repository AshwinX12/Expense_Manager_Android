package com.expensemanager.app.di;

import android.content.Context;
import com.expensemanager.app.data.attachment.AttachmentStorage;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class RepositoryModule_ProvideAttachmentStorageFactory implements Factory<AttachmentStorage> {
  private final Provider<Context> contextProvider;

  private RepositoryModule_ProvideAttachmentStorageFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AttachmentStorage get() {
    return provideAttachmentStorage(contextProvider.get());
  }

  public static RepositoryModule_ProvideAttachmentStorageFactory create(
      Provider<Context> contextProvider) {
    return new RepositoryModule_ProvideAttachmentStorageFactory(contextProvider);
  }

  public static AttachmentStorage provideAttachmentStorage(Context context) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideAttachmentStorage(context));
  }
}
