import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConsultationTableViewComponent } from './components/business/consultation/consultation-table-view/consultation-table-view.component';
import { ExaminationTableViewComponent } from './components/business/examination/examination-table-view/examination-table-view.component';
import { ItemTableViewComponent } from './components/business/item/item-table-view/item-table-view.component';
import { MedicineTableViewComponent } from './components/business/medicine/medicine-table-view/medicine-table-view.component';
import { PharmacyTableViewComponent } from './components/business/pharmacy/pharmacy-table-view/pharmacy-table-view.component';
import { PrescriptionTableViewComponent } from './components/business/prescription/prescription-table-view/prescription-table-view.component';
import { PricelistTableViewComponent } from './components/business/pricelist/pricelist-table-view/pricelist-table-view.component';
import { PromotionTableViewComponent } from './components/business/promotion/promotion-table-view/promotion-table-view.component';
import { ReservationTableViewComponent } from './components/business/reservation/reservation-table-view/reservation-table-view.component';
import { WorkingHoursTableViewComponent } from './components/business/working-hours/working-hours-table-view/working-hours-table-view.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { ConfirmComponent } from './components/confirm/confirm.component';
import { DermatologistDashboardComponent } from './components/navigation/dermatologist-dashboard/dermatologist-dashboard.component';
import { PatientDashboardComponent } from './components/navigation/patient-dashboard/patient-dashboard.component';
import { PharmacistDashboardComponent } from './components/navigation/pharmacist-dashboard/pharmacist-dashboard.component';
import { PharmacyAdminDashboardComponent } from './components/navigation/pharmacy-admin-dashboard/pharmacy-admin-dashboard.component';
import { SupplierDashboardComponent } from './components/navigation/supplier-dashboard/supplier-dashboard.component';
import { SystemAdminDashboardComponent } from './components/navigation/system-admin-dashboard/system-admin-dashboard.component';
import { UnregisteredUserDashboardComponent } from './components/navigation/unregistered-user-dashboard/unregistered-user-dashboard.component';
import { RequestTokenComponent } from './components/request-token/request-token.component';
import { DermatologistTableViewComponent } from './components/user/dermatologist/dermatologist-table-view/dermatologist-table-view.component';
import { PatientTableViewComponent } from './components/user/patient/patient-table-view/patient-table-view.component';
import { PharmacistTableViewComponent } from './components/user/pharmacist/pharmacist-table-view/pharmacist-table-view.component';
import { PharmacyAdminTableViewComponent } from './components/user/pharmacy-admin/pharmacy-admin-table-view/pharmacy-admin-table-view.component';
import { SupplierTableViewComponent } from './components/user/supplier/supplier-table-view/supplier-table-view.component';
import { SystemAdminTableViewComponent } from './components/user/system-admin/system-admin-table-view/system-admin-table-view.component';


const routes: Routes = [
  {
    path: 'request-token',
    component: RequestTokenComponent
  },
  {
    path: 'confirm',
    component: ConfirmComponent
  },
  {
    path: 'change-password',
    component: ChangePasswordComponent
  },
  {
    path: 'konsultacija',
    component: ConsultationTableViewComponent
  },
  {
    path: 'pregled',
    component: ExaminationTableViewComponent
  },
  {
    path: 'stavka',
    component: ItemTableViewComponent
  },
  {
    path: 'lek',
    component: MedicineTableViewComponent
  },
  {
    path: 'apoteka',
    component: PharmacyTableViewComponent
  },
  {
    path: 'recept',
    component: PrescriptionTableViewComponent
  },
  {
    path: 'cenovnik',
    component: PricelistTableViewComponent
  },
  {
    path: 'promocija',
    component: PromotionTableViewComponent
  },
  {
    path: 'rezervacija',
    component: ReservationTableViewComponent
  },
  {
    path: 'radni-sati',
    component: WorkingHoursTableViewComponent
  },
  {
    path: 'dermatolog',
    component: DermatologistTableViewComponent
  },
  {
    path: 'pacijent',
    component: PatientTableViewComponent
  },
  {
    path: 'farmaceut',
    component: PharmacistTableViewComponent
  },
  {
    path: 'administrator-apoteke',
    component: PharmacyAdminTableViewComponent
  },
  {
    path: 'dobavljac',
    component: SupplierTableViewComponent
  },
  {
    path: 'administrator-sistema',
    component: SystemAdminTableViewComponent
  },
  {
    path: 'dashboard/dermatolog',
    component: DermatologistDashboardComponent
  },
  {
    path: 'dashboard/pacijent',
    component: PatientDashboardComponent
  },
  {
    path: 'dashboard/farmaceut',
    component: PharmacistDashboardComponent
  },
  {
    path: 'dashboard/administrator-apoteke',
    component: PharmacyAdminDashboardComponent
  },
  {
    path: 'dashboard/dobavljac',
    component: SupplierDashboardComponent
  },
  {
    path: 'dashboard/administrator-sistema',
    component: SystemAdminDashboardComponent
  },
  {
    path: 'dashboard/visitor',
    component: UnregisteredUserDashboardComponent
  },



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
