package com.expensemanager.app.data.repository;

import com.expensemanager.app.data.db.dao.CategoryDao;
import com.expensemanager.app.data.db.dao.SubcategoryDao;
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
public final class CategoryRepository_Factory implements Factory<CategoryRepository> {
  private final Provider<CategoryDao> categoryDaoProvider;

  private final Provider<SubcategoryDao> subcategoryDaoProvider;

  private CategoryRepository_Factory(Provider<CategoryDao> categoryDaoProvider,
      Provider<SubcategoryDao> subcategoryDaoProvider) {
    this.categoryDaoProvider = categoryDaoProvider;
    this.subcategoryDaoProvider = subcategoryDaoProvider;
  }

  @Override
  public CategoryRepository get() {
    return newInstance(categoryDaoProvider.get(), subcategoryDaoProvider.get());
  }

  public static CategoryRepository_Factory create(Provider<CategoryDao> categoryDaoProvider,
      Provider<SubcategoryDao> subcategoryDaoProvider) {
    return new CategoryRepository_Factory(categoryDaoProvider, subcategoryDaoProvider);
  }

  public static CategoryRepository newInstance(CategoryDao categoryDao,
      SubcategoryDao subcategoryDao) {
    return new CategoryRepository(categoryDao, subcategoryDao);
  }
}
