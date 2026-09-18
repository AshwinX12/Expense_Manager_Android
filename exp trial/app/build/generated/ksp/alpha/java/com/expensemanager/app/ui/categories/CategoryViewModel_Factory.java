package com.expensemanager.app.ui.categories;

import com.expensemanager.app.data.repository.CategoryRepository;
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
public final class CategoryViewModel_Factory implements Factory<CategoryViewModel> {
  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private CategoryViewModel_Factory(Provider<CategoryRepository> categoryRepositoryProvider) {
    this.categoryRepositoryProvider = categoryRepositoryProvider;
  }

  @Override
  public CategoryViewModel get() {
    return newInstance(categoryRepositoryProvider.get());
  }

  public static CategoryViewModel_Factory create(
      Provider<CategoryRepository> categoryRepositoryProvider) {
    return new CategoryViewModel_Factory(categoryRepositoryProvider);
  }

  public static CategoryViewModel newInstance(CategoryRepository categoryRepository) {
    return new CategoryViewModel(categoryRepository);
  }
}
