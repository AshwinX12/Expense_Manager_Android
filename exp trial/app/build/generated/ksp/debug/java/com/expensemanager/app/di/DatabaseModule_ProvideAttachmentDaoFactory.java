package com.expensemanager.app.di;

import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.AttachmentDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideAttachmentDaoFactory implements Factory<AttachmentDao> {
  private final Provider<AppDatabase> dbProvider;

  private DatabaseModule_ProvideAttachmentDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AttachmentDao get() {
    return provideAttachmentDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideAttachmentDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideAttachmentDaoFactory(dbProvider);
  }

  public static AttachmentDao provideAttachmentDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAttachmentDao(db));
  }
}
