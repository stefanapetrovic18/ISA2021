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
import { RequestTokenComponent } from './components/request-token/request-token.component';


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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
