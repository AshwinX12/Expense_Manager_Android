package com.expensemanager.app.ui.add;

import androidx.lifecycle.SavedStateHandle;
import com.expensemanager.app.data.repository.AccountRepository;
import com.expensemanager.app.data.repository.AttachmentRepository;
import com.expensemanager.app.data.repository.CategoryRepository;
import com.expensemanager.app.data.repository.SplitExpenseRepository;
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
public final class AddTransactionViewModel_Factory implements Factory<AddTransactionViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<AccountRepository> accountRepositoryProvider;

  private final Provider<AttachmentRepository> attachmentRepositoryProvider;

  private final Provider<SplitExpenseRepository> splitExpenseRepositoryProvider;

  private AddTransactionViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<AttachmentRepository> attachmentRepositoryProvider,
      Provider<SplitExpenseRepository> splitExpenseRepositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.accountRepositoryProvider = accountRepositoryProvider;
    this.attachmentRepositoryProvider = attachmentRepositoryProvider;
    this.splitExpenseRepositoryProvider = splitExpenseRepositoryProvider;
  }

  @Override
  public AddTransactionViewModel get() {
    return newInstance(savedStateHandleProvider.get(), transactionRepositoryProvider.get(), categoryRepositoryProvider.get(), accountRepositoryProvider.get(), attachmentRepositoryProvider.get(), splitExpenseRepositoryProvider.get());
  }

  public static AddTransactionViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<AccountRepository> accountRepositoryProvider,
      Provider<AttachmentRepository> attachmentRepositoryProvider,
      Provider<SplitExpenseRepository> splitExpenseRepositoryProvider) {
    return new AddTransactionViewModel_Factory(savedStateHandleProvider, transactionRepositoryProvider, categoryRepositoryProvider, accountRepositoryProvider, attachmentRepositoryProvider, splitExpenseRepositoryProvider);
  }

  public static AddTransactionViewModel newInstance(SavedStateHandle savedStateHandle,
      TransactionRepository transactionRepository, CategoryRepository categoryRepository,
      AccountRepository accountRepository, AttachmentRepository attachmentRepository,
      SplitExpenseRepository splitExpenseRepository) {
    return new AddTransactionViewModel(savedStateHandle, transactionRepository, categoryRepository, accountRepository, attachmentRepository, splitExpenseRepository);
  }
}
