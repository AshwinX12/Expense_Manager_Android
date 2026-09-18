package com.expensemanager.app

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.expensemanager.app.data.repository.SettingsRepository
import com.expensemanager.app.ui.navigation.AppNavHost
import com.expensemanager.app.ui.components.AppLockScreen
import com.expensemanager.app.ui.theme.ExpenseManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    companion object {
        const val EXTRA_OPEN_ADD_TRANSACTION = "open_add_transaction"
    }

    @Inject
    lateinit var settingsRepository: SettingsRepository

    /** Set when launched from the widget's + button; cleared once navigation has happened. */
    private val openAddTransaction = mutableStateOf(false)

    override fun onStop() {
        super.onStop()
        // Keep the home-screen widget in step with whatever was just added or edited
        com.expensemanager.app.widget.ExpenseWidgetProvider.refresh(applicationContext)
    }

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent.getBooleanExtra(EXTRA_OPEN_ADD_TRANSACTION, false)) {
            openAddTransaction.value = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        openAddTransaction.value = intent?.getBooleanExtra(EXTRA_OPEN_ADD_TRANSACTION, false) == true

        setContent {
            // Collect theme preference from DB so toggling it takes effect immediately.
            // The flows are remembered: building a new Room Flow on every recomposition
            // restarts each collection (and any state derived from it) whenever anything
            // above re-renders, e.g. on a theme switch.
            val themeMode by remember { settingsRepository.getThemeModeFlow() }
                .collectAsState(initial = com.expensemanager.app.data.db.entity.ThemeMode.SYSTEM)
            val themeStyle by remember { settingsRepository.getThemeStyleFlow() }
                .collectAsState(initial = com.expensemanager.app.data.db.entity.ThemeStyle.CLASSIC)

            val isBiometricEnabled by remember { settingsRepository.isBiometricEnabledFlow() }
                .collectAsState(initial = false)
            val appPin by remember { settingsRepository.getPinFlow() }.collectAsState(initial = null)
            val isSwipeNavigationEnabled by remember { settingsRepository.isSwipeNavigationEnabledFlow() }
                .collectAsState(initial = null)

            val currencyCode by remember { settingsRepository.getBaseCurrencyFlow() }
                .collectAsState(initial = com.expensemanager.app.util.AppCurrency.code)
            LaunchedEffect(currencyCode) { com.expensemanager.app.util.AppCurrency.set(currencyCode) }
            
            var isUnlocked by remember { mutableStateOf(false) }
            // null = not yet determined, true = lock needed, false = no lock
            val shouldLockOnLaunch = remember { mutableStateOf<Boolean?>(null) }

            // Determine lock state ONCE at startup — not reactively.
            // This prevents the user from being locked out mid-session when
            // they enable app lock from settings.
            LaunchedEffect(Unit) {
                val needsLock = combine(
                    settingsRepository.isAppLockEnabledFlow(),
                    settingsRepository.getPinFlow()
                ) { lockEnabled, pin -> lockEnabled && !pin.isNullOrEmpty() }
                    .first()
                shouldLockOnLaunch.value = needsLock
            }

            ExpenseManagerTheme(themeMode = themeMode, themeStyle = themeStyle) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when {
                        // Still loading settings
                        isSwipeNavigationEnabled == null || shouldLockOnLaunch.value == null -> {
                            // Wait for settings to load
                        }
                        // App was locked at startup and hasn't been unlocked yet
                        shouldLockOnLaunch.value == true && !isUnlocked -> {
                            AppLockScreen(
                                correctPin = appPin,
                                isBiometricEnabled = isBiometricEnabled,
                                onUnlock = { isUnlocked = true }
                            )
                        }
                        // Normal app
                        else -> {
                            AppNavHost(
                                isSwipeNavigationEnabled = isSwipeNavigationEnabled!!,
                                openAddTransaction = openAddTransaction.value,
                                onAddTransactionHandled = { openAddTransaction.value = false }
                            )
                        }
                    }
                }
            }
        }
    }
}
