import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-request-token',
  templateUrl: './request-token.component.html',
  styleUrls: ['./request-token.component.css']
})
export class RequestTokenComponent implements OnInit {
  email = '';
  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit() {

  }
  requestToken() {
    this.auth.requestToken(this.email).subscribe(
      data => {
        window.alert(data);
      }, error => {
        window.alert(error);
      }
    );
  }
  cancel() {
    this.router.navigate(['/']);
  }
}
