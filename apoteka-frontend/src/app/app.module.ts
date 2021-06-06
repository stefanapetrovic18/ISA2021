import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatInputModule } from '@angular/material/input';
import { MatSortModule } from '@angular/material/sort';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatRadioModule } from '@angular/material/radio';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RequestTokenComponent } from './components/request-token/request-token.component';
import { ConfirmComponent } from './components/confirm/confirm.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { AuthInterceptor } from './auth/auth-interceptor';
import { ConsultationTableViewComponent } from './components/business/consultation/consultation-table-view/consultation-table-view.component';
import { ConsultationDeleteComponent } from './components/business/consultation/consultation-delete/consultation-delete.component';
import { ConsultationEditComponent } from './components/business/consultation/consultation-edit/consultation-edit.component';
import { ConsultationViewComponent } from './components/business/consultation/consultation-view/consultation-view.component';
import { ConsultationAddComponent } from './components/business/consultation/consultation-add/consultation-add.component';
import { ExaminationAddComponent } from './components/business/examination/examination-add/examination-add.component';
import { ExaminationDeleteComponent } from './components/business/examination/examination-delete/examination-delete.component';
import { ExaminationEditComponent } from './components/business/examination/examination-edit/examination-edit.component';
import { ExaminationTableViewComponent } from './components/business/examination/examination-table-view/examination-table-view.component';
import { ExaminationViewComponent } from './components/business/examination/examination-view/examination-view.component';
import { ItemAddComponent } from './components/business/item/item-add/item-add.component';
import { ItemDeleteComponent } from './components/business/item/item-delete/item-delete.component';
import { ItemEditComponent } from './components/business/item/item-edit/item-edit.component';
import { ItemTableViewComponent } from './components/business/item/item-table-view/item-table-view.component';
import { ItemViewComponent } from './components/business/item/item-view/item-view.component';
import { MedicineAddComponent } from './components/business/medicine/medicine-add/medicine-add.component';
import { MedicineDeleteComponent } from './components/business/medicine/medicine-delete/medicine-delete.component';
import { MedicineEditComponent } from './components/business/medicine/medicine-edit/medicine-edit.component';
import { MedicineTableViewComponent } from './components/business/medicine/medicine-table-view/medicine-table-view.component';
import { MedicineViewComponent } from './components/business/medicine/medicine-view/medicine-view.component';
import { PharmacyAddComponent } from './components/business/pharmacy/pharmacy-add/pharmacy-add.component';
import { PharmacyDeleteComponent } from './components/business/pharmacy/pharmacy-delete/pharmacy-delete.component';
import { PharmacyEditComponent } from './components/business/pharmacy/pharmacy-edit/pharmacy-edit.component';
import { PharmacyTableViewComponent } from './components/business/pharmacy/pharmacy-table-view/pharmacy-table-view.component';
import { PharmacyViewComponent } from './components/business/pharmacy/pharmacy-view/pharmacy-view.component';
import { PrescriptionAddComponent } from './components/business/prescription/prescription-add/prescription-add.component';
import { PrescriptionDeleteComponent } from './components/business/prescription/prescription-delete/prescription-delete.component';
import { PrescriptionEditComponent } from './components/business/prescription/prescription-edit/prescription-edit.component';
import { PrescriptionTableViewComponent } from './components/business/prescription/prescription-table-view/prescription-table-view.component';
import { PrescriptionViewComponent } from './components/business/prescription/prescription-view/prescription-view.component';
import { PricelistAddComponent } from './components/business/pricelist/pricelist-add/pricelist-add.component';
import { PricelistDeleteComponent } from './components/business/pricelist/pricelist-delete/pricelist-delete.component';
import { PricelistEditComponent } from './components/business/pricelist/pricelist-edit/pricelist-edit.component';
import { PricelistTableViewComponent } from './components/business/pricelist/pricelist-table-view/pricelist-table-view.component';
import { PricelistViewComponent } from './components/business/pricelist/pricelist-view/pricelist-view.component';
import { PromotionAddComponent } from './components/business/promotion/promotion-add/promotion-add.component';
import { PromotionDeleteComponent } from './components/business/promotion/promotion-delete/promotion-delete.component';
import { PromotionEditComponent } from './components/business/promotion/promotion-edit/promotion-edit.component';
import { PromotionTableViewComponent } from './components/business/promotion/promotion-table-view/promotion-table-view.component';
import { PromotionViewComponent } from './components/business/promotion/promotion-view/promotion-view.component';
import { ReservationAddComponent } from './components/business/reservation/reservation-add/reservation-add.component';
import { ReservationDeleteComponent } from './components/business/reservation/reservation-delete/reservation-delete.component';
import { ReservationEditComponent } from './components/business/reservation/reservation-edit/reservation-edit.component';
import { ReservationTableViewComponent } from './components/business/reservation/reservation-table-view/reservation-table-view.component';
import { ReservationViewComponent } from './components/business/reservation/reservation-view/reservation-view.component';
import { WorkingHoursAddComponent } from './components/business/working-hours/working-hours-add/working-hours-add.component';
import { WorkingHoursDeleteComponent } from './components/business/working-hours/working-hours-delete/working-hours-delete.component';
import { WorkingHoursEditComponent } from './components/business/working-hours/working-hours-edit/working-hours-edit.component';
import { WorkingHoursTableViewComponent } from './components/business/working-hours/working-hours-table-view/working-hours-table-view.component';
import { WorkingHoursViewComponent } from './components/business/working-hours/working-hours-view/working-hours-view.component';
import { PatientAddComponent } from './components/user/patient/patient-add/patient-add.component';
import { PatientDeleteComponent } from './components/user/patient/patient-delete/patient-delete.component';
import { PatientEditComponent } from './components/user/patient/patient-edit/patient-edit.component';
import { PatientTableViewComponent } from './components/user/patient/patient-table-view/patient-table-view.component';
import { PatientViewComponent } from './components/user/patient/patient-view/patient-view.component';
import { PharmacistAddComponent } from './components/user/pharmacist/pharmacist-add/pharmacist-add.component';
import { PharmacistDeleteComponent } from './components/user/pharmacist/pharmacist-delete/pharmacist-delete.component';
import { PharmacistEditComponent } from './components/user/pharmacist/pharmacist-edit/pharmacist-edit.component';
import { PharmacistTableViewComponent } from './components/user/pharmacist/pharmacist-table-view/pharmacist-table-view.component';
import { PharmacistViewComponent } from './components/user/pharmacist/pharmacist-view/pharmacist-view.component';
import { PharmacyAdminAddComponent } from './components/user/pharmacy-admin/pharmacy-admin-add/pharmacy-admin-add.component';
import { PharmacyAdminDeleteComponent } from './components/user/pharmacy-admin/pharmacy-admin-delete/pharmacy-admin-delete.component';
import { PharmacyAdminEditComponent } from './components/user/pharmacy-admin/pharmacy-admin-edit/pharmacy-admin-edit.component';
import { PharmacyAdminTableViewComponent } from './components/user/pharmacy-admin/pharmacy-admin-table-view/pharmacy-admin-table-view.component';
import { PharmacyAdminViewComponent } from './components/user/pharmacy-admin/pharmacy-admin-view/pharmacy-admin-view.component';
import { SupplierAddComponent } from './components/user/supplier/supplier-add/supplier-add.component';
import { SupplierDeleteComponent } from './components/user/supplier/supplier-delete/supplier-delete.component';
import { SupplierEditComponent } from './components/user/supplier/supplier-edit/supplier-edit.component';
import { SupplierTableViewComponent } from './components/user/supplier/supplier-table-view/supplier-table-view.component';
import { SupplierViewComponent } from './components/user/supplier/supplier-view/supplier-view.component';
import { SystemAdminAddComponent } from './components/user/system-admin/system-admin-add/system-admin-add.component';
import { SystemAdminDeleteComponent } from './components/user/system-admin/system-admin-delete/system-admin-delete.component';
import { SystemAdminEditComponent } from './components/user/system-admin/system-admin-edit/system-admin-edit.component';
import { SystemAdminTableViewComponent } from './components/user/system-admin/system-admin-table-view/system-admin-table-view.component';
import { SystemAdminViewComponent } from './components/user/system-admin/system-admin-view/system-admin-view.component';
import { DermatologistAddComponent } from './components/user/dermatologist/dermatologist-add/dermatologist-add.component';
import { DermatologistDeleteComponent } from './components/user/dermatologist/dermatologist-delete/dermatologist-delete.component';
import { DermatologistEditComponent } from './components/user/dermatologist/dermatologist-edit/dermatologist-edit.component';
import { DermatologistTableViewComponent } from './components/user/dermatologist/dermatologist-table-view/dermatologist-table-view.component';
import { DermatologistViewComponent } from './components/user/dermatologist/dermatologist-view/dermatologist-view.component';
import { PharmacistDashboardComponent } from './components/navigation/pharmacist-dashboard/pharmacist-dashboard.component';
import { PharmacyAdminDashboardComponent } from './components/navigation/pharmacy-admin-dashboard/pharmacy-admin-dashboard.component';
import { PatientDashboardComponent } from './components/navigation/patient-dashboard/patient-dashboard.component';
import { DermatologistDashboardComponent } from './components/navigation/dermatologist-dashboard/dermatologist-dashboard.component';
import { SystemAdminDashboardComponent } from './components/navigation/system-admin-dashboard/system-admin-dashboard.component';
import { SupplierDashboardComponent } from './components/navigation/supplier-dashboard/supplier-dashboard.component';
import { UnregisteredUserDashboardComponent } from './components/navigation/unregistered-user-dashboard/unregistered-user-dashboard.component';
import { OrderAddComponent } from './components/business/order/order-add/order-add.component';
import { OrderDeleteComponent } from './components/business/order/order-delete/order-delete.component';
import { OrderEditComponent } from './components/business/order/order-edit/order-edit.component';
import { OrderTableViewComponent } from './components/business/order/order-table-view/order-table-view.component';
import { OrderViewComponent } from './components/business/order/order-view/order-view.component';
import { OrderItemAddComponent } from './components/business/order-item/order-item-add/order-item-add.component';
import { OrderItemDeleteComponent } from './components/business/order-item/order-item-delete/order-item-delete.component';
import { OrderItemEditComponent } from './components/business/order-item/order-item-edit/order-item-edit.component';
import { OrderItemTableViewComponent } from './components/business/order-item/order-item-table-view/order-item-table-view.component';
import { OrderItemViewComponent } from './components/business/order-item/order-item-view/order-item-view.component';
import {InventoryDeleteComponent} from './components/business/inventory/inventory-delete/inventory-delete.component';
import {InventoryViewComponent} from './components/business/inventory/inventory-view/inventory-view.component';
import {InventoryAddComponent} from './components/business/inventory/inventory-add/inventory-add.component';
import {InventoryItemTableViewComponent} from './components/business/inventory-item/inventory-item-table-view/inventory-item-table-view.component';
import {InventoryItemViewComponent} from './components/business/inventory-item/inventory-item-view/inventory-item-view.component';
import {InventoryItemAddComponent} from './components/business/inventory-item/inventory-item-add/inventory-item-add.component';
import {InventoryItemDeleteComponent} from './components/business/inventory-item/inventory-item-delete/inventory-item-delete.component';
import {InventoryTableViewComponent} from './components/business/inventory/inventory-table-view/inventory-table-view.component';
import {InventoryEditComponent} from './components/business/inventory/inventory-edit/inventory-edit.component';
import {InventoryItemEditComponent} from './components/business/inventory-item/inventory-item-edit/inventory-item-edit.component';
import {OfferTableViewComponent} from './components/business/offer/offer-table-view/offer-table-view.component';
import {OfferEditComponent} from './components/business/offer/offer-edit/offer-edit.component';
import {OfferAddComponent} from './components/business/offer/offer-add/offer-add.component';
import {OfferDeleteComponent} from './components/business/offer/offer-delete/offer-delete.component';
import {OfferViewComponent} from './components/business/offer/offer-view/offer-view.component';
import {MatStepperModule} from '@angular/material/stepper';
import {MatChipsModule} from '@angular/material/chips';
import { VacationRequestAddComponent } from './components/business/vacation-request/vacation-request-add/vacation-request-add.component';
import { VacationRequestTableViewComponent } from './components/business/vacation-request/vacation-request-table-view/vacation-request-table-view.component';
import { BusinessReportComponent } from './components/business/pharmacy/business-report/business-report.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import { MatNativeDateModule } from '@angular/material/core';
import {MatGridListModule} from '@angular/material/grid-list';
import { ComplaintAnswerComponent } from './components/business/complaint/complaint-answer/complaint-answer.component';
import { ComplaintAddComponent } from './components/business/complaint/complaint-add/complaint-add.component';
import { ComplaintTableViewComponent } from './components/business/complaint/complaint-table-view/complaint-table-view.component';
import { PharmacyPageComponent } from './components/business/pharmacy/pharmacy-page/pharmacy-page.component';
import {MatSliderModule} from '@angular/material/slider';
import {MatMenuModule} from '@angular/material/menu';
import { RatingAddComponent } from './components/business/rating/rating-add/rating-add.component';
import { VacationRequestRejectComponent } from './components/business/vacation-request/vacation-request-reject/vacation-request-reject.component';
import { StockpileTableViewComponent } from './components/business/stockpile/stockpile-table-view/stockpile-table-view.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    RequestTokenComponent,
    ConfirmComponent,
    ChangePasswordComponent,

    ConsultationAddComponent,
    ConsultationDeleteComponent,
    ConsultationEditComponent,
    ConsultationTableViewComponent,
    ConsultationViewComponent,

    ExaminationAddComponent,
    ExaminationDeleteComponent,
    ExaminationEditComponent,
    ExaminationTableViewComponent,
    ExaminationViewComponent,

    ItemAddComponent,
    ItemDeleteComponent,
    ItemEditComponent,
    ItemTableViewComponent,
    ItemViewComponent,

    MedicineAddComponent,
    MedicineDeleteComponent,
    MedicineEditComponent,
    MedicineTableViewComponent,
    MedicineViewComponent,

    PharmacyAddComponent,
    PharmacyDeleteComponent,
    PharmacyEditComponent,
    PharmacyTableViewComponent,
    PharmacyViewComponent,

    PrescriptionAddComponent,
    PrescriptionDeleteComponent,
    PrescriptionEditComponent,
    PrescriptionTableViewComponent,
    PrescriptionViewComponent,

    PricelistAddComponent,
    PricelistDeleteComponent,
    PricelistEditComponent,
    PricelistTableViewComponent,
    PricelistViewComponent,

    PromotionAddComponent,
    PromotionDeleteComponent,
    PromotionEditComponent,
    PromotionTableViewComponent,
    PromotionViewComponent,

    ReservationAddComponent,
    ReservationDeleteComponent,
    ReservationEditComponent,
    ReservationTableViewComponent,
    ReservationViewComponent,

    WorkingHoursAddComponent,
    WorkingHoursDeleteComponent,
    WorkingHoursEditComponent,
    WorkingHoursTableViewComponent,
    WorkingHoursViewComponent,

    DermatologistAddComponent,
    DermatologistDeleteComponent,
    DermatologistEditComponent,
    DermatologistTableViewComponent,
    DermatologistViewComponent,

    PatientAddComponent,
    PatientDeleteComponent,
    PatientEditComponent,
    PatientTableViewComponent,
    PatientViewComponent,

    PharmacistAddComponent,
    PharmacistDeleteComponent,
    PharmacistEditComponent,
    PharmacistTableViewComponent,
    PharmacistViewComponent,

    PharmacyAdminAddComponent,
    PharmacyAdminDeleteComponent,
    PharmacyAdminEditComponent,
    PharmacyAdminTableViewComponent,
    PharmacyAdminViewComponent,
    BusinessReportComponent,

    SupplierAddComponent,
    SupplierDeleteComponent,
    SupplierEditComponent,
    SupplierTableViewComponent,
    SupplierViewComponent,

    SystemAdminAddComponent,
    SystemAdminDeleteComponent,
    SystemAdminEditComponent,
    SystemAdminTableViewComponent,
    SystemAdminViewComponent,
    PharmacistDashboardComponent,
    PharmacyAdminDashboardComponent,
    PatientDashboardComponent,
    DermatologistDashboardComponent,
    SystemAdminDashboardComponent,
    SupplierDashboardComponent,

    UnregisteredUserDashboardComponent,
    DermatologistDashboardComponent,
    PatientDashboardComponent,
    PharmacistDashboardComponent,
    PharmacyAdminDashboardComponent,
    SupplierDashboardComponent,
    SystemAdminDashboardComponent,
    OrderAddComponent,
    OrderDeleteComponent,
    OrderEditComponent,
    OrderTableViewComponent,
    OrderViewComponent,
    OrderItemAddComponent,
    OrderItemDeleteComponent,
    OrderItemEditComponent,
    OrderItemTableViewComponent,
    OrderItemViewComponent,
    InventoryAddComponent,
    InventoryDeleteComponent,
    InventoryEditComponent,
    InventoryTableViewComponent,
    InventoryViewComponent,
    InventoryItemAddComponent,
    InventoryItemDeleteComponent,
    InventoryItemEditComponent,
    InventoryItemTableViewComponent,
    InventoryItemViewComponent,
    OfferAddComponent,
    OfferDeleteComponent,
    OfferEditComponent,
    OfferTableViewComponent,
    OfferViewComponent,
    VacationRequestAddComponent,
    VacationRequestTableViewComponent,
    ComplaintAnswerComponent,
    ComplaintAddComponent,
    ComplaintTableViewComponent,
    PharmacyPageComponent,
    RatingAddComponent,
    VacationRequestRejectComponent,
    StockpileTableViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatFormFieldModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatToolbarModule,
    MatTabsModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    MatSelectModule,
    MatRadioModule,
    MatInputModule,
    FormsModule,
    MatTooltipModule,
    MatExpansionModule,
    MatCheckboxModule,
    MatButtonToggleModule,
    MatSidenavModule,
    MatCardModule,
    MatStepperModule,
    ReactiveFormsModule,
    MatChipsModule,
    MatDatepickerModule,
    MatProgressBarModule,
    MatNativeDateModule,
    MatGridListModule,
    MatSliderModule,
    MatMenuModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
