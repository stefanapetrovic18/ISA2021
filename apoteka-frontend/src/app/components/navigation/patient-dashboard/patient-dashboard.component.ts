import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { Patient } from 'src/app/model/user/patient';
import { PatientService } from 'src/app/service/user/patient.service';
import { ComplaintAddComponent } from '../../business/complaint/complaint-add/complaint-add.component';
import { ConsultationAddComponent } from '../../business/consultation/consultation-add/consultation-add.component';
import { ExaminationAddComponent } from '../../business/examination/examination-add/examination-add.component';
import { PatientEditComponent } from '../../user/patient/patient-edit/patient-edit.component';

@Component({
  selector: 'app-patient-dashboard',
  templateUrl: './patient-dashboard.component.html',
  styleUrls: ['./patient-dashboard.component.css']
})
export class PatientDashboardComponent implements OnInit {
  patient: Patient;

  constructor(private router: Router, private dialog: MatDialog, private tokenStorageService: TokenStorageService,
              private patientService: PatientService) { }

  ngOnInit(): void {
    this.patientService.findByUsername(this.tokenStorageService.getUsername()).subscribe(
      data => {
        this.patient = data;
        console.log(data);
      }, error => {
        console.log(error);
        window.alert('Korisnik nije pronađen.');
      }
    )
  }
  navigate(route: string) {
    this.router.navigateByUrl(route);
  }
  open(component: string) {
    switch(component) {
      case 'konsultacija':
        this.dialog.open(ConsultationAddComponent);
        break;
      case 'edit':
        this.dialog.open(PatientEditComponent, { data: this.patient }).afterClosed().subscribe(a => location.reload());
        break;
      case 'complaint':
        this.dialog.open(ComplaintAddComponent);
        break;
    }
  }

}
