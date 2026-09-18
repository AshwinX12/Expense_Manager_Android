package com.expensemanager.app.data.repository;

import com.expensemanager.app.data.db.dao.SplitExpenseDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SplitExpenseRepository_Factory implements Factory<SplitExpenseRepository> {
  private final Provider<SplitExpenseDao> daoProvider;

  private SplitExpenseRepository_Factory(Provider<SplitExpenseDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public SplitExpenseRepository get() {
    return newInstance(daoProvider.get());
  }

  public static SplitExpenseRepository_Factory create(Provider<SplitExpenseDao> daoProvider) {
    return new SplitExpenseRepository_Factory(daoProvider);
  }

  public static SplitExpenseRepository newInstance(SplitExpenseDao dao) {
    return new SplitExpenseRepository(dao);
  }
}
