# Expense Manager Comprehensive Testing Checklist

This checklist covers both the core functionality of the app and all recently added or modified features. Follow this end-to-end to ensure the application is stable and fully functional.

## 1. App Launch & Security
- [ ] **Cold Start:** Launch the app from a cold state. Ensure it loads smoothly without crashing.
- [ ] **App Lock Setup:** Go to Settings -> App Lock. Enable PIN protection.
- [ ] **App Lock Verification:** Close the app completely and reopen it. Verify the PIN screen appears.
- [ ] **App Lock Timeout:** Minimize the app, wait for the timeout duration, and bring it back to the foreground. Verify it prompts for the PIN.
- [ ] **App Lock Disabling:** Go to Settings, disable App Lock, close and reopen the app. It should open directly.

## 2. Navigation & UI Aesthetics
- [ ] **Bottom Navigation Bar:** Check that the text in the navigation bar is inline (e.g., the 'Transactions' label does not break onto a new line).
- [ ] **Theme Switching:** Go to Settings -> Appearance. Toggle between Dark Mode, Light Mode, and System Default. Check that the theme applies immediately across all screens.

## 3. Adding Transactions (Core Flow)
- [ ] **Default Time:** Tap the "+" button. Verify the Time field automatically defaults to the current real-time.
- [ ] **Numpad Automatic Open:** Verify the custom numpad opens by default when creating a transaction.
- [ ] **Numpad Conflict Resolution:** Tap on a text field (like Note or Merchant). Verify the custom numpad hides when the system keyboard appears.
- [ ] **Numpad Backspace:** Hold down the backspace button on the custom numpad and ensure it deletes smoothly and continuously instead of one character at a time.
- [ ] **Categories (Alphabetical):** Open the category dropdown and verify categories are sorted alphabetically.
- [ ] **Create Custom Category:** Try creating a new category from the dropdown menu, assigning it a color, and saving it.
- [ ] **Bulk Add Mode:** Enable "Bulk Add". Save a transaction. Verify the app stays on the Add screen and the numpad automatically opens for the next entry.
- [ ] **Split Expense:** Toggle "Split Bill". Add a few participants, split evenly, and save. Verify the split is recorded.

## 4. Home Screen
- [ ] **Recent Transactions List:** Verify transactions show the correct category name, icon, and color.
- [ ] **Goal Transfers in Home:** If a transaction is a goal transfer, verify it says "Transfer to Goal: [Name]" with a "G" icon instead of "Uncategorized".
- [ ] **Uncategorized with Note:** Create a transaction with no category but add a Note. Verify the Note text becomes the main heading and the first letter of the Note is used as the icon.
- [ ] **Dashboard Metrics:** Check that "Today's Spend", "Total Balance", and month metrics are accurate.
- [ ] **Quick Add Shortcuts:** Tap a Quick Add chip (if configured) and verify a transaction is logged immediately.

## 5. Transactions Screen
- [ ] **No Swipe to Delete:** Try swiping left or right on a transaction. Verify that nothing happens (swipe-to-delete was removed).
- [ ] **Long Press Menu:** Long press any transaction. Verify the bottom sheet menu opens with options (Schedule, Delete).
- [ ] **Delete via Menu:** Select Delete from the long press menu. Verify the transaction is removed and balances update.
- [ ] **Schedule via Menu:** Select Schedule from the long press menu. Verify the Add Scheduled Dialog opens and is pre-filled with the transaction's details.

## 6. Planning Screen (Budgets)
- [ ] **Budget Creation:** Navigate to Planning -> Budgets. Add a new budget for a specific category.
- [ ] **Alphabetical Categories:** Verify categories in the budget creation dialog are alphabetical.
- [ ] **Immediate UI Update (Reactive):** 
    1. Note the current budget progress.
    2. Go to Add Transaction and log an expense for that category.
    3. Return to the Budgets screen. Verify the progress bar has updated *immediately* (without needing to restart the app).
- [ ] **Expand/Collapse Sections:** Verify the Budgets section card expands and collapses properly when tapped.

## 7. Planning Screen (Goals)
- [ ] **Goal Creation:** Navigate to Planning -> Goals. Add a new savings goal.
- [ ] **Expand/Collapse Sections:** Verify the Goals section card expands and collapses properly when tapped.
- [ ] **Add Funds to Goal:** Tap the goal and add funds. 
- [ ] **Goal Transfer Verification:** Go to the Transactions screen. Verify the transfer appears as an Expense transaction, deducts from your balance, and has the title "Transfer to Goal: [Goal Name]" (not "Uncategorized").

## 8. Planning Screen (Scheduled Transactions)
- [ ] **Add via Plus Button:** In the Planning screen under the Scheduled section, tap the "+" icon. Verify the dialog opens to create a new recurring rule.
- [ ] **Scheduled Worker Execution:** Set a scheduled transaction to trigger today (or wait for the trigger condition) and verify the app generates the transaction in the background automatically.

## 9. Lending & Borrowing Screen
- [ ] **Add Entry:** Add a new "Lent" entry and a new "Borrowed" entry.
- [ ] **Balances:** Verify the "You're Owed" and "You Owe" cards reflect the correct totals.
- [ ] **Settle:** Tap "Settle" on one of the items. Verify the status updates to Settled.
- [ ] **Long Press Menu (Edit/Delete):** Long press a lending/borrowing card. Verify the management menu pops up. Select Delete and confirm it gets removed.

## 10. Data Backup & Export (Settings)
- [ ] **Full Database Backup:** Go to Settings -> Backup & Export -> Backup Data. Verify a `.db` file is generated and can be saved to your device.
- [ ] **Database Restore:** Try restoring from a previously saved backup file. Verify the app reloads with the restored data.
- [ ] **CSV Export:** Select CSV Export. Verify the exported file is readable and contains all transactions.
- [ ] **PDF Export:** Select PDF Export. Open the generated file and verify the layout and transaction history are readable.

## 11. Reports Screen
- [ ] **Chart Rendering:** Open the Reports tab and verify the bar/pie charts render successfully without crashing.
- [ ] **Category Breakdown:** Check that the category breakdowns align with the transactions you entered during testing.

## 12. Edge Cases & Polish
- [ ] **Negative/Zero Inputs:** Try saving a transaction, budget, or goal with an amount of `0` or left blank. Verify appropriate error messages appear.
- [ ] **App State Retention:** Open the app, navigate deep into a screen (e.g., inside Add Transaction), minimize the app, and reopen it from recents. Verify it retains your current screen.
- [ ] **Large Numbers:** Enter a massively large number in the numpad (e.g. `9999999999999.99`). Verify the UI does not break or clip the text severely.
