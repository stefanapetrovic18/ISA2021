import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-vacation-request-reject',
  templateUrl: './vacation-request-reject.component.html',
  styleUrls: ['./vacation-request-reject.component.scss']
})
export class VacationRequestRejectComponent implements OnInit {
  rejectionReason = '';
  constructor(public dialogRef: MatDialogRef<VacationRequestRejectComponent>) { }

  ngOnInit() {
  }
  close() {
    this.dialogRef.close();
  }

}
