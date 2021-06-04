import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Rating } from 'src/app/model/business/rating';
import { Patient } from 'src/app/model/user/patient';
import { RatingService } from 'src/app/service/business/rating.service';

@Component({
  selector: 'app-rating-add',
  templateUrl: './rating-add.component.html',
  styleUrls: ['./rating-add.component.scss']
})
export class RatingAddComponent implements OnInit {

  rating = 0;

  constructor(@Inject(MAT_DIALOG_DATA) private data: {type: string, id: number, patient: Patient},
              private dialogRef: MatDialogRef<RatingAddComponent>,
              private ratingService: RatingService) { }

  ngOnInit() {
  }

  rate() {
    let r = new Rating();
    r.patient = this.data.patient;
    r.rating = this.rating;
    switch(this.data.type) {
      case "pharmacy":
        this.ratingService.ratePharmacy(r, this.data.id).subscribe(
          data => {
            window.alert('Uspešno ocenjivanje!');
            location.reload();
          }, error => {
            window.alert('Neuspešno ocenjivanje!');
          }
        );
        break;
      case "medicine":
        this.ratingService.rateMedicine(r, this.data.id).subscribe(
          data => {
            window.alert('Uspešno ocenjivanje!');
            location.reload();
          }, error => {
            window.alert('Neuspešno ocenjivanje!');
          }
        );
        break;
      case "pharmacist":
        this.ratingService.ratePharmacist(r, this.data.id).subscribe(
          data => {
            window.alert('Uspešno ocenjivanje!');
            location.reload();
          }, error => {
            window.alert('Neuspešno ocenjivanje!');
          }
        );
        break;
      case "dermatologist":
        this.ratingService.rateDermatologist(r, this.data.id).subscribe(
          data => {
            window.alert('Uspešno ocenjivanje!');
            location.reload();
          }, error => {
            window.alert('Neuspešno ocenjivanje!');
          }
        );
        break;
    }
    this.dialogRef.close();
  }

  close() {
    this.dialogRef.close();
  }

}
