package com.expensemanager.app.di;

import com.expensemanager.app.data.db.dao.CategoryDao;
import com.expensemanager.app.data.db.dao.SubcategoryDao;
import com.expensemanager.app.data.repository.CategoryRepository;
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
public final class RepositoryModule_ProvideCategoryRepositoryFactory implements Factory<CategoryRepository> {
  private final Provider<CategoryDao> categoryDaoProvider;

  private final Provider<SubcategoryDao> subcategoryDaoProvider;

  private RepositoryModule_ProvideCategoryRepositoryFactory(
      Provider<CategoryDao> categoryDaoProvider, Provider<SubcategoryDao> subcategoryDaoProvider) {
    this.categoryDaoProvider = categoryDaoProvider;
    this.subcategoryDaoProvider = subcategoryDaoProvider;
  }

  @Override
  public CategoryRepository get() {
    return provideCategoryRepository(categoryDaoProvider.get(), subcategoryDaoProvider.get());
  }

  public static RepositoryModule_ProvideCategoryRepositoryFactory create(
      Provider<CategoryDao> categoryDaoProvider, Provider<SubcategoryDao> subcategoryDaoProvider) {
    return new RepositoryModule_ProvideCategoryRepositoryFactory(categoryDaoProvider, subcategoryDaoProvider);
  }

  public static CategoryRepository provideCategoryRepository(CategoryDao categoryDao,
      SubcategoryDao subcategoryDao) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideCategoryRepository(categoryDao, subcategoryDao));
  }
}
