package com.expensemanager.app.worker;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.processing.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = WeeklySummaryWorker.class
)
public interface WeeklySummaryWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.expensemanager.app.worker.WeeklySummaryWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(
      WeeklySummaryWorker_AssistedFactory factory);
}
