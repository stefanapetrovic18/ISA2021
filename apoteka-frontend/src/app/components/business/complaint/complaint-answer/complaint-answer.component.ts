import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ComplaintResponse } from 'src/app/dto/complaint-response';
import { Complaint } from 'src/app/model/business/complaint';
import { ComplaintService } from 'src/app/service/business/complaint.service';

@Component({
  selector: 'app-complaint-answer',
  templateUrl: './complaint-answer.component.html',
  styleUrls: ['./complaint-answer.component.scss']
})
export class ComplaintAnswerComponent implements OnInit {
  data = new ComplaintResponse();
  constructor(@Inject(MAT_DIALOG_DATA) private id: any, private complaintService: ComplaintService, private dialogRef: MatDialogRef<ComplaintAnswerComponent>) { }

  ngOnInit() {
  }
  answer() {
    this.data.complaintID = this.id;
    this.complaintService.answer(this.data).subscribe(
      data => {
        window.alert('Odgovor je poslat!');
      }, error => {
        window.alert('Odgovor nije poslat!');
      }
    );
  }
  close() {
    this.dialogRef.close();
  }

}
