import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AudittrailComponent } from './pages/audittrail/audittrail.component';
import { ConfigurationComponent } from './pages/configuration/configuration.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { LoginComponent } from './pages/login/login.component';
import { MainComponent } from './pages/main/main.component';
import { PurchaseComponent } from './pages/purchase/purchase.component';
import { SalesComponent } from './pages/sales/sales.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { TransactionComponent } from './pages/transaction/transaction.component';
import { TxdetailsComponent } from './pages/txdetails/txdetails.component';
import { UseraccountComponent } from './pages/useraccount/useraccount.component';

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "", redirectTo: "main", pathMatch: 'full' },
  {
    path: "main", component: MainComponent, children: [
      { path: "", component: DashboardComponent },
      { path: "purchase", component: PurchaseComponent },
      { path: "purchase/:transactionid", component: TxdetailsComponent },
      { path: "transaction", component: TransactionComponent },
      { path: "sales", component: SalesComponent },
      {
        path: "settings", component: SettingsComponent, children: [
          { path: "user", component: UseraccountComponent },
          { path: "audittrail", component: AudittrailComponent },
          { path: "configuration", component: ConfigurationComponent },

        ]
      },
      { path: "product", component: InventoryComponent },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
