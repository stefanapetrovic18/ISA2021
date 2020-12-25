import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.css']
})
export class ConfirmComponent implements OnInit {
  confirmed = false;
  constructor(private auth: AuthService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    let token = this.route.snapshot.queryParamMap.get('token');
    this.auth.confirm(token).subscribe(
      data => {
        window.alert(data);
        this.router.navigate(['']);
      }, error => {
        window.alert(error);
        this.router.navigate(['']);
      }
    )
  }
}
