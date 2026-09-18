package com.expensemanager.app.di;

import com.expensemanager.app.data.attachment.AttachmentStorage;
import com.expensemanager.app.data.db.dao.AttachmentDao;
import com.expensemanager.app.data.repository.AttachmentRepository;
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
public final class RepositoryModule_ProvideAttachmentRepositoryFactory implements Factory<AttachmentRepository> {
  private final Provider<AttachmentDao> attachmentDaoProvider;

  private final Provider<AttachmentStorage> attachmentStorageProvider;

  private RepositoryModule_ProvideAttachmentRepositoryFactory(
      Provider<AttachmentDao> attachmentDaoProvider,
      Provider<AttachmentStorage> attachmentStorageProvider) {
    this.attachmentDaoProvider = attachmentDaoProvider;
    this.attachmentStorageProvider = attachmentStorageProvider;
  }

  @Override
  public AttachmentRepository get() {
    return provideAttachmentRepository(attachmentDaoProvider.get(), attachmentStorageProvider.get());
  }

  public static RepositoryModule_ProvideAttachmentRepositoryFactory create(
      Provider<AttachmentDao> attachmentDaoProvider,
      Provider<AttachmentStorage> attachmentStorageProvider) {
    return new RepositoryModule_ProvideAttachmentRepositoryFactory(attachmentDaoProvider, attachmentStorageProvider);
  }

  public static AttachmentRepository provideAttachmentRepository(AttachmentDao attachmentDao,
      AttachmentStorage attachmentStorage) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideAttachmentRepository(attachmentDao, attachmentStorage));
  }
}
