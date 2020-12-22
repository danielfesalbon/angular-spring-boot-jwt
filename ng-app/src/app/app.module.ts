import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { MainComponent } from './pages/main/main.component';


import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { SidebarModule } from 'primeng/sidebar';
import { TooltipModule } from 'primeng/tooltip';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { PanelMenuModule } from 'primeng/panelmenu';
import { ToastModule } from 'primeng/toast';
import { BlockUIModule } from 'primeng/blockui';
import { FocusTrapModule } from 'primeng/focustrap';
import { AccordionModule } from 'primeng/accordion';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { ToolbarModule } from 'primeng/toolbar';
import { InplaceModule } from 'primeng/inplace';
import { PanelModule } from 'primeng/panel';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { CalendarModule } from 'primeng/calendar';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { CardModule } from 'primeng/card';
import { OrderListModule } from 'primeng/orderlist';
import { ListboxModule } from 'primeng/listbox';
import { FieldsetModule } from 'primeng/fieldset';
import { ChartModule } from 'primeng/chart';
import { CarouselModule } from 'primeng/carousel';
import { KeyFilterModule } from 'primeng/keyfilter';
import { DataViewModule } from 'primeng/dataview';
import { FileUploadModule, FileUpload } from 'primeng/fileupload';
import { InputSwitchModule } from 'primeng/inputswitch';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { TabViewModule } from 'primeng/tabview';
import { RippleModule } from 'primeng/ripple';
import { CheckboxModule } from 'primeng/checkbox';
import { RadioButtonModule } from 'primeng/radiobutton';
import { SalesComponent } from './pages/sales/sales.component';
import { TransactionComponent } from './pages/transaction/transaction.component';
import { PurchaseComponent } from './pages/purchase/purchase.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { InventoryComponent } from './pages/inventory/inventory.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { RatingModule } from 'primeng/rating';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainComponent,
    SalesComponent,
    TransactionComponent,
    PurchaseComponent,
    SettingsComponent,
    InventoryComponent,
    DashboardComponent
  ],
  imports: [
    RippleModule,
    TabViewModule,
    KeyFilterModule,
    DataViewModule,
    ChartModule,
    FieldsetModule,
    ListboxModule,
    OrderListModule,
    CarouselModule,
    InputSwitchModule,
    CardModule,
    OverlayPanelModule,
    ReactiveFormsModule,
    CalendarModule,
    CheckboxModule,
    InputTextareaModule,
    DropdownModule,
    MessageModule,
    MessagesModule,
    PanelModule,
    InplaceModule,
    ToolbarModule,
    RadioButtonModule,
    MenubarModule,
    ScrollPanelModule,
    AccordionModule, 
    RatingModule,
    FocusTrapModule,
    BlockUIModule,
    ToastModule,
    FormsModule,
    ConfirmDialogModule,
    FileUploadModule,
    DialogModule,
    TooltipModule,
    PanelMenuModule,
    SidebarModule,
    ButtonModule,
    InputTextModule,
    PasswordModule,
    BrowserModule,
    RouterModule,
    AppRoutingModule,
    TableModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [ConfirmationService, MessageService, FileUpload],
  bootstrap: [AppComponent]
})
export class AppModule { }
