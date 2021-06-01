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
import {ConsultationAddComponent} from './components/business/consultation/consultation-add/consultation-add.component';
import {OrderTableViewComponent} from './components/business/order/order-table-view/order-table-view.component';
import {VacationRequestTableViewComponent} from './components/business/vacation-request/vacation-request-table-view/vacation-request-table-view.component';
import { OfferTableViewComponent } from './components/business/offer/offer-table-view/offer-table-view.component';
import { RoleGuardService } from './auth/role-guard.service';


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
    component: ChangePasswordComponent,
    canActivate: [RoleGuardService]
  },
  {
    path: 'konsultacija',
    component: ConsultationTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_PHARMACY_ADMIN', 'ROLE_PATIENT', 'ROLE_PHARMACIST']
    }
  },
  {
    path: 'pregled',
    component: ExaminationTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_PHARMACY_ADMIN', 'ROLE_PATIENT', 'ROLE_DERMATOLOGIST']
    }
  },
  {
    path: 'stavka',
    component: ItemTableViewComponent,
    canActivate: [RoleGuardService]
  },
  {
    path: 'lek',
    component: MedicineTableViewComponent
  },
  {
    path: 'ponuda',
    component: OfferTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_SUPPLIER', 'ROLE_PHARMACIST']
    }
  },
  {
    path: 'apoteka',
    component: PharmacyTableViewComponent
  },
  {
    path: 'recept',
    component: PrescriptionTableViewComponent,
    canActivate: [RoleGuardService]
  },
  {
    path: 'cenovnik',
    component: PricelistTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRole: 'ROLE_PHARMACY_ADMIN'
    }
  },
  {
    path: 'promocija',
    component: PromotionTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_PHARMACY_ADMIN', 'ROLE_PATIENT']
    }
  },
  {
    path: 'rezervacija',
    component: ReservationTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_PHARMACY_ADMIN', 'ROLE_PATIENT']
    }
  },
  {
    path: 'radni-sati',
    component: WorkingHoursTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_PHARMACY_ADMIN', 'ROLE_PHARMACIST', 'ROLE_DERMATOLOGIST']
    }
  },
  {
    path: 'dermatolog',
    component: DermatologistTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_PHARMACY_ADMIN', 'ROLE_PATIENT', 'ROLE_DERMATOLOGIST']
    }
  },
  {
    path: 'pacijent',
    component: PatientTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_PHARMACY_ADMIN', 'ROLE_PATIENT']
    }
  },
  {
    path: 'farmaceut',
    component: PharmacistTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_PHARMACY_ADMIN', 'ROLE_PHARMACIST', 'ROLE_PHARMACIST']
    }
  },
  {
    path: 'administrator-apoteke',
    component: PharmacyAdminTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_PHARMACY_ADMIN']
    }
  },
  {
    path: 'dobavljac',
    component: SupplierTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_PHARMACY_ADMIN', 'ROLE_SUPPLIER']
    }
  },
  {
    path: 'administrator-sistema',
    component: SystemAdminTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRole: 'ROLE_SYSTEM_ADMIN'
    }
  },
  {
    path: 'dashboard/dermatolog',
    component: DermatologistDashboardComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRole: 'ROLE_DERMATOLOGIST'
    }
  },
  {
    path: 'dashboard/pacijent',
    component: PatientDashboardComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRole: 'ROLE_PATIENT'
    }
  },
  {
    path: 'dashboard/farmaceut',
    component: PharmacistDashboardComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRole: 'ROLE_PHARMACIST'
    }
  },
  {
    path: 'dashboard/administrator-apoteke',
    component: PharmacyAdminDashboardComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRole: 'ROLE_PHARMACY_ADMIN'
    }
  },
  {
    path: 'dashboard/dobavljac',
    component: SupplierDashboardComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRole: 'ROLE_SUPPLIER'
    }
  },
  {
    path: 'dashboard/administrator-sistema',
    component: SystemAdminDashboardComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRole: 'ROLE_SYSTEM_ADMIN'
    }
  },
  {
    path: 'dashboard/visitor',
    component: UnregisteredUserDashboardComponent
  },
  {
    path: 'konsultacija/zakazivanje',
    component: ConsultationAddComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRole: 'ROLE_PATIENT'
    }
  },
  {
    path: 'narudzbina',
    component: OrderTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_PHARMACY_ADMIN', 'ROLE_SUPPLIER']
    }
  },
  {
    path: 'odsustvo',
    component: VacationRequestTableViewComponent,
    canActivate: [RoleGuardService],
    data: {
      expectedRoles: ['ROLE_SYSTEM_ADMIN', 'ROLE_PHARMACY_ADMIN', 'ROLE_PHARMACIST', 'ROLE_DERMATOLOGIST']
    }
  }



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
