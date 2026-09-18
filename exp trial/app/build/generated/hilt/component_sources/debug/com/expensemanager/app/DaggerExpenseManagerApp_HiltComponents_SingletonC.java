package com.expensemanager.app;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.expensemanager.app.data.attachment.AttachmentStorage;
import com.expensemanager.app.data.db.AppDatabase;
import com.expensemanager.app.data.db.dao.AccountDao;
import com.expensemanager.app.data.db.dao.AttachmentDao;
import com.expensemanager.app.data.db.dao.BudgetDao;
import com.expensemanager.app.data.db.dao.CategoryDao;
import com.expensemanager.app.data.db.dao.GoalDao;
import com.expensemanager.app.data.db.dao.LendingDao;
import com.expensemanager.app.data.db.dao.QuickAddShortcutDao;
import com.expensemanager.app.data.db.dao.RecurringRuleDao;
import com.expensemanager.app.data.db.dao.ReminderDao;
import com.expensemanager.app.data.db.dao.SettingsDao;
import com.expensemanager.app.data.db.dao.SplitExpenseDao;
import com.expensemanager.app.data.db.dao.SubcategoryDao;
import com.expensemanager.app.data.db.dao.TransactionDao;
import com.expensemanager.app.data.repository.AccountRepository;
import com.expensemanager.app.data.repository.AttachmentRepository;
import com.expensemanager.app.data.repository.BudgetRepository;
import com.expensemanager.app.data.repository.CategoryRepository;
import com.expensemanager.app.data.repository.GoalRepository;
import com.expensemanager.app.data.repository.LendingRepository;
import com.expensemanager.app.data.repository.RecurringTransactionRepository;
import com.expensemanager.app.data.repository.ReminderRepository;
import com.expensemanager.app.data.repository.SettingsRepository;
import com.expensemanager.app.data.repository.SplitExpenseRepository;
import com.expensemanager.app.data.repository.TransactionRepository;
import com.expensemanager.app.di.DatabaseModule_ProvideAccountDaoFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideAttachmentDaoFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideBudgetDaoFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideCategoryDaoFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideDatabaseFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideGoalDaoFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideLendingDaoFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideQuickAddShortcutDaoFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideRecurringRuleDaoFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideReminderDaoFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideSettingsDaoFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideSplitExpenseDaoFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideSubcategoryDaoFactory;
import com.expensemanager.app.di.DatabaseModule_ProvideTransactionDaoFactory;
import com.expensemanager.app.di.RepositoryModule_ProvideAccountRepositoryFactory;
import com.expensemanager.app.di.RepositoryModule_ProvideAttachmentRepositoryFactory;
import com.expensemanager.app.di.RepositoryModule_ProvideAttachmentStorageFactory;
import com.expensemanager.app.di.RepositoryModule_ProvideBudgetRepositoryFactory;
import com.expensemanager.app.di.RepositoryModule_ProvideCategoryRepositoryFactory;
import com.expensemanager.app.di.RepositoryModule_ProvideGoalRepositoryFactory;
import com.expensemanager.app.di.RepositoryModule_ProvideLendingRepositoryFactory;
import com.expensemanager.app.di.RepositoryModule_ProvideRecurringTransactionRepositoryFactory;
import com.expensemanager.app.di.RepositoryModule_ProvideReminderRepositoryFactory;
import com.expensemanager.app.di.RepositoryModule_ProvideSettingsRepositoryFactory;
import com.expensemanager.app.di.RepositoryModule_ProvideTransactionRepositoryFactory;
import com.expensemanager.app.ui.accounts.AccountViewModel;
import com.expensemanager.app.ui.accounts.AccountViewModel_HiltModules;
import com.expensemanager.app.ui.accounts.AccountViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.expensemanager.app.ui.accounts.AccountViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.expensemanager.app.ui.add.AddTransactionViewModel;
import com.expensemanager.app.ui.add.AddTransactionViewModel_HiltModules;
import com.expensemanager.app.ui.add.AddTransactionViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.expensemanager.app.ui.add.AddTransactionViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.expensemanager.app.ui.categories.CategoryViewModel;
import com.expensemanager.app.ui.categories.CategoryViewModel_HiltModules;
import com.expensemanager.app.ui.categories.CategoryViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.expensemanager.app.ui.categories.CategoryViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.expensemanager.app.ui.home.HomeViewModel;
import com.expensemanager.app.ui.home.HomeViewModel_HiltModules;
import com.expensemanager.app.ui.home.HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.expensemanager.app.ui.home.HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.expensemanager.app.ui.lending.LendingViewModel;
import com.expensemanager.app.ui.lending.LendingViewModel_HiltModules;
import com.expensemanager.app.ui.lending.LendingViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.expensemanager.app.ui.lending.LendingViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.expensemanager.app.ui.planning.BudgetViewModel;
import com.expensemanager.app.ui.planning.BudgetViewModel_HiltModules;
import com.expensemanager.app.ui.planning.BudgetViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.expensemanager.app.ui.planning.BudgetViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.expensemanager.app.ui.planning.GoalViewModel;
import com.expensemanager.app.ui.planning.GoalViewModel_HiltModules;
import com.expensemanager.app.ui.planning.GoalViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.expensemanager.app.ui.planning.GoalViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.expensemanager.app.ui.planning.ScheduledViewModel;
import com.expensemanager.app.ui.planning.ScheduledViewModel_HiltModules;
import com.expensemanager.app.ui.planning.ScheduledViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.expensemanager.app.ui.planning.ScheduledViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.expensemanager.app.ui.reports.ReportsViewModel;
import com.expensemanager.app.ui.reports.ReportsViewModel_HiltModules;
import com.expensemanager.app.ui.reports.ReportsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.expensemanager.app.ui.reports.ReportsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.expensemanager.app.ui.settings.SettingsViewModel;
import com.expensemanager.app.ui.settings.SettingsViewModel_HiltModules;
import com.expensemanager.app.ui.settings.SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.expensemanager.app.ui.settings.SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.expensemanager.app.ui.transactions.TransactionsViewModel;
import com.expensemanager.app.ui.transactions.TransactionsViewModel_HiltModules;
import com.expensemanager.app.ui.transactions.TransactionsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.expensemanager.app.ui.transactions.TransactionsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.expensemanager.app.worker.BudgetCheckWorker;
import com.expensemanager.app.worker.BudgetCheckWorker_AssistedFactory;
import com.expensemanager.app.worker.RecurringTransactionWorker;
import com.expensemanager.app.worker.RecurringTransactionWorker_AssistedFactory;
import com.expensemanager.app.worker.ReminderCheckWorker;
import com.expensemanager.app.worker.ReminderCheckWorker_AssistedFactory;
import com.expensemanager.app.worker.WeeklySummaryWorker;
import com.expensemanager.app.worker.WeeklySummaryWorker_AssistedFactory;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerExpenseManagerApp_HiltComponents_SingletonC {
  private DaggerExpenseManagerApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public ExpenseManagerApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements ExpenseManagerApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public ExpenseManagerApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements ExpenseManagerApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public ExpenseManagerApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements ExpenseManagerApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public ExpenseManagerApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements ExpenseManagerApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public ExpenseManagerApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements ExpenseManagerApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public ExpenseManagerApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements ExpenseManagerApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public ExpenseManagerApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements ExpenseManagerApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public ExpenseManagerApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends ExpenseManagerApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends ExpenseManagerApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    FragmentCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends ExpenseManagerApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends ExpenseManagerApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    ActivityCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    Map keySetMapOfClassOfAndBooleanBuilder() {
      MapBuilder mapBuilder = MapBuilder.<String, Boolean>newMapBuilder(11);
      mapBuilder.put(AccountViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, AccountViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(AddTransactionViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, AddTransactionViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(BudgetViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, BudgetViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(CategoryViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, CategoryViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(GoalViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, GoalViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, HomeViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(LendingViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, LendingViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(ReportsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ReportsViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(ScheduledViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ScheduledViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SettingsViewModel_HiltModules.KeyModule.provide());
      mapBuilder.put(TransactionsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, TransactionsViewModel_HiltModules.KeyModule.provide());
      return mapBuilder.build();
    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(keySetMapOfClassOfAndBooleanBuilder());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @CanIgnoreReturnValue
    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectSettingsRepository(instance, singletonCImpl.provideSettingsRepositoryProvider.get());
      return instance;
    }
  }

  private static final class ViewModelCImpl extends ExpenseManagerApp_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    Provider<AccountViewModel> accountViewModelProvider;

    Provider<AddTransactionViewModel> addTransactionViewModelProvider;

    Provider<BudgetViewModel> budgetViewModelProvider;

    Provider<CategoryViewModel> categoryViewModelProvider;

    Provider<GoalViewModel> goalViewModelProvider;

    Provider<HomeViewModel> homeViewModelProvider;

    Provider<LendingViewModel> lendingViewModelProvider;

    Provider<ReportsViewModel> reportsViewModelProvider;

    Provider<ScheduledViewModel> scheduledViewModelProvider;

    Provider<SettingsViewModel> settingsViewModelProvider;

    Provider<TransactionsViewModel> transactionsViewModelProvider;

    ViewModelCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        SavedStateHandle savedStateHandleParam, ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    Map hiltViewModelMapMapOfClassOfAndProviderOfViewModelBuilder() {
      MapBuilder mapBuilder = MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(11);
      mapBuilder.put(AccountViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (accountViewModelProvider)));
      mapBuilder.put(AddTransactionViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (addTransactionViewModelProvider)));
      mapBuilder.put(BudgetViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (budgetViewModelProvider)));
      mapBuilder.put(CategoryViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (categoryViewModelProvider)));
      mapBuilder.put(GoalViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (goalViewModelProvider)));
      mapBuilder.put(HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (homeViewModelProvider)));
      mapBuilder.put(LendingViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (lendingViewModelProvider)));
      mapBuilder.put(ReportsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (reportsViewModelProvider)));
      mapBuilder.put(ScheduledViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (scheduledViewModelProvider)));
      mapBuilder.put(SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (settingsViewModelProvider)));
      mapBuilder.put(TransactionsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (transactionsViewModelProvider)));
      return mapBuilder.build();
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.accountViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.addTransactionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.budgetViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.categoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.goalViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.lendingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.reportsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.scheduledViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.transactionsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(hiltViewModelMapMapOfClassOfAndProviderOfViewModelBuilder());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        switch (id) {
          case 0: // com.expensemanager.app.ui.accounts.AccountViewModel
          return (T) new AccountViewModel(singletonCImpl.provideAccountRepositoryProvider.get());

          case 1: // com.expensemanager.app.ui.add.AddTransactionViewModel
          return (T) new AddTransactionViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.provideTransactionRepositoryProvider.get(), singletonCImpl.provideCategoryRepositoryProvider.get(), singletonCImpl.provideAccountRepositoryProvider.get(), singletonCImpl.provideAttachmentRepositoryProvider.get(), singletonCImpl.splitExpenseRepositoryProvider.get());

          case 2: // com.expensemanager.app.ui.planning.BudgetViewModel
          return (T) new BudgetViewModel(singletonCImpl.provideBudgetRepositoryProvider.get(), singletonCImpl.provideCategoryRepositoryProvider.get(), singletonCImpl.provideTransactionRepositoryProvider.get());

          case 3: // com.expensemanager.app.ui.categories.CategoryViewModel
          return (T) new CategoryViewModel(singletonCImpl.provideCategoryRepositoryProvider.get());

          case 4: // com.expensemanager.app.ui.planning.GoalViewModel
          return (T) new GoalViewModel(singletonCImpl.provideGoalRepositoryProvider.get(), singletonCImpl.provideAccountRepositoryProvider.get(), singletonCImpl.provideTransactionRepositoryProvider.get());

          case 5: // com.expensemanager.app.ui.home.HomeViewModel
          return (T) new HomeViewModel(singletonCImpl.provideTransactionRepositoryProvider.get(), singletonCImpl.provideAccountRepositoryProvider.get(), singletonCImpl.provideBudgetRepositoryProvider.get(), singletonCImpl.provideCategoryRepositoryProvider.get(), singletonCImpl.quickAddShortcutDao(), singletonCImpl.provideRecurringTransactionRepositoryProvider.get());

          case 6: // com.expensemanager.app.ui.lending.LendingViewModel
          return (T) new LendingViewModel(singletonCImpl.provideLendingRepositoryProvider.get());

          case 7: // com.expensemanager.app.ui.reports.ReportsViewModel
          return (T) new ReportsViewModel(singletonCImpl.provideTransactionRepositoryProvider.get(), singletonCImpl.provideCategoryRepositoryProvider.get(), singletonCImpl.provideBudgetRepositoryProvider.get());

          case 8: // com.expensemanager.app.ui.planning.ScheduledViewModel
          return (T) new ScheduledViewModel(singletonCImpl.provideRecurringTransactionRepositoryProvider.get(), singletonCImpl.provideCategoryRepositoryProvider.get(), singletonCImpl.provideAccountRepositoryProvider.get());

          case 9: // com.expensemanager.app.ui.settings.SettingsViewModel
          return (T) new SettingsViewModel(singletonCImpl.provideSettingsRepositoryProvider.get(), singletonCImpl.provideTransactionRepositoryProvider.get());

          case 10: // com.expensemanager.app.ui.transactions.TransactionsViewModel
          return (T) new TransactionsViewModel(singletonCImpl.provideTransactionRepositoryProvider.get(), singletonCImpl.provideCategoryRepositoryProvider.get(), singletonCImpl.provideAccountRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends ExpenseManagerApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends ExpenseManagerApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends ExpenseManagerApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    Provider<AppDatabase> provideDatabaseProvider;

    Provider<BudgetRepository> provideBudgetRepositoryProvider;

    Provider<BudgetCheckWorker_AssistedFactory> budgetCheckWorker_AssistedFactoryProvider;

    Provider<RecurringTransactionRepository> provideRecurringTransactionRepositoryProvider;

    Provider<RecurringTransactionWorker_AssistedFactory> recurringTransactionWorker_AssistedFactoryProvider;

    Provider<ReminderRepository> provideReminderRepositoryProvider;

    Provider<ReminderCheckWorker_AssistedFactory> reminderCheckWorker_AssistedFactoryProvider;

    Provider<TransactionRepository> provideTransactionRepositoryProvider;

    Provider<WeeklySummaryWorker_AssistedFactory> weeklySummaryWorker_AssistedFactoryProvider;

    Provider<SettingsRepository> provideSettingsRepositoryProvider;

    Provider<AccountRepository> provideAccountRepositoryProvider;

    Provider<CategoryRepository> provideCategoryRepositoryProvider;

    Provider<AttachmentStorage> provideAttachmentStorageProvider;

    Provider<AttachmentRepository> provideAttachmentRepositoryProvider;

    Provider<SplitExpenseRepository> splitExpenseRepositoryProvider;

    Provider<GoalRepository> provideGoalRepositoryProvider;

    Provider<LendingRepository> provideLendingRepositoryProvider;

    SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    BudgetDao budgetDao() {
      return DatabaseModule_ProvideBudgetDaoFactory.provideBudgetDao(provideDatabaseProvider.get());
    }

    TransactionDao transactionDao() {
      return DatabaseModule_ProvideTransactionDaoFactory.provideTransactionDao(provideDatabaseProvider.get());
    }

    RecurringRuleDao recurringRuleDao() {
      return DatabaseModule_ProvideRecurringRuleDaoFactory.provideRecurringRuleDao(provideDatabaseProvider.get());
    }

    AccountDao accountDao() {
      return DatabaseModule_ProvideAccountDaoFactory.provideAccountDao(provideDatabaseProvider.get());
    }

    ReminderDao reminderDao() {
      return DatabaseModule_ProvideReminderDaoFactory.provideReminderDao(provideDatabaseProvider.get());
    }

    Map mapOfStringAndProviderOfWorkerAssistedFactoryOfBuilder() {
      MapBuilder mapBuilder = MapBuilder.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>newMapBuilder(4);
      mapBuilder.put("com.expensemanager.app.worker.BudgetCheckWorker", ((Provider) (budgetCheckWorker_AssistedFactoryProvider)));
      mapBuilder.put("com.expensemanager.app.worker.RecurringTransactionWorker", ((Provider) (recurringTransactionWorker_AssistedFactoryProvider)));
      mapBuilder.put("com.expensemanager.app.worker.ReminderCheckWorker", ((Provider) (reminderCheckWorker_AssistedFactoryProvider)));
      mapBuilder.put("com.expensemanager.app.worker.WeeklySummaryWorker", ((Provider) (weeklySummaryWorker_AssistedFactoryProvider)));
      return mapBuilder.build();
    }

    Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return mapOfStringAndProviderOfWorkerAssistedFactoryOfBuilder();
    }

    HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    SettingsDao settingsDao() {
      return DatabaseModule_ProvideSettingsDaoFactory.provideSettingsDao(provideDatabaseProvider.get());
    }

    CategoryDao categoryDao() {
      return DatabaseModule_ProvideCategoryDaoFactory.provideCategoryDao(provideDatabaseProvider.get());
    }

    SubcategoryDao subcategoryDao() {
      return DatabaseModule_ProvideSubcategoryDaoFactory.provideSubcategoryDao(provideDatabaseProvider.get());
    }

    AttachmentDao attachmentDao() {
      return DatabaseModule_ProvideAttachmentDaoFactory.provideAttachmentDao(provideDatabaseProvider.get());
    }

    SplitExpenseDao splitExpenseDao() {
      return DatabaseModule_ProvideSplitExpenseDaoFactory.provideSplitExpenseDao(provideDatabaseProvider.get());
    }

    GoalDao goalDao() {
      return DatabaseModule_ProvideGoalDaoFactory.provideGoalDao(provideDatabaseProvider.get());
    }

    QuickAddShortcutDao quickAddShortcutDao() {
      return DatabaseModule_ProvideQuickAddShortcutDaoFactory.provideQuickAddShortcutDao(provideDatabaseProvider.get());
    }

    LendingDao lendingDao() {
      return DatabaseModule_ProvideLendingDaoFactory.provideLendingDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 2));
      this.provideBudgetRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<BudgetRepository>(singletonCImpl, 1));
      this.budgetCheckWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<BudgetCheckWorker_AssistedFactory>(singletonCImpl, 0));
      this.provideRecurringTransactionRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<RecurringTransactionRepository>(singletonCImpl, 4));
      this.recurringTransactionWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<RecurringTransactionWorker_AssistedFactory>(singletonCImpl, 3));
      this.provideReminderRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ReminderRepository>(singletonCImpl, 6));
      this.reminderCheckWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<ReminderCheckWorker_AssistedFactory>(singletonCImpl, 5));
      this.provideTransactionRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<TransactionRepository>(singletonCImpl, 8));
      this.weeklySummaryWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<WeeklySummaryWorker_AssistedFactory>(singletonCImpl, 7));
      this.provideSettingsRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<SettingsRepository>(singletonCImpl, 9));
      this.provideAccountRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AccountRepository>(singletonCImpl, 10));
      this.provideCategoryRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<CategoryRepository>(singletonCImpl, 11));
      this.provideAttachmentStorageProvider = DoubleCheck.provider(new SwitchingProvider<AttachmentStorage>(singletonCImpl, 13));
      this.provideAttachmentRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AttachmentRepository>(singletonCImpl, 12));
      this.splitExpenseRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<SplitExpenseRepository>(singletonCImpl, 14));
      this.provideGoalRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<GoalRepository>(singletonCImpl, 15));
      this.provideLendingRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<LendingRepository>(singletonCImpl, 16));
    }

    @Override
    public void injectExpenseManagerApp(ExpenseManagerApp expenseManagerApp) {
      injectExpenseManagerApp2(expenseManagerApp);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @CanIgnoreReturnValue
    private ExpenseManagerApp injectExpenseManagerApp2(ExpenseManagerApp instance) {
      ExpenseManagerApp_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @Override
      @SuppressWarnings("unchecked")
      public T get() {
        switch (id) {
          case 0: // com.expensemanager.app.worker.BudgetCheckWorker_AssistedFactory
          return (T) new BudgetCheckWorker_AssistedFactory() {
            @Override
            public BudgetCheckWorker create(Context context, WorkerParameters params) {
              return new BudgetCheckWorker(context, params, singletonCImpl.provideBudgetRepositoryProvider.get());
            }
          };

          case 1: // com.expensemanager.app.data.repository.BudgetRepository
          return (T) RepositoryModule_ProvideBudgetRepositoryFactory.provideBudgetRepository(singletonCImpl.budgetDao(), singletonCImpl.transactionDao());

          case 2: // com.expensemanager.app.data.db.AppDatabase
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.expensemanager.app.worker.RecurringTransactionWorker_AssistedFactory
          return (T) new RecurringTransactionWorker_AssistedFactory() {
            @Override
            public RecurringTransactionWorker create(Context context2, WorkerParameters params2) {
              return new RecurringTransactionWorker(context2, params2, singletonCImpl.provideRecurringTransactionRepositoryProvider.get());
            }
          };

          case 4: // com.expensemanager.app.data.repository.RecurringTransactionRepository
          return (T) RepositoryModule_ProvideRecurringTransactionRepositoryFactory.provideRecurringTransactionRepository(singletonCImpl.recurringRuleDao(), singletonCImpl.transactionDao(), singletonCImpl.accountDao());

          case 5: // com.expensemanager.app.worker.ReminderCheckWorker_AssistedFactory
          return (T) new ReminderCheckWorker_AssistedFactory() {
            @Override
            public ReminderCheckWorker create(Context context3, WorkerParameters params3) {
              return new ReminderCheckWorker(context3, params3, singletonCImpl.provideReminderRepositoryProvider.get());
            }
          };

          case 6: // com.expensemanager.app.data.repository.ReminderRepository
          return (T) RepositoryModule_ProvideReminderRepositoryFactory.provideReminderRepository(singletonCImpl.reminderDao());

          case 7: // com.expensemanager.app.worker.WeeklySummaryWorker_AssistedFactory
          return (T) new WeeklySummaryWorker_AssistedFactory() {
            @Override
            public WeeklySummaryWorker create(Context context4, WorkerParameters params4) {
              return new WeeklySummaryWorker(context4, params4, singletonCImpl.provideTransactionRepositoryProvider.get());
            }
          };

          case 8: // com.expensemanager.app.data.repository.TransactionRepository
          return (T) RepositoryModule_ProvideTransactionRepositoryFactory.provideTransactionRepository(singletonCImpl.transactionDao(), singletonCImpl.accountDao());

          case 9: // com.expensemanager.app.data.repository.SettingsRepository
          return (T) RepositoryModule_ProvideSettingsRepositoryFactory.provideSettingsRepository(singletonCImpl.settingsDao());

          case 10: // com.expensemanager.app.data.repository.AccountRepository
          return (T) RepositoryModule_ProvideAccountRepositoryFactory.provideAccountRepository(singletonCImpl.accountDao());

          case 11: // com.expensemanager.app.data.repository.CategoryRepository
          return (T) RepositoryModule_ProvideCategoryRepositoryFactory.provideCategoryRepository(singletonCImpl.categoryDao(), singletonCImpl.subcategoryDao());

          case 12: // com.expensemanager.app.data.repository.AttachmentRepository
          return (T) RepositoryModule_ProvideAttachmentRepositoryFactory.provideAttachmentRepository(singletonCImpl.attachmentDao(), singletonCImpl.provideAttachmentStorageProvider.get());

          case 13: // com.expensemanager.app.data.attachment.AttachmentStorage
          return (T) RepositoryModule_ProvideAttachmentStorageFactory.provideAttachmentStorage(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 14: // com.expensemanager.app.data.repository.SplitExpenseRepository
          return (T) new SplitExpenseRepository(singletonCImpl.splitExpenseDao());

          case 15: // com.expensemanager.app.data.repository.GoalRepository
          return (T) RepositoryModule_ProvideGoalRepositoryFactory.provideGoalRepository(singletonCImpl.goalDao());

          case 16: // com.expensemanager.app.data.repository.LendingRepository
          return (T) RepositoryModule_ProvideLendingRepositoryFactory.provideLendingRepository(singletonCImpl.lendingDao(), singletonCImpl.transactionDao(), singletonCImpl.accountDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
