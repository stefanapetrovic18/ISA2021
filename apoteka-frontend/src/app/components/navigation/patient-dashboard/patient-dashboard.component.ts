import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ConsultationAddComponent } from '../../business/consultation/consultation-add/consultation-add.component';
import { ExaminationAddComponent } from '../../business/examination/examination-add/examination-add.component';

@Component({
  selector: 'app-patient-dashboard',
  templateUrl: './patient-dashboard.component.html',
  styleUrls: ['./patient-dashboard.component.css']
})
export class PatientDashboardComponent implements OnInit {

  constructor(private router: Router, private dialog: MatDialog) { }

  ngOnInit(): void {
  }
  navigate(route: string) {
    this.router.navigateByUrl(route);
  }
  open(component: string) {
    switch(component) {
      case 'edit':
        break;
      case 'konsultacija':
        this.dialog.open(ConsultationAddComponent);
        break;
      case 'pregled':
        this.dialog.open(ExaminationAddComponent);
        break;
    }
  }

}
