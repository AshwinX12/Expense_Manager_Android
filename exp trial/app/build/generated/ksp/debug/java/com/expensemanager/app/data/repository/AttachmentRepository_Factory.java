package com.expensemanager.app.data.repository;

import com.expensemanager.app.data.attachment.AttachmentStorage;
import com.expensemanager.app.data.db.dao.AttachmentDao;
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
public final class AttachmentRepository_Factory implements Factory<AttachmentRepository> {
  private final Provider<AttachmentDao> attachmentDaoProvider;

  private final Provider<AttachmentStorage> attachmentStorageProvider;

  private AttachmentRepository_Factory(Provider<AttachmentDao> attachmentDaoProvider,
      Provider<AttachmentStorage> attachmentStorageProvider) {
    this.attachmentDaoProvider = attachmentDaoProvider;
    this.attachmentStorageProvider = attachmentStorageProvider;
  }

  @Override
  public AttachmentRepository get() {
    return newInstance(attachmentDaoProvider.get(), attachmentStorageProvider.get());
  }

  public static AttachmentRepository_Factory create(Provider<AttachmentDao> attachmentDaoProvider,
      Provider<AttachmentStorage> attachmentStorageProvider) {
    return new AttachmentRepository_Factory(attachmentDaoProvider, attachmentStorageProvider);
  }

  public static AttachmentRepository newInstance(AttachmentDao attachmentDao,
      AttachmentStorage attachmentStorage) {
    return new AttachmentRepository(attachmentDao, attachmentStorage);
  }
}
