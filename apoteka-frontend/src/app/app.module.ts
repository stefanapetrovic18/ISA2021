import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
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
    MatSidenavModule
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
